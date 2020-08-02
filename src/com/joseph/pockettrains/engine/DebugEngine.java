package com.joseph.pockettrains.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.joseph.pockettrains.gameobject.RenderLockObject;
import com.joseph.pockettrains.gui.DebugConsoleOverlay;
import com.joseph.pockettrains.gui.LogicDebugGui;
import com.joseph.pockettrains.handlers.DebugGKELAH;
import com.joseph.pockettrains.interfaces.IManager;
import com.joseph.pockettrains.managers.LineManager;
import com.joseph.pockettrains.reference.Reference;
import com.joseph.pockettrains.screen.Screen;
import com.joseph.pockettrains.threads.DebugRenderThread;
import com.joseph.pockettrains.threads.ShutdownThread;
import com.joseph.pockettrains.track.Tracks;

public class DebugEngine {
	public static Random rand;
	private static DebugEngine instance;
	private static String stats = "";
	private static boolean running = true;

	private BufferedImage i;
	private Graphics g;
	private Graphics g2;
	private JFrame frame;
	private LogicDebugGui logicManager;
	private DebugConsoleOverlay dcgo;
	private DebugGKELAH dgkelah;
	private FontRenderContext frc;
	
	private RenderLockObject rlo;
	private DebugRenderThread rtInstance;
	private ShutdownThread sdtInstance;
	
	private static ArrayList<IManager> managers = new ArrayList<IManager>();
	
	public static void main(String[] args) {
		new DebugEngine();
	}
	
	public DebugEngine() {
		sdtInstance = new ShutdownThread();
		Runtime.getRuntime().addShutdownHook(sdtInstance);
		rand = new Random();
		Tracks.init();
		initialize();
		
		run();
		while (running) {
			run();
		}
	}
	
	private void initialize() {
		instance = this;
		initGraphics();

//		TrainUpdateManager m = new TrainUpdateManager();
//		managers.add(m);
		LineManager m1 = new LineManager();
		managers.add(m1);
		dcgo = new DebugConsoleOverlay(g);
		
		Graphics2D g2d = (Graphics2D) g;
		this.frc = g2d.getFontRenderContext();
		
		System.gc();
	}
	
	private void initGraphics() {
		frame = new JFrame(Reference.FRAME_TILE);
		frame.setBounds(0, 0, Screen.width, Screen.height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dgkelah = new DebugGKELAH();
		frame.addKeyListener(dgkelah);
		
		rlo = new RenderLockObject();
		rtInstance = new DebugRenderThread("RenderThread", rlo, this);
		rtInstance.start();
		
		logicManager = new LogicDebugGui();
		
		i = new BufferedImage(Screen.width, Screen.height, BufferedImage.TYPE_INT_RGB);
		g2 = i.createGraphics();
		g = frame.getGraphics();
	}
	
	private void run() {
		final double tick = 60.0;
		double ms = 1000000000 / tick;
		double deltaTime = 0;
		int ticks = 0;
		int fps = 0;
		long frameLimit = 80;
		long currentTime;
		int seconds = 0;
		int minutes = 0;
		int hours = 0;
		long time = System.nanoTime();
		long timer = System.currentTimeMillis();

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
				if (seconds >= 60) {
					seconds %= 60;
					minutes++;
					
					if (minutes >= 60) {
						minutes %= 60;
						hours++;
					}
				}
				
				// GT stands for GameTime. P.C stands for Player coordinates
				stats = "Ticks: " + ticks + ", FPS: " + fps + ", GT: " + ((hours < 10) ? "0" + hours : hours) + ":" + ((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds);
				System.out.println(stats);
				ticks = 0;
				fps = 0;
				if (Reference.HARD_CORE_DEBUG_MODE) {
					System.out.println(Runtime.getRuntime().freeMemory());
				}
				System.gc();
				if (Reference.HARD_CORE_DEBUG_MODE) {
					System.out.println(Runtime.getRuntime().freeMemory());
				}
			}
		}
	}
	
	private void update(double deltaTime) {
		for (IManager manager : managers) {
			manager.update(deltaTime);
		}
		logicManager.updateUpdateableGraphicsElements(deltaTime);
		dcgo.updateUpdateableGraphicsElements(deltaTime);
	}
	
	public void render(Graphics g, ImageObserver observer) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, Screen.width, Screen.height);
		
		logicManager.drawGuiBackground(g2, observer);
		logicManager.drawUpdateableGraphicsElements(g2, observer);
		
		dcgo.drawGuiBackground(g2, observer);
		dcgo.drawUpdateableGraphicsElements(g2, observer);
		
		
		g2.setColor(Color.BLUE);
		g2.setFont(new Font("Arial", 1, 20));
		g2.drawString(stats, 25, 60);			
		
		g.drawImage(i, 0, 0, frame);
	}

	public JFrame getFrame() {
		return this.frame;
	}
	
	public Graphics getG() {
		return this.g;
	}
	
	public Graphics getG2() {
		return this.g2;
	}
	
	public FontRenderContext getFrc() {
		return frc;
	}
	
	public DebugConsoleOverlay getDcgo() {
		return this.dcgo;
	}
	
	public static DebugEngine getInstance() {
		return instance;
	}
	
	public void notifyCons(String s) {
//		this.dcm.handleCommand(s);
		
	}
}