package com.joseph.pockettrains.line;

import java.util.ArrayList;

import com.joseph.pockettrains.track.Track;
import com.joseph.pockettrains.track.TrackWithDirection;
import com.joseph.pockettrains.train.AbstractTrain;

public class Line {
	private AbstractTrain train;
	private ArrayList<Track> claimedTracks;
	private String registryName;
	
	public Line(AbstractTrain train, ArrayList<Track> tracks) {
		this.train = train;
		this.claimedTracks = tracks;
	}
	
	public Line(AbstractTrain train) {
		this(train, new ArrayList<Track>());
	}
	
	public void update() {
		this.train.update();
	}
	
	public void setRegistryName(String name) {
		this.registryName = name;
	}
	
	public String getRegistryName() {
		return registryName;
	}
	
	public AbstractTrain getTrain() {
		return train;
	}
	
	public ArrayList<Track> getClaimedTracks() {
		return claimedTracks;
	}
	
	public void addDestination(String destinationName) {
		for (Track track : claimedTracks) {
			if (track.getStation1().getName().toLowerCase().equals(destinationName)) {
				if (track.getStation2().equals(train.getEndMoveStation())) {
					train.addMovement(new TrackWithDirection(track, true));
				}
			}
			if (track.getStation2().getName().toLowerCase().equals(destinationName)) {
				if (track.getStation1().equals(train.getEndMoveStation())) {
					train.addMovement(new TrackWithDirection(track, false));
				}
			}
		}
	}
	
	public void clearDestinations() {
		train.clearDestinations();
	}
	
	public void claimTrack(Track track) {
		if (track == null) {
			return;
		}
		if (track.claim(this)) {
			this.claimedTracks.add(track);
		}
	}
	
	public boolean declaimTrack(Track track) {
		this.claimedTracks.remove(track);
		return true;
	}
	
	public boolean canClaimTrack(Track track) {
		// TODO
		return true;
	}
}