package entities;

import static util.Constants.EnemyConstants.*;

import main.Game;

public class Raider_1 extends Enemy {

	public Raider_1(float x, float y) {
		// TODO Auto-generated constructor stub
		super(x, y, raider_1_width, raider_1_height, raider_1);
		initHitbox(x, y, (int) (22 * Game.scale), (int) (19 * Game.scale));
		
	}
	
}
