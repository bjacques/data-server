package com.brad.dataserver.integration;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketSender {

	private final Socket socket;
	private final PrintWriter pw;

	public ClientSocketSender(String host, Integer port) throws Exception {
		socket = new Socket(host, port);
		pw = new PrintWriter(socket.getOutputStream(), true);
	}

	public void send(String value) {
		pw.println(value);
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
}
