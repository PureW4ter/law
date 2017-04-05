package com.destinesia.entity;

public class Counts {
	private long waitingCheck;
	private long promotion;
	private long passed;
	private long notPassed;
	
	public long getWaitingCheck() {
		return waitingCheck;
	}
	public void setWaitingCheck(long waitingCheck) {
		this.waitingCheck = waitingCheck;
	}
	public long getPromotion() {
		return promotion;
	}
	public void setPromotion(long promotion) {
		this.promotion = promotion;
	}
	public long getPassed() {
		return passed;
	}
	public void setPassed(long passed) {
		this.passed = passed;
	}
	public long getNotPassed() {
		return notPassed;
	}
	public void setNotPassed(long notPassed) {
		this.notPassed = notPassed;
	}
}
