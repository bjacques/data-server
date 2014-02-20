package com.brad.dataserver.integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.brad.dataserver.consumer.ConsumerImpl;
import com.brad.dataserver.dao.Store;
import com.brad.dataserver.dao.StoreInMemory;
import com.brad.dataserver.socket.SocketListener;

public class ClientWithMemoryStoreIntegrationTest {

	private static final String HOST = "localhost";
	private static final Integer PORT = 4445;
	
	private SocketListener socketListener;
	private ClientSocketSender client;
	private Store store;
	
	@Before
	public void setUp() throws Exception {
		store = new StoreInMemory();
		startServerOnSeparateThread(PORT);
		client = new ClientSocketSender(HOST, PORT);
	}
	
	@After
	public void tearDown() throws Exception {
		socketListener.stop();
	}

	@Test
	public void receiveRequestAndRespondOk() throws Exception {
		String input = "my bad pants smell";
		String response = client.send(input);
		assertThat(store.readLastValue(), is(input));
		assertThat(response, is(SocketListener.RESPONSE_OK));
	}
	
	@Test
	public void receiveNullRequestAndRespondOk() throws Exception {
		String response = client.send(null);
		assertThat(response, is(SocketListener.RESPONSE_OK));
	}

	/**
	 * test will block on serverSocket.accept() unless we start server on a separate thread
	 * 
	 * @param port
	 * @throws InterruptedException
	 */
	private void startServerOnSeparateThread(Integer port) throws InterruptedException {
		socketListener = new SocketListener(port);
		ConsumerImpl consumer = new ConsumerImpl();
		consumer.setStore(store);
		socketListener.setConsumer(consumer);
		new Thread(new ServerThread(socketListener)).start();
		Thread.sleep(500);
	}
	
	private class ServerThread implements Runnable {
		
		private SocketListener socketListener;

		public ServerThread(SocketListener socketListener) {
			this.socketListener = socketListener;
		}
		
		@Override
		public void run() {
			socketListener.start();
		}
	}
}
