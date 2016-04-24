package ealanhill;

import java.util.List;
import java.util.Random;

import ealanhill.objects.Room;
import ealanhill.objects.Tile;
import ealanhill.objects.World;

/**
 * Creates a Roguelike maze.
 * 
 * @author Alan Hill
 *
 */
public class WorldBuilder {

	/** the world's width */
	private int worldWidth = -1;
	/** the world's height */
	private int worldHeight = -1;
	/** the number of tries to attempt to place a room */
	private int roomTries = -1;
	/** the maximum size of a room (either the height or width) */
	private int maxRoomSize = -1;
	/** the world */
	private World world = null;
	
	/**
	 * Default constructor to set up the class
	 */
	public WorldBuilder() {
		
	}
	
	/**
	 * Fills the world with walls.
	 * 
	 * @return the {@link WorldBuilder}
	 */
	public WorldBuilder fill() {
		// loop through the x and y coordinates to insert the walls
		for(int x = 0; x < this.worldWidth; x++) {
			for(int y = 0; y < this.worldHeight; y++) {
				this.world.insertTile(x, y, Tile.Wall);
			}
		}
		
		return this;
	}
	
	/**
	 * Sets the world's width to the desired value, if a value
	 * is provided.
	 * 
	 * @param worldWidth the desired value for the world's width,
	 * can be {@code null}
	 * 
	 * @return the {@link WorldBuilder}
	 */
	public WorldBuilder setWorldWidth(Integer worldWidth) {
		// we don't want to set the worldWidth to null, as that would
		// cause a NullPointerException
		if(worldWidth != null) {
			// grab the int value
			this.worldWidth = worldWidth.intValue();
		}
		
		return this;
	}
	
	/**
	 * Sets the world's height to the desired value, if a value is
	 * provided.
	 * 
	 * @param worldHeight the desired value for the world's height,
	 * can be {@code null}
	 * 
	 * @return the {@link WorldBuilder}
	 */
	public WorldBuilder setWorldHeight(Integer worldHeight) {
		// we don't want to set the worldHeight to null, as that would
		// cause a NullPointerException
		if(worldHeight != null) {
			// grab the int value
			this.worldHeight = worldHeight.intValue();
		}
		
		return this;
	}
	
	/**
	 * Sets the desired number of tries to place a room in the world.
	 * 
	 * @param roomTries the desired number of tries to place a room,
	 * can be {@code null}
	 * 
	 * @return the {@link WorldBuilder}
	 */
	public WorldBuilder setRoomTries(Integer roomTries) {
		// we don't want to set the roomTries to null, as that would
		// cause a NullPointerException
		if(roomTries != null) {
			// grab the int value
			this.roomTries = roomTries;
		}
		
		return this;
	}
	
	/**
	 * Sets the desired maximum room size, both the height and width
	 * 
	 * @param maxRoomSize the maximum room size, height and width,
	 * can be {@code null}
	 * 
	 * @return the {@link WorldBuilder}
	 */
	public WorldBuilder setMaxRoomSize(Integer maxRoomSize) {
		// we don't want to set the maxRoomSize to null, as that would
		// cause a NullPointerExcept
		if(maxRoomSize != null) {
			// grab the int value
			this.maxRoomSize = maxRoomSize;
		}
		
		return this;
	}
	
	/**
	 * Sets the {@link World} for the builder, if desired.
	 * 
	 * @param world the {@link World}
	 * 
	 * @return the {@link WorldBuilder}
	 */
	public WorldBuilder setWorld(World world) {
		this.world = world;
		return this;
	}
	
	/**
	 * Adds a {@link Room} to the {@link World}
	 * 
	 * @param roomToAdd the room to be added to the {@link World}
	 * 
	 * @return the {@link WorldBuilder}
	 */
	public WorldBuilder addRoom(Room roomToAdd) {
		// attempt to insert the room into the world
		if(this.world.insertRoom(roomToAdd)) {
			// if we successfully add the room, then we
			// want to grow the maze from that room
			growMaze(roomToAdd);
		}
		
		return this;
	}
	
	/**
	 * Builds the {@link World} using the settings provided or
	 * the default settings:
	 * <ul>
	 * <li>World Height = 80 characters</li>
	 * <li>World Width = 80 characters</li>
	 * <li>Maximum Room Size = 10 characters</li>
	 * <li>Room Tries = 10 tries</li>
	 * </ul>
	 * 
	 * @return a {@link World} created using the provided settings
	 * or defaults
	 */
	public World create() {
		// check each of the settings and see if we need
		// to use the default settings
		if(this.worldHeight == -1) {
			this.worldHeight = 80;
		}
		
		if(this.worldWidth == -1) {
			this.worldWidth = 80;
		}
		
		if(this.roomTries == -1) {
			this.roomTries = 100;
		}
		
		if(this.maxRoomSize == -1) {
			this.maxRoomSize = 10;
		}
		
		// if a world has not been provided, then create one
		if(this.world == null) {
			this.world = new World(this.worldWidth, this.worldHeight);
		}
		
		// fill the world with walls
		fill();
		
		// rather than create a world with a uniform number of rooms
		// each time, we create rooms based on the number of attempts,
		// each room that is created is placed in the world, only if
		// they do not overlap other rooms or corridors
		for(int i = 0; i < this.roomTries; i++) {
			// the room to be added to the world
			Room roomToAdd = generateRandomRoom();
			
			// attempt to add the room to the world
			addRoom(roomToAdd);
		}
		
		// go through all the rooms in the world
		// and add doors to each room
		for(Room room : this.world.getRooms()) {
			insertDoors(room);
		}
		
		// return the world we created
		return this.world;
	}
	
