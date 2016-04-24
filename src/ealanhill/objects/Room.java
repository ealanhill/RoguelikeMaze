package ealanhill.objects;

import java.awt.Rectangle;

public class Room extends Rectangle {

	private static final long serialVersionUID = -1502658617382773639L;

	public Room(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public boolean collides(Rectangle r) {
		return !(this.x > r.x + r.width ||
				 this.x + this.width < r.x ||
				 this.y > r.y + r.height ||
				 this.y + this.height < r.y
				);
	}
}
