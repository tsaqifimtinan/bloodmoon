package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	public static final String player_atlas = "Raider_3_Spritelist.png";
	public static final String level_atlas = "stones&grass.png";
	public static final String sky_atlas = "sky.png";
	
	
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
}
