package entities;

import static util.Constants.EnemyConstants.*;

import java.awt.Graphics;

import main.Game;

import static util.Constants.Direction.*;

public abstract class Enemy extends Entity{
	protected int aniIndex, enemyState, enemyType;
	private int aniTick, aniSpeed = 8;
	private float walkSpeed = 1.0f;
	protected int walkDir = left;
	private EnemyManager enemyManager;
	
	public Enemy(float x, float y, int width, int height, int scale, int enemyType) {
		super(x, y, width, height, scale);
	    this.enemyType = enemyType;
	    this.aniIndex = 0; // Initialize aniIndex
	    this.enemyState = idle; // Initialize enemyState
	    initHitbox(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	private void updateAnimationTick() {
		aniTick++;
		
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			
			if(aniIndex >= getSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
			}
		}
	}
	
	public void update() {
		updateMove();
		updateHitBox();
		updateAnimationTick();
	}
	
	private void updateMove() {
	    switch (enemyState) {
	        case idle:
	            enemyState = running;
	            break;
	        case running:
	            float xSpeed = 0;
	            float ySpeed = 0;

	            if (walkDir == right) {
	                xSpeed = +walkSpeed;
	                aniIndex = 1;
	            } else if (walkDir == left) {
	                xSpeed = -walkSpeed;
	                aniIndex = 1;
	            } else if (walkDir == up) {
	                ySpeed = -walkSpeed;
	                aniIndex = 1;
	            } else if (walkDir == down) {
	                ySpeed = +walkSpeed;
	                aniIndex = 1;
	            }

	            // Update the x and y positions based on xSpeed and ySpeed
	            x += xSpeed;
	            y += ySpeed;

	            // Wrap around the window horizontally
	            if (x < -90) {
	                x = Game.game_width; // Move to the right edge
	            } else if (x > Game.game_width) {
	                x = -90; // Move to the left edge
	            }

	            // Wrap around the window vertically
	            if (y < 0) {
	                y = Game.game_height; // Move to the bottom edge
	            } else if (y > Game.game_height) {
	                y = 0; // Move to the top edge
	            }
	            break;
	    }
	}
	
	public void render (Graphics g) {
		drawHitBox(g);
	}

	public int getAniIndex() {
		return aniIndex;
	}
	
	public int getEnemyState() {
		return enemyState;
	}
	
}
