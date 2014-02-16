package com.brad.dataserver.control;

import java.io.IOException;

import com.brad.dataserver.consumer.ConsumerImpl;
import com.brad.dataserver.dao.StoreFile;
import com.brad.dataserver.socket.SocketListener;

public class Server {

	public static void main(String[] args) throws IOException, InterruptedException {
		SocketListener sl = new SocketListener(4444);
		ConsumerImpl consumer = new ConsumerImpl();
//		consumer.setStore(new StoreInMemory());
		consumer.setStore(new StoreFile("g:\\temp"));
		sl.setConsumer(consumer);
		sl.start();
		
//		Thread.sleep(3000);		
//		sl.shutdown();
//		System.out.println("Shutdown server");
	}
}
