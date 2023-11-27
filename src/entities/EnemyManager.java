package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import util.LoadSave;
import static util.Constants.EnemyConstants.*;

public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] raider1Arr;
	private ArrayList<Raider_1> raiders = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImg();
		addRaiders();
	}
	
	private void addRaiders() {
		// TODO Auto-generated method stub
		raiders = LoadSave.getRaiders();
		System.out.println("Numbers of Raiders: " + raiders.size());
	}

	public void update() {
		for (Raider_1 r : raiders) {
			r.update();
		}
		
	}

	private Raider_1[] getRaiders() {
		// TODO Auto-generated method stub
		return null;
	}

	public void draw(Graphics g) {
		drawRaiders(g);
	}

	private void drawRaiders(Graphics g) {
		// TODO Auto-generated method stub
		for (Raider_1 r : raiders) {
			g.drawImage(raider1Arr[r.getEnemyState()][r.getAniIndex()], (int) r.getHitbox().x, (int) r.getHitbox().y, raider_1_width, raider_1_height, null);
		}
	}

	private void loadEnemyImg() {
		// TODO Auto-generated method stub
		raider1Arr = new BufferedImage[10][12];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.raider_1_atlas);
		
		for (int j = 0; j < raider1Arr.length; j++) {
			for (int i = 0; i < raider1Arr[j].length; i++) {
				raider1Arr[j][i] = temp.getSubimage(i * raider_1_width_default, j * raider_1_width_default, raider_1_width_default, raider_1_width_default);
			}
		}
	}
}
