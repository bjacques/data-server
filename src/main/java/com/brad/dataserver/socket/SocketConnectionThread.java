package com.brad.dataserver.socket;

import static com.brad.dataserver.socket.SocketListener.RESPONSE_OK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.brad.dataserver.consumer.Consumer;

public class SocketConnectionThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(SocketConnectionThread.class);

	private final Socket connection;
	private final Consumer consumer;

	public SocketConnectionThread(Socket connection, Consumer consumer) {
		this.connection = connection;
		this.consumer = consumer;
	}
	
	@Override
	public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        	String value = br.readLine();
        	logger.info(String.format("Rx: %s", value));
        	
        	consumer.consume(value);
        	
        	PrintWriter pw = new PrintWriter(connection.getOutputStream());
        	pw.println(RESPONSE_OK);
        }
        catch (IOException e) {
            logger.error("Unable to process client request");
            e.printStackTrace();
        }
        finally {
        	logger.info("Cleaning up socket listener");
        	closeSocket(connection);
        }
    }
}
