package com.brad.dataserver.dao;


public interface Store {

	/**
	 * Persists the value to the store. 
	 * Does not allow duplicates or null values, but does not throw an exception in such cases.
	 * @param value
	 */
	void store(String value);

	/**
	 * @return the most recent value added to the store
	 */
	String readLastValue();

	/**
	 * @return the number of values persisted to the store
	 */
	int size();
}
