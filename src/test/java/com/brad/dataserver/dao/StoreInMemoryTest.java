package com.brad.dataserver.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.brad.dataserver.dao.Store;
import com.brad.dataserver.dao.StoreInMemory;


public class StoreInMemoryTest {
	
	private Store memoryStore;

	@Before
	public void setUp() throws IOException {
		memoryStore = new StoreInMemory();
	}
	

	@Test
	public void valueStoredCanBeRetrieved() throws Exception {
		String lastInputValue = "last line";
		memoryStore.store("first line");
		memoryStore.store(lastInputValue);
		String lastStoredValue = memoryStore.readLastValue();
		assertThat(lastStoredValue, is(lastInputValue));
	}
}
