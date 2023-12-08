package objects;

import main.Game;

public class Cannon extends GameObject {
	private int tileY;

	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY = y / Game.tiles_size;
		initHitbox(40, 26);
		hitbox.x -= (int) (4 * Game.scale);
		hitbox.y += (int) (6 * Game.scale);
	}
	
	public void update() {
		if(doAnimation)
			updateAnimationTick();
	}
	
	public int getTileY() {
		return tileY;
	}
}
