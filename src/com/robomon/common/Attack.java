package com.robomon.common;

public enum Attack {
	EMPTY(-1), PUNCH(1,1), COWER(0);
	// Set to 1 if offensive or 0 if defensive
	public int offensive;
	public int power;
	
	private Attack(int offensive) {
		this.offensive = offensive;
	}
	
	private Attack(int offensive, int power){
		this.offensive = offensive;
		this.power = power;
	}
	
	public  String toString() {
		if (this.offensive == -1){
			return "";
		} else {
			return this.name();
		}
	}
}