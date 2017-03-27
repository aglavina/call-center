package com.aglavina.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Call extends Entity {

	private int duration;
	private boolean finished;
	private static Logger logger = LogManager.getLogger(Call.class);
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void addDuration(int interval) {
		setDuration(getDuration()+interval);
		logger.debug("call id: " + getId() + " current duration: " + getDuration() / 1000 + "s ");
	}
}
