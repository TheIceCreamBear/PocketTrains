package com.joseph.pockettrains.player;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import com.joseph.pockettrains.handlers.InputHandler;
import com.joseph.pockettrains.reference.KeyBindings;
import com.joseph.pockettrains.reference.Reference;
import com.joseph.pockettrains.screen.Screen;

public class ScreenPlayer extends AbstractPlayer {
	
	private InputHandler handler;
	private int x = 0;
	private int y = 0;
	
	private int xRenderMax = Screen.width;
	private int yRenderMax = Screen.height;
	
	private int upKey;
	private int downKey;
	private int leftKey;
	private int rightKey;
	
	private EnumDirection direction;
	
	public ScreenPlayer(Component c) {
		this(c, Color.blue, 1);
	}
	
	public ScreenPlayer(Component c, Color color, int id) {
		this.handler = new InputHandler(c);
		
		if (id == 1) {
			this.upKey = KeyBindings.mapUP;
			this.downKey = KeyBindings.mapDOWN;
			this.leftKey = KeyBindings.mapLEFT;
			this.rightKey = KeyBindings.mapRIGHT;
			this.direction = EnumDirection.RIGHT;
		} else if (id == 2) {
			this.upKey = KeyBindings.mapUP2;
			this.downKey = KeyBindings.mapDOWN2;
			this.leftKey = KeyBindings.mapLEFT2;
			this.rightKey = KeyBindings.mapRIGHT2;
			this.direction = EnumDirection.LEFT;
		}
	}
	

	@Override
	public void update(double deltaTime) {
		if (Reference.HARD_CORE_DEBUG_MODE) {
			
		}
		
		if (handler.isKeyDown(KeyEvent.VK_ESCAPE)) {
			System.exit(-1);
		}
		
		this.direction = this.getNewDirection();
		this.movePlayer();
	}

	@Override
	public void draw(Graphics g, ImageObserver observer) {
		// GAME DRAWS WHERE ITEM.X - THIS.X
	}

	private EnumDirection getNewDirection() {
		if (this.handler.isKeyDown(this.upKey)) {
			return EnumDirection.UP;
		} else if (this.handler.isKeyDown(this.downKey)) {
			return EnumDirection.DOWN;
		} else if (this.handler.isKeyDown(this.rightKey)) {
			return EnumDirection.RIGHT;
		} else if (this.handler.isKeyDown(this.leftKey)) {
			return EnumDirection.LEFT;
		} else {
			return EnumDirection.NO_MOVEMENT;
		}
	}
	
	private void movePlayer() {
		switch (this.direction) {
			case UP:
				this.y -= 2;
				break;
			case DOWN:
				this.y += 2;
				break;
			case LEFT:
				this.x -= 2;
				break;
			case RIGHT:
				this.x += 2;
				break;
			case NO_MOVEMENT:
				
				break;
			default:
				break;
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getxRenderMax() {
		return this.xRenderMax;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getyRenderMax() {
		return this.yRenderMax;
	}
}