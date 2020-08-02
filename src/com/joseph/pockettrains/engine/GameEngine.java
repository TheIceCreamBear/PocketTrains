package com.joseph.pockettrains.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.joseph.pockettrains.gameobject.GameObject;
import com.joseph.pockettrains.gameobject.RenderLockObject;
import com.joseph.pockettrains.interfaces.IDrawable;
import com.joseph.pockettrains.interfaces.IManager;
import com.joseph.pockettrains.interfaces.IUpdateable;
import com.joseph.pockettrains.managers.LineManager;
import com.joseph.pockettrains.reference.Reference;
import com.joseph.pockettrains.screen.Screen;
import com.joseph.pockettrains.threads.RenderThread;
import com.joseph.pockettrains.threads.ShutdownThread;

/**
 * 
 * @author Joseph Terribile - Current Maintainer
 * @author David Santamaria - Original Author
 *
 */
public class GameEngine {
	public static Random rand;
	private static GameEngine instance;
	private static String stats = "";
	private static boolean running = true;

	private BufferedImage i;
	private EnumRenderState state;
	private Graphics g;
	private Graphics g2;
	private JFrame frame;
	
	private RenderLockObject rlo;
	private RenderThread rtInstance;
	private ShutdownThread sdtInstance;
	
	private static ArrayList<GameObject> updateableAndDrawable = new ArrayList<GameObject>();
	private static ArrayList<IUpdateable> updateable = new ArrayList<IUpdateable>();
	private static ArrayList<IDrawable> drawable = new ArrayList<IDrawable>();
	private static ArrayList<IManager> managers = new ArrayList<IManager>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (Reference.DEBUG_MODE) {			
			System.out.println(Runtime.getRuntime().maxMemory());
			System.err.println("x: " + Screen.width + "y: " + Screen.height);
		}
		new GameEngine();
	}
	
	public static void startGameEngine() {
		new GameEngine();
	}
	
	public GameEngine() {
		sdtInstance = new ShutdownThread();
		Runtime.getRuntime().addShutdownHook(sdtInstance);
		rand = new Random();
		initialize();
		
		run();
		while (running) {
			run();
		}
	}
	
	private void initialize() {
//		TrainUpdateManager m = new TrainUpdateManager();
//		managers.add(m);
		LineManager m1 = new LineManager();
		managers.add(m1);

		initGraphics();
		
		System.gc();
		instance = this;
	}
	
	private void initGraphics() {
		frame = new JFrame(Reference.FRAME_TILE);
		frame.setBounds(0, 0, Screen.width, Screen.height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rlo = new RenderLockObject();
		rtInstance = new RenderThread("RenderThread", rlo, this);
		rtInstance.start();
		
		i = new BufferedImage(Screen.width, Screen.height, BufferedImage.TYPE_INT_RGB);
		g2 = i.createGraphics();
		g = frame.getGraphics();
	}

	public void update(double deltaTime) {
		updateManagers(deltaTime);
		switch (state) {
			case JOB:
				updateJob(deltaTime);
				break;
			case MAP:
				updateMap(deltaTime);
				break;
			case MENU:
				updateMenu(deltaTime);
				break;
			case STATION:
				updateStation(deltaTime);
				break;
			case STATION_MENU:
				updateStationMenu(deltaTime);
				break;
			case TRAIN_MENU:
				updateTrainMenu(deltaTime);
				break;
			default:
				break;
		}
//		for(GameObject gameObject: updateableAndDrawable) {
//			gameObject.update(deltaTime);
//		}
//		
//		for(IUpdateable upject : updateable) {
//			upject.update(deltaTime);
//		}
	}
	
	public void render(Graphics g, ImageObserver observer) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, Screen.width, Screen.height);
		switch (state) {
			case JOB:
				renderJob(g, observer);
				break;
			case MAP:
				renderMap(g, observer);
				break;
			case MENU:
				renderMenu(g, observer);
				break;
			case STATION:
				renderStation(g, observer);
				break;
			case STATION_MENU:
				renderStationMenu(g, observer);
				break;
			case TRAIN_MENU:
				renderTrainMenu(g, observer);
				break;
			default:
				break;
		}
