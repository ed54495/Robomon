package com.robomon.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.robomon.common.Buildings;
import com.robomon.common.Map;

public class IntroScreen extends BasicGameState {
	
	private RoundedRectangle newGame;
	private RoundedRectangle continueGame;
	private Boolean startNewGame = false;
	private Boolean loadGame = false;
	
	private int spriteSize = 70;
	private int buildingSpriteSize = 26;
	private SpriteSheet mapSprites;
	private Map[][] map = {{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER_TOP_LEFT, Map.WATER_TOP, Map.WATER_TOP_RIGHT},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER_TOP_LEFT, Map.WATER_TOP, Map.WATER_DIRT_TOP_LEFT, Map.WATER, Map.WATER_DIRT_TOP_RIGHT},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER_MID_LEFT, Map.WATER, Map.WATER, Map.WATER, Map.WATER},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER_BOTTOM_LEFT, Map.WATER_DIRT_BOTTOM_LEFT, Map.WATER, Map.WATER, Map.WATER},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER_MID_LEFT, Map.WATER, Map.WATER, Map.WATER}};

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		newGame = new RoundedRectangle(150, 250, 150, 40, 10);
		continueGame = new RoundedRectangle(400, 250, 150, 40, 10);
		
		mapSprites = new SpriteSheet("resources/images/mapSprites.png", 64, 64);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		int x = 0;
		int y = 0;
		for(Map[] row: map){
			for(Map sprite: row){
				Map.mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, spriteSize, spriteSize);
				x += spriteSize;
			}
			x = 0;
			y += spriteSize;
		}
		Buildings.drawBuilding(25, 25, buildingSpriteSize, 3, 3, 3, Map.ROUND_WOOD_DOOR, Map.WOOD_WINDOW);

		g.setColor(Color.darkGray);
		g.fill(newGame);
		g.fill(continueGame);
		
		Font myFont = g.getFont();
		g.setColor(Color.white);
		g.draw(newGame);
		g.drawString("New Game", newGame.getCenterX() - myFont.getWidth("New Game")/2f,265);

		g.drawString("Fighter Guys!", 350 - myFont.getWidth("Fighter Guys!")/2f, 100);
		
		g.setColor(Color.gray);
		g.draw(continueGame);
		g.drawString("Continue", continueGame.getCenterX() - myFont.getWidth("Continue")/2f, 265);
		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int arg2) throws SlickException {
		if(startNewGame){
			sbg.enterState(Game.CHARACTER_CREATION);
		}
	}
	
	public void mouseClicked(int button, int x, int y, int clickCount){
		if(newGame.contains(x, y)){
			startNewGame = true;
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.INTRO_SCREEN;
	}

}
