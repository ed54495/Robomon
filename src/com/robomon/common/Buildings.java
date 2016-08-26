package com.robomon.common;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public enum Buildings {
	
	HOUSE(new Map[][] {{Map.WATER, Map.WATER},{Map.WATER, Map.WATER}}),
	
	BROWN_ROOF_ANGLED(new Map[][] {{Map.BROWN_ROOF_LEFT_BACK_SLANT, Map.BROWN_ROOF_RIGHT_BACK_SLANT},
		{Map.BROWN_ROOF_LEFT_MID_SLANT, Map.BROWN_ROOF_RIGHT_MID_SLANT},
		{Map.BROWN_ROOF_LEFT_FRONT_SLANT, Map.BROWN_ROOF_RIGHT_FRONT_SLANT}}),
	
	WHITE_WALL(new Map[][] {{Map.WHITE_LEFT_TOP, Map.WHITE_MID_TOP, Map.WHITE_RIGHT_TOP},
		{Map.WHITE_LEFT_MID, Map.WHITE_MID_MID, Map.WHITE_RIGHT_MID}});
	
	public Map[][] building;
	
	private static SpriteSheet mapSprites;
	
	static {
		try {
			mapSprites = new SpriteSheet("resources/images/mapSprites.png", 64, 64);
		} catch (SlickException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	private Buildings(Map[][] building) {
		this.building = building;
	}
	
	public static void drawBuilding(int x, int y, float spriteSize, int height, int width, int depth, Map door, Map window){
		drawWhiteBoards(x, Math.round(y + depth*spriteSize), spriteSize, height, width);
		drawBrownRoofAngled(x, y, spriteSize*1.5f, depth);
		mapSprites.getSubImage(door.x, door.y).draw((width * spriteSize/2 - spriteSize/2 + x), (depth + height)*spriteSize - spriteSize + y, spriteSize, spriteSize);
		mapSprites.getSubImage(window.x, window.y).draw((width* spriteSize/2 - spriteSize/2 + x), (depth + height)*spriteSize - 2.5f*spriteSize +y, spriteSize, spriteSize);
	}
	
	public static void drawWhiteBoards(int x, int y, float spriteSize, int height, int width){
		int xStart = x;
		int centerWidth = width - 2;
		while(height > 0){
			if(width == 1){
				mapSprites.getSubImage(Map.WHITE_BOARDS_SINGLE.x, Map.WHITE_BOARDS_SINGLE.y).draw(x, y, spriteSize, spriteSize);
			} else {
				mapSprites.getSubImage(Map.WHITE_BOARDS_LEFT.x, Map.WHITE_BOARDS_LEFT.y).draw(x, y, spriteSize, spriteSize);
				x += spriteSize;
				while(centerWidth > 0){
					mapSprites.getSubImage(Map.WHITE_BOARDS_MID.x, Map.WHITE_BOARDS_MID.y).draw(x, y, spriteSize, spriteSize);
					x += spriteSize;
					centerWidth--;
				}
				centerWidth = width - 2;
				mapSprites.getSubImage(Map.WHITE_BOARDS_RIGHT.x, Map.WHITE_BOARDS_RIGHT.y).draw(x, y, spriteSize, spriteSize);
			}
			x = xStart;
			y += spriteSize;
			height--;
		}
	}
	
	public static void drawBrownRoofAngled(int x, int y, float spriteSize, int depth){
		int xStart = x;
		depth -= 2;
		for(Map sprite : Buildings.BROWN_ROOF_ANGLED.building[0]){
			mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, spriteSize, spriteSize);
			x += spriteSize;
		}
		x = xStart;
		y += spriteSize;
		while(depth > 0){
			for(Map sprite : Buildings.BROWN_ROOF_ANGLED.building[1]){
				mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, spriteSize, spriteSize);
				x += spriteSize;
			}
			x = xStart;
			y += spriteSize;
			depth--;
		}
		for(Map sprite : Buildings.BROWN_ROOF_ANGLED.building[2]){
			mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, spriteSize, spriteSize);
			x += spriteSize;
		}
	}
}
