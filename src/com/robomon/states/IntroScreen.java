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
	private int buildingSpriteSize = 25;
	private SpriteSheet mapSprites;
	private Map[][] map = {{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER, Map.WATER, Map.WATER, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER, Map.GRASS, Map.GRASS}};

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		newGame = new RoundedRectangle(150, 250, 150, 40, 10);
		continueGame = new RoundedRectangle(400, 250, 150, 40, 10);
		
		mapSprites = new SpriteSheet("resources/images/mapSprites.png", 16, 16, 1);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		int x = 0;
		int y = 0;
		for(Map[] row: map){
			for(Map sprite: row){
				mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, spriteSize, spriteSize);
				x += spriteSize;
			}
			x = 0;
			y += spriteSize;
		}
		x = 25;
		int xEdge = 25;
		y = 25 + 2*buildingSpriteSize;
		for(Map[] row: Buildings.BROWN_HOUSE_3X3.building){
			for(Map sprite: row){
				mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, buildingSpriteSize, buildingSpriteSize);
				x += buildingSpriteSize;
			}
			x = xEdge;
			y += buildingSpriteSize;
		}
		x = 25;
		xEdge = 25;
		y = 25;
		for(Map[] row: Buildings.WHITE_ROOF_ANGLED_3X3.building){
			for(Map sprite: row){
				mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, buildingSpriteSize, buildingSpriteSize);
				x += buildingSpriteSize;
			}
			x = xEdge;
			y += buildingSpriteSize;
		}
		mapSprites.getSubImage(Map.ROUND_WOOD_DOOR.x, Map.ROUND_WOOD_DOOR.y).draw(25 + buildingSpriteSize, 25 + 4*buildingSpriteSize, buildingSpriteSize, buildingSpriteSize);
		mapSprites.getSubImage(Map.WOOD_WINDOW.x, Map.WOOD_WINDOW.y).draw(25 + buildingSpriteSize, 25 + Math.round(2.5*buildingSpriteSize), buildingSpriteSize, buildingSpriteSize);
		
		

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
