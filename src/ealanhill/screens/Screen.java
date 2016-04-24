package ealanhill.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ealanhill.objects.Tile;
import ealanhill.objects.World;

/**
 * A screen to display a world and interact with the user.
 * 
 * @author Alan Hill
 *
 */
public class Screen {

	/** the terminal to show the user for the ascii world */
	private AsciiPanel terminal = null;
	/** the world to be displayed */
	private World world = null;
	/** the x value to center the view */
	private int centerX = 0;
	/** the y value to center the view */
	private int centerY = 0;
	/** how wide the screen should be */
	private int screenWidth = 0;
	/** how high the screen should be */
	private int screenHeight = 0;
	
	/**
	 * Default constructor.
	 * 
	 * @param terminal the {@link AsciiPanel} to show the world
	 * @param world the {@link World} to be displayed to the user
	 */
	public Screen(AsciiPanel terminal, World world) {
		// set the terminal and world
		this.terminal = terminal;
		this.world = world;
		// get the character width and height from the
		// terminal to set the screen appropriately
		this.screenWidth = terminal.getCharWidth();
		this.screenHeight = terminal.getCharHeight();
	}
	
	/**
	 * Displays the world to the user.
	 */
	public void display() {
		// gets the appropriate location of the x and y coordinates
		// in relation to where we are in the display
		int left = getScrollX();
		int top = getScrollY();
		
		// diplay the tiles
		displayTiles(this.terminal, left, top);
	}
	
	/**
	 * Determines the x coordinate of where we are in relation
	 * to the display
	 * 
	 * @return
	 */
	public int getScrollX() {
		// 1. calculate the center of the screen and subtract that from the center x coordinate
		// 2. determine the difference between the world's width and the screen's width
		// 3. determine which one is the minimum between step 1 & 2
		// 4. return the maximum number between 0 and the value determined in step 3
		return Math.max(0, Math.min(centerX - (screenWidth / 2), world.getWidth() - screenWidth));
	}
	
	/**
	 * Determines the y coordinate of where we are in relation
	 * to the display
	 * 
	 * @return
	 */
	public int getScrollY() {
		// 1. calculate the center of the screen and subtract that from the center x coordinate
		// 2. determine the difference between the world's width and the screen's width
		// 3. determine which one is the minimum between step 1 & 2
		// 4. return the maximum number between 0 and the value determined in step 3
		return Math.max(0, Math.min(centerY - (screenHeight / 2), world.getHeight() - screenHeight));
	}
	
	/**
	 * Moves the screen based on the user key input.
	 * <ul>
	 * <li>The up arrow and the 'w' button moves the screen up</li>
	 * <li>The down arrow and the 's' button moves the screen down</li>
	 * <li>The right arrow and the 'd' button moves the screen right</li>
	 * <li>The left arrow and the 'a' button moves the screen left</li>
	 * <li>The 'z' button moves the screen down and left</li>
	 * <li>The 'q' button moves the screen up and left</li>
	 * <li>The 'e' button moves the screen up and right</li>
	 * <li>The 'c' button moves the screen down and right</li>
	 * </ul>
	 * 
	 * @param keyEvent the {@link KeyEvent} generated when the user pressed
	 * a key
	 * 
	 * @return the {@link Screen}
	 */
	public Screen respondToUserInput(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			scrollBy(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			scrollBy( 1, 0);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			scrollBy( 0,-1);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			scrollBy( 0, 1);
			break;
		case KeyEvent.VK_Z:
			scrollBy(-1,-1);
			break;
		case KeyEvent.VK_C:
			scrollBy( 1,-1);
			break;
		case KeyEvent.VK_Q:
			scrollBy(-1, 1);
			break;
		case KeyEvent.VK_E:
			scrollBy( 1, 1);
			break;
		}
		
		return this;
	}
	
	/**
	 * Shifts the center of the screen based on the provided x and y coordinates
	 * @param x the x coordinate to shift the screen
	 * @param y the y coordinate to shift the screen
	 */
	private void scrollBy(int x, int y) {
		centerX = Math.max(0, Math.min(centerX + x, world.getWidth() - 1));
		centerY = Math.max(0, Math.min(centerY + y, world.getHeight() - 1));
	}
	
	/**
	 * Displays the tiles to the user.
	 * 
	 * @param terminal the {@link AsciiPanel} to display the world on
	 * @param left how far to the left the screen is
	 * @param top how far to the top the screen is
	 */
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for(int x = 0; x < world.getWidth(); x++) {
			for(int y = 0; y < world.getHeight(); y++) {
				Tile tile = world.getTile(x + left, y + top);
				// display the tile at this location
				terminal.write(tile.getGlyph(), // get the character to display
								x, // the location in the world
								y, // the location in the world
								tile.getColor() // get the color of the character
							);
			}
		}
	}
}
