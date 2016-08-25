package com.robomon.common;

public enum Buildings {
	
	HOUSE(new Map[][] {{Map.WATER, Map.WATER},{Map.WATER, Map.WATER}}),
	
	WHITE_ROOF_ANGLED_3X3(new Map[][] {{Map.WHITE_ROOF_LEFT_BACK, Map.WHITE_MID_TOP, Map.WHITE_ROOF_RIGHT_BACK}, 
		{Map.WHITE_ROOF_LEFT_MID, Map.WHITE_MID_MID, Map.WHITE_ROOF_RIGHT_MID},
		{Map.WHITE_ROOF_LEFT_FRONT, Map.WHITE_SMALL_LIP, Map.WHITE_ROOF_RIGHT_FRONT}}),
	
	BROWN_HOUSE_3X3(new Map[][] {{Map.BROWN_LEFT_TOP, Map.BROWN_MID_TOP, Map.BROWN_RIGHT_TOP},
		{Map.BROWN_LEFT_MID, Map.BROWN_MID_MID, Map.BROWN_RIGHT_MID},
		{Map.BROWN_LEFT_BOTTOM, Map.BROWN_MID_BOTTOM, Map.BROWN_RIGHT_BOTTOM}});
	
	public Map[][] building;
	
	private Buildings(Map[][] building) {
		this.building = building;
	}
}
