package util;

import static util.Constants.EnemyConstants.raider_1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.Raider_1;
import entities.Player;
import entities.Raider_1;
import main.Game;

public class LoadSave {
	public static final String player_atlas = "Raider_3_Spritelist.png";
	public static final String level_atlas = "outside_sprites.png";
	public static final String raider_1_atlas = "Raider_1_Spritelist.png";
	public static final String level_one_data = "level_one_data_long.png";
	public static final String menu_buttons = "button_atlas.png";
	public static final String menu_background = "menu_background.png";
	public static final String pause_background = "pause_menu.png";
	public static final String sound_buttons = "sound_button.png";
	public static final String urm_buttons = "urm_buttons.png";
	public static final String volume_buttons = "volume_buttons.png";
	public static final String menu_background_img = "Battleground1.png";
	public static final String playing_bg_img = "sky.png";
	public static final String big_clouds = "big_clouds.png";
	public static final String small_clouds = "small_clouds.png";
	public static final String status_bar = "health_power_bar.png";
	
	public static BufferedImage getSpriteAtlas (String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
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
		return img;
	}
	
	public static ArrayList<Raider_1> getRaiders() {
		BufferedImage img = getSpriteAtlas(level_one_data);
		ArrayList<Raider_1> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == raider_1)
					list.add(new Raider_1(i * Game.tiles_size, j * Game.tiles_size));
			}
		return list;

	}
	
	public static int[][] GetLevelData() {
		BufferedImage img = getSpriteAtlas(level_one_data);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}

}
