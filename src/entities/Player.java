package entities;

import static util.Constants.Direction.*;
import static util.Constants.PlayerConstants.GetSpriteAmount;
import static util.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity{
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 8;
	private int playerAction = idle;
	private boolean moving = false, attack = false;
	private boolean left, up, right, down;
	private float playerSpeed = 2.0f;
	
	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 256, 256, null);
	}
	
	private void updateAnimationTick() {
		// TODO Auto-generated method stub
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attack = false;
			}
		}
	}
	
	private void setAnimation() {
		// TODO Auto-generated method stub
		int startAni = playerAction;
		
		if(moving) {
			playerAction = running;
		}
		else {
			playerAction = idle;
		}
		
		if (attack) {
			playerAction = attack_1;
		}
		
		if (startAni != playerAction) {
			resetAniTick();
		}
	}
	
	private void resetAniTick() {
		// TODO Auto-generated method stub
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;
		
		if (left && !right) {
			x-=playerSpeed;
			moving = true;
		}
		else if (right && !left) {
			x+=playerSpeed;
			moving = true;
		}
		
		if (up && !down) {
			y-=playerSpeed;
			moving = true;
		}
		else if (down && !up) {
			y+=playerSpeed;
			moving = true;
		}
	}
	
	private void loadAnimations() {
		InputStream is = getClass().getResourceAsStream("/Raider_3_Spritelist.png");
		try {
			BufferedImage img = ImageIO.read(is);
			
			animations = new BufferedImage[10][8];
			
			for (int j = 0; j < animations.length; j++) {
				for (int i = 0; i < animations[j].length; i++) {
					animations[j][i] = img.getSubimage(i*128, j*128, 128, 128);
				}
			}
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
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	public void setAttack(boolean attack) {
		this.attack = attack;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	
}
