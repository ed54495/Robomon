package com.robomon.common;

public enum Map {

	GRASS(5,0), WATER(0,0);
	
	public int x;
	public int y;
	
	private Map(int x, int y){
		this.x = x;
		this.y = y;
	}
}
