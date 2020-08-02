package com.joseph.pockettrains.managers;

import java.util.ArrayList;

import com.joseph.pockettrains.interfaces.IManager;
import com.joseph.pockettrains.resource.TrainPart;
import com.joseph.pockettrains.resource.TrainStorageObject;

public class ResourceManager implements IManager {
	private ArrayList<TrainStorageObject> listOfCraftedCars;
	private ArrayList<TrainPart> listOfParts;
	private int numBux;
	private int numCoins;
	private int numCrates;
	private int numSpecialCrates;
	
	public ResourceManager() {
		this.listOfCraftedCars = new ArrayList<TrainStorageObject>();
		this.listOfParts = new ArrayList<TrainPart>(); 
	}
	
	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
	}
}