package com.brad.dataserver.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketSender {

	private final Socket socket;
	private final PrintWriter pw;
	private final BufferedReader br;

	public ClientSocketSender(String host, Integer port) throws Exception {
		socket = new Socket(host, port);
		pw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public String send(String value) {
		pw.println(value);
		
		String response = null;
		try {
			 response = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
}
