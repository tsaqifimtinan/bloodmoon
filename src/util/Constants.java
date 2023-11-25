package util;

public class Constants {
	
	public static class Direction {
		public static final int up = 0;
		public static final int left = 1;
		public static final int down = 2;
		public static final int right = 3;
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