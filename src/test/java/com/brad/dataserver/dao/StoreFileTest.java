package com.brad.dataserver.dao;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.brad.dataserver.dao.StoreFile;


public class StoreFileTest {
	
	private static final String DIR = "g:\\temp\\dataloader";
	
	private StoreFile fileStore;

	@Before
	public void setUp() throws IOException {
		fileStore = new StoreFile(DIR);
	}
	
	@Test
	public void constructorCreatesNewFile() throws Exception {
		File f = fileStore.getFile();
		assertThat(f, notNullValue());
		assertThat(f.canRead(), is(true));
	}

	@Test(expected=IOException.class)
	public void constructorThrowsIOExceptionWhenInvalidDirectory() throws Exception {
		fileStore = new StoreFile("g:\\temp\\some-invalid-directory");
	}
	
	@Test
	public void valueStoredInFileCanBeRetrieved() throws Exception {
		String lastInputValue = "last line";
		fileStore.store("first line");
		fileStore.store(lastInputValue);
		String lastStoredValue = fileStore.readLastValue();
		assertThat(lastStoredValue, is(lastInputValue));
	}
}