//		for(IDrawable staject : drawable) {
//			staject.draw(g, observer);
//		}
//		
//		for(GameObject gameObject : updateableAndDrawable) {
//			gameObject.draw(g2, observer);
//		}
		if (Reference.DEBUG_MODE) {
			g2.setColor(Color.GREEN);
			g2.setFont(new Font("Arial", 1, 20));
			g2.drawString(stats, 25, 60);			
		}
		
		g.drawImage(i, 0, 0, frame);
	}

	private void run() {
		long time = System.nanoTime();
		final double tick = 60.0;
		double ms = 1000000000 / tick;
		double deltaTime = 0;
		int ticks = 0;
		int fps = 0;
		long timer = System.currentTimeMillis();
		long frameLimit = 80;
		long currentTime;
		int seconds = 0;
		int minutes = 0;
		int hours = 0;

		while (running) {
			
			currentTime = System.nanoTime();
			deltaTime += (currentTime - time) / ms;
			time = currentTime;

			if (deltaTime >= 1) {
				ticks++;
				update(deltaTime);
				deltaTime--;
			}
			
			synchronized (rlo) {
				rlo.setWasNotified(true);
				rlo.notify();
			}
			fps++;
			
			while (deltaTime < frameLimit) {
				currentTime = System.nanoTime();
				deltaTime += (currentTime - time) / ms;
				time = currentTime;
			}
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				seconds++;
				if(seconds > 60) {
					seconds %= 60;
					minutes++;
					
					if(minutes > 60) {
						minutes %= 60;
						hours++;
					}
				}
				
				// GT stands for GameTime. P.C stands for Player coordinates
				stats = "Ticks: " + ticks + ", FPS: " + fps + ", GT: " + ((hours < 10) ? "0" + hours : hours) + ":" + ((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds);
				if (Reference.DEBUG_MODE) {					
					System.out.println(stats);
				}
				ticks = 0;
				fps = 0;
				if (Reference.DEBUG_MODE) {					
					System.out.println(Runtime.getRuntime().freeMemory());
				}
				System.gc();
				if (Reference.DEBUG_MODE) {					
					System.out.println(Runtime.getRuntime().freeMemory());
				}
			}
		}
	}
	
	private void updateManagers(double deltaTime) {
		for (IManager manager : managers) {
			manager.update(deltaTime);
		}
	}
	
	private void updateStation(double deltaTime) {
		// TODO implement
	}
	
	private void updateMap(double deltaTime) {
		// TODO implement
	}
	
	private void updateJob(double deltaTime) {
		// TODO implement
	}
	
	private void updateStationMenu(double deltaTime) {
		// TODO implement
	}
	
	private void updateMenu(double deltaTime) {
		// TODO implement
	}
	
	private void updateTrainMenu(double deltaTime) {
		// TODO implement
	}
	
	private void renderStation(Graphics g, ImageObserver observer) {
		// TODO implement
	}
	
	private void renderMap(Graphics g, ImageObserver observer) {
		// TODO implement
	}
	
	private void renderJob(Graphics g, ImageObserver observer) {
		// TODO implement
	}
	
	private void renderStationMenu(Graphics g, ImageObserver observer) {
		// TODO implement
	}
	
	private void renderMenu(Graphics g, ImageObserver observer) {
		// TODO Enum for different submenus
	}
	
	private void renderTrainMenu(Graphics g, ImageObserver observer) {
		// TODO implement
	}
	
	public enum EnumRenderState {
		STATION,
		STATION_MENU,
		TRAIN_MENU,
		MAP,
		JOB, 
		MENU;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Graphics getG() {
		return g;
	}
	
	public Graphics getG2() {
		return g2;
	}
	
	public static GameEngine getInstance() {
		return instance;
	}
	
	public static boolean isRunning() {
		return running;
	}
}

/*

-XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=stackdepth=1024 -XX:StartFlightRecording=duration=60m,filename=PocketTrains.jfr
*/