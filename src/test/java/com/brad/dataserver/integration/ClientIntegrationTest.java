package com.brad.dataserver.integration;



import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.PrintWriter;
import java.net.Socket;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.brad.dataserver.control.Client;
import com.brad.dataserver.control.Server;
import com.brad.dataserver.socket.SocketListener;

public class ClientIntegrationTest {

	private Server server;
	private Client client;
	
	@Before
	public void setUp() throws InterruptedException {
		startServer();
		
	}
	
	private void startServer() throws InterruptedException {
		server = new Server();
		Thread.sleep(1000);
	}

	@Test
	public void storeValueAndRespondOk() throws Exception {
		String response = client.request("my bad pants smell");
		assertThat(response, is(SocketListener.RESPONSE_OK));
	}
	
	private PrintWriter getClientSocketWriter() throws Exception {
		Socket socket = new Socket(HOST, PORT);
		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		return pw;
	}
}
