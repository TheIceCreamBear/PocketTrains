package com.joseph.pockettrains.mission;

import com.joseph.pockettrains.resource.EnumMissionReward;
import com.joseph.pockettrains.station.AbstractStation;

public class AbstractMission {
	private AbstractStation destination;
	private AbstractStation startStation;
	private EnumMissionReward type;
	private int amountOfReward;
	private int numberOfCars;
	
	public AbstractMission(AbstractStation destination, AbstractStation start, EnumMissionReward type, int amountReward, int numCars) {
		this.destination = destination;
		this.startStation = start;
		this.type = type;
		this.amountOfReward = amountReward;
		this.numberOfCars = numCars;
	}
	
	public boolean completeMission() {
		// resource manager add reward
		
		// make all null
		
		return false;
	}
	
	public AbstractStation getDestination() {
		return this.destination;
	}
	
	public AbstractStation getStartStation() {
		return this.startStation;
	}
	
	public boolean isCrate() {
		return this.type == EnumMissionReward.CRATE;
	}
	
	public boolean isBuxMission() {
		return this.type == EnumMissionReward.BUX;
	}
	
	public int getAmountOfReward() {
		return this.amountOfReward;
	}
	
	public int getNumberOfCars() {
		return this.numberOfCars;
	}
}