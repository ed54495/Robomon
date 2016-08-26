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
import com.robomon.common.Background;
import com.robomon.states.Game;

public class Battle extends BasicGameState {
	
	private Fighter opponent;
	private Fighter character;
	private int[] opponentLocation = {440, 60};
	private int[] characterLocation = {100, 60};
	private int opponentStartX = opponentLocation[0];
	private int characterStartX = characterLocation[0];
	private SpriteSheet characterSelectSheet;
	private int animationDelay;
	private Attack opponentAttack = Attack.EMPTY;
	private Attack characterAttack = Attack.EMPTY;
	private int opponentAttackDamage = 0;
	private int characterAttackDamage = 0;
	private int levelsGained = 0;
	
	// health bars
	private RoundedRectangle characterHealth;
	private RoundedRectangle characterHealthOutline;
	private RoundedRectangle opponentHealth;
	private RoundedRectangle opponentHealthOutline;
	private RoundedRectangle characterXp;
	private int characterStartXp;
	private RoundedRectangle characterXpOutline;
	
	// HUD objects
	private RoundedRectangle leftHUD;
	private RoundedRectangle rightHUD;
	private Rectangle buttonOne;
	private Rectangle buttonTwo;
	private Rectangle buttonThree;
	private Rectangle buttonFour;
	private Boolean showMenu = false;
	private Boolean fightSelected = false;
	private String[] mainMenu = {"FIGHT", "INVENTORY", "RUN", null};
	private String message = "";
	
	// Boolean values to trigger hit animations
	private Boolean opponentHit = false;
	private Boolean characterHit = false;
	private Boolean textFreeze = true;
	private Boolean awaitingClick = false;
	private Boolean awaitingSecodaryClick = false;
	private Boolean attackInProgress = false;
	private Boolean xpAnimation = false;

	private ParticleSystem system;
	private ConfigurableEmitter characterEmitter;
	private ConfigurableEmitter opponentEmitter;
	
	private Boolean victory = false;
	private Boolean failure = false;
	private int messageIndex = 0;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		leftHUD = new RoundedRectangle(20, 280, 320, 100, 10);
		rightHUD = new RoundedRectangle(360, 280, 320, 100, 10);
		buttonOne = new Rectangle(360, 280, 160, 50);
		buttonTwo = new Rectangle(360, 330, 160, 50);
		buttonThree = new Rectangle(520, 280, 160, 50);
		buttonFour = new Rectangle(520, 330, 160, 50);
		
		characterSelectSheet = new SpriteSheet("resources/images/characters.png", 16, 16, 1);
		characterHealth = new RoundedRectangle(30, 260, 160, 10, 5);
		opponentHealth = new RoundedRectangle(370, 260, 160, 10, 5);
		characterHealthOutline = new RoundedRectangle(30, 260, 160, 10, 5);
		opponentHealthOutline = new RoundedRectangle(370, 260, 160, 10, 5);
		characterXp = new RoundedRectangle(30, 240, 160, 10, 5);
		characterXpOutline = new RoundedRectangle(30, 240, 160, 10, 5);
		
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
		Background.drawGreyBrickBackground(Game.backgroundSpriteSize);
		
		g.setColor(Color.black);
		g.draw(leftHUD);
		g.draw(rightHUD);
		
		g.drawString(message, 40, 300);
		
		g.drawString(character.getHp() + "/" + character.getMaxhp(), 200, 255);
		g.drawString("Lvl " + opponent.getLevel(), 540, 255);
		g.drawString("Lvl " + character.getLevel(), 200, 235);
		
		character.drawFighter(g, characterSelectSheet, characterLocation[0], characterLocation[1], 10);
		opponent.drawFighter(g, characterSelectSheet, opponentLocation[0], opponentLocation[1], 10);
		
		// Draw health bars
		g.setColor(Color.red);
		g.fill(characterHealth);
		g.fill(opponentHealth);
		g.setColor(Color.blue);
		g.fill(characterXp);
		g.setColor(Color.white);
		g.draw(characterHealthOutline);
		g.draw(opponentHealthOutline);
		g.draw(characterXpOutline);
		
		// Choose which menu to display
		g.setColor(Color.black);
		if(fightSelected) {
			g.drawString(character.attacks[0].toString(), 380,  300);
			g.drawString(character.attacks[1].toString(), 380,  335);
			g.drawString(character.attacks[2].toString(), 530,  300);
			g.drawString(character.attacks[3].toString(), 530,  335);
		} else if(showMenu){
			g.drawString(mainMenu[0], 380,  300);
			g.drawString(mainMenu[1], 380,  335);
			g.drawString(mainMenu[2], 530, 300);
		}
		
		// renders particle effects
		system.render();
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		// Let particleSystem object know framerate
		system.update(delta);
		
