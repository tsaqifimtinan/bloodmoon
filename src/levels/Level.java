package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Raider_1;
import main.Game;
import util.LoadSave;
import static util.HelpMethods.*;

public class Level {
	private int[][] lvlData;
	private BufferedImage img;
	private ArrayList<Raider_1> raiders;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}
	
	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.tiles_in_width;
		maxLvlOffsetX = Game.tiles_size * maxTilesOffset;
	}

	private void createEnemies() {
		raiders = GetRaiders(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<Raider_1> getRaiders() {
		return raiders;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
