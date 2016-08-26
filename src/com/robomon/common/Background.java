package com.robomon.common;

public enum Background {
	
	GREY_BRICKS(new Map[][] {{Map.GREY_LEFT_TOP, Map.GREY_MID_TOP, Map.GREY_MID_TOP, Map.GREY_MID_TOP, Map.GREY_MID_TOP, Map.GREY_MID_TOP, Map.GREY_MID_TOP, Map.GREY_MID_TOP, Map.GREY_MID_TOP, Map.GREY_RIGHT_TOP},
		{Map.GREY_LEFT_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_RIGHT_MID},
		{Map.GREY_LEFT_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_RIGHT_MID},
		{Map.GREY_LEFT_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_RIGHT_MID},
		{Map.GREY_LEFT_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_RIGHT_MID},
		{Map.GREY_LEFT_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_MID_MID, Map.GREY_RIGHT_MID}});	
	
	public Map[][] background;
	
	private Background(Map[][] background){
		this.background = background;
	}
	
	public static void drawGreyBrickBackground(int spriteSize) {
		int x = 0;
		int y = 0;
		for(Map[] row: Background.GREY_BRICKS.background){
			for(Map sprite: row){
				Map.mapSprites.getSubImage(sprite.x, sprite.y).draw(x, y, spriteSize, spriteSize);
				x += spriteSize;
			}
			x = 0;
			y += spriteSize;
		}
	}

}
