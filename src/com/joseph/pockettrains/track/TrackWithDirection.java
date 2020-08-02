package com.joseph.pockettrains.track;

import com.joseph.pockettrains.station.AbstractStation;

public class TrackWithDirection {
	private Track track;
	private AbstractStation destinationOfDirection;
	private boolean toStationOne;
	
	public TrackWithDirection(Track track, boolean toOne) {
		this.track = track;
		this.toStationOne = toOne;
		if (toOne) {
			this.destinationOfDirection = track.getStation1();
		} else {
			this.destinationOfDirection = track.getStation2();
		}
	}
	
	public AbstractStation getDestinationOfDirection() {
		return destinationOfDirection;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public boolean isToStationOne() {
		return toStationOne;
	}
	
	@Override
	public String toString() {
		return "{" + track.getString() + ",toOne=" + this.toStationOne + ",Destination=" + this.destinationOfDirection.getName() + "}";
	}
}