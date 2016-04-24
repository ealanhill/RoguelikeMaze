package ealanhill.objects;

import java.awt.Color;

/**
 * The types of tiles to be displayed to the user.
 * 
 * @author Alan Hill
 *
 */
public enum Tile {

	/** The floor of a room or corridor, a user can walk on it*/
	Floor(' ', Color.BLACK),
	/** A wall to bound the world */
	Wall('#', Color.WHITE),
	/** A closed door to the user */
	ClosedDoor('X', Color.RED),
	/** Indicates the boundary of the world */
	Bounds('.', Color.BLUE);
	
	/** the character to be displayed to indicate the tile to the user */
	private char glyph;
	/** the color of the character */
	private Color color;
	
	/**
	 * Construct a tile with the given character and color
	 * 
	 * @param glyph the character to represent the tile
	 * @param color the color of the tile
	 */
	private Tile(char glyph, Color color) {
		this.glyph = glyph;
		this.color = color;
	}
	
	/**
	 * Retrieve the character to be displayed to the user
	 * 
	 * @return
	 */
	public char getGlyph() {
		return this.glyph;
	}
	
	/**
	 * Retrieve the color of the character
	 * 
	 * @return
	 */
	public Color getColor() {
		return this.color;
	}
}
