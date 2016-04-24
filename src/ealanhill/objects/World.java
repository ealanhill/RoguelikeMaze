package ealanhill.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * The world the maze is to be displayed in and the
 * user is to be able to explore.
 * 
 * @author Alan Hill
 *
 */
public class World {

	/** a 2d array to hold the tiles */
	private Tile[][] tiles;
	/** the height of the world */
	private int height = 0;
	/** the width of the world */
	private int width = 0;
	/** a list of all the rooms in the world */
	private List<Room> rooms = new ArrayList<Room>();
	
	/**
	 * Create a world with the specified height and width
	 * 
	 * @param width
	 * @param height
	 */
	public World(int width, int height) {
		this.height = height;
		this.width = width;
		this.tiles = new Tile[width][height];
	}
	
	/**
	 * Create a world with the specified height and width and 
	 * the given set of tiles.
	 * 
	 * @param width
	 * @param height
	 * @param tiles
	 */
	public World(int width, int height, Tile[][] tiles) {
		this.height = height;
		this.width = width;
		this.tiles = tiles;
	}
	
	/**
	 * Get a {@link Tile} at the specified x and y coordinate
	 * 
	 * @param x
	 * @param y
	 * @return a {@link Tile}
	 */
	public Tile getTile(int x, int y) {
		// ensure the coordinates are within the bounds
		// of the world
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.Bounds;
		}
		
		return this.tiles[x][y];
	}
	
	/**
	 * Inserts a {@link Tile} into the world at the specified
	 * x and y coordinate.
	 * 
	 * @param x
	 * @param y
	 * @param tile 
	 */
	public void insertTile(int x, int y, Tile tile) {
		// ensure the values are within the bounds of the world
		if(x > 0 || x < width || y > 0 || y < height) {
			this.tiles[x][y] = tile;
		}
	}
	
	/**
	 * Attempts to insert a room into the world, if it doesn't
	 * overlap another room or a corridor.
	 * 
	 * @param roomToAdd the {@link Room} to be added into the world
	 * 
	 * @return {@code true} if the room was added, {@code false} if not
	 */
	public boolean insertRoom(Room roomToAdd) {
		// check to see if the room is safe to add
		boolean safeToAdd = true;
		
		// go through all the rooms and see if they
		// collide with the room we want to add
		for(Room room : this.rooms) {
			if(room.collides(roomToAdd)) {
				// if so, it's not safe to add the room
				safeToAdd = false;
			}
		}
		
		// get the cardinal points of the room, to see if there's
		// a corridor along the side of the room
		int north = roomToAdd.y - 1;
		int south = roomToAdd.y + roomToAdd.height;
		int east = roomToAdd.x + roomToAdd.width;
		int west = roomToAdd.x - 1;
		
		// ensure the cardinal values are within the bounds of the world
		if(north >= 0 && east < this.width && south < this.height && west >= 0) {
			// go along the top and bottom of the room and see if there's a corridor
			for(int i = roomToAdd.x; i < roomToAdd.x + roomToAdd.width; i++) {
				if(this.tiles[i][north] == Tile.Floor || this.tiles[i][south] == Tile.Floor) {
					// if there is, then it's not safe to add the room
					safeToAdd = false;
				}
			}
			
			// go along the sides of the room and see if there's a corridor
			for(int i = roomToAdd.y; i < roomToAdd.y + roomToAdd.height; i++) {
				if(this.tiles[east][i] == Tile.Floor || this.tiles[west][i] == Tile.Floor) {
					// if there is, then it's not safe to add the room
					safeToAdd = false;
				}
			}
		}
		
		// if the room is safe to be added, then add it,
		// and carve out the room from the tiles
		if(safeToAdd) {
			this.rooms.add(roomToAdd);
			carveRoom(roomToAdd);
		}
		
		// return whether or not we added the room
		return safeToAdd;
	}
	
	/**
	 * Get the height of the world
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Get the width of the world
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Get all the rooms within the room
	 * 
	 * @return a {@link List} of {@link Room}s in the world
	 */
	public List<Room> getRooms() {
		return this.rooms;
	}
	
	/**
	 * Converts the wall tiles to floor tiles in the world.
	 * 
	 * @param roomToCarve the {@link Room} to be carved
	 */
	private void carveRoom(Room roomToCarve) {
		for(int x = roomToCarve.x; x < (roomToCarve.x + roomToCarve.width); x++) {
			for(int y = roomToCarve.y; y < (roomToCarve.y + roomToCarve.height); y++) {
				insertTile(x, y, Tile.Floor);
			}
		}
	}
}
