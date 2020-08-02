package com.joseph.pockettrains.station;

import java.util.ArrayList;

import com.joseph.pockettrains.mission.AbstractMission;

public class AbstractStation {
	private ArrayList<AbstractMission> stored = new ArrayList<AbstractMission>();
	private String name;
	private int maxCarsStored;
	private int numStoredCars;
	// TODO have update for storage and draw for other things
	
	/**
	 * A Temporary constructor for development TODO remove
	 * @param name
	 */
	public AbstractStation(String name) {
		this.name = name.toLowerCase();
	}
	
	private AbstractStation(ArrayList<AbstractMission> listIn, String name, int maxStroed, int numStored) {
		this.stored = listIn;
		this.name = name.toLowerCase();
		this.maxCarsStored = maxStroed;
		this.numStoredCars = numStored;
	}
	
	public void upgrade() {
		// resourcemanager.instance.remove(bux * 10)
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof AbstractStation) {
			// compare
			return this == obj;
//			AbstractStation as = (AbstractStation) obj;
//			return this.name.equals(as.name);
		}
		return false;
	}
	
	public void addMission(AbstractMission mission) {
		if (mission.getNumberOfCars() + this.numStoredCars <= this.maxCarsStored) {
			this.stored.add(mission);
			this.numStoredCars += mission.getNumberOfCars();
		}
	}
	
	public String getName() {
		if (name == null) {
			return "";
		}
		return name;
	}

	public static AbstractStation loadAbstractStationFromFile() {
		return new AbstractStation(null, null, 0, 0); // TODO
	}
}