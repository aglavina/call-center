package com.aglavina.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class Call extends Entity {

	private Date startDuration = new Date();
	private Date finishDuration;
	private boolean finished;
	private static Logger logger = LogManager.getLogger(Call.class);
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		if (finished && !this.finished) {
			finishDuration = new Date();
		}
		this.finished = finished;
	}

	public int getDuration() {
		return isFinished() ? (finishDuration.getTime() - startDuration.getTime()) / 1000 :
		 (new Date().getTime() - startDuration.getTime()) / 1000 ;		
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void addDuration(int interval) {
		setDuration(getDuration()+interval);
		logger.debug("call id: " + getId() + " current duration: " + getDuration() / 1000 + "s ");
	}
}
