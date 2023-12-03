package util;

import static util.Constants.EnemyConstants.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
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
	public static final String completed = "completed_sprite.png";
	
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
	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];

		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;
	}
}
