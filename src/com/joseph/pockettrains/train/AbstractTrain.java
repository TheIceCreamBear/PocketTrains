package com.joseph.pockettrains.train;

import java.util.ArrayList;
import java.util.HashMap;

import com.joseph.pockettrains.mission.AbstractMission;
import com.joseph.pockettrains.station.AbstractStation;
import com.joseph.pockettrains.station.Stations;
import com.joseph.pockettrains.track.TrackWithDirection;

//@SuppressWarnings("unused")
public class AbstractTrain { // TODO MAY NOT BE ABSTRACT, MAY BE ACTUAL TRAIN INSTANCE CLASS
	private final EnumTrainType type;
	private int engines;
	private int fuelCars;
	private int fuel;
	private int pulledCars;
	private int missionTicks;
	private int maxPulledCars;
	private int destinationIndex;
	/** The distance of the current track, previous station to next station */
	private int segmentDistance;
	/** The distance traveled in the current track. */
	private int segmentDistanceTraveled;
	/** The distance traveled for all tracks and stations in the current list or string of stations. Used for Graphics. */
	private int totalDistanceTraveled;
	/** The total distance for all tracks and stations in the current list or string of stations. Used for Graphics. */
	private int totalTravelDistance;
	/** Weather or not this train is moving and has left the resting station. */
	private boolean isOnMission;
	private ArrayList<TrackWithDirection> destinationList;
	/** 
	 * the station the train is waiting in. 
	 * Equivalent to {@link com.joseph.pockettrains.station.Stations#EMPTY Stations.EMPTY} if not in a station 
	 */
	private AbstractStation waitingStation;
	/**
	 * the station at the end of the destination list. if the list is empty, then this is the same as waiting station.
	 */
	private AbstractStation endMoveStation;
	private ArrayList<AbstractMission> missionList;
	
	public AbstractTrain(EnumTrainType type) {
		this.type = type;
		this.engines = 1;
		this.fuelCars = 0;
		this.pulledCars = 0;
		this.destinationIndex = 0;
		this.fuel = this.type.getMaxFuelPerSingleFuelCar();
		this.maxPulledCars = this.type.getMaxNumberOfCarsPerSingleEngine();
		this.destinationList = new ArrayList<TrackWithDirection>();
		this.missionList = new ArrayList<AbstractMission>();
	}
	
	public void update() {
		// if at station then drop off cars
		if (this.isOnMission) {
//			if (ime to do tick)
			this.missionTicks++;
			if (this.missionTicks == 60) {
				this.missionTicks = 0;
				this.segmentDistanceTraveled++;
				this.totalDistanceTraveled++;
				this.fuel--;
			}
			// arrive at station
			if (this.segmentDistanceTraveled >= this.segmentDistance) {
				this.destinationIndex++;
				try {
					this.segmentDistance = this.destinationList.get(destinationIndex).getTrack().getDistance();
				} catch (java.lang.IndexOutOfBoundsException e) {
					this.destinationIndex--;
					this.segmentDistance = this.destinationList.get(destinationIndex).getTrack().getDistance();
				}
				this.segmentDistanceTraveled = 0;
				// TODO drop off missions for destination
			}
			// finish travel
			if (this.totalDistanceTraveled >= this.totalTravelDistance) {
				this.isOnMission = false;
				this.totalTravelDistance = 0;
				this.totalDistanceTraveled = 0;
				this.destinationIndex = 0;
				this.destinationList.clear();
				this.waitingStation = this.endMoveStation;
			}
		} else {
			// regain fuel
		}
	}
	
	public void leaveStation() {
		if (destinationList.size() > 0) {
			// check enough fuel
			this.isOnMission = true;
			this.waitingStation = Stations.EMPTY;
		}
	}
	
	public void boost() {
		// RESOURCE TAKE BUX
		this.isOnMission = false;
		this.totalTravelDistance = 0;
		this.totalDistanceTraveled = 0;
		this.destinationIndex = 0;
		this.destinationList.clear();
		this.waitingStation = this.endMoveStation;
		// DEAL WITH PULLED CARS HERE FOR BOOST
	}
	
	public void addMovement(TrackWithDirection twd) {
		this.endMoveStation = twd.getDestinationOfDirection();
		this.destinationList.add(twd);
		if (this.segmentDistance == 0) {
			this.segmentDistance = twd.getTrack().getDistance();
		}
		
		this.totalTravelDistance += twd.getTrack().getDistance();
		System.err.println(this.totalTravelDistance);
	}
	
	public void clearDestinations() {
		this.destinationList.clear();
	}
	
	public void addEngine() {
		if (this.engines < this.type.getMaxEngines()) {
//			if (ResourceManager.instance.removeEngineFromStorage(this.type)) {
			this.engines++;
			this.maxPulledCars = this.type.getMaxNumberOfCarsPerSingleEngine() * this.engines;
//			}
		}
	}
	
	public void addEngineCommand() {
		if (this.engines < this.type.getMaxEngines()) {
			this.engines++;
			this.maxPulledCars = this.type.getMaxNumberOfCarsPerSingleEngine() * this.engines;
		}
	}
	
	public void addFuelCar() {
		if (this.fuelCars < this.type.getMaxFuelCars()) {
//			if (ResourceManager.instance.removeFuelCarFromStorage(this.type)) {
			this.fuelCars++;
			this.fuel = this.type.getMaxFuelPerSingleFuelCar() * (this.fuelCars + 1);
//			}
		}
	}
	
	public void addFuelCarCommand() {
		if (this.fuelCars < this.type.getMaxFuelCars()) {
			this.fuelCars++;
			this.fuel = this.type.getMaxFuelPerSingleFuelCar() * (this.fuelCars + 1);
		}
	}
	
