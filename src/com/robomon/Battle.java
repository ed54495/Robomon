package com.robomon;

import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.robomon.common.Attack;
import com.robomon.states.Game;

public class Battle extends BasicGameState {
	
	private Fighter opponent;
	private Fighter character;
	private int[] opponentLocation = {440, 60};
	private int[] characterLocation = {100, 60};
	private int opponentStartX = opponentLocation[0];
	private int characterStartX = characterLocation[0];
	private SpriteSheet characterSelectSheet;
	private int delay;
	
	// health bars
	private RoundedRectangle characterHealth;
	private RoundedRectangle characterHealthOutline;
	private RoundedRectangle opponentHealth;
	private RoundedRectangle opponentHealthOutline;
	
	// HUD objects
	private RoundedRectangle characterHUD;
	private RoundedRectangle opponentHUD;
	private Rectangle buttonOne;
	private Boolean fightSelected = false;
	
	// Boolean values to trigger hit animations
	private Boolean opponentHit = false;
	private Boolean characterHit = false;
	private Boolean frozen = false;

	private ParticleSystem system;
	private ConfigurableEmitter characterEmitter;
	private ConfigurableEmitter opponentEmitter;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		characterHUD = new RoundedRectangle(20, 280, 320, 100, 10);
		opponentHUD = new RoundedRectangle(360, 280, 320, 100, 10);
		buttonOne = new Rectangle(30, 290, 120, 40);
		
		characterSelectSheet = new SpriteSheet("resources/images/characters.png", 16, 16, 1);
		characterHealth = new RoundedRectangle(30, 260, 160, 10, 5);
		opponentHealth = new RoundedRectangle(370, 260, 160, 10, 5);
		characterHealthOutline = new RoundedRectangle(30, 260, 160, 10, 5);
		opponentHealthOutline = new RoundedRectangle(370, 260, 160, 10, 5);
		
		try {
			//load the test particle and 
			Image image = new Image("resources/images/particle.png", false);
			system = new ParticleSystem(image,800);
			
			File xmlFile = new File("resources/effects/staff_emitter.xml");
			characterEmitter = ParticleIO.loadEmitter(xmlFile);
			characterEmitter.setPosition(characterLocation[0] + 15, characterLocation[1] + 25);
			system.addEmitter(characterEmitter);
			opponentEmitter = ParticleIO.loadEmitter(xmlFile);
			opponentEmitter.setPosition(opponentLocation[0] + 15, opponentLocation[1] + 25);
			system.addEmitter(opponentEmitter);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		system.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		g.draw(characterHUD);
		g.draw(opponentHUD);
		
		character.drawFighter(g, characterSelectSheet, characterLocation[0], characterLocation[1], 10);
		opponent.drawFighter(g, characterSelectSheet, opponentLocation[0], opponentLocation[1], 10);
		
		// Draw health bars
		g.setColor(Color.red);
		g.fill(characterHealth);
		g.fill(opponentHealth);
		g.setColor(Color.white);
		g.draw(characterHealthOutline);
		g.draw(opponentHealthOutline);
		
		g.drawString(character.getHp() + "/" + character.getMaxhp(), 200, 255);
		g.drawString(opponent.getHp() + "/" + opponent.getMaxhp(), 540, 255);
		
		// Choose which menu to display
		if(fightSelected) {
			g.drawString("Punch", 40,  300);
			g.drawString("Cower in fear", 40,  335);
		} else {
			g.drawString("Fight", 40,  300);
			g.drawString("Inventory", 40,  335);
		}
		
		// renders particle effects
		system.render();
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		// Let particleSystem object know framerate
		system.update(delta);
		
		// Check if opponents hit animation is happening
		if(opponentHit){
			opponent.setMouthOpen(1);
			if(delay > 0){
				delay -= delta;
				shakeOpponent(delay, opponentStartX);
			} else {
				delay = 1500;
				opponentLocation[0] = opponentStartX;
				opponent.setMouthOpen(0);
				opponentHealth.setWidth(((float)opponent.getHp()/opponent.getMaxhp()) * opponentHealthOutline.getWidth());
				opponentHit = false;
				if(opponent.getHp() <= 0){
					sbg.enterState(Game.VICTORY, new FadeOutTransition(), new FadeInTransition());
				}
			}
		} 
		// Check if users characters hit animation is happening
		else if(characterHit){
			if(delay > 1000){
				delay -= delta;
			} else if(delay > 0){
				character.setMouthOpen(1);
				delay -= delta;
				shakeCharacter(delay, characterStartX);
			} else {
				delay = 0;
				characterLocation[0] = characterStartX;
				character.setMouthOpen(0);
				characterHealth.setWidth((float)character.getHp()/character.getMaxhp() * characterHealthOutline.getWidth());
				characterHit = false;
				if(character.getHp() <= 0){
					sbg.enterState(Game.OVER, new FadeOutTransition(), new FadeInTransition());
				}
			}
		}
	}

	public void mouseClicked(int button, int x, int y, int clickCount){
		// Freeze inputs if animation is happening
		if(!frozen && !characterHit && !opponentHit){
			// Check if button one (fight button) is clicked
			if(!fightSelected && buttonOne.contains(x, y)){
				fightSelected = true;
			} 
			// Checks if attack in first position is clicked
			else if (fightSelected && buttonOne.contains(x,y)){
				character.attackFighter(Attack.PUNCH, opponent);
				opponentHit = true;
				delay = 1000;
				fightSelected = false;
				
				opponent.autoAttackFighter(character);
				characterHit = true;
			}
		}
	}
	
	// moves opponent fighter when hit
	private void shakeOpponent(int delay, int startX){
		if(delay > 500){
			opponentLocation[0] = startX + (1000 - delay)/20;
			
		} else {
			opponentLocation[0] = startX + delay/20;
		}
		opponentEmitter.setPosition(opponentLocation[0] + 15, opponentLocation[1] + 25, false);
	}
	
	// Moves users fighter when hit
	private void shakeCharacter(int delay, int startX){
		if(delay > 500){
			characterLocation[0] = startX - (1000 - delay)/20;
			
		} else {
			characterLocation[0] = startX - delay/20;
		}
		characterEmitter.setPosition(characterLocation[0] + 15, characterLocation[1] + 25, false);
	}
	
	@Override
	public int getID() {
		return Game.BATTLE;
	}

	public Fighter getOpponent() {
		return opponent;
	}

	public void setOpponent(Fighter opponent) {
		this.opponent = opponent;
	}

	public Fighter getCharacter() {
		return character;
	}

	public void setCharacter(Fighter character) {
		this.character = character;
	}

}
