package ealanhill.objects;

import java.awt.Color;

public enum Tile {

	Floor(' ', Color.BLACK),
	Wall('#', Color.WHITE),
	OpenDoor('X', Color.GREEN),
	ClosedDoor('X', Color.RED),
	Bounds('.', null);
	
	private char glyph;
	private Color color;
	
	private Tile(char glyph, Color color) {
		this.glyph = glyph;
		this.color = color;
	}
	
	public char getGlyph() {
		return this.glyph;
	}
	
	public Color getColor() {
		return this.color;
	}
}
