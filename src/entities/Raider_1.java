package entities;

import static util.Constants.EnemyConstants.*;
import static util.Constants.EnemyConstants.attack_1;
import static util.Constants.EnemyConstants.idle;
import static util.Constants.EnemyConstants.running;

import java.util.Random;

import main.Game;

public class Raider_1 extends Enemy {
	private float scale = 0.5f;

	public Raider_1(float x, float y) {
		super(x, y, raider_1_width, raider_1_height, raider_1);
		initHitbox(x, y, (int) (22 * scale), (int) (19 * scale));
	}
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();

	}
	
	private void updateMove(int[][] lvlData, Player player) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir)
			updateInAir(lvlData);
		else {
			switch (enemyState) {
			case idle:
				newState(running);
				break;
			case running:
				if (canSeePlayer(lvlData, player))
					turnTowardsPlayer(player);
				if (isPlayerCloseForAttack(player))
					newState(attack_1);

				move(lvlData);
				break;
			}
		}

	}
}
