package com.brad.dataserver.dao;


public interface Store {

	void store(String value);

	String readLastValue();

	int size();
}
