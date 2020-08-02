package com.joseph.pockettrains.threads;

import com.joseph.pockettrains.exceptions.InvalidMethodCallerException;
import com.joseph.pockettrains.util.GeneralUtility;
import com.joseph.pockettrains.util.SaveGameUtilities;

public class ShutdownThread extends Thread {
	@Override
	public void run() {
		SaveGameUtilities.getInstance().saveGame();
		try {
			GeneralUtility.getInstance().deassignAllVars();
		} catch (InvalidMethodCallerException e) {
			e.printStackTrace();
		}
	}	
}