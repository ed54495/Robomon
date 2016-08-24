package com.robomon.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.robomon.common.Map;

public class Overworld extends BasicGameState {
	
	private int spriteSize = 70;
	private SpriteSheet mapSprites;
	private Map[][] map = {{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.WATER, Map.WATER, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS},
			{Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS, Map.GRASS}};

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		mapSprites = new SpriteSheet("resources/images/mapSprites.png", 16, 16, 1);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
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
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return Game.OVERWORLD;
	}

}
