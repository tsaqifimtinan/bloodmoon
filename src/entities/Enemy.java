package entities;

import static util.Constants.EnemyConstants.*;
import static util.HelpMethods.*;

import java.awt.geom.Rectangle2D.Float;

import static util.Constants.Direction.*;

import main.Game;

public abstract class Enemy extends Entity {
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected float fallSpeed;
	protected float gravity = 0.04f * Game.scale;
	protected float walkSpeed = 0.35f * Game.scale;
	protected int walkDir = left;
	protected int tileY;
	protected float attackDistance = Game.tiles_size;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x, y, width, height);
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		} else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
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
	
	protected void newState(int enemyState) {
		this.enemyState = enemyState;
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
			if (aniIndex >= getSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
				if(enemyState == attack_1) {
					enemyState= idle;
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

	public int getAniIndex() {
		return aniIndex;
	}

	public int getEnemyState() {
		return enemyState;
	}

}