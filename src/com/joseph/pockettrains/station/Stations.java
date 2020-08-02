package com.joseph.pockettrains.station;

import java.util.HashMap;

public class Stations {
	public static final HashMap<String, AbstractStation> registry = new HashMap<String, AbstractStation>();
	public static final AbstractStation EMPTY;
	public static final AbstractStation BARCELONA;
	public static final AbstractStation MUNICH;
	public static final AbstractStation MILAN;
	public static final AbstractStation PARIS;
	public static final AbstractStation ROME;
	
	static {
		register(EMPTY = new AbstractStation("EMPTY"), EMPTY.getName());
		register(BARCELONA = new AbstractStation("BARCELONA"), BARCELONA.getName());
		register(MUNICH = new AbstractStation("MUNICH"), MUNICH.getName());
		register(MILAN = new AbstractStation("MILAN"), MILAN.getName());
		register(PARIS = new AbstractStation("PARIS"), PARIS.getName());
		register(ROME = new AbstractStation("ROME"), ROME.getName());
	}
	
	private static void register(AbstractStation station, String regName) {
		registry.put(regName, station);
	}
}