package entities;

import static util.Constants.EnemyConstants.*;
import static util.HelpMethods.*;
import static util.Constants.Direction.*;

import main.Game;

public abstract class Enemy extends Entity {
	private int aniIndex, enemyState, enemyType;
	private int aniTick, aniSpeed = 25;
	private boolean firstUpdate = true;
	private boolean inAir;
	private float fallSpeed;
	private float gravity = 0.04f * Game.scale;
	private float walkSpeed = 0.35f * Game.scale;
	private int walkDir = left;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x, y, width, height);

	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;
			}
		}
	}

	public void update(int[][] lvlData) {
		updateMove(lvlData);
		updateAnimationTick();

	}

	private void updateMove(int[][] lvlData) {
		if (firstUpdate) {
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
			firstUpdate = false;
		}

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += fallSpeed;
				fallSpeed += gravity;
			} else {
				inAir = false;
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			}
		} else {
			switch (enemyState) {
			case idle:
				enemyState = running;
				break;
			case running:
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

				break;
			}
		}

	}

	private void changeWalkDir() {
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