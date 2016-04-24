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

public class RoguelikeApplication extends JFrame implements KeyListener {

	private static final long serialVersionUID = 4329591566564043380L;
	private AsciiPanel terminal = new AsciiPanel(100, 100);
	private Screen screen = null;
	private Properties properties = new Properties();
	
	public RoguelikeApplication() {
		super();
		add(terminal);
		pack();
		addKeyListener(this);
		loadProperties();
	}
	
	public void buildWorld() {
		WorldBuilder builder = new WorldBuilder();
		
		Integer maxRoomSize = properties.getProperty("maxRoomSize") == null ? null : new Integer(properties.getProperty("maxRoomSize"));
		Integer roomTries = properties.getProperty("roomTries") == null ? null : new Integer(properties.getProperty("roomTries"));
		Integer worldWidth = properties.getProperty("worldWidth") == null ? null : new Integer(properties.getProperty("worldWidth"));
		Integer worldHeight = properties.getProperty("worldHeight") == null ? null : new Integer(properties.getProperty("worldHeight"));
		
		World world = builder.setMaxRoomSize(maxRoomSize)
							 .setRoomTries(roomTries)
							 .setWorldWidth(worldWidth)
							 .setWorldHeight(worldHeight)
							 .create();
		
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
		RoguelikeApplication application = new RoguelikeApplication();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setVisible(true);
		application.buildWorld();
	}
	
	private void loadProperties() {
		try {
			InputStream input = new FileInputStream("config.properties");
			
			properties.load(input);
			
			input.close();
		} catch(IOException e) {
			// nothing to do if we can't find the properties file, we can just use the defaults
		}
	}
}
