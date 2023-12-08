package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import gamestates.Playing;
import util.LoadSave;
import static util.Constants.ObjectConstants.*;

public class ObjectManager {
	private Playing playing;
	private BufferedImage[][] potionImgs, boxImgs;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> boxes;
	
	public ObjectManager (Playing playing) {
		this.playing = playing;
		loadImgs();
		
		potions = new ArrayList<>();
		potions.add(new Potion(300, 300, RED_POTION));
		potions.add(new Potion(400, 300, BLUE_POTION));
		
		boxes = new ArrayList<>();
		boxes.add(new GameContainer(500, 300, BARREL));
		boxes.add(new GameContainer(600, 300, BOX));
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.potion_atlas);
		potionImgs = new BufferedImage[2][7];
		
		for (int j = 0; j < potionImgs.length; j++) {
			for (int i = 0; i < potionImgs[j].length; i++) {
				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
			}
		}
		
		BufferedImage boxSprite = LoadSave.getSpriteAtlas(LoadSave.box_atlas);
		boxImgs = new BufferedImage[2][8];
		
		for (int j = 0; j < boxImgs.length; j++) {
			for (int i = 0; i < boxImgs[j].length; i++) {
				boxImgs[j][i] = boxSprite.getSubimage(40 * i, 30 * j, 40, 30);
			}
		}
	}
	
	public void update() {
		for (Potion p : potions) {
			if (p.isActive()) {
				p.update();
			}
		}
		
		for (GameContainer b : boxes) {
			if (b.isActive()) {
				b.update();
			}
		}
	}
	
	public void draw (Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawBoxes(g, xLvlOffset);
	}

	private void drawBoxes(Graphics g, int xLvlOffset) {
		// TODO Auto-generated method stub
		for (GameContainer b : boxes)
			if (b.isActive()) {
				int type = 0;
				if (b.getObjType() == BARREL)
					type = 1;
				g.drawImage(boxImgs[type][b.getAniIndex()], (int) (b.getHitbox().x - b.getxDrawOffset() - xLvlOffset), (int) (b.getHitbox().y - b.getyDrawOffset()), CONTAINER_WIDTH, CONTAINER_HEIGHT, null);
			}
	}

	private void drawPotions(Graphics g, int xLvlOffset) {
		for (Potion p : potions)
			if (p.isActive()) {
				int type = 0;
				if (p.getObjType() == RED_POTION)
					type = 1;
				g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
						null);
			}
	}
}
