package com.robomon;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import com.robomon.common.Attack;

public class Fighter {

	public String name = "bill";
	
	// sprite choices
	private int skinChoice = 0;
	private int pantsChoice = 0;
	private int shoeChoice = 0;
	private int[] shirtChoice = {0, 0};
	private int[] shirtColorChoice = {0, 0};
	private int[] hairChoice = {0, 0};
	private int[] hairColorChoice = {0, 0};
	private int[] shield = {0, 0};
	private int[] weapon = {0, 0};
	private int mouthOpen = 0;
	
	// List of attacks
	public Attack[] attacks = {Attack.PUNCH, Attack.COWER, Attack.EMPTY, Attack.EMPTY};
	
	// Fighter stats
	private int level = 1;
	private int hp = 10;
	private int maxhp = 10;
	private int xp = 0;
	private int maxXp = 50;
	private int strength = 1;
	
	private Random random = new Random();
	

	
	public Fighter(String name, int skin, int pants, int shoes, int[] shirt, int[] shirtColor, int[] hair, int[] hairColor) {
		this.name = name;
		skinChoice = skin;
		pantsChoice = pants;
		shoeChoice = shoes;
		shirtChoice = shirt;
		shirtColorChoice = shirtColor;
		hairChoice = hair;
		hairColorChoice = hairColor;
		this.setLevel(2);
	}
	
	
	public void drawFighter(Graphics g, SpriteSheet characterSelectSheet, int x, int y, int scale) {
		characterSelectSheet.getSubImage(mouthOpen, skinChoice).draw(x, y, scale);
		characterSelectSheet.getSubImage(3, pantsChoice).draw(x, y, scale);
		characterSelectSheet.getSubImage(4, shoeChoice).draw(x, y, scale);
		characterSelectSheet.getSubImage(6 + shirtChoice[0] + shirtColorChoice[0], shirtChoice[1] + shirtColorChoice[1]).draw(x, y, scale);
		characterSelectSheet.getSubImage(19 + hairChoice[0] + hairColorChoice[0], hairChoice[1] + hairColorChoice[1]).draw(x, y, scale);
		characterSelectSheet.getSubImage(33 + shield[0], shield[1]).draw(x, y, scale);
		characterSelectSheet.getSubImage(42 + weapon[0], weapon[1]).draw(x, y, scale);
	}

	// Calculates damage based on attack
	public int attackFighter(Attack a, Fighter opponent){
		if(a.offensive == 1){
			int damage = Math.round((50 + random.nextInt(50)) * (a.power * this.strength) / 100f);
			if(opponent.getHp() > damage){
				opponent.setHp(opponent.getHp() - damage);
			} else {
				opponent.setHp(0);
			}
			return damage;
		} else {
			return 0;
		}
	}
	
	// Auto attack method for computer fighters
	public Attack autoAttackFighter(Fighter opponent){
		if(random.nextInt(10) > 7){
			return Attack.COWER;
		} else {
			return Attack.PUNCH;
		}
	}
	
	// Set new level and calculate hp and strength values
	public void setLevel(int level) {
		this.level = level;
		this.maxhp = (int) Math.round(9 + Math.pow(level, 1.1) * 3);
		this.hp = maxhp;
		this.strength = 1 + level * 2;
		this.maxXp = (int) Math.round(10 + Math.pow(level, 1.3) * 2);
	}
	
	public int addXp(int addXp){
		int levels = 0;
		int newXp = this.xp + addXp;
		if(newXp >= this.maxXp){
			newXp = newXp - this.maxXp;
			this.setLevel(this.level + 1);
			levels++;
			levels += this.addXp(newXp);
		} else {
			this.xp = newXp;
		}
		return levels;
	}
	
	public int getXp(){
		return this.xp;
	}
	
	public int getMaxXp() {
		return this.maxXp;
	}

	public int[] getSheild() {
		return shield;
	}

	public int getMouthOpen() {
		return mouthOpen;
	}


	public void setMouthOpen(int mouthOpen) {
		this.mouthOpen = mouthOpen;
	}
	
	public void setSheild(int[] sheild) {
		this.shield = sheild;
	}


	public int[] getWeapon() {
		return weapon;
	}


	public void setWeapon(int[] weapon) {
		this.weapon = weapon;
	}


	public int getHp() {
		return hp;
	}


	public void setHp(int hp) {
		this.hp = hp;
	}


	public int getMaxhp() {
		return maxhp;
	}


	public void setMaxhp(int maxhp) {
		this.maxhp = maxhp;
	}
	
	public int getLevel() {
		return this.level;
	}
	

}
