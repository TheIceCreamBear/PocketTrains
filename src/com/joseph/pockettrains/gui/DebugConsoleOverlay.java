package com.joseph.pockettrains.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;

import com.joseph.pockettrains.engine.DebugEngine;
import com.joseph.pockettrains.line.Line;
import com.joseph.pockettrains.managers.LineManager;
import com.joseph.pockettrains.reference.Reference;
import com.joseph.pockettrains.station.AbstractStation;
import com.joseph.pockettrains.station.Stations;
import com.joseph.pockettrains.track.Tracks;
import com.joseph.pockettrains.train.AbstractTrain;
import com.joseph.pockettrains.train.AbstractTrain.EnumTrainType;

public class DebugConsoleOverlay implements IGuiOverlay {
	private ArrayList<String> previousCommands;
	private Line selectedLine;
	private String command;
	private boolean cursor;
	private boolean changePrevious;
	private int cursorTick;
	private int previousIndex;
	private int height;
	private int length;
	private int x;
	private int y;
	
	public DebugConsoleOverlay(Graphics g) {
		this.command = "";
		this.previousCommands = new ArrayList<String>();
		this.previousCommands.add(command);
		this.previousIndex = 0;
		this.cursor = true;
		this.changePrevious = false;
		this.cursorTick = 0;
		this.x = 25;
		this.y = 750;
		this.height = 25;
		this.length = 1150;
	}
	
