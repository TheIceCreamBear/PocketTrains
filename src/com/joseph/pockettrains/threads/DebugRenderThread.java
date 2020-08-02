package com.joseph.pockettrains.threads;

import com.joseph.pockettrains.engine.DebugEngine;
import com.joseph.pockettrains.engine.GameEngine;
import com.joseph.pockettrains.gameobject.RenderLockObject;

public class DebugRenderThread extends Thread {
	private RenderLockObject rlo;
	private DebugEngine gEngine;
	public DebugRenderThread(String name, RenderLockObject rlo, DebugEngine ge) {
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