package ealanhill.objects;

import java.util.ArrayList;
import java.util.List;

public class World {

	private Tile[][] tiles;
	private int height = 0;
	private int width = 0;
	private List<Room> rooms = new ArrayList<Room>();
	
	public World(int width, int height) {
		this.height = height;
		this.width = width;
		this.tiles = new Tile[width][height];
	}
	
	public World(int width, int height, Tile[][] tiles) {
		this.height = height;
		this.width = width;
		this.tiles = tiles;
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.Bounds;
		}
		
		return this.tiles[x][y];
	}
	
	public void insertTile(int x, int y, Tile tile) {
		this.tiles[x][y] = tile;
	}
	
	public boolean overlaps(Room roomToAdd) {
		boolean overlaps = false;
		
		for(Room room : rooms) {
			if(room.intersects(roomToAdd)) {
				overlaps = true;
			}
		}
		
		return overlaps;
	}
	
	public boolean insertRoom(Room roomToAdd) {
		boolean safeToAdd = true;
		for(Room room : this.rooms) {
			if(room.collides(roomToAdd)) {
				safeToAdd = false;
			}
		}
		
		int north = roomToAdd.y - 1;
		int south = roomToAdd.y + roomToAdd.height;
		int east = roomToAdd.x + roomToAdd.width;
		int west = roomToAdd.x - 1;
		
		if(north >= 0 && east < this.width && south < this.height && west >= 0) {
			for(int i = roomToAdd.x; i < roomToAdd.x + roomToAdd.width; i++) {
				if(this.tiles[i][north] == Tile.Floor || this.tiles[i][south] == Tile.Floor) {
					safeToAdd = false;
				}
			}
			
			for(int i = roomToAdd.y; i < roomToAdd.y + roomToAdd.height; i++) {
				if(this.tiles[east][i] == Tile.Floor || this.tiles[west][i] == Tile.Floor) {
					safeToAdd = false;
				}
			}
		}
		
		if(safeToAdd) {
			this.rooms.add(roomToAdd);
			carveRoom(roomToAdd);
		}
		
		return safeToAdd;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public Room getRoom(int x, int y) {
		for(Room room : this.rooms) {
			if(room.contains(x, y)) {
				return room;
			}
		}
		
		return null;
	}
	
	public List<Room> getRooms() {
		return this.rooms;
	}
	
	private void carveRoom(Room roomToCarve) {
		for(int x = roomToCarve.x; x < (roomToCarve.x + roomToCarve.width); x++) {
			for(int y = roomToCarve.y; y < (roomToCarve.y + roomToCarve.height); y++) {
				insertTile(x, y, Tile.Floor);
			}
		}
	}
}
