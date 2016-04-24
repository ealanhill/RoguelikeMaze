package ealanhill.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ealanhill.objects.Tile;
import ealanhill.objects.World;

public class Screen {

	private AsciiPanel terminal = null;
	private World world = null;
	private int centerX = 0;
	private int centerY = 0;
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	public Screen(AsciiPanel terminal, World world) {
		this.terminal = terminal;
		this.world = world;
		this.centerX = world.getWidth()/2;
		this.centerY = world.getHeight()/2;
		this.screenWidth = terminal.getCharWidth();
		this.screenHeight = terminal.getCharHeight();
	}
	
	public void display() {
		int left = getScrollX();
		int top = getScrollY();
		
		displayTiles(this.terminal, left, top);
		
//		terminal.write('X', this.centerX, this.centerY);
	}
	
	public int getScrollX() {
		return Math.max(0, Math.min(centerX - (screenWidth / 2), world.getWidth() - screenWidth));
	}
	
	public int getScrollY() {
		return Math.max(0, Math.min(centerY - (screenHeight / 2), world.getHeight() - screenHeight));
	}
	
	public Screen respondToUserInput(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			break;
		case KeyEvent.VK_ENTER:
			break;
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
	
	private void scrollBy(int x, int y) {
		centerX = Math.max(0, Math.min(centerX + x, world.getWidth() - 1));
		centerY = Math.max(0, Math.min(centerY + y, world.getHeight() - 1));
	}
	
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for(int x = 0; x < world.getWidth(); x++) {
			for(int y = 0; y < world.getHeight(); y++) {
				Tile tile = world.getTile(x + left, y + top);
				terminal.write(tile.getGlyph(),
								x,
								y,
								tile.getColor()
							);
			}
		}
	}
}
