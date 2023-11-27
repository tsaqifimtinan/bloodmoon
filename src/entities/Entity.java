package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import main.Game;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitBox;
	protected int scale = 2;
	protected int hitBoxWidthOffset = 90; // adjust this value as needed
	protected int hitBoxHeightOffset = -120; // adjust this value as needed
	
	public Entity (float x, float y, int width, int height, int scale){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scale = scale;
		
		initHitbox(x, y, width, height);
	}
	
	protected void drawHitBox(Graphics g) {
		//hitbox debug
		g.setColor(Color.GREEN);
		g.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
	}

	protected void initHitbox(float x, float y, int width, int height) {
		hitBox = new Rectangle2D.Float(x, y, width, height);
	}
	
	protected void updateHitBox() {
		hitBox.x = (int) x + hitBoxWidthOffset - 3;
		hitBox.y = (int) y - hitBoxHeightOffset;
		hitBox.width = (int) (width * scale) - (hitBoxWidthOffset * 2 + 25);
		hitBox.height = (int) (height * scale) + hitBoxHeightOffset;
	}
	
	public void update() {
        float velocityX = 1.0f;
        float velocityY = 1.0f;

        x += velocityX;
        y += velocityY;

        updateHitBox();
    }
	
	public Rectangle2D.Float getHitbox() {
		return hitBox;
	}
	
	public void setY(float newY) {
        this.y = y;
    }
	
	public float getY (float newY) {
		return y;
	}
}