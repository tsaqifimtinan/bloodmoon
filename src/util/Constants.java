package util;

import main.Game;

public class Constants {
	
	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.scale);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.scale);
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.scale);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.scale);
	}
	
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.scale);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.scale);
		}

		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.scale);
		}

		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.scale);

		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.scale);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.scale);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.scale);
		}
	}
	
	public static class Direction {
		public static final int up = 0;
		public static final int left = 1;
		public static final int down = 2;
		public static final int right = 3;
	}
	
	public static class EnemyConstants {
		public static final int raider_1 = 0;
		// basic crabby states
		public static final int idle = 0;
		public static final int running = 1;
		public static final int attack_1 = 2;
		public static final int hurt = 3;
		public static final int die = 4;
		
		public static final int shot = 3;
		public static final int attack_2 = 5;
		public static final int reload = 6;
		public static final int jump = 7;
		
		// temp width and height
		public static final int raider_1_width_default = 72;
		public static final int raider_1_height_default = 32;
		
		public static final int raider_1_width = (int) (raider_1_width_default * Game.scale);
		public static final int raider_1_height = (int) (raider_1_height_default * Game.scale);
		
		public static final int raider_1_drawoffset_x = (int) (26 * Game.scale);
		public static final int raider_1_drawoffset_y = (int) (9 * Game.scale);
		
		public static int getSpriteAmount(int enemytype, int enemystate) {
			switch(enemytype) {
			case raider_1:
				switch(enemystate) {
				case idle:
					return 9;
				case running:
					return 6;
				case attack_1:
					return 7;
				case hurt:
					return 4;
				case die:
					return 5;
				}
			}
			return 0;
		}
	}
	
	public static class PlayerConstants {
		public static final int idle = 0;
		public static final int running = 1;
		public static final int jumping = 2;
		public static final int falling = 3;
		public static final int ground = 4;
		public static final int hit = 5;
		public static final int attack_1 = 6;
		public static final int attack_jump_1 = 7;
		public static final int attack_jump_2 = 8;
		
		public static int GetSpriteAmount (int player_action) {
			
			switch(player_action) {
			case idle:
			case running:
			case jumping:
				return 6;
			default:
				return 1;
			}
		}
	}
}