	public void addMission(AbstractMission mission) {
		if (!((mission.getNumberOfCars() + this.pulledCars) > this.maxPulledCars)) {
			this.missionList.add(mission);
			this.pulledCars += mission.getNumberOfCars();
		}
	}
	
	public void startMove() {
		if (this.isOnMission) {
			return;
		}
		this.isOnMission = true;
		this.waitingStation = null;
		this.segmentDistanceTraveled = 0;
		this.totalDistanceTraveled = 0;
	}
	
	public void setWaitingStation(AbstractStation waitingStation) {
		this.waitingStation = waitingStation;
		this.endMoveStation = this.waitingStation;
	}
	
	public EnumTrainType getType() {
		return type;
	}
	
	public int getPulledCars() {
		return pulledCars;
	}
	
	public int getEngines() {
		return engines;
	}
	
	public int getFuelCars() {
		return fuelCars;
	}
	
	public int getFuel() {
		return fuel;
	}
	
	public AbstractStation getEndMoveStation() {
		return endMoveStation;
	}
	
	public AbstractStation getWaitingStation() {
		if (this.waitingStation == null) {
			return Stations.EMPTY;
		}
		return waitingStation;
	}
	
	public ArrayList<TrackWithDirection> getDestinationList() {
		return destinationList;
	}
	
	public ArrayList<AbstractMission> getMissionList() {
		return missionList;
	}
	
	public int getSegmentDistance() {
		return segmentDistance;
	}
	
	public int getSegmentDistanceTraveled() {
		return segmentDistanceTraveled;
	}
	
	public int getTotalDistanceTraveled() {
		return totalDistanceTraveled;
	}
	
	public int getTotalTravelDistance() {
		return totalTravelDistance;
	}
	
	public int getDistanceScalled() {
		int in = (int) (((double) this.totalDistanceTraveled * 536) / this.totalTravelDistance);
		return in;
	}
	
	public int getSegmentDistanceScalled() {
		int in = (int) (((double) this.segmentDistanceTraveled * 536) / this.segmentDistance);
		return in;
	}
	
	public boolean isOnMission() {
		return isOnMission;
	}
	
	public enum EnumTrainType {
		BLUE_BELL(1, 1, 8, 250, 50, 3, 0),
		CHERRY(1, 1, 8, 230, 54, 3, 1),
		EMERALD(1, 1, 8, 270, 47, 3, 2),
		VANILLA(1, 1, 8, 260, 52, 3, 3),
		CARBON(1, 1, 12, 300, 54, 4, 4),
		REGIONAL(2, 1, 8, 315, 104, 7, 5); // TODO TRAIN TYPES

		private static final HashMap<Integer, EnumTrainType> ID_MAP = new HashMap<Integer, EnumTrainType>();
		
		static {
			ID_MAP.put(BLUE_BELL.internalID, BLUE_BELL);
			ID_MAP.put(CHERRY.internalID, CHERRY);
			ID_MAP.put(EMERALD.internalID, EMERALD);
			ID_MAP.put(VANILLA.internalID, VANILLA);
			ID_MAP.put(CARBON.internalID, CARBON);
			ID_MAP.put(REGIONAL.internalID, REGIONAL);
		}
		
		private final int numEngines;
		private final int numTanks;
		private final int numCarsPerEngine;
		private final int numFuelPerTank;
		private final int mph;
		private final int partsNeeded;
		private final int internalID;
		
		EnumTrainType(int eng, int tank, int cars, int fuel, int mph, int partsNeeded, int internalID) {
			this.numEngines = eng;
			this.numTanks = tank;
			this.numCarsPerEngine = cars;
			this.numFuelPerTank = fuel;
			this.mph = mph;
			this.partsNeeded = partsNeeded;
			this.internalID = internalID;
		}
		
		/**
		 * 
		 * @return the max number of engines the referenced train type can have
		 */
		public int getMaxEngines() {
			return this.numEngines;
		}
		
		/**
		 * 
		 * @return the max number of fuel cars the referenced train type can have
		 */
		public int getMaxFuelCars() {
			return this.numTanks;
		}
		
		/**
		 * This value should always be multiplied by the current number or engines to get the current maximum amount of cars that can be pulled.
		 * This number should not ever count fuel cars. 
		 * <p>
		 * The way the iOS game does it is {@code thisNum / 2} for each extra engine and {@code thisNum} for the first engine. The
		 * way it is currently implemented here is <code>thisNum * numOfEngines</code>, where {@code thisNum} is the number returned by this method
		 * @return the max number of cars one engine of the referenced type can pull
		 */
		public int getMaxNumberOfCarsPerSingleEngine() {
			return this.numCarsPerEngine;
		}
		
		/**
		 * This value should always be multiplied by the current amount of (fuel cars + 1) to account for the fuel in the train with only 
		 * one engine and no fuel cars.
		 * @return the maximum amount of fuel that one fuel car of the referenced type can  hold
		 */
		public int getMaxFuelPerSingleFuelCar() {
			return this.numFuelPerTank;
		}
		
		/**
		 * 
		 * @return the speed of the train in MPH
		 */
		public int getMPH() {
			return this.mph;
		}
		
		/**
		 * 
		 * @return the number of parts required by this train type to be present to craft a segment of the train
		 */
		public int getPartsNeeded() {
			return partsNeeded;
		}
		
		/**
		 * 
		 * @return the internal ID of this TrainType. used when building a train from the in-game command line.
		 */
		public int getInternalID() {
			return internalID;
		}
		
		/**
		 * 
		 * @param internalID - The Non negative id of the {@code EnumTrainType} to get
		 * @return the instance of {@code EnumTrainType} based on the given non negative integer ID
		 */
		public static EnumTrainType getTypeByID(int internalID) {
			return ID_MAP.get(internalID);
		}
	}
}