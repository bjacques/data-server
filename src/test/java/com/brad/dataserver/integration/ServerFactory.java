package com.brad.dataserver.integration;

import com.brad.dataserver.consumer.ConsumerImpl;
import com.brad.dataserver.dao.Store;
import com.brad.dataserver.socket.SocketListener;

public class ServerFactory {

	private SocketListener socketListener;
	private final Integer port;
	
	public ServerFactory(Integer port) {
		this.port = port;
	}
	
	/**
	 * test will block on serverSocket.accept() unless we start server on a separate thread
	 * 
	 * @param port
	 * @throws InterruptedException
	 */
	public SocketListener startServerOnSeparateThread(Store store) throws InterruptedException {
		socketListener = new SocketListener(port);
		ConsumerImpl consumer = new ConsumerImpl();
		consumer.setStore(store);
		socketListener.setConsumer(consumer);
		new Thread(new ServerThread(socketListener)).start();
		Thread.sleep(500);
		return socketListener;
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