	/**
	 * Generates a room with random height, width, and coordinate
	 * in the world.
	 * 
	 * @return a {@link Room}
	 */
	private Room generateRandomRoom() {
		// randomly create the width and height based on the maximum room
		// size, with a minimum size of 3 characters
		int roomWidth = new Random().nextInt(this.maxRoomSize) + 3;
		int roomHeight = new Random().nextInt(this.maxRoomSize) + 3;
		
		// randomly create the coordinates, ensuring the room will fit
		// within the bounds of the room
		int roomX = new Random().nextInt(this.worldWidth - roomWidth);
		int roomY = new Random().nextInt(this.worldHeight - roomHeight);
		
		// return a room created from the parameters
		return new Room(roomX, roomY, roomWidth, roomHeight);
	}
	
	/**
	 * Creates a passage from the provided {@link Room} to the
	 * "previous" room in the list of rooms in the world
	 * 
	 * @param room the {@link Room} to create a corridor from
	 */
	private void growMaze(Room room) {
		// if the number of rooms in the world
		// is less than 2, we cannot create a corridor
		if(this.world.getRooms().size() < 2) {
			return;
		}
		
		// grab the rooms from the world
		List<Room> rooms = this.world.getRooms();
		
		// get the center of the room to use that
		// as the start of the corridor
		int centerX = (int)room.getCenterX();
		int centerY = (int)room.getCenterY();
		
		// get the "previous" room in the list of rooms
		Room previousRoom = rooms.get(rooms.size() - 2);
		
		// get the center of the room to use to end the corridor
		int previousCenterX = (int)previousRoom.getCenterX();
		int previousCenterY = (int)previousRoom.getCenterY();
		
		// randomly determine if the corridor is to start from the horizontal
		// or vertical position; NOTE: when the random number was between 0 and 2,
		// the program favored vertical corridors
		if(new Random().nextInt(99) % 2 == 1) {
			// create a corridor starting in the horizontal
			insertCorridor(centerY, previousCenterY, previousCenterX, false);
			insertCorridor(centerX, previousCenterX, centerY, true);
		} else {
			// create a corridor starting in the vertical
			insertCorridor(centerX, previousCenterX, previousCenterY, true);
			insertCorridor(centerY, previousCenterY, centerX, false);
		}
	}
	
	/**
	 * Inserts a corridor starting the minimum point, and continuing to the maximum,
	 * while holding one of the coordinate values constant
	 * 
	 * @param point1 one of the coordinate values
	 * @param point2 one of the coordinate values
	 * @param constant the coordinate value to be held constant
	 * @param horizontal whether or not the corridor is to be horizontal
	 */
	private void insertCorridor(int point1, int point2, int constant, boolean horizontal) {
		// determine which point is the max and which is the min
		int max = Math.max(point1, point2);
		int min = Math.min(point1, point2);
		
		// starting at the minimum point, insert floor tiles until the maximum point
		for(int i = min; i < max; i++) {
			// if this is a horizontal corridor, then the y coordinate is held constant
			if(horizontal) {
				this.world.insertTile(i, constant, Tile.Floor);
			} else {
				// otherwise the x coordinate is held constant
				this.world.insertTile(constant, i, Tile.Floor);
			}
		}
	}
	
	/**
	 * Randomly inserts closed doors into the provided room.
	 * 
	 * @param room the {@link Room} we want to insert doors into
	 */
	private void insertDoors(Room room) {
		// get the cardinal points of the room since
		// those are the only locations doors will be
		int north = room.y - 1;
		int south = room.y + room.height;
		int east = room.x + room.width;
		int west = room.x - 1;
		
		// randomly determine if we want to insert a
		// door on the north or south ends of the room
		if(new Random().nextInt(10) % 2 == 1) {
			// go through the upper and lower regions of
			// the room and attempt to insert a door
			for(int i = room.x; i < room.x + room.width; i++) {
				insertDoor(i, north);
				insertDoor(i, south);
			}
		}
		
		// randomly determine if we want to insert a
		// door on the east or west ends of the room
		if(new Random().nextInt(10) % 2 == 1) {
			// go through the left and right regions of
			// the room and attempt to insert a door
			for(int i = room.y; i < room.y + room.height; i++) {
				insertDoor(east, i);
				insertDoor(west, i);
			}
		}
	}
	
	/**
	 * Attempts to insert a door into a room.
	 * 
	 * @param x the x coordinate of the door
	 * @param y the y coordinate of the door
	 */
	private void insertDoor(int x, int y) {
		// first check to see if the tile is a floor tile, that's an opening to the corridor
		if(this.world.getTile(x, y) == Tile.Floor && (
				// check to see if there's a wall on the left or right side of the opening, which would
				// indicate a good corridor
				(this.world.getTile(x + 1, y) == Tile.Wall && this.world.getTile(x - 1, y) == Tile.Wall) ||
				// check to see if there's a wall above or below the opening, which would indicate
				// a good corridor
				(this.world.getTile(x, y + 1) == Tile.Wall && this.world.getTile(x, y - 1) == Tile.Wall)
			))
		{
			// insert the closed door
			this.world.insertTile(x, y, Tile.ClosedDoor);
		}
	}
}
