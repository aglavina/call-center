package com.aglavina.model;

public class Operator extends Entity implements Comparable<Operator> {

	private EmployeeType type;
	private Call currentCall;
	
	public boolean isAvailable() {
		return (getCurrentCall() == null || getCurrentCall().isFinished());
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	public int compareTo(Operator arg0) {
		if (type.compareTo(arg0.getType()) != 0) 
			return type.compareTo(arg0.getType());
		else if (getId() < arg0.getId())
			return -1;
		else if (getId() > arg0.getId())
			return 1;
		else
			return 0;
	}

	public Call getCurrentCall() {
		return currentCall;
	}

	public void setCurrentCall(Call currentCall) {
		this.currentCall = currentCall;
	}
}
