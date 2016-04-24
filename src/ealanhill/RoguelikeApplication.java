package ealanhill;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;
import ealanhill.objects.World;
import ealanhill.screens.Screen;

/**
 * The main application to read the "config.properties" file, generate
 * the maze from the properties provided, and display the maze to the user.
 * 
 * @author Alan Hill
 *
 */
public class RoguelikeApplication extends JFrame implements KeyListener {

	/** the UID for this frame */
	private static final long serialVersionUID = 4329591566564043380L;
	/** the panel to display the ascii art of the maze */
	private AsciiPanel terminal = new AsciiPanel(100, 100);
	/** the screen that holds and reacts to user interaction */
	private Screen screen = null;
	/** the properties we have read from the properties file for configuring the world */
	private Properties properties = new Properties();
	
	/**
	 * Default constructor to set up the display
	 */
	public RoguelikeApplication() {
		// call our super constructor
		super();
		// add the terminal to the frame
		add(terminal);
		// ensure the user can properly see the screen
		pack();
		// add this as the key listener to delegate the user action to the screen
		addKeyListener(this);
		// load the properties file
		loadProperties();
	}
	
	/**
	 * Builds the world using the properties loaded from the properties file.
	 */
	public void buildWorld() {
		// create our world builder
		WorldBuilder builder = new WorldBuilder();
		
		// attempt to get the desired maximum room size
		Integer maxRoomSize = properties.getProperty("maxRoomSize") == null ?
				null : new Integer(properties.getProperty("maxRoomSize"));
		
		// attempt to get the desire number of tries to place a room
		Integer roomTries = properties.getProperty("roomTries") == null ?
				null : new Integer(properties.getProperty("roomTries"));
		
		// attempt to get the desired width of the world
		Integer worldWidth = properties.getProperty("worldWidth") == null ?
				null : new Integer(properties.getProperty("worldWidth"));
		
		// attempt to get the desire height of the world
		Integer worldHeight = properties.getProperty("worldHeight") == null ?
				null : new Integer(properties.getProperty("worldHeight"));
		
		// build our world with the desired settings
		World world = builder.setMaxRoomSize(maxRoomSize)
							 .setRoomTries(roomTries)
							 .setWorldWidth(worldWidth)
							 .setWorldHeight(worldHeight)
							 .create();
		
		// create a new screen with our terminal and the world
		screen = new Screen(terminal, world);
		
		repaint();
	}
	
	@Override
	public void repaint() {
		terminal.clear();
		screen.display();
		super.repaint();
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		screen = this.screen.respondToUserInput(keyEvent);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {	}
	
	public static void main(String[] args) {
		// create the new application
		RoguelikeApplication application = new RoguelikeApplication();
		// ensure the application closes when the user closes the JFrame
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ensure the user can view the JFrame
		application.setVisible(true);
		// build the world
		application.buildWorld();
	}
	
	/**
	 * Reads the "config.properties" file if it exists.
	 */
	private void loadProperties() {
		try {
			// get a stream for the file
			InputStream input = new FileInputStream("config.properties");
			
			// load the file into the properties object
			properties.load(input);
			
			// close our stream
			input.close();
		} catch(IOException e) {
			// nothing to do if we can't find the properties file, we can just use the defaults
		}
	}
}
