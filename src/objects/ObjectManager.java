package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import util.LoadSave;
import static util.Constants.ObjectConstants.*;
import static util.HelpMethods.*;
import static util.Constants.Projectiles.*;
import static util.HelpMethods.*;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[][] potionImgs, containerImgs;
	private BufferedImage[] cannonImgs;
	private BufferedImage spikeImg, cannonBallImg;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	
	private long lastHealthChangeTime = System.currentTimeMillis();
	private static final long HEALTH_CHANGE_COOLDOWN = 500; // 1 second in milliseconds


	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}

	public void checkSpikesTouched(Player p) {
	    long currentTime = System.currentTimeMillis();

	    if (currentTime - lastHealthChangeTime >= HEALTH_CHANGE_COOLDOWN) {
	        for (Spike s : spikes) {
	            if (s.getHitbox().intersects(p.getHitbox())) {
	                p.changeHealth(-SPIKE_VALUE);
	                lastHealthChangeTime = currentTime;
	            }
	        }
	    }
	}

	public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Potion p : potions)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
	}

	public void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else
			playing.getPlayer().changePower(BLUE_POTION_VALUE);
	}

	public void checkObjectHit(Rectangle2D.Float attackbox) {
		for (GameContainer gc : containers)
			if (gc.isActive() && !gc.doAnimation) {
				if (gc.getHitbox().intersects(attackbox)) {
					gc.setAnimation(true);
					int type = 0;
					if (gc.getObjType() == BARREL)
						type = 1;
					potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
					return;
				}
			}
	}

	public void loadObjects(Level newLevel) {
		potions = new ArrayList<>(newLevel.getPotions());
		containers = new ArrayList<>(newLevel.getContainers());
		spikes = newLevel.getSpikes();
		cannons = newLevel.getCannons();
		projectiles.clear();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.potion_atlas);
		potionImgs = new BufferedImage[2][7];

		for (int j = 0; j < potionImgs.length; j++)
			for (int i = 0; i < potionImgs[j].length; i++)
				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

		BufferedImage containerSprite = LoadSave.getSpriteAtlas(LoadSave.box_atlas);
		containerImgs = new BufferedImage[2][8];

		for (int j = 0; j < containerImgs.length; j++)
			for (int i = 0; i < containerImgs[j].length; i++)
				containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

		spikeImg = LoadSave.getSpriteAtlas(LoadSave.trap_atlas);

		cannonImgs = new BufferedImage[7];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.cannon_atlas);

		for (int i = 0; i < cannonImgs.length; i++)
			cannonImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);
		
		cannonBallImg = LoadSave.getSpriteAtlas(LoadSave.cannon_ball);
	}

	public void update(int[][] lvlData, Player player) {
		for (Potion p : potions)
			if (p.isActive())
				p.update();

		for (GameContainer gc : containers)
			if (gc.isActive())
				gc.update();

		updateCannons(lvlData, player);
		updateProjectiles (lvlData, player);
	}
	
	private void updateProjectiles(int[][] lvlData, Player player) {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.updatePos();
				if (p.getHitbox().intersects(player.getHitbox())) {
					player.changeHealth(-15);
					p.setActive(false);
				} else if (IsProjectileHittingLevel(p, lvlData))
					p.setActive(false);
			}
	}
	
	private void updateCannons(int[][] lvlData, Player player) {
		for (Cannon c : cannons) {
			if (!c.doAnimation)
				if (c.getTileY() == player.getTileY())
					if (isPlayerInRange(c, player))
						if (isPlayerInfrontOfCannon(c, player))
							if (CanCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY())) {
								c.setAnimation(true);
							}
			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0) {
				shootCannon(c);
			}
		}
	}

	private boolean isPlayerInRange(Cannon c, Player player) {
		int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
		return absValue <= Game.tiles_size * 5;
	}

	private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
		if (c.getObjType() == CANNON_LEFT) {
			if (c.getHitbox().x > player.getHitbox().x)
				return true;

		} 
		
		else if (c.getHitbox().x < player.getHitbox().x) {
			return true;
		}
			
		return false;
	}

	private void shootCannon(Cannon c) {
		c.setAnimation(true);
		
		int dir = 1;
		if(c.getObjType() == CANNON_LEFT)
			dir = -1;
		
		projectiles.add(new Projectile ((int) c.getHitbox().x, (int) c.getHitbox().y, dir));
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawContainers(g, xLvlOffset);
		drawTraps(g, xLvlOffset);
		drawCannons(g, xLvlOffset);
		drawProjectiles(g, xLvlOffset);
	}

	private void drawProjectiles(Graphics g, int xLvlOffset) {
		for (Projectile p : projectiles)
			if (p.isActive())
				g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);

	}

	private void drawCannons(Graphics g, int xLvlOffset) {
		for (Cannon c : cannons) {
			int x = (int) (c.getHitbox().x - xLvlOffset);
			int width = CANNON_WIDTH;

			if (c.getObjType() == CANNON_RIGHT) {
				x += width;
				width *= -1;
			}

			g.drawImage(cannonImgs[c.getAniIndex()], x, (int) (c.getHitbox().y), width, CANNON_HEIGHT, null);
		}

	}

	private void drawTraps(Graphics g, int xLvlOffset) {
		for (Spike s : spikes)
			g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

	}

	private void drawContainers(Graphics g, int xLvlOffset) {
		for (GameContainer gc : containers)
			if (gc.isActive()) {
				int type = 0;
				if (gc.getObjType() == BARREL)
					type = 1;
				g.drawImage(containerImgs[type][gc.getAniIndex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
						CONTAINER_HEIGHT, null);
			}
	}

	private void drawPotions(Graphics g, int xLvlOffset) {
		for (Potion p : potions)
			if (p.isActive()) {
				int type = 0;
				if (p.getObjType() == RED_POTION)
					type = 1;
				g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
						null);
			}
	}

	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for (Potion p : potions)
			p.reset();
		for (GameContainer gc : containers)
			gc.reset();
		for (Cannon c : cannons)
			c.reset();
	}
}