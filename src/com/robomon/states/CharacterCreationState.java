package com.robomon.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.robomon.Fighter;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Font;

public class CharacterCreationState extends BasicGameState {
	
	private Image right;
	private Image left;
	public SpriteSheet characterSelectSheet;
	
	// Sprite selections
	private int characterChoice = 0;
	private int pantsChoice = 0;
	private int shoeChoice = 0;
	private int[] shirtChoice = {0, 0};
	private int[] shirtColorChoice = {0, 0};
	private int[] hairChoice = {0, 0};
	private int[] hairColorChoice = {0, 0};
	
	private int[] characterLocation = {100, 40};
	private int characterScale = 10;
	
	// List of customizable attributes
	private String[] attributes = {"Skin", "Hair Color", "Hair Style","Shirt Color", "Shirt", "Pants", "Shoes"};
	private int[] attributeLocation = {500, 50};
	
	private ArrayList<Shape> arrows = new ArrayList<Shape>();

	// Ready button
	private RoundedRectangle start;
	private Boolean ready = false;
	
	private ParticleSystem system;
	private ConfigurableEmitter emitter;

	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		characterSelectSheet = new SpriteSheet("resources/images/characters.png", 16, 16, 1);
		right = new Image("resources/images/right.png");
		left = new Image("resources/images/left.png");
		
		// Create rectangles to detect which arrow was clicked
		for(int i = 0; i < attributes.length; i++){
			arrows.add(new Rectangle(attributeLocation[0] - 75 - 13.5f, attributeLocation[1] + i * 25,
					25, 25));
			arrows.add(new Rectangle(attributeLocation[0] + 75 - 13.5f, attributeLocation[1] + i * 25,
					25, 25));
		}
		
		start = new RoundedRectangle(400, 275, 200, 75, 10);

		//load the particle and set up effects
		try {
			Image image = new Image("resources/images/particle.png", false);
			system = new ParticleSystem(image,300);
			
			File xmlFile = new File("resources/effects/staff_emitter.xml");
			emitter = ParticleIO.loadEmitter(xmlFile);
			emitter.setPosition(characterLocation[0] + 15, characterLocation[1] + 25);
			system.addEmitter(emitter);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		system.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);	
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		characterSelectSheet.getSubImage(0, characterChoice).draw(characterLocation[0], characterLocation[1], characterScale);
		characterSelectSheet.getSubImage(3, pantsChoice).draw(characterLocation[0], characterLocation[1], characterScale);
		characterSelectSheet.getSubImage(4, shoeChoice).draw(characterLocation[0], characterLocation[1], characterScale);
		characterSelectSheet.getSubImage(6 + shirtChoice[0] + shirtColorChoice[0], shirtChoice[1] + shirtColorChoice[1]).draw(characterLocation[0], characterLocation[1], characterScale);
		characterSelectSheet.getSubImage(19 + hairChoice[0] + hairColorChoice[0], hairChoice[1] + hairColorChoice[1]).draw(characterLocation[0], characterLocation[1], characterScale);
		characterSelectSheet.getSubImage(33, 0).draw(characterLocation[0], characterLocation[1], characterScale);
		characterSelectSheet.getSubImage(42, 0).draw(characterLocation[0], characterLocation[1], characterScale);
		
		// get font to be able to center text
		Font myFont = g.getFont();
		
		// render arrows and attribute list
		for(int i = 0; i < attributes.length; i++){
			left.draw(attributeLocation[0] - 75 - 13.5f, (attributeLocation[1] + i * 25), .25f);
			g.drawString(attributes[i], attributeLocation[0] - myFont.getWidth(attributes[i])/2f, attributeLocation[1] + i * 25);
			right.draw(attributeLocation[0] + 75 - 13.5f, attributeLocation[1] + i * 25, .25f);
		}
		
		g.draw(start);
		g.drawString("Begin", 500 - myFont.getWidth("Begin")/2f, 310 - myFont.getHeight("Begin")/2f);
		
		// Render particle effects
		system.render();
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		// Let particleSystem object know framerate
		system.update(delta);
		
