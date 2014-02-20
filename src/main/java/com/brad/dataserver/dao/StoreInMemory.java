package com.brad.dataserver.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class StoreInMemory implements Store {
	
	private static final Logger logger  = Logger.getLogger(StoreInMemory.class);
	
	private List<String> store = new ArrayList<String>();

	@Override
	public void store(String value) {
		logger.debug("Add: " + value);
		if (value != null 
				&& !store.contains(value)) {
			store.add(value);
		}
	}

	@Override
	public String readLastValue() {
		String value = store.size() > 0 ? store.get(store.size()-1) : null;
		logger.debug("Read: " + value);
		return value;
	}
	
	public int size() {
		return store.size();
	}
	
	@Override
	public String toString() {
		return store.toString();
	}
}
