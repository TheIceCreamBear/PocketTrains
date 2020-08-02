package com.joseph.pockettrains.managers;

import java.util.ArrayList;
import java.util.HashMap;

import com.joseph.pockettrains.interfaces.IManager;
import com.joseph.pockettrains.train.AbstractTrain;

public class TrainUpdateManager implements IManager {
	private ArrayList<AbstractTrain> trains;
	private HashMap<String, AbstractTrain> registry;
	private static TrainUpdateManager instance;
	
	public TrainUpdateManager() {
		registry = new HashMap<String, AbstractTrain>();
		trains = new ArrayList<AbstractTrain>();
		instance = this;
	}
	
	@Override
	public void update(double deltaTime) {
		for (AbstractTrain train : trains) {
			train.update();
		}
	}
	
	public void addTrain(String namedID, AbstractTrain train) {
		this.registry.put(namedID, train);
		this.trains.add(train);
	}
	
	public AbstractTrain getTrainByName(String name) {
		if (name.equalsIgnoreCase("null")) {
			return (AbstractTrain) null;
		}
		return this.registry.get(name);
	}
	
//	public String getNameByTrain(AbstractTrain train) {
//		return this.trains.
//	}
	
	public ArrayList<AbstractTrain> getTrains() {
		return this.trains;
	}
	
	public HashMap<String, AbstractTrain> getMap() {
		return registry;
	}
	
	public static TrainUpdateManager getInstance() {
		return instance;
	}
}