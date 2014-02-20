package com.brad.dataserver.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.hamcrest.Matchers;
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
	
	@Test
	public void nullValueIsNotStored() {
		int initialSize = memoryStore.size();
		memoryStore.store(null);
		assertThat(memoryStore.size(),is(initialSize));
	}
	
	@Test
	public void duplicatesAreDiscardedAndSizeRemainsTheSame() {
		int initialSize = memoryStore.size();
		String A = "A";
		memoryStore.store(A);
		assertThat(memoryStore.size(),is(initialSize+1));
		memoryStore.store(A);
		assertThat(memoryStore.size(),is(initialSize+1));
	}
	
	@Test
	public void sizeIncreasesWhenDifferentValuesAdded() {
		int initialSize = memoryStore.size();
		memoryStore.store("A");
		assertThat(memoryStore.size(),is(initialSize+1));
		memoryStore.store("B");
		assertThat(memoryStore.size(),is(initialSize+2));
	}
}
