package com.joseph.pockettrains.player;

import com.joseph.pockettrains.gameobject.GameObject;

public abstract class AbstractPlayer extends GameObject {
	protected enum EnumDirection {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		NO_MOVEMENT;
	}
}