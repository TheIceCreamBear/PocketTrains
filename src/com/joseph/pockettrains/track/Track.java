package com.joseph.pockettrains.track;

import com.joseph.pockettrains.line.Line;
import com.joseph.pockettrains.station.AbstractStation;

public class Track {
	private AbstractStation station1;
	private AbstractStation station2;
	private Line claimedBy;
	private String regName;
	private int distance;
	
	public Track(AbstractStation station1, AbstractStation station2, int distance) {
		this.station1 = station1;
		this.station2 = station2;
		this.distance = distance;
	}

	public AbstractStation getStation1() {
		return station1;
	}

	public AbstractStation getStation2() {
		return station2;
	}
	
	public Track setRegName(String name) {
		this.regName = name;
		return this;
	}
	
	public String toString() {
		return "{" + this.regName + ",Distance=" + this.distance + ",Hash=" + Integer.toHexString(hashCode()) + "}";
	}
	
	public String getString() {
		return this.regName + ",Distance=" + this.distance + ",Hash=" + Integer.toHexString(hashCode());
	}
	
	/**
	 * TODO think about making this number the pocketTrains number * 10
	 * @return
	 */
	public int getDistance() {
		return distance;
	}
	
	public boolean claim(Line line) {
		if (this.claimedBy == null) {
			this.claimedBy = line;
			return true;
		} else {
			this.claimedBy.declaimTrack(this);
			if (line.canClaimTrack(this)) {
				this.claimedBy = line;
				return true;
			}
			return false;
		}
	}
	
	// TODO make a save file that has these is a special format and load all these from that file
}