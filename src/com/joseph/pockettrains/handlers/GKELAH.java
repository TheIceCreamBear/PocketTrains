package com.joseph.pockettrains.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



/**
 * GKELAH, or GlobalKeyEventHandlerAndListener, is a key event handler that listens for all
 * key events and does a specific action based on the state of the engine and the key pressed
 * @author Joseph
 */
public class GKELAH implements KeyListener {
	public GKELAH() {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_SLASH) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// TODO set engine state to menu
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
}