		// Move to next state when ready button is clicked
		if(ready) {
			sbg.enterState(Game.BATTLE);
		}
	}

	public void mouseClicked(int button, int x, int y, int clickCount){
		if(button == 0){
			// change sprites base on user input
			if(arrows.get(0).contains(x, y)){
				changeSkin(0);
			} else if(arrows.get(1).contains(x, y)){
				changeSkin(1);
			} else if(arrows.get(2).contains(x, y)){
				changeHairColor(0);
			} else if(arrows.get(3).contains(x, y)){
				changeHairColor(1);
			} else if(arrows.get(4).contains(x, y)){
				changeHairStyle(0);
			} else if(arrows.get(5).contains(x, y)){
				changeHairStyle(1);
			} else if(arrows.get(6).contains(x, y)){
				changeShirtColor(0);
			} else if(arrows.get(7).contains(x, y)){
				changeShirtColor(1);
			} else if(arrows.get(8).contains(x, y)){
				changeShirt(0);
			} else if(arrows.get(9).contains(x, y)){
				changeShirt(1);
			} else if(arrows.get(10).contains(x, y)){
				changePants(0);
			} else if(arrows.get(11).contains(x, y)){
				changePants(1);
			} else if(arrows.get(12).contains(x, y)){
				changeShoes(0);
			} else if(arrows.get(13).contains(x, y)){
				changeShoes(1);
			}
			
			// If ready button pressed set up battle with new opponent
			if(start.contains(x, y)){
				Game.fighter = new Fighter(characterChoice, pantsChoice, shoeChoice, shirtChoice, shirtColorChoice, hairChoice, hairColorChoice);
				Game.fighter.setLevel(5);
				Game.battle.setCharacter(Game.fighter);
				Fighter opponent = new Fighter(3, 1, 1, new int[] {0, 0}, new int[] {4, 5}, new int[] {1, 2}, new int[] {0, 8});
				opponent.setSheild(new int[] {2,2});
				opponent.setWeapon(new int[] {1, 0});
				Game.battle.setOpponent(opponent);
				ready = true;
			}
		}
	}
	
	private void changeSkin(int direction) {
		if(direction == 0) {
			if(characterChoice == 0){
				characterChoice = 3;
			} else {
				characterChoice--;
			}
		} else if(direction == 1) {
			if(characterChoice == 3){
				characterChoice = 0;
			} else {
				characterChoice++;
			}
		}
	}
	
	private void changeHairColor(int direction) {
		if(direction == 0) {
			if(hairColorChoice[0] == 0){
				if(hairColorChoice[1] == 0){
					hairColorChoice[1] = 8;
				} else {
					hairColorChoice[1] -= 4;
					hairColorChoice[0] = 4;
				}
			} else {
				hairColorChoice[0] = 0;
			}
		} else if(direction == 1) {
			if(hairColorChoice[0] == 0){
				if(hairColorChoice[1] == 8){
					hairColorChoice[0] = 0;
					hairColorChoice[1] = 0;
				} else {
					hairColorChoice[0] = 4;
				}
			} else {
				hairColorChoice[0] = 0;
				hairColorChoice[1] += 4;
			}
		}
	}
	
	private void changeHairStyle(int direction) {
		if(direction == 0) {
			if(hairChoice[0] == 0){
				if(hairChoice[1] == 0){
					hairChoice[0] = 3;
					hairChoice[1] = 3;
				} else {
					hairChoice[0] = 3;
					hairChoice[1]--;
				}
			} else {
				hairChoice[0]--;
			}
		} else if(direction == 1) {
			if(hairChoice[0] == 3){
				if(hairChoice[1] == 3){
					hairChoice[0] = 0;
					hairChoice[1] = 0;
				} else {
					hairChoice[0] = 0;
					hairChoice[1]++;
				}
			} else {
				hairChoice[0]++;
			}
		}
	}
	
	private void changeShirtColor(int direction) {
		if(direction == 0){
			if(shirtColorChoice[0] == 0){
				shirtColorChoice[0] = 8;
				if (shirtColorChoice[1] == 0){
					shirtColorChoice[1] = 5;
				} else {
					shirtColorChoice[1] -= 5;
				}
			} else {
				shirtColorChoice[0] -= 4;
			}
		} else if (direction == 1){
			if(shirtColorChoice[0] == 8){
				shirtColorChoice[0] = 0;
				if (shirtColorChoice[1] == 5){
					shirtColorChoice[1] = 0;
				} else {
					shirtColorChoice[1] += 5;
				}
			} else {
				shirtColorChoice[0] += 4;
			}
		}
	}
	
	private void changeShirt(int direction) {
		if(direction == 0) {
			if(shirtChoice[0] == 0){
				if(shirtChoice[1] == 0){
					shirtChoice[0] = 3;
					shirtChoice[1] = 4;
				} else {
					shirtChoice[0] = 3;
					shirtChoice[1]--;
				}
			} else {
				shirtChoice[0] --;
			}
		} else if(direction == 1) {
			if(shirtChoice[0] == 3){
				if(shirtChoice[1] == 4){
					shirtChoice[0] = 0;
					shirtChoice[1] = 0;
				} else {
					shirtChoice[0] = 0;
					shirtChoice[1]++;
				}
			} else {
				shirtChoice[0]++;
			}
		}
	}
	
	private void changePants(int direction) {
		if(direction == 0) {
			if(pantsChoice == 0){
				pantsChoice = 8;
			} else {
				pantsChoice--;
			}
		} else if(direction == 1) {
			if(pantsChoice == 8){
				pantsChoice = 0;
			} else {
				pantsChoice++;
			}
		}
	}
	
	private void changeShoes(int direction) {
		if(direction == 0) {
			if(shoeChoice == 0){
				shoeChoice = 8;
			} else {
				shoeChoice--;
			}
		} else if(direction == 1) {
			if(shoeChoice == 8){
				shoeChoice = 0;
			} else {
				shoeChoice++;
			}
		}
	}
	
	@Override
	public int getID() {
		return Game.CHARACTER_CREATION;
	}

}
