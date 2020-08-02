package com.joseph.pockettrains.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SaveGameUtilities {
	private File saveFile;
	private static SaveGameUtilities instance;
	
	static {
		if (instance == null) {
			new SaveGameUtilities();
		}
	}
	
	public SaveGameUtilities() {
		// init file
		instance = this;
	}
	
	public void saveGame() {
		
	}
	
	public void loadGame() throws FileNotFoundException {
		Scanner scan = new Scanner(saveFile);
	}
	
	public static SaveGameUtilities getInstance() {
		return instance;
	}
}