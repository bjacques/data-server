package com.brad.dataserver.socket;

import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.brad.dataserver.consumer.Consumer;
import com.brad.dataserver.socket.SocketListener;

@RunWith(MockitoJUnitRunner.class)
public class SocketListenerTest {

	private SocketListener socketListener;
	
	@Mock private Consumer consumer;
	
	private static final String HOST = "localhost";
	private static final int PORT = 4444;
	
	@Before
	public void setUp() {
		socketListener = new SocketListener(PORT);
		socketListener.setConsumer(consumer);
	}
	
	@Test
	public void sendsInputToConsumerAfterLineBreak() throws Exception {
		String value = "Rich? Yeah! Can you dig it?";
		getClientSocketWriter().println(value);
		Thread.sleep(1000);
		verify(consumer).consume(value);
	}
	
	private PrintWriter getClientSocketWriter() throws Exception {		
		Socket socket = new Socket(HOST, PORT);
		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		return pw;
	}

}
