package com.brad.dataserver.integration;

import static com.brad.dataserver.socket.SocketListener.RESPONSE_OK;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.brad.dataserver.dao.DelayedStore;
import com.brad.dataserver.dao.Store;
import com.brad.dataserver.dao.StoreInMemory;
import com.brad.dataserver.socket.SocketListener;

/**
 * The delayedStore will process requests slowly allowing the testing
 * of multiple concurrent client connections
 *
 */
public class MulitpleClientsIntegrationTest {

	private static final String HOST = "localhost";
	private static final Integer PORT = 4445;
	private static final int delayInStoringData = 2000;
	private static final int requestTimeout = delayInStoringData + 500;
	
	private static final ExecutorService pool = Executors.newFixedThreadPool(2);

	private ServerFactory serverBootstrap = new ServerFactory(PORT);
	private SocketListener socketListener;
	private ClientSocketSender clientA, clientB;
	private Store delayedStore;
	
	@Before
	public void setUp() throws Exception {
		delayedStore = new DelayedStore(new StoreInMemory(), delayInStoringData);
		socketListener = serverBootstrap.startServerOnSeparateThread(delayedStore);
		clientA = new ClientSocketSender(HOST, PORT);
		clientB = new ClientSocketSender(HOST, PORT);
	}
	
	@After
	public void tearDown() throws Exception {
		socketListener.stop();
	}

	@Test
	public void receiveConcurrentClientRequestsAndRespondOkToBoth() throws Exception {
		String inputA = "A";
		String inputB = "B";
		
		Future<String> futureA = sendRequestOnNewThread(clientA, inputA);
		Future<String> futureB = sendRequestOnNewThread(clientB, inputB);
		
		assertThat(futureA.get(requestTimeout, MILLISECONDS), 
				is(RESPONSE_OK));
		assertThat(futureB.get(requestTimeout, MILLISECONDS), 
				is(RESPONSE_OK));
	}

	private Future<String> sendRequestOnNewThread(final ClientSocketSender client, final String input) throws InterruptedException, ExecutionException, TimeoutException {

		Future<String> future = pool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return client.send(input);
			}
		});
		return future;
	}
} 
