package objects;

import static util.Constants.ObjectConstants.*;

import main.Game;

public class GameContainer extends GameObject {

	public GameContainer(int x, int y, int objType) {
		super(x, y, objType);
		createHitbox();
	}

	private void createHitbox() {
		// TODO Auto-generated method stub
		if(objType == BOX) {
			initHitbox(25, 18);
			xDrawOffset = (int) (7 * Game.scale);
			yDrawOffset = (int) (12 * Game.scale);
		}
		
		else {
			initHitbox(23, 25);
			xDrawOffset = (int) (8 * Game.scale);
			yDrawOffset = (int) (5 * Game.scale);
		}
	}
	
	public void update() {
		if(doAnimation) {
			updateAnimationTick();		}
	}
}
