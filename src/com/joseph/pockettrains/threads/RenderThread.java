package com.joseph.pockettrains.threads;

import com.joseph.pockettrains.engine.GameEngine;
import com.joseph.pockettrains.gameobject.RenderLockObject;

public class RenderThread extends Thread {
	private RenderLockObject rlo;
	private GameEngine gEngine;
	public RenderThread(String name, RenderLockObject rlo, GameEngine ge) {
		super(name);
		this.rlo = rlo;
		this.gEngine = ge;
	}
	
	@Override
	public void run() {
		synchronized (rlo) {
			while (GameEngine.isRunning()) {
				try {
					rlo.wait();
					if (!rlo.wasNotified()) {
						continue;
					} else {
						gEngine.render(gEngine.getG(), gEngine.getFrame());
						rlo.setWasNotified(false);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
	}
}