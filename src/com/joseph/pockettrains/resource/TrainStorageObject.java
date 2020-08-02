package com.joseph.pockettrains.resource;

import com.joseph.pockettrains.train.AbstractTrain.EnumTrainType;

public class TrainStorageObject {
	private EnumTrainCarType carType;
	private EnumTrainType trainType;
	private int amount;
	
	public TrainStorageObject(EnumTrainCarType carType, EnumTrainType trainType) {
		this.carType = carType;
		this.trainType = trainType;
	}
	
	public EnumTrainCarType getCarType() {
		return this.carType;
	}
	
	public EnumTrainType getTrainType() {
		return this.trainType;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void incrimentAmount(int incriment) {
		this.amount += incriment;
	}
	
	public void decrimentAmount(int decriment) {
		this.amount += decriment;
	}
}