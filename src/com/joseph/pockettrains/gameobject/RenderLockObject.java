package com.joseph.pockettrains.gameobject;

public class RenderLockObject {
	private boolean wasNotified;
	public RenderLockObject() {
		this.wasNotified = false;
	}
	
	public void setWasNotified(boolean wasNotified) {
		this.wasNotified = wasNotified;
	}
	
	public boolean wasNotified() {
		return this.wasNotified;
	}
}