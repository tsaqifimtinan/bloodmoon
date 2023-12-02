package entities;

import static util.Constants.PlayerConstants.*;
import static util.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;
import util.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = idle;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.0f * Game.scale;
	private int[][] lvlData;
	private float xDrawOffset = 35 * Game.scale;
	private float yDrawOffset = 65 * Game.scale;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.scale;
	private float jumpSpeed = -2.25f * Game.scale;
	private float fallSpeedAfterCollision = 0.5f * Game.scale;
	private boolean inAir = false;
	
	private BufferedImage statusBarImg;
	private int statusBarWidth = (int) (192 * Game.scale);
	private int statusBarHeight = (int) (58 * Game.scale);
	private int statusBarX = (int) (10 * Game.scale);
	private int statusBarY = (int) (10 * Game.scale);
	private int healthBarWidth = (int) (150 * Game.scale);
	private int healthBarHeight = (int) (4 * Game.scale);
	private int healthBarXStart = (int) (34 * Game.scale);
	private int healthBarYStart = (int) (14 * Game.scale);
	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	
	private Rectangle2D.Float attackBox;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, (int) (15 * Game.scale), (int) (28 * Game.scale));
		initAttackBox();
	}

	private void initAttackBox() {
		// TODO Auto-generated method stub
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.scale), (int) (20 * Game.scale));
	}

	public void update() {
		updateHealthBar();
		updateAttackBox();
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	private void updateAttackBox() {
		// TODO Auto-generated method stub
		if(right) {
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.scale * 10);
		}
		
		else if (left) {
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.scale * 10);
		}
		
		attackBox.y = hitbox.y + (Game.scale * 10);
	}

	private void updateHealthBar() {
		// TODO Auto-generated method stub
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
//		drawHitbox(g, lvlOffset);
		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}

	private void drawAttackBox(Graphics g, int lvlOffset) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
//		g.drawRect(attackBox.x, attackBox.y, attackBox.width, attackBox.height);
	}

	private void drawUI(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.blue);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}

		}

	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = running;
		else
			playerAction = idle;

		if (inAir) {
			if (airSpeed < 0)
				playerAction = attack_jump;
			else
				playerAction = idle;
		}

		if (attacking)
			playerAction = attack_1;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!inAir)
			if ((!left && !right) || (right && left))
				return;

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

	}
	
	public void changeHealth (int value) {
		currentHealth += value;
		
		if(currentHealth <= 0) {
			currentHealth = 0;
			//gameOver();
		}
		
		else if (currentHealth >= maxHealth) {
			currentHealth = maxHealth;
		}
	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.player_atlas);

		animations = new BufferedImage[10][9];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 128, j * 128, 128, 128);
	
		statusBarImg = LoadSave.getSpriteAtlas(LoadSave.status_bar);
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;

	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
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

	public void setJump(boolean jump) {
		this.jump = jump;
	}

}