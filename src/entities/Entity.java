package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	protected int aniTick, aniIndex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	
	protected int maxHealth = 100;
	protected int currentHealth = maxHealth;
	protected Rectangle2D.Float attackBox;
	protected float walkSpeed = 1.0f * Game.scale;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y,(int) (width * Game.scale), (int) (height * Game.scale));
	}
	
	protected void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.CYAN);
		g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	protected void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.green);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public int getState() {
		return state;
	}
	
	public int getAniIndex() {
		return aniIndex;
	}
}