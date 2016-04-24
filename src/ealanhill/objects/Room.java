package ealanhill.objects;

import java.awt.Rectangle;

/**
 * A room in the roguelike maze. Inherits from {@link Rectangle}
 * since a room is generally rectangular in shape
 * 
 * @author Alan Hill
 *
 */
public class Room extends Rectangle {

	private static final long serialVersionUID = -1502658617382773639L;

	/**
	 * Constructs a new Room whose upper-left corner is specified as (x,y)
	 * and whose width and height are specified by the arguments of the same name.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Room(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**
	 * Determines whether or not the room collides with another room
	 * 
	 * @param r the room to see if this room collides with it
	 * @return {@code true} if the rooms collide, {@code false} otherwise
	 */
	public boolean collides(Room r) {
		// the rooms collide if any of their edges
		// fall within each other
		return !(this.x > r.x + r.width ||
				 this.x + this.width < r.x ||
				 this.y > r.y + r.height ||
				 this.y + this.height < r.y
				);
	}
}
