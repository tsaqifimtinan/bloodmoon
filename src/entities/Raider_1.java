package entities;

import static util.Constants.Direction.*;
import static util.Constants.EnemyConstants.*;
import static util.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Raider_1 extends Enemy {
	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;

	public Raider_1(float x, float y) {
		// TODO Auto-generated constructor stub
		super(x, y, raider_1_width, raider_1_height, raider_1);
		initHitbox(x, y, (int) (22 * Game.scale), (int) (19 * Game.scale));
		initAttackBox();
	}
	
	private void initAttackBox() {
		// TODO Auto-generated method stub
		attackBox = new Rectangle2D.Float(x + flipX(), y, (int) (22 * Game.scale), (int) (19 * Game.scale));
		attackBoxOffsetX = (int) (-22 * Game.scale);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehaviour(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		// TODO Auto-generated method stub
		attackBox.x = hitbox.x - attackBoxOffsetX * flipW();
		attackBox.y = hitbox.y;
	}

	private void updateBehaviour(int[][] lvlData, Player player) {
		if (firstUpdate) {
			firstUpdateCheck(lvlData);
		}

		if (inAir) {
			updateInAir(lvlData);
		} 
		
		else {
			switch (enemyState) {
			case idle:
				newState(running);
				break;
			case running:
				if(canSeePlayer (lvlData, player)) {
					turnTowardsPlayer (player);
				}
				if(isPlayerCloseForAttack (player)) {
					newState(attack_1);
				}
				
				move(lvlData);
				break;
			case attack_1:
				if (aniIndex == 0)
					attackChecked = false;
				if(aniIndex == 3 && !attackChecked)
					checkPlayerHit(attackBox, player);
				break;
			case hurt:
				break;
			}
		}
	}
	
	
	
	public void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.CYAN);
		g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}
	
	public int flipX() {
		if(walkDir == right) {
			return 0;
		}
		
		else {
			return width;
		}
	}
	
	public int flipW() {
		if(walkDir == right) {
			return 1;
		}
		
		else {
			return -1;
		}
	}
	
}
