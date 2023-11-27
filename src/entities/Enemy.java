package entities;

import static util.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity{
	private int aniIndex, enemyState, enemyType;
	private int aniTick, aniSpeed = 8;
	
	public Enemy(float x, float y, int width, int height, int scale, int enemyType) {
		super(x, y, width, height, scale);
		this.enemyType = enemyType;
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
		updateAnimationTick();
	}
	
	public int getAniIndex() {
		return aniIndex;
	}
	
	public int getEnemyState() {
		return enemyState;
	}
	
}
