package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import main.Game;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle hitBox;
	protected int scale = 2;
	protected int hitBoxWidthOffset = 90; // adjust this value as needed
	protected int hitBoxHeightOffset = -120; // adjust this value as needed
	
	public Entity (float x, float y, int width, int height, int scale){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scale = scale;
		
		initHitBox();
	}
	
	protected void drawHitBox(Graphics g) {
		//hitbox debug
		g.setColor(Color.GREEN);
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}

	public void initHitBox() {
		hitBox = new Rectangle((int)x, (int)y, width, height);
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
	
	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public void setY(float newY) {
        this.y = newY;
    }
	
	public float getY (float newY) {
		return newY;
	}
}