package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static util.Constants.PlayerConstants.*;
import static util.Constants.Direction.*;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 8;
	private int playerAction = idle;
	private int playerDir = -1;
	private boolean moving = false;
	
	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		importImg();
		loadAnimations();
		setPanelSize();
		addKeyListener (new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	private void loadAnimations() {
		// TODO Auto-generated method stub
		animations = new BufferedImage[10][8];
		
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*128, j*128, 128, 128);
			}
		}
		
	}

	private void importImg() {
		// TODO Auto-generated method stub
		InputStream is = getClass().getResourceAsStream("/Raider_3_Spritelist.png");
		
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280, 800);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	public void setDirection (int direction) {
		this.playerDir = direction;
		moving = true;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	private void updateAnimationTick() {
		// TODO Auto-generated method stub
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
			}
		}
	}
	
	private void setAnimation() {
		// TODO Auto-generated method stub
		if(moving ) {
			playerAction = running;
		}
		else {
			playerAction = idle;
		}
	}
	
	private void updatePos() {
		// TODO Auto-generated method stub
		if (moving) {
			switch(playerDir) {
			case left:
				xDelta -= 5;
				break;
			case up:
				yDelta -= 5;
				break;
			case right:
				xDelta += 5;
				break;
			case down:
				yDelta += 5;
				break;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateAnimationTick();
		setAnimation();
		updatePos();
		g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 256, 256, null);
	}

	

	

}