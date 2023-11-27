package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.Raider_1;
import main.Game;

public class LoadSave {
	public static final String player_atlas = "Raider_3_Spritelist.png";
	public static final String level_atlas = "stones&grass.png";
	public static final String sky_atlas = "sky.png";
	public static final String raider_1_atlas = "Raider_1_Spritelist.png";
	
	
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
	    ArrayList<Raider_1> raiders = new ArrayList<>();
	    Random random = new Random();
	    for (int i = 0; i < 3; i++) {
	        int x = random.nextInt(1000);
	        int y = random.nextInt(476) + 300; // Adjusted range to ensure y is between 300 and 775
	        Raider_1 raider = new Raider_1(x, y);
	        raiders.add(raider);
	    }
	    return raiders;
	}

}
