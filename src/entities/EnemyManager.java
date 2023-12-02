package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
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
			if (c.isActive())
				c.update(lvlData, player);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawRaiders(g, xLvlOffset);
	}

	private void drawRaiders(Graphics g, int xLvlOffset) {
		for (Raider_1 c : raiders) {
			if (c.isActive()) {
				g.drawImage(raiderArr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - raider_1_drawoffset_x + c.flipX(), (int) c.getHitbox().y - raider_1_drawoffset_y, raider_1_width * c.flipW(), raider_1_height, null);
//				c.drawHitbox(g, xLvlOffset);
//				c.drawAttackBox(g, xLvlOffset);
			}
		}

	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Raider_1 c : raiders) {
			if(c.isActive()) {
				if(attackBox.intersects(c.getHitbox())) {
					c.hurt(10);
					return;
				}
			}	
		}
	}

	private void loadEnemyImgs() {
		raiderArr = new BufferedImage[10][12];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.raider_1_atlas);
		for (int j = 0; j < raiderArr.length; j++)
			for (int i = 0; i < raiderArr[j].length; i++)
				raiderArr[j][i] = temp.getSubimage(i * raider_1_width_default, j * raider_1_height_default, raider_1_width_default, raider_1_height_default);
	}
	
	public void resetAllEnemies() {
		for (Raider_1 c : raiders)
			c.resetEnemy();
	}
}