package com.aglavina.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aglavina.model.Operator;

public class CallProcessor implements Runnable {		
	private final Operator operator;
	private final Callback callback;
	private static final int INTERVAL = 500; // check every 500 ms
	private static Logger logger = LogManager.getLogger(CallProcessor.class);
	public CallProcessor(Operator operator, Callback callback) {
		super();
		this.operator=operator;
		this.callback=callback;
	}
	public void run() {
		try {
			checkCallIsFinished();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			callback.complete(operator);
		}
	}
	private void checkCallIsFinished() throws InterruptedException {
		for (; !operator.getCurrentCall().isFinished();) {
			Thread.sleep(INTERVAL);
		}
	}
	
}
