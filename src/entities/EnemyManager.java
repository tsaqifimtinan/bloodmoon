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
	private BufferedImage[][] raiderArr;
	private ArrayList<Raider_1> raiders = new ArrayList<>();
	
	public EnemyManager (Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	
	private void addEnemies() {
		// TODO Auto-generated method stub
		raiders = LoadSave.getRaiders();
		System.out.println("Size of raiders: " + raiders.size());
	}

	public void update() {
		for (Raider_1 r : raiders) {
			r.update();
		}
	}
	
	public void draw (Graphics g, int xLvlOffset) {
		drawRaiders(g, xLvlOffset);
	}

	private void drawRaiders(Graphics g, int xLvlOffset) {
		// TODO Auto-generated method stub
		for (Raider_1 r : raiders) {
			g.drawImage(raiderArr[r.getEnemyState()][r.getAniIndex()], (int) r.getHitbox().x - xLvlOffset, (int) r.getHitbox().y, raider_1_width, raider_1_height, null);
		}
	}

	private void loadEnemyImgs() {
		// TODO Auto-generated method stub
		raiderArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.raider_1_atlas);
		
		for (int j = 0; j < raiderArr.length; j++) {
			for (int i = 0; i < raiderArr[j].length; i++) {
				raiderArr[j][i] = temp.getSubimage(i * raider_1_width_default, j * raider_1_height_default, raider_1_width_default, raider_1_height_default);
			}
		}
	}
}
