package com.brad.dataserver.consumer;

import org.apache.log4j.Logger;

import com.brad.dataserver.dao.Store;

public class ConsumerImpl implements Consumer {
	
	private static final Logger logger = Logger.getLogger(ConsumerImpl.class);

	private Store store;
	
	@Override
	public void consume(String value) {
		logger.info("Consume: " + value);
		store.store(value);
	}

	public void setStore(Store store) {
		this.store = store;
	}

}
