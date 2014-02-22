package com.brad.dataserver.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.brad.dataserver.consumer.Consumer;

/**
 * Creates a server socket to listen for client connections
 * Each request spawns a new thread to handle the request. Should the server
 * be heavily utilised it will potentially run out of resources and grind
 * to a halt.
 *
 */
public class SocketListener {
	
	private static final Logger logger = Logger.getLogger(SocketListener.class);

	public static final String RESPONSE_OK = "OK";

	private ServerSocket serverSocket;
	private boolean isActive = true;
	private Consumer consumer;
	private int port;
	
	public SocketListener(int port) {
		this.port = port;
	}
	
	public void start() {
//		final ExecutorService single = Executors.newSingleThreadExecutor();
		try {
			serverSocket = new ServerSocket(port);
			
			while(isActive) {
				
				Socket connection = null;
				
				try {
					logger.info(String.format("Waiting for client to connect on port %d ...", port));
					
					final Socket conn = serverSocket.accept();
					
					logger.debug("Client connected");
					
					new Thread(new Runnable() {
						@Override
			            public void run() {
			                try {
			                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			                	String value = br.readLine();
			                	logger.debug(String.format("Rcv [%s]: %s", Thread.currentThread().getId(), value));
			                	
			                	consumer.consume(value);
			                	
			                	PrintWriter pw = new PrintWriter(conn.getOutputStream(), true);
			                	String response = RESPONSE_OK;
			                	pw.println(response);
			                	logger.debug(String.format("Rsp [%s]: %s ", Thread.currentThread().getId(), response));
			                }
			                catch (IOException e) {
			                    logger.error("Unable to process client request");
			                    e.printStackTrace();
			                }
			                finally {
			                	logger.info("Close client connection");
			                	closeSocket(conn);
			                }
			            }
					}).start();
				}
				catch (SocketException se) {
					logger.error(se.getMessage());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (IOException e) {
			logger.error("Failed to create ServerSocket");
			e.printStackTrace();
		}
		finally {
			closeServerSocket(serverSocket);
		}
	}

	private void closeSocket(Socket connection) {
		try {
			if (connection != null) connection.close();
		}
		catch(Exception e) {
			logger.error("Failed to close connection"); 
		}
	}

	private void closeServerSocket(ServerSocket serverSocket2) {
		try {
			if (serverSocket != null) serverSocket.close();
		}
		catch(Exception e) {
			logger.error("Failed to close ServerSocket");
		}
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public void stop() {
		isActive = false;
		closeServerSocket(serverSocket);
	}
}
