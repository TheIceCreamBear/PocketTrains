package com.joseph.pockettrains.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import com.joseph.pockettrains.engine.DebugEngine;
import com.joseph.pockettrains.line.Line;
import com.joseph.pockettrains.managers.LineManager;
import com.joseph.pockettrains.managers.TrainUpdateManager;
import com.joseph.pockettrains.reference.Reference;
import com.joseph.pockettrains.train.AbstractTrain;

public class LogicDebugGui implements IGuiOverlay {
	private int height;
	private int length;
	private int x;
	private int y;
	
	public LogicDebugGui() {
//		this.x = 1050;
		this.y = 65;
//		this.length = 145;
		this.height = 680;
		this.x = 25;
		this.length = 1150;
	}
	
	@Override
	public void drawGuiBackground(Graphics g, ImageObserver observer) {
		g.setColor(Color.white);
//		g.setColor(Reference.LOGIC_BACK);
		g.fillRect(x, y, length, height);
	}

	@Override
	public void drawUpdateableGraphicsElements(Graphics g, ImageObserver observer) {
		ArrayList<Line> lineList = LineManager.getInstance().getLineList();
		Line[] lines = new Line[lineList.size()];
		lineList.toArray(lines);
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] == DebugEngine.getInstance().getDcgo().getSelectedLine()) {
				g.setColor(Reference.LOGIC_SELECTED);
			} else {
				g.setColor(Reference.LOGIC_DARKER_GREEN);
			}
			int fontHeight = (int) Reference.LOGIC_FONT.getStringBounds("[{}]", DebugEngine.getInstance().getFrc()).getHeight();
			g.setFont(Reference.LOGIC_FONT);
			String debugString = lines[i].getRegistryName() + ":" + lines[i].getTrain().getType().toString() + ",Engines=" + lines[i].getTrain().getEngines() + ",FuelCars=" + lines[i].getTrain().getFuelCars() + ",Hash=" + Integer.toHexString(lines[i].hashCode()) + ",TrainHash=" + Integer.toHexString(lines[i].getTrain().hashCode()) + ",WaitingStation=" + lines[i].getTrain().getWaitingStation().getName() + ",Fuel=" + lines[i].getTrain().getFuel();
			g.drawString(debugString, x + 10, y + 15 + (113 * (i)));
			String lineString = lines[i].getClaimedTracks().toString();
			g.drawString(lineString, x + 10, y + 15 + (113 * (i)) + fontHeight);
			String destinationString = lines[i].getTrain().getDestinationList().toString();
			g.drawString(destinationString, x + 10, y + 15 + (113 * (i)) + (fontHeight * 2));
			String missionString = lines[i].getTrain().getMissionList().toString() + " : " + lines[i].getTrain().getPulledCars();
			g.drawString(missionString, x + 10, y + 15 + (113 * (i)) + (fontHeight * 3));
			
			g.drawString("[                                                                                                                                     ] : " + lines[i].getTrain().getSegmentDistanceScalled() + "; " + lines[i].getTrain().getSegmentDistanceTraveled() + '/' + lines[i].getTrain().getSegmentDistance(), x + 10, y + 15 + (113 * (i)) + (fontHeight * 4));
			g.setColor(Color.CYAN);
			g.fillRect(x + 13, y + 5 + (113 * (i)) + (fontHeight * 4), lines[i].getTrain().getSegmentDistanceScalled(), 11);
			
			if (lines[i] == DebugEngine.getInstance().getDcgo().getSelectedLine()) {
				g.setColor(Reference.LOGIC_SELECTED);
			} else {
				g.setColor(Reference.LOGIC_DARKER_GREEN);
			}
			g.drawString("[                                                                                                                                     ] : " + lines[i].getTrain().getDistanceScalled() + "; " + lines[i].getTrain().getTotalDistanceTraveled() + '/' + lines[i].getTrain().getTotalTravelDistance(), x + 10, y + 15 + (113 * (i)) + (fontHeight * 5));
			g.setColor(Color.CYAN);
			g.fillRect(x + 13, y + 5 + (113 * (i)) + (fontHeight * 5), lines[i].getTrain().getDistanceScalled(), 11);
		}
	}

	@Override
	public void updateUpdateableGraphicsElements(double deltaTime) {
	}

	@Override
	public boolean removeGui() {
		return false;
	}

	@Override
	public void setGuiToRemove() {
	}
}