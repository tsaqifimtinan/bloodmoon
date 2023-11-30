package util;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.tiles_size;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.game_height)
			return true;
		float xIndex = x / Game.tiles_size;
		float yIndex = y / Game.tiles_size;

		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.tiles_size);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.tiles_size;
			int xOffset = (int) (Game.tiles_size - hitbox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * Game.tiles_size;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.tiles_size);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.tiles_size;
			int yOffset = (int) (Game.tiles_size - hitbox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.tiles_size;

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}

	/**
	 * We just check the bottomleft of the enemy here +/- the xSpeed. We never check
	 * bottom right in case the enemy is going to the right. It would be more
	 * correct checking the bottomleft for left direction and bottomright for the
	 * right direction. But it wont have big effect in the game. The enemy will
	 * simply change direction sooner when there is an edge on the right side of the
	 * enemy, when its going right.
	 */
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
	    float checkX;

	    // Adjust the check position based on the direction of movement
	    if (xSpeed > 0) {
	        // Moving to the right, check bottom right
	        checkX = hitbox.x + hitbox.width + xSpeed;
	    } else {
	        // Moving to the left, check bottom left
	        checkX = hitbox.x + xSpeed;
	    }

	    // Check for collision at the adjusted position
	    return IsSolid(checkX, hitbox.y + hitbox.height + 1, lvlData);
	}

	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
			if (!IsTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}

		return true;
	}

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.tiles_size);
		int secondXTile = (int) (secondHitbox.x / Game.tiles_size);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);

	}
}
