package entities;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import main.Game;
import util.Constants.Direction;
import util.LoadSave;
import static util.Constants.EnemyConstants.*;

public class EnemyManager {
	private Playing playing;
    private BufferedImage[][] raider1Arr;
    private ArrayList<Raider_1> raiders = new ArrayList<>();
    
    public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}

	private void addEnemies() {
		raiders = LoadSave.getRaiders();

	}

	public void update(int[][] lvlData, Player player) {
		for (Raider_1 c : raiders)
			c.update(lvlData, player);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawRaiders(g, xLvlOffset);
	}

	private void drawRaiders(Graphics g, int xLvlOffset) {
		for (Raider_1 c : raiders) {
			g.drawImage(raider1Arr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - raider_1_drawoffset_x, (int) c.getHitbox().y - raider_1_drawoffset_y, raider_1_width, 
					raider_1_height, null);
//			c.drawHitbox(g, xLvlOffset);
		}

	}

	private void loadEnemyImgs() {
		raider1Arr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.raider_1_atlas);
		for (int j = 0; j < raider1Arr.length; j++)
			for (int i = 0; i < raider1Arr[j].length; i++)
				raider1Arr[j][i] = temp.getSubimage(i * raider_1_width_default, j * raider_1_height_default, raider_1_width_default, raider_1_height_default);
	}
}
