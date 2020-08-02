package com.joseph.pockettrains.util;

import com.joseph.pockettrains.exceptions.InvalidMethodCallerException;
import com.joseph.pockettrains.threads.ShutdownThread;

public class GeneralUtility {
	private static GeneralUtility instance;
	
	static {
		if (instance == null) {
			instance = new GeneralUtility();
		}
	}
	public GeneralUtility() {
		instance = this;
	}
	
	public void deassignAllVars() throws InvalidMethodCallerException {
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		StackTraceElement previous = stes[2];
		if (previous.getClassName().equals(ShutdownThread.class.getName()) || previous.getClassName().equals(GeneralUtility.class.getName())) {
			// TODO, Deassign
		} else {
			throw new InvalidMethodCallerException("The class " + previous.getClassName() + " may not call com.joseph.pockettrains.util.GeneralUtility.deassignAllVars().");
		}
	}
	
	public static GeneralUtility getInstance() {
		return instance;
	}
}