		if(victory && !awaitingClick && !xpAnimation){
			switch (messageIndex) {
			case 0:
				message = "You have defeated " + opponent.name + "!";
				break;

			case 1:
				message = character.name + " gained " + calcXp() + " experience!";
				xpAnimation = true;
				animationDelay = 1000;
				levelsGained = character.addXp(calcXp());
				break;
				
			case 2:
				sbg.enterState(Game.VICTORY, new FadeOutTransition(), new FadeInTransition());
				break;
			default:
				break;
			}
			awaitingClick = true;
			messageIndex++;
		}
		
		checkXpAnimation(delta);
		
		checkHitFlags(delta, sbg);
	}

	public void mouseClicked(int button, int x, int y, int clickCount){
		awaitingClick = false;
		// Freeze inputs if animation is happening
		if(!textFreeze && !characterHit && !opponentHit && !awaitingClick){
			// Check if button one (fight button) is clicked
			if(!fightSelected && buttonOne.contains(x, y)){
				fightSelected = true;
			} 
			// Checks if menu is attack menu
			else if (fightSelected){
				if(buttonOne.contains(x, y)){
					characterAttack = character.attacks[0];
				} else if (buttonTwo.contains(x, y)){
					characterAttack = character.attacks[1];
				} else if (buttonThree.contains(x, y)){
					characterAttack = character.attacks[2];
				} else if (buttonFour.contains(x, y)){
					characterAttack = character.attacks[3];
				}
				if(characterAttack.offensive != -1){
					message = character.name + " used " + characterAttack.toString() + "!";
					characterAttackDamage = character.attackFighter(characterAttack, opponent);
					opponentHit = true;
					attackInProgress = true;
					animationDelay = 1000;
					fightSelected = false;
				}
				
			}
		} else if(textFreeze){
			message = "What will you do?";
			textFreeze = false;
			showMenu = true;
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
	
	private void checkHitFlags(int delta, StateBasedGame sbg){
		if(attackInProgress && !victory && !failure){
			// Check if opponents hit animation is happening
			if(opponentHit){
				opponent.setMouthOpen(1);
				if(animationDelay > 0){
					animationDelay -= delta;
					shakeOpponent(animationDelay, opponentStartX);
				} else {
					message = character.name + " dealt " + characterAttackDamage + " to " + opponent.name + "...";
					opponentLocation[0] = opponentStartX;
					opponent.setMouthOpen(0);
					opponentHealth.setWidth(((float)opponent.getHp()/opponent.getMaxhp()) * opponentHealthOutline.getWidth());
					opponentHit = false;
					if(opponent.getHp() <= 0){
						victory = true;
						awaitingSecodaryClick = true;
					} else {
						animationDelay = 1200;
						opponentAttack = opponent.autoAttackFighter(character);
						opponentAttackDamage = opponent.attackFighter(opponentAttack, character);
						characterHit = true;
					}
					awaitingClick = true;
				}
			} 
			// Check if users characters hit animation is happening
			else if(characterHit && !awaitingClick){
				message = opponent.name + " used " + opponentAttack.toString() + "!";
				if (animationDelay > 1000) {
					animationDelay -= delta;
				} else if(animationDelay > 0){
					character.setMouthOpen(1);
					animationDelay -= delta;
					shakeCharacter(animationDelay, characterStartX);
				} else {
					animationDelay = 0;
					characterLocation[0] = characterStartX;
					character.setMouthOpen(0);
					characterHealth.setWidth((float)character.getHp()/character.getMaxhp() * characterHealthOutline.getWidth());
					characterHit = false;
					if(character.getHp() <= 0){
						sbg.enterState(Game.OVER, new FadeOutTransition(), new FadeInTransition());
					}
					awaitingClick = true;
				}
			} else if(!awaitingClick) {
				message = opponent.name + " dealt " + opponentAttackDamage + " to " + character.name;
				textFreeze = true;
				attackInProgress = false;
			}
		}
	}
	
	private void checkXpAnimation(int delta) {
		if(xpAnimation){
			if(animationDelay > 0){
				animationDelay -= delta;
				float xp = ((1000 - animationDelay)/1000f) * calcXp();
				characterXp.setWidth(((float)(xp + characterStartXp)/character.getMaxXp()) * characterXpOutline.getWidth());
			} else {
				animationDelay = 0;
				xpAnimation = false;
			}
		}
	}
	
	private int calcXp(){
		return (int) Math.round(5 + Math.pow(opponent.getLevel(), 1.2) * 2);
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
		this.message = opponent.name + " wants to fight!";
	}

	public Fighter getCharacter() {
		return character;
	}

	public void setCharacter(Fighter character) {
		this.character = character;
		characterStartXp = character.getXp();
		if(characterStartXp == 0){
			characterStartXp = 1;
		}
		characterXp.setWidth(((float)characterStartXp/character.getMaxXp()) * characterXpOutline.getWidth());
	}

}
