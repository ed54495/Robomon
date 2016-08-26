package com.robomon.common;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public enum Map {
	
	GRASS(1,1), 
	WATER(11,1),
	WATER_TOP_LEFT(10, 0),
	WATER_TOP(11, 0),
	WATER_TOP_RIGHT(12, 0),
	WATER_MID_LEFT(10, 1),
	WATER_MID_RIGHT(12, 1),
	WATER_BOTTOM_LEFT(10, 2),
	WATER_BOTTOM(11, 2),
	WATER_BOTTOM_RIGHT(12,2),
	
	WATER_DIRT_BOTTOM_RIGHT(13, 0),
	WATER_DIRT_BOTTOM_LEFT(14, 0),
	WATER_DIRT_TOP_RIGHT(13,1),
	WATER_DIRT_TOP_LEFT(14, 1),
	
	DIRT(6, 1),
	EMPTY(8,2),
	
	BROWN_ROOF_LEFT_BACK_SLANT(2, 6), 
	BROWN_ROOF_RIGHT_BACK_SLANT(3, 6),
	BROWN_ROOF_LEFT_MID_SLANT(2, 7),
	BROWN_ROOF_RIGHT_MID_SLANT(3, 7), 
	BROWN_ROOF_LEFT_FRONT_SLANT(2, 8),
	BROWN_ROOF_RIGHT_FRONT_SLANT(3, 8),
	BROWN_ROOF_POINT_FRONT(4, 6),
	BROWN_ROOF_POINT_MID(5, 6),
	BROWN_ROOF_POINT_BACK(6, 6),
	
	BROWN_ROOF_LEFT_MID_FLAT(4, 7),
	BROWN_ROOF_MID_MID_FLAT(5, 7),
	BROWN_ROOF_RIGHT_MID_FLAT(6, 7),
	BROWN_ROOF_LEFT_BOTTOM_FLAT(4, 8),
	BROWN_ROOF_MID_FRONT_FLAT(5, 8),
	BROWN_ROOF_RIGHT_BOTTOM_FLAT(6, 8),
	
	WHITE_LEFT_TOP(4, 4), 
	WHITE_MID_TOP(5, 4),
	WHITE_RIGHT_TOP(6, 4),
	WHITE_LEFT_MID(4, 3),
	WHITE_MID_MID(5, 3),
	WHITE_RIGHT_MID(6, 3),
	
	WHITE_BOARDS_LEFT(0, 5),
	WHITE_BOARDS_MID(1, 5),
	WHITE_BOARDS_RIGHT(2, 5),
	WHITE_BOARDS_SINGLE(3, 5),
	
	GREY_LEFT_TOP(13, 4),
	GREY_MID_TOP(14, 4),
	GREY_RIGHT_TOP(15, 4),
	GREY_LEFT_MID(13, 3),
	GREY_MID_MID(14, 3),
	GREY_RIGHT_MID(15, 3),
	
	ROUND_WOOD_DOOR(14, 10),
	WOOD_WINDOW(10, 11);
	
	public int x;
	public int y;
	
	public static SpriteSheet mapSprites;
	
	static {
		try {
			mapSprites = new SpriteSheet("resources/images/mapSprites.png", 64, 64);
		} catch (SlickException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	private Map(int x, int y){
		this.x = x;
		this.y = y;
	}
}
