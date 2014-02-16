package com.brad.dataserver.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.brad.dataserver.consumer.Consumer;

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
					connection = serverSocket.accept();
					
					final Socket conn = connection;
					
					logger.debug("Client connected");
					
					new Thread(new Runnable() {
						@Override
			            public void run() {
			                try {
			                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			                	String value = br.readLine();
			                	logger.info(String.format("Rx: %s", value));
			                	
			                	consumer.consume(value);
			                	
			                	PrintWriter pw = new PrintWriter(conn.getOutputStream());
			                	pw.println(RESPONSE_OK);
			                }
			                catch (IOException e) {
			                    logger.error("Unable to process client request");
			                    e.printStackTrace();
			                }
			                finally {
			                	logger.info("Cleaning up socket listener");
			                	closeSocket(conn);
			                }
			            }
					}).start();
				}
				catch (IOException e) {
					logger.error("Failed to accept connection");
					e.printStackTrace();
				}
				finally {
					closeSocket(connection);
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

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
