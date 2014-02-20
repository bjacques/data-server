package com.brad.dataserver.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class StoreInMemory implements Store {
	
	private static final Logger logger  = Logger.getLogger(StoreInMemory.class);
	
	private List<String> store = new ArrayList<String>();

	@Override
	public void store(String value) {
		logger.info("Add: " + value);
		store.add(value);
	}

	@Override
	public String readLastValue() {
		String value = store.size() > 0 ? store.get(store.size()-1) : null;
		logger.info("Read: " + value);
		return value;
	}
	
	@Override
	public String toString() {
		return store.toString();
	}
}