	@Override
	public void drawGuiBackground(Graphics g, ImageObserver observer) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, length, height);
	}
	
	@Override
	public void drawUpdateableGraphicsElements(Graphics g, ImageObserver observer) {
		g.setColor(Color.DARK_GRAY);
		g.setFont(Reference.TEXT_FONT);
		g.drawString(this.command, this.x, this.y + 20);
		if (this.cursor) {
			int xOffset = (int) Reference.TEXT_FONT.getStringBounds(command, DebugEngine.getInstance().getFrc()).getWidth() + 1;
			g.setColor(Reference.CURSOR_COLOR);
			g.fillRect(this.x + xOffset, this.y + 5, 2, 15);
		}
	}
	
	@Override
	public void updateUpdateableGraphicsElements(double deltaTime) {
		// TODO make insert of letter possible
		if (this.cursorTick > 0) {
			this.cursorTick--;
		}
		
		if (this.cursorTick == 0) {
			this.cursor = !this.cursor;
			this.cursorTick = 30;
		}
		
		if (this.previousIndex > 0 && this.changePrevious) {
			if (this.previousCommands.size() != 0 && this.previousCommands.size() >= this.previousIndex) {
				this.changePrevious = false;
				this.command = this.previousCommands.get(this.previousCommands.size() - this.previousIndex);
			}
		}
	}
	
	@Override
	public boolean removeGui() {
		return false;
	}
	
	@Override
	public void setGuiToRemove() {
	}
	
	public Line getSelectedLine() {
		return selectedLine;
	}
	
	public void notifyKeyTyped(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_TYPED) {
			char temp = e.getKeyChar();
			if ((int) temp == 8) { // BackSpace
				if (this.command.length() == 0) {
					return;
				}
				this.command = this.command.substring(0, this.command.length() - 1);
				return;
			}
			this.command += temp;
		}
	}
	
	public void notiftUP() {
		if (this.previousIndex == this.previousCommands.size() - 1 || this.previousCommands.size() == 0) {
			return;
		}
		this.previousIndex++;
		this.changePrevious = true;
		System.err.println(this.previousIndex);
	}
	
	public void notifyDown() {
		if (this.previousIndex == 0) {
			return;
		}
		this.previousIndex--;
		this.changePrevious = true;
		if (this.previousIndex == 0) {
			command = "";
		}
		System.err.println(this.previousIndex);
	}
	
	public void notifyEneter() {
		this.previousIndex = 0;
		String command = this.command;
		this.command = "";
		this.runCommand(command);
	}
	
	private void runCommand(String command) {
		if (command.length() == 0) {
			return;
		}
		this.previousCommands.add(command);
		String[] args = command.split(" ");
		if (Reference.DEBUG_MODE) {
			System.err.println(Arrays.toString(args));
		}
		try {
			switch (args[0].toLowerCase()) {
				case "line":
					line(args);
					break;
				case "list":
					list(args);
					break;
				case "clearcons":
					this.previousCommands.clear();
					this.previousIndex = 1;
					break;
				case "exit":
					System.exit(0);
				default:
					System.err.println("Invalid command");
					break;
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.err.println("ERROR: invalid command syntax for command " + e.getStackTrace()[0]);
			e.printStackTrace();
		}
	}
	
	private void line(String[] args) {
		switch (args[1].toLowerCase()) {
			case "claim":
				this.selectedLine.claimTrack(Tracks.REGISTRY.get(args[2].toLowerCase()));
				if (this.selectedLine.getTrain().getWaitingStation() == Stations.EMPTY) {
					this.selectedLine.getTrain().setWaitingStation(Tracks.REGISTRY.get(args[2].toLowerCase()).getStation1());
				}
				break;
			case "set":
				this.selectedLine = LineManager.getInstance().getLineByName(args[2]);
				System.out.println(this.selectedLine);
				break;
			case "add":
				lineAdd(args);
				break;
			case "destroy":
				LineManager.getInstance().deRegisterLine(LineManager.getInstance().getLineByName(args[2]), args[2]);
				break;
			case "train":
				lineTrain(args);
				break;
			default:
				System.err.println("Invalid Subcommand");
				break;
		}
	}
	
	private void lineAdd(String[] args) {
		Line l = (Line) null;
		AbstractTrain tmp = (AbstractTrain) null;
		String name = args[2];
		if (LineManager.getInstance().getLineByName(name) != null) {
			System.err.println("Name already in use.");
			return;
		}
		switch (args.length) {
			case 3: // list add <name>
				tmp = new AbstractTrain(EnumTrainType.BLUE_BELL);
				break;
			case 4: // list add <name> <type:int>
				tmp = new AbstractTrain(EnumTrainType.getTypeByID(Integer.parseInt(args[3])));
				break;
			case 5: // list add <name> <type:int> <engines>
				tmp = new AbstractTrain(EnumTrainType.getTypeByID(Integer.parseInt(args[3])));
				int engine = Integer.parseInt(args[4]);
				for (int i = 1; i < engine; i++) {
					tmp.addEngineCommand();
				}
				break;
			case 6: // list add <name> <type:int> <engines> <fuels>
				tmp = new AbstractTrain(EnumTrainType.getTypeByID(Integer.parseInt(args[3])));
				int engines = Integer.parseInt(args[4]);
				int fuelCars = Integer.parseInt(args[5]);
				for (int i = 1; i < engines; i++) {
					tmp.addEngineCommand();
				}
				for (int i = 0; i < fuelCars; i++) {
					tmp.addFuelCar();
				}
				break;
			default: 
				System.err.println("Invalid command syntax");
		}
		l = new Line(tmp);
		LineManager.getInstance().registerLine(l, name);
	}
	
	private void lineTrain(String[] args) {
		switch (args[2].toLowerCase()) {
			case "addengine":
				this.selectedLine.getTrain().addEngineCommand();
				break;
			case "addfuelcar":
				this.selectedLine.getTrain().addFuelCarCommand();
				break;
			case "addmission":
				lineTrainAddMission(args);
				break;
			case "destination":
				lineTrainDestination(args);
				break;
			case "start":
				this.selectedLine.getTrain().startMove();
				break;
			case "boost":
				if (this.selectedLine.getTrain().isOnMission()) {
					this.selectedLine.getTrain().boost();
				}
			default: 
				break;
		}
	}
	
	private void lineTrainAddMission(String[] args) {
		try {
			AbstractStation to = Stations.registry.get(args[4].toLowerCase());
			AbstractStation empty = Stations.EMPTY;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("derp");
		}
	}
	
	private void lineTrainDestination(String[] args) {
		switch (args[3].toLowerCase()) {
			case "add":
				this.selectedLine.addDestination(args[4].toLowerCase());
				break;
			case "clear":
				this.selectedLine.clearDestinations();
				break;
		}
	}
	
	private void list(String[] args) {
		switch (args[1].toLowerCase()) {
			case "train":
				break;
			default:
				break;
		}
	}
}