package util;

import main.Game;

public class Constants {
	
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
		
		public static final int raider_1_width_default = 128;
		public static final int raider_1_height_default = 128;
		
		public static final int raider_1_width = (int) (raider_1_width_default * 2);
		public static final int raider_1_height = (int) (raider_1_height_default * 2);
		
		public static final int raider_1_drawoffset_x = 90;
		public static final int raider_1_drawoffset_y = -120;
		
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
	}
	
	public static class PlayerConstants {
		public static final int idle = 0;
		public static final int walking = 1;
		public static final int running = 2;
		public static final int attack_1 = 3;
		public static final int attack_2 = 4;
		public static final int attack_3 = 5;
		public static final int guard = 6;
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
			case guard:
				return 5;
			case attack_3:
			case die:
				return 4;
			case hurt:
				return 2;
			default:
				return 1;
			}
		}
	}
}
