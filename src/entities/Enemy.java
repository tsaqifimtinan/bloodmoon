package entities;

import static util.Constants.EnemyConstants.*;
import static util.Constants.*;
import static util.HelpMethods.*;
import java.awt.geom.Rectangle2D;
import static util.Constants.Direction.*;
import main.Game;

public abstract class Enemy extends Entity {
	protected int enemyType;
	protected boolean firstUpdate = true;
	protected int walkDir = left;
	protected int tileY;
	protected float attackDistance = Game.tiles_size;
	protected boolean active = true;
	protected boolean attackChecked;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = getMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = 0.25f * Game.scale;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += airSpeed;
			airSpeed += gravity;
		} else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
			tileY = (int) hitbox.y / Game.tiles_size;
		}
	}
	
	protected void move(int[][] lvlData) {
		float xSpeed = 0;

		if (walkDir == left)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if (IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return;
			}

		changeWalkDir();
	}
	
	protected void newState(int state) {
		this.state = state;
		aniTick = 0;
		aniIndex = 0;
	}
	
	protected void turnTowardsPlayer (Player player) {
		if (player.hitbox.x > hitbox.x) {
			walkDir = right;
		}
		
		else {
			walkDir = left;
		}
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) player.getHitbox().y / Game.tiles_size;
		
		if (playerTileY == tileY) {
			if(isPlayerInRange(player)) {
				if(IsSightClear(lvlData, hitbox, player.hitbox, tileY)) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean isPlayerInRange(Player player) {
		// TODO Auto-generated method stub
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}
	
	protected boolean isPlayerCloseForAttack (Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(enemyType, state)) {
				aniIndex = 0;
				
				switch(state) {
				case attack_1, hurt -> state = idle;
				case die -> active = false;
				}
			}
		}
	}

	protected void changeWalkDir() {
		if (walkDir == left)
			walkDir = right;
		else
			walkDir = left;

	}
	
	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(idle);
		active = true;
		airSpeed = 0;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0) {
			newState(die);
		}
		
		else {
			newState(hurt);
		}
	}
	
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.hitbox)) {
			player.changeHealth(-getEnemyDmg(enemyType));
		}
		
		attackChecked = true;
	}
	
	public boolean isActive() {
		return active;
	}

}