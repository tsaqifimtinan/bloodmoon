package entities;

import static util.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.util.Random;

import main.Game;

import static util.Constants.Direction.*;

public abstract class Enemy extends Entity{
	protected int aniIndex, enemyState, enemyType;
	private int aniTick, aniSpeed = 8;
	private float walkSpeed = 1.0f;
	protected int walkDir = left;
	private EnemyManager enemyManager;
	// Add a field to track the time in idle state
	private int idleTimer = 0;
	private int maxIdleTime = 50; // Adjust the maximum idle time as needed
	private int runningTimer = 0;
	private int maxRunningTime;
	
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
	            idleTimer++;
	            if (idleTimer >= maxIdleTime) {
	                idleTimer = 0;
	                enemyState = running;
	            }
	            break;
	        case running:
	            runningTimer++; // Increment the running timer

	            float ySpeed = (float) (Math.random() * 2 * walkSpeed - walkSpeed);
	            float xSpeed = -walkSpeed;

	            x += xSpeed;
	            y += ySpeed;

	            if (x < -90) {
	                x = Game.game_width;
	            } else if (x > Game.game_width) {
	                x = -90;
	            }

	            if (y < 0) {
	                y = Game.game_height;
	            } else if (y > Game.game_height) {
	                y = 0;
	            }

	            // Transition to idle state after reaching the maximum running time
	            if (runningTimer >= setMaxTime()) {
	                runningTimer = 0; // Reset the running timer
	                enemyState = idle; // Transition to idle state
	            }
	            break;
	    }
	}

	public int setMaxTime() {
		Random random = new Random();
		return maxRunningTime = random.nextInt(5000);
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