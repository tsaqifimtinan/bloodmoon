package entities;

import static util.Constants.EnemyConstants.*;

import main.Game;

public class Raider_1 extends Enemy{

	public Raider_1(float x, float y) {
		super(x, y, raider_1_width, raider_1_height, Game.scale, raider_1);
		initHitbox(x, y, (int) (128 * Game.scale), (int) (128 * Game.scale));
	}

	public int getWalkDir() {
		// TODO Auto-generated method stub
		return walkDir;
	}
}
