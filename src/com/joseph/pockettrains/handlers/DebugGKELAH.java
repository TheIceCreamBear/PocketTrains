package com.joseph.pockettrains.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.joseph.pockettrains.engine.DebugEngine;



/**
 * GKELAH, or GlobalKeyEventHandlerAndListener, is a key event handler that listens for all
 * key events and does a specific action based on the state of the engine and the key pressed
 * @author Joseph
 */
public class DebugGKELAH implements KeyListener {
	public DebugGKELAH() {
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if ((int) e.getKeyChar() == 10) {
			return;
		}
		DebugEngine.getInstance().getDcgo().notifyKeyTyped(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			DebugEngine.getInstance().getDcgo().notifyEneter();
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			DebugEngine.getInstance().getDcgo().notiftUP();
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			DebugEngine.getInstance().getDcgo().notifyDown();
			return;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
}