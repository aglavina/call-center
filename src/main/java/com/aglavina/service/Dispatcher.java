package com.aglavina.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aglavina.model.Call;
import com.aglavina.model.Operator;

public class Dispatcher implements Callback {
	private static final int MAX_THREADS = 10;
	private BlockingQueue<Operator> availableOperators = new PriorityBlockingQueue<Operator>();
	private ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
	private volatile int usedThreads = 0;
	private static Integer syncObject = new Integer(0);
	private static Logger logger = LogManager.getLogger(Dispatcher.class);
	public void addOperator(Operator operator) {
		availableOperators.add(operator);
	}
	public void dispatchCall(Call call) { 
		synchronized (syncObject) {
			syncDispatchCall(call);
		}
	}

	private void syncDispatchCall(Call call) {
		if (availableOperators.isEmpty())
			throw new NoAvailableOperatorException();
		else if (usedThreads >= MAX_THREADS)
			throw new NoThreadAvailableException();
		else
			processCall(call);
	}

	private void processCall(Call call) {
		try {
			usedThreads++;
			Operator operator = availableOperators.take();
			operator.setCurrentCall(call);
			executor.execute(new CallProcessor(operator,this));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public void complete(Operator operator) {
		logger.debug("Call " + operator.getCurrentCall().getId() + " completed. Total duration: " + operator.getCurrentCall().getDuration() / 1000 + "s");
		availableOperators.add(operator);
		usedThreads--;
	}
}
