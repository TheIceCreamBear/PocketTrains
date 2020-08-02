package com.joseph.pockettrains.track;

import java.util.HashMap;

import com.joseph.pockettrains.station.Stations;

@SuppressWarnings("unused")
public class Tracks {
	public static final HashMap<String, Track> REGISTRY = new HashMap<String, Track>();
	private static Track paris_Milan;
	private static Track milan_Rome;
	private static Track milan_munich;
	private static Track Barca_milan;
	
	public static void init() {
		REGISTRY.put("paris-milan", paris_Milan = new Track(Stations.PARIS, Stations.MILAN, 51).setRegName("paris-milan"));
		REGISTRY.put("milan-rome", milan_Rome = new Track(Stations.MILAN, Stations.ROME, 33).setRegName("milan-rome"));
		REGISTRY.put("barca-milan", Barca_milan = new Track(Stations.BARCELONA, Stations.MILAN, 53).setRegName("barca-milan"));
		REGISTRY.put("milan-munich", milan_munich = new Track(Stations.MILAN, Stations.MUNICH, 46).setRegName("milan-munich"));
	}
}