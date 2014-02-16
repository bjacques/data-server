package com.brad.dataserver.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.brad.dataserver.consumer.ConsumerImpl;
import com.brad.dataserver.dao.Store;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerImplTest {
	
	@InjectMocks private ConsumerImpl consumer;
	
	@Mock private Store store;
	
	@Test
	public void consumesValueAndStores() throws Exception {
		String value = "yoohoo";
		consumer.consume(value);
		Mockito.verify(store).store(value);
	}
}
