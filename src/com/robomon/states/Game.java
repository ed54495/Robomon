package com.robomon.states;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.robomon.Battle;
import com.robomon.Fighter;

public class Game extends StateBasedGame {
	
	public static int screenWidth = 700;
	public static int screenHeight = 400;
	public static Fighter fighter;
	public static Battle battle = new Battle();
	
	public static final int CHARACTER_CREATION = 1;
	public static final int BATTLE = 2;
	public static final int VICTORY = 3;
	public static final int OVERWORLD = 4;
	public static final int OVER = 5;

	public Game(String name) {
		super(name);
	}

	public static void main(String[] args) throws SlickException {
		// Set location of native library.  change to /linux for linux computer
		System.setProperty("org.lwjgl.librarypath", new File("libraries/native/macosx").getAbsolutePath());
		
		// Create and start game
		AppGameContainer app = new AppGameContainer(new Game("Robomon"));
		
		app.setDisplayMode(screenWidth, screenHeight, false);
		app.setAlwaysRender(true);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new CharacterCreationState());
		this.addState(new Overworld());
		this.addState(battle);
		this.addState(new Victory());
	}
	
}
