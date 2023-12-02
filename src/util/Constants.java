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
		
		public static final int idle = 0;
		public static final int walking = 1;
		public static final int running = 2;
		public static final int shot = 3;
		public static final int attack_1 = 4;
		public static final int attack_2 = 5;
		public static final int reload = 6;
		public static final int jump = 7;
		public static final int hurt = 8;
		public static final int die = 9;
		
		// temp width and height
		public static final int raider_1_width_default = 128;
		public static final int raider_1_height_default = 128;
		
		public static final int raider_1_width = (int) (raider_1_width_default * 1.5);
		public static final int raider_1_height = (int) (raider_1_height_default * 1.5);
		
		public static final int raider_1_drawoffset_x = (int) (37 * Game.scale);
		public static final int raider_1_drawoffset_y = (int) (75 * Game.scale);
		
		public static int getSpriteAmount(int enemytype, int enemystate) {
			switch(enemytype) {
			case raider_1:
				switch(enemystate) {
				case idle:
				case attack_1:
					return 6;
				case walking:
				case running:
					return 8;
				case shot:
				case reload:
					return 12;
				case attack_2:
					return 3;
				case jump:
					return 11;
				case hurt:
					return 2;
				case die:
					return 4;
				}
			}
			return 0;
		}
		
		public static int getMaxHealth(int enemy_type) {
			switch(enemy_type) {
			case raider_1:
				return 10;
			default:
				return 1;
			}
		}
		
		public static int getEnemyDmg(int enemy_type) {
			switch(enemy_type) {
			case raider_1:
				return 15;
			default:
				return 1;
			}
		}
	}
	
	public static class PlayerConstants {
		public static final int idle = 0;
		public static final int walking = 1;
		public static final int running = 2;
		public static final int attack_1 = 3;
		public static final int attack_2 = 4;
		public static final int attack_3 = 5;
		public static final int taunt = 6;
		public static final int attack_jump = 7;
		public static final int hurt = 8;
		public static final int die = 9;
		
		public static int GetSpriteAmount (int player_action) {
			
			switch(player_action) {
			case idle:
				return 6;
			case walking:
				return 7;
			case running:
			case attack_jump:
				return 8;
			case attack_1:
			case attack_2:
			case taunt:
				return 5;
			case attack_3:
			case die:
				return 4;
			case hurt:
				return 2;
			}
			return 0;
		}
	}
}
