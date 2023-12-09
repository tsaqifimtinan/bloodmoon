package entities;

import static util.Constants.PlayerConstants.*;
import static util.HelpMethods.*;
import static util.Constants.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import gamestates.Playing;
import main.Game;
import util.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private boolean moving = false, attacking = false, taunting = false;
	private boolean left, right, jump;
	private int[][] lvlData;
	private float xDrawOffset = 40 * Game.scale;
	private float yDrawOffset = 65 * Game.scale;

	// Jumping / Gravity
	private float jumpSpeed = -2.25f * Game.scale;
	private float fallSpeedAfterCollision = 0.5f * Game.scale;
	
	private BufferedImage statusBarImg;
	private int statusBarWidth = (int) (192 * Game.scale);
	private int statusBarHeight = (int) (58 * Game.scale);
	private int statusBarX = (int) (10 * Game.scale);
	private int statusBarY = (int) (10 * Game.scale);
	private int healthBarWidth = (int) (150 * Game.scale);
	private int healthBarHeight = (int) (4 * Game.scale);
	private int healthBarXStart = (int) (34 * Game.scale);
	private int healthBarYStart = (int) (14 * Game.scale);
	
	private int healthWidth = healthBarWidth;
	
	private int flipX = 0;
	private int flipW = 1;
	private boolean attackChecked;
	private Playing playing;
	private int tileY = 0;

	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = idle;
		this.maxHealth = 100;
		this.currentHealth = maxHealth;
		this.walkSpeed = Game.scale * 1.0f;
		loadAnimations();
		initHitbox(15, 28);
		initAttackBox();
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.scale), (int) (20 * Game.scale));
	}

	public void update() {
		updateHealthBar();

		if (currentHealth <= 0) {
			if(state != die) {
				state = die;
				aniTick = 0;
				aniIndex = 0;
				playing.setPlayerDying(true);
			} else if (aniIndex == GetSpriteAmount(die) - 1 && aniTick >= aniSpeed - 1) {
				playing.setGameOver(true);
			} else {
				updateAnimationTick();
			}
			
			return;
		}

		updateAttackBox();

		updatePos();
		if (moving) {
			checkPotionTouched();
			checkSpikesTouched();
			tileY = (int) (hitbox.y / Game.tiles_size);
		}
		if (attacking)
			checkAttack();
		
		updateAnimationTick();
		setAnimation();
	}

	private void checkSpikesTouched() {
		// TODO Auto-generated method stub
		playing.checkSpikesTouched(this);
	}

	private void checkPotionTouched() {
		playing.checkPotionTouched(hitbox);
	}

	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
	}

	private void updateAttackBox() {
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.scale * 10);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.scale * 10);

		attackBox.y = hitbox.y + (Game.scale * 10);
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
//		drawHitbox(g, lvlOffset);
//		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = state;

		if (moving)
			state = running;
		else
			state = idle;

		if (inAir) {
			if (airSpeed < 0)
				state = attack_jump;
			else
				state = idle;
		}

		if (attacking) {
			state = attack_1;
			if (startAni != attack_1) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}
		if (startAni != state)
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

		if (left) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}

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
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
	}

	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}
	
	public void kill() {
		currentHealth = 0;
	}

	public void changePower(int value) {
		System.out.println("Added power!");
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

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		state = idle;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	public int getTileY() {
		return tileY;
	}
}