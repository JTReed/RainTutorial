package com.tutorial.rain;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.tutorial.rain.entity.mob.Player;
import com.tutorial.rain.graphics.Screen;
import com.tutorial.rain.input.Keyboard;
import com.tutorial.rain.level.Level;
import com.tutorial.rain.level.RandomLevel;

/* Main Game does exactly what it sounds like - 
 * Displays the game and runs the game
 * controls threads
 */
public class MainGame extends Canvas implements Runnable
{
	// Runnable - makes sure that we can pass this into Thread constructor
	// Canvas is a blank frame on the screen to manipulate

	private static final long serialVersionUID = 1L;

	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;

	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	private Screen screen;
	public static String title = "Rain";
	private Level level;
	private Player player;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private Keyboard key; 

	public MainGame()
	{
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		level = new RandomLevel(64, 64);		
		key = new Keyboard();
		player = new Player(key);
		
		addKeyListener(key);	// do this after instantiating key
	}

	public synchronized void start()
	{
		// Synchronized - reduces thread interference

		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop()
	{
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run()
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;		// FPS counter
		int updates = 0; 	// update counter
		requestFocus();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				update(); 		// logic - restricted to 60 times per second
				updates++;
				delta--;
			}
			render(); 			// graphics - no limit
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + "   |   " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames  = 0;
			}
		}
		stop();
	}

	public void update()
	{
		key.update();
		player.update();
	}

	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // triple buffering == good
			return;
		}
		
		int xScroll = player.x - screen.width / 2;
		int yScroll = player.y - screen.height / 2;
		
		screen.clear();
		level.render(xScroll, yScroll, screen);
		player.render(screen);
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}		
		
		Graphics g = bs.getDrawGraphics(); // creating graphics context for the buffer
		// between here and g.dispose() is where ALL graphics rendering needs to go
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show(); // IMPORTANT - must show that last buffer

	}

	public static void main(String[] args)
	{
		MainGame game = new MainGame();
		game.frame.setResizable(false); // Make sure this is applied FIRST if wanted
		game.frame.setTitle(title);
		game.frame.add(game); // Fills the window with /something/ - possible because it extends canvas
		game.frame.pack(); // sets the size of the frame based on the components
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); // centers the window
		game.frame.setVisible(true);

		game.start();
	}
}
