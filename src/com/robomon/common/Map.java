package com.robomon.common;

public enum Map {

	GRASS(5,0), 
	WATER(0,0),
	EMPTY(0,5),
	WHITE_ROOF_LEFT_BACK(13, 21), 
	WHITE_ROOF_RIGHT_BACK(14, 21),
	WHITE_ROOF_LEFT_MID(13, 22),
	WHITE_ROOF_RIGHT_MID(14, 22), 
	WHITE_ROOF_LEFT_FRONT(13, 23),
	WHITE_ROOF_RIGHT_FRONT(14, 23),
	WHITE_LEFT_TOP(17, 21), 
	WHITE_MID_TOP(18, 21),
	WHITE_RIGHT_TOP(19, 21),
	WHITE_LEFT_MID(17, 22),
	WHITE_MID_MID(18, 22),
	WHITE_RIGHT_MID(19, 22),
	WHITE_LEFT_BOTTOM(17, 23),
	WHITE_MID_BOTTOM(18, 23),
	WHITE_RIGHT_BOTTOM(19, 23),
	WHITE_SMALL_LIP(15, 22),
	BROWN_ROOF_LEFT_BACK(20, 21), 
	BROWN_ROOF_RIGHT_BACK(21, 21),
	BROWN_ROOF_LEFT_MID(20, 22),
	BROWN_ROOF_RIGHT_MID(21, 22), 
	BROWN_ROOF_LEFT_FRONT(20, 23),
	BROWN_ROOF_RIGHT_FRONT(21, 23),
	BROWN_LEFT_TOP(24, 21), 
	BROWN_MID_TOP(25, 21),
	BROWN_RIGHT_TOP(26, 21),
	BROWN_LEFT_MID(24, 22),
	BROWN_MID_MID(25, 22),
	BROWN_RIGHT_MID(26, 22),
	BROWN_LEFT_BOTTOM(24, 23),
	BROWN_MID_BOTTOM(25, 23),
	BROWN_RIGHT_BOTTOM(26, 23),
	BROWN_SMALL_LIP(22, 22),
	
	ROUND_WOOD_DOOR(32, 0),
	WOOD_WINDOW(40, 0);
	
	public int x;
	public int y;
	
	private Map(int x, int y){
		this.x = x;
		this.y = y;
	}
}
