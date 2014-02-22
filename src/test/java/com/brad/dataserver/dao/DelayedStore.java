package com.brad.dataserver.dao;

/**
 * Test only class that simulates slow store operations so we
 * can test concurrent client requests
 *
 */
public class DelayedStore implements Store {

	private Store store;
	private int pauseMilliSeconds = 1000;
	
	public DelayedStore(Store store, int pauseMilliSeconds) {
		this.store = store;
		if (pauseMilliSeconds > 0)
			this.pauseMilliSeconds = pauseMilliSeconds;
	}
	
	@Override
	public void store(String value) {
		delay();
		store.store(value);
	}

	@Override
	public String readLastValue() {
		delay();
		return store.readLastValue();
	}

	@Override
	public int size() {		
		return store.size();
	}

	private void delay() {
		try {
			Thread.sleep(pauseMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
