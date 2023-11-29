package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import util.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne = new Level(LoadSave.GetLevelData());
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.level_atlas);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < Game.tiles_in_height; j++)
			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.tiles_size * i - lvlOffset, Game.tiles_size * j, Game.tiles_size, Game.tiles_size, null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levelOne;
	}
}
