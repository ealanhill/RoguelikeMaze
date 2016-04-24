package ealanhill;

import java.util.List;
import java.util.Random;

import ealanhill.objects.Room;
import ealanhill.objects.Tile;
import ealanhill.objects.World;

public class WorldBuilder {

	private int worldWidth = -1;
	private int worldHeight = -1;
	private int roomTries = -1;
	private int maxRoomSize = -1;
	private World world = null;
	
	public WorldBuilder() {
		
	}
	
	public WorldBuilder fill() {
		for(int x = 0; x < this.worldWidth; x++) {
			for(int y = 0; y < this.worldHeight; y++) {
				this.world.insertTile(x, y, Tile.Wall);
			}
		}
		
		return this;
	}
	
	public WorldBuilder setWorldWidth(Integer worldWidth) {
		if(worldWidth != null) {
			this.worldWidth = worldWidth.intValue();
		}
		
		return this;
	}
	
	public WorldBuilder setWorldHeight(Integer worldHeight) {
		if(worldHeight != null) {
			this.worldHeight = worldHeight.intValue();
		}
		
		return this;
	}
	
	public WorldBuilder setRoomTries(Integer roomTries) {
		if(roomTries != null) {
			this.roomTries = roomTries;
		}
		
		return this;
	}
	
	public WorldBuilder setMaxRoomSize(Integer maxRoomSize) {
		if(maxRoomSize != null) {
			this.maxRoomSize = maxRoomSize;
		}
		
		return this;
	}
	
	public WorldBuilder setWorld(World world) {
		this.world = world;
		return this;
	}
	
	public WorldBuilder addRoom(Room roomToAdd) {
		if(this.world.insertRoom(roomToAdd)) {
			growMaze(roomToAdd);
		}
		
		return this;
	}
	
	public World create() {
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
		
		if(this.world == null) {
			this.world = new World(this.worldWidth, this.worldHeight);
		}
		
		fill();
		
		for(int i = 0; i < this.roomTries; i++) {
			Room roomToAdd = generateRandomRoom();
			
			addRoom(roomToAdd);
		}
		
		for(Room room : this.world.getRooms()) {
			insertDoors(room);
		}
		
		return this.world;
	}
	
	private Room generateRandomRoom() {
		int roomWidth = new Random().nextInt(this.maxRoomSize) + 3;
		int roomHeight = new Random().nextInt(this.maxRoomSize) + 3;
		int roomX = new Random().nextInt(this.worldWidth - roomWidth);
		int roomY = new Random().nextInt(this.worldHeight - roomHeight);
		
		return new Room(roomX, roomY, roomWidth, roomHeight);
	}
	
	private void growMaze(Room room) {
		if(this.world.getRooms().size() < 2) {
			return;
		}
		
		List<Room> rooms = this.world.getRooms();
		
		int centerX = (int)room.getCenterX();
		int centerY = (int)room.getCenterY();
		
		Room previousRoom = rooms.get(rooms.size() - 2);
		
		int previousCenterX = (int)previousRoom.getCenterX();
		int previousCenterY = (int)previousRoom.getCenterY();
		
		if(new Random().nextInt(99) % 2 == 1) {
			insertCorridor(centerY, previousCenterY, previousCenterX, false);
			insertCorridor(centerX, previousCenterX, centerY, true);
		} else {
			insertCorridor(centerX, previousCenterX, previousCenterY, true);
			insertCorridor(centerY, previousCenterY, centerX, false);
		}
	}
	
	private void insertCorridor(int point1, int point2, int constant, boolean horizontal) {
		int max = Math.max(point1, point2);
		int min = Math.min(point1, point2);
		
		for(int i = min; i < max; i++) {
			if(horizontal) {
				this.world.insertTile(i, constant, Tile.Floor);
			} else {
				this.world.insertTile(constant, i, Tile.Floor);
			}
		}
	}
	
	private void insertDoors(Room room) {
		int north = room.y - 1;
		int south = room.y + room.height;
		int east = room.x + room.width;
		int west = room.x - 1;
		
		if(new Random().nextInt(10) % 2 == 1) {
			for(int i = room.x; i < room.x + room.width; i++) {
				insertDoor(i, north);
				insertDoor(i, south);
			}
		}
		
		if(new Random().nextInt(10) % 2 == 1) {
			for(int i = room.y; i < room.y + room.height; i++) {
				insertDoor(east, i);
				insertDoor(west, i);
			}
		}
	}
	
	private void insertDoor(int x, int y) {
		if(this.world.getTile(x, y) == Tile.Floor && (
				(this.world.getTile(x + 1, y) == Tile.Wall && this.world.getTile(x - 1, y) == Tile.Wall) ||
				(this.world.getTile(x, y + 1) == Tile.Wall && this.world.getTile(x, y - 1) == Tile.Wall)
			))
		{
			this.world.insertTile(x, y, Tile.ClosedDoor);
		}
	}
}
