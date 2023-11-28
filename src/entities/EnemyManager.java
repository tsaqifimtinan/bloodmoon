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
    private ArrayList<BufferedImage[][]> scaledRaider1Arr = new ArrayList<>();
    
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
	    for (Raider_1 r : raiders) {
	        BufferedImage enemyImage = raider1Arr[r.getEnemyState()][r.getAniIndex()];

	        int flipDirection = (r.getWalkDir() == Direction.left) ? -1 : 1;

	        BufferedImage flippedImage = flipImage(enemyImage, flipDirection);

	        int flippedWidth = flippedImage.getWidth() * 2;
	        int flippedHeight = flippedImage.getHeight() * 2;

	        // Create a scaled instance of the image
	        BufferedImage scaledImage = new BufferedImage(flippedWidth, flippedHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics scaledGraphics = scaledImage.getGraphics();
	        scaledGraphics.drawImage(flippedImage, 0, 0, flippedWidth, flippedHeight, null);

	        // Draw the scaled image
	        g.drawImage(scaledImage, (int) r.getHitbox().x, (int) r.getHitbox().y, null);
	    }
	}

	private void loadEnemyImg() {
		raider1Arr = new BufferedImage[10][12];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.raider_1_atlas);
		
		for (int j = 0; j < raider1Arr.length; j++) {
			for (int i = 0; i < raider1Arr[j].length; i++) {
				raider1Arr[j][i] = temp.getSubimage(i * raider_1_width_default, j * raider_1_width_default, raider_1_width_default, raider_1_width_default);
			}
		}
	}
	
	private BufferedImage flipImage(BufferedImage image, int direction) {
        AffineTransform tx = AffineTransform.getScaleInstance(direction, 1);
        tx.translate((direction - 1) * image.getWidth(), 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }
}
