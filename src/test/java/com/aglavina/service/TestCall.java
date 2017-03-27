package com.aglavina.service;

import java.security.SecureRandom;

import com.aglavina.model.Call;

public class TestCall extends Call {
	private static final int MIN_DURATION=5;
	private static final int MAX_DURATION=10;

	private final int limitDuration;
	private static final SecureRandom sr = new SecureRandom();

	public TestCall() {
		super();
		this.limitDuration=getRandomDuration();
	}
	public int getLimitDuration() {
		return limitDuration;
	}
	
	@Override
	public boolean isFinished() {
		if (getDuration() >= getLimitDuration()) 
			setFinished(true);
		return super.isFinished();
	}
	
	
	private int getRandomDuration() {
		return  ((Math.abs(sr.nextInt()) % (MAX_DURATION + 1 - MIN_DURATION) ) + MIN_DURATION ) * 1000;
	}
}
