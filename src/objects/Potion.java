package objects;

import main.Game;

public class Potion extends GameObject {

	public Potion(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		initHitbox(7, 14);
		xDrawOffset = (int) (3 * Game.scale);
		yDrawOffset = (int) (2 * Game.scale);
	}
	
	public void update() {
		updateAnimationTick();
	}
}
