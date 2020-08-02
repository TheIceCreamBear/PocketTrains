package com.joseph.pockettrains.gui;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * Should always be drawn
 * <p>
 * Draws the amount of coins, crates, and bux the player has
 * @author Joseph
 *
 */
public class ResourcesOverlay implements IGuiOverlay {
	// private ResourceManager manager;
	
	public ResourcesOverlay() {
		// TODO
	}

	@Override
	public void drawGuiBackground(Graphics g, ImageObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawUpdateableGraphicsElements(Graphics g, ImageObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUpdateableGraphicsElements(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeGui() {
		return false;
	}

	@Override
	public void setGuiToRemove() {
		// does not get removed
	}
}