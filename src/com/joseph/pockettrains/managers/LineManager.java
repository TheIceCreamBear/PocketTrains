package com.joseph.pockettrains.managers;

import java.util.ArrayList;
import java.util.HashMap;

import com.joseph.pockettrains.interfaces.IManager;
import com.joseph.pockettrains.line.Line;

public class LineManager implements IManager {
	private static LineManager instance;
	private ArrayList<Line> lineList;
	private HashMap<String, Line> registry;
	
	public LineManager() {
		this.lineList = new ArrayList<Line>();
		this.registry = new HashMap<String, Line>();
		instance = this;
	}
	
	public LineManager(ArrayList<Line> list, HashMap<String, Line> preBuiltRegistry) {
		this.lineList = list;
		this.registry = preBuiltRegistry;
	}
	
	@Override
	public void update(double deltaTime) {
		for (Line line : lineList) {
			line.update();
		}
	}
	
	public void registerLine(Line line, String name) {
		line.setRegistryName(name);
		this.lineList.add(line);
		this.registry.put(name, line);
	}
	
	public void deRegisterLine(Line line, String name) {
		if (line.getRegistryName().equals(name)) {
			this.lineList.remove(line);
			this.registry.remove(name, line);
		}
	}
	
	public Line getLineByName(String name) {
		return this.registry.get(name);
	}
	
	public static LineManager getInstance() {
		return instance;
	}
	
	public ArrayList<Line> getLineList() {
		return lineList;
	}
}