package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import util.LoadSave;
import static util.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] raiderArr;
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
		drawCrabs(g, xLvlOffset);
	}

	private void drawCrabs(Graphics g, int xLvlOffset) {
		for (Raider_1 c : raiders) {
			g.drawImage(raiderArr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - raider_1_drawoffset_x, (int) c.getHitbox().y - raider_1_drawoffset_y, raider_1_width, raider_1_height, null);
//			c.drawHitbox(g, xLvlOffset);
		}

	}

	private void loadEnemyImgs() {
		raiderArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.raider_1_atlas);
		for (int j = 0; j < raiderArr.length; j++)
			for (int i = 0; i < raiderArr[j].length; i++)
				raiderArr[j][i] = temp.getSubimage(i * raider_1_width_default, j * raider_1_height_default, raider_1_width_default, raider_1_height_default);
	}
}