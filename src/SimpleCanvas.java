//Jacqueline Lee (jrl9rc)
//Qi Tang (qt4ur)

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * SimpleCanvas.java
 * 
 * The SimpleCanvas class contains the drawing methods needed to manage the
 * ZombieSurvival. Many of the methods in this class will not be called by the
 * programmer - instead, they will be automatically called when something
 * occurs. For instance, mouseClicked is called when someone clicks on the
 * ZombieSurvival.
 * 
 * @author Jason Lawrence and Mark Sherriff
 * 
 */
public class SimpleCanvas extends JPanel implements MouseListener, MouseMotionListener {

	// width and height of the window
	private int width;
	private int height;

	// lastTime that the screen was refreshed
	private long lastTime;

	// a link back to the ZombieSurvival for updating it
	private ZombieSurvival simulator;

	// BufferedImages to handle the sprite graphics
	// We've provided a 2D array for zombies and humans in case you want to do
	// animation
	private BufferedImage[][] zombieSprites;
	private BufferedImage[][] humanSprites;
	private BufferedImage boomSprite;

	/**
	 * Constructor for the SimpleCanvas
	 * 
	 * @param width_
	 *            width of the window
	 * @param height_
	 *            height of the window
	 * @param simulator_
	 *            link back to the ZombieSurvival
	 */
	public SimpleCanvas(int width_, int height_, ZombieSurvival simulator_) {
		width = width_;
		height = height_;
		simulator = simulator_;
		lastTime = -1L;

		humanSprites = loadHumanSprites("sprite.png");
		zombieSprites = loadZombieSprites("zombie.png");
		try {
			boomSprite = ImageIO.read(new File("boom.png"));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}

	}

	/**
	 * Loads the images needed to draw the human character. Add code here to do
	 * animation or to use different sprites.
	 * 
	 * @param filename
	 *            name of file to load
	 * @return 2D array of sprites
	 */
	public BufferedImage[][] loadHumanSprites(String filename) {

		BufferedImage[][] spriteArray = new BufferedImage[4][8];
		BufferedImage spriteSheet = null;

		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}

		// load right facing
		spriteArray[0][0] = spriteSheet.getSubimage(3, 80, 46, 80);
		spriteArray[0][1] = spriteSheet.getSubimage(50, 80, 46, 80);
		spriteArray[0][2] = spriteSheet.getSubimage(98, 80, 46, 80);
		spriteArray[0][3] = spriteSheet.getSubimage(147, 80, 46, 80);
		spriteArray[0][4] = spriteSheet.getSubimage(197, 80, 46, 80);
		spriteArray[0][5] = spriteSheet.getSubimage(247, 80, 46, 80);
		spriteArray[0][6] = spriteSheet.getSubimage(297, 80, 46, 80);
		spriteArray[0][7] = spriteSheet.getSubimage(347, 80, 46, 80);
		
		
		// load left facing
		spriteArray[1][0] = getFlippedImage(spriteSheet.getSubimage(3, 80, 46, 80));
		spriteArray[1][1] = getFlippedImage(spriteSheet.getSubimage(50, 80, 46, 80));
		spriteArray[1][2] = getFlippedImage(spriteSheet.getSubimage(198, 80, 46, 80));
		spriteArray[1][3] = getFlippedImage(spriteSheet.getSubimage(147, 80, 46, 80));
		spriteArray[1][4] = getFlippedImage(spriteSheet.getSubimage(197, 80, 46, 80));
		spriteArray[1][5] = getFlippedImage(spriteSheet.getSubimage(247, 80, 46, 80));
		spriteArray[1][6] = getFlippedImage(spriteSheet.getSubimage(297, 80, 46, 80));
		spriteArray[1][7] = getFlippedImage(spriteSheet.getSubimage(347, 80, 46, 80));
		
		// load up facing
		spriteArray[3][0] = spriteSheet.getSubimage(3, 235, 46, 80);
		spriteArray[3][1] = spriteSheet.getSubimage(50, 235, 46, 80);
		spriteArray[3][2] = spriteSheet.getSubimage(95, 235, 46, 80);
		spriteArray[3][3] = spriteSheet.getSubimage(145, 235, 46, 80);
		spriteArray[3][4] = spriteSheet.getSubimage(195, 235, 46, 80);
		spriteArray[3][5] = spriteSheet.getSubimage(245, 235, 46, 80);
		spriteArray[3][6] = spriteSheet.getSubimage(295, 235, 46, 80);
		spriteArray[3][7] = spriteSheet.getSubimage(345, 235, 46, 80);
		
		// load down facing
		spriteArray[2][0] = spriteSheet.getSubimage(3, 158, 46, 80);
		spriteArray[2][1] = spriteSheet.getSubimage(50,158, 46, 80);
		spriteArray[2][2] = spriteSheet.getSubimage(95, 158, 46, 80);
		spriteArray[2][3] = spriteSheet.getSubimage(145, 158, 46, 80);
		spriteArray[2][4] = spriteSheet.getSubimage(195, 158, 46, 80);
		spriteArray[2][5] = spriteSheet.getSubimage(245, 158, 46, 80);
		spriteArray[2][6] = spriteSheet.getSubimage(295, 158, 46, 80);
		spriteArray[2][7] = spriteSheet.getSubimage(345, 158, 46, 80);
		
		
		return spriteArray;
	}

	/**
	 * Loads the images needed to draw the zombie character. Add code here to do
	 * animation or to use different sprites.
	 * 
	 * @param filename
	 *            name of file to load
	 * @return 2D array of sprites
	 */
	public BufferedImage[][] loadZombieSprites(String filename) {

		BufferedImage[][] spriteArray = new BufferedImage[4][8];
		BufferedImage spriteSheet = null;

		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}

		// load right facing
		spriteArray[1][0] = spriteSheet.getSubimage(3, 100, 75, 100);
		spriteArray[1][1] = spriteSheet.getSubimage(78, 100, 75, 100);
		spriteArray[1][2] = spriteSheet.getSubimage(153, 100, 75, 100);
		// load left facing
		spriteArray[3][0] = spriteSheet.getSubimage(3, 300, 75, 100);
		spriteArray[3][1] = spriteSheet.getSubimage(78, 300, 75, 100);
		spriteArray[3][2] = spriteSheet.getSubimage(153, 300, 75, 100);
		// load up facing
		spriteArray[0][0] = spriteSheet.getSubimage(3, 3, 75, 100);
		spriteArray[0][1] = spriteSheet.getSubimage(78, 3, 75, 100);
		spriteArray[0][2] = spriteSheet.getSubimage(153, 3, 75, 100);
		// load down facing
		spriteArray[2][0] = spriteSheet.getSubimage(3, 200, 75, 100);
		spriteArray[2][1] = spriteSheet.getSubimage(78, 200, 75, 100);
		spriteArray[2][2] = spriteSheet.getSubimage(153, 200, 75, 100);
		
		return spriteArray;
	}

	/**
	 * Called to start the game
	 */
	public void setupAndDisplay() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(this));
		f.setSize(width, height);
		f.setLocation(100, 100);
		f.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * This method is NEVER called by the programmer. This method is called
	 * whenever the screen refreshes. Consequently, it calls the draw() method
	 * in ZombieSurvival, telling it to update its components.
	 */
	protected void paintComponent(Graphics g) {
		boolean first = (lastTime == -1L);
		//System.out.println("lastTime: " + lastTime);
		long elapsedTime = System.nanoTime() - lastTime;
		lastTime = System.nanoTime();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		try {
			simulator.draw((Graphics2D) g, (first ? 0.0f : (float) elapsedTime / 1e9f));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("elapsedTime: " + elapsedTime);
		repaint();
	}

	/**
	 * drawDot does just what it says - it puts a dot on the screen.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param x
	 *            x coordinate of dot
	 * @param y
	 *            y coordinate of dot
	 * @param color
	 *            Color instance of color of dot
	 */
	public void drawDot(Graphics2D g, double x, double y, Color color) {
		g.setColor(color);
		g.fillOval((int) (x + 0.5f), (int) (y + 0.5f), 8, 8);
	}
	
	
	
	/**
	 * Given an obstacle Rectangle, this will draw it to the screen
	 * as a white rectangle.  You may change this or add to this method
	 * if you want to use other colors or images.
	 * @param g Graphics engine passed from paintComponent() into ZombieSurvival.draw()
	 * @param o obstacle Rectangle
	 */
	public void drawObstacle(Graphics2D g, Rectangle o) {
		g.setColor(Color.white);
		g.fill(o);
	}

	/**
	 * This method loads the proper graphic from the BufferedImage 2D array and
	 * draws it on the screen. Change the code here to make the character point
	 * in the correct direction.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param z
	 *            Zombie to draw
	 */
	public void drawZombie(Graphics2D g, Zombie z) {

		// TODO: Change this! Right now this draws a zombie at 100,100
		
		
		double xOfGirl = z.getGirl().getX();
		double yOfGirl = z.getGirl().getY();
		
		//g.drawImage(zombieSprites[0][0],(int)z.getX(),(int)z.getY(), null);
		double x = xOfGirl - z.getX();
		double y = yOfGirl - z.getY();
		
		if(x==0)
			x=1;
		if(y==0)
			y=1;
		//double angle = Math.atan(Math.abs(yBetween/xBetween));
		if (x>0 && Math.abs(x/y)>=1){//
//			g.drawImage(zombieSprites[1][0], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==0)
				g.drawImage(zombieSprites[1][0], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==1)
				g.drawImage(zombieSprites[1][1], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==2)
				g.drawImage(zombieSprites[1][2], (int)z.getX(), (int)z.getY(), null);		
		}
		if (x<0 && Math.abs(x/y)>=1 ){
			if ((z.getGirl().getTick()/60)%3==0)
				g.drawImage(zombieSprites[3][0], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==1)
				g.drawImage(zombieSprites[3][1], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==2)
				g.drawImage(zombieSprites[3][2], (int)z.getX(), (int)z.getY(), null);
//			g.drawImage(zombieSprites[3][0], (int)z.getX(), (int)z.getY(), null);
		}
		if (y>0 && Math.abs(x/y)<1 ){
			if ((z.getGirl().getTick()/60)%3==0)
				g.drawImage(zombieSprites[2][0], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==1)
				g.drawImage(zombieSprites[2][1], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==2)
				g.drawImage(zombieSprites[2][2], (int)z.getX(), (int)z.getY(), null);
//			g.drawImage(zombieSprites[2][0], (int)z.getX(), (int)z.getY(), null);
		}
		if (y<0 && Math.abs(x/y)<1 ){
			if ((z.getGirl().getTick()/60)%3==0)
				g.drawImage(zombieSprites[0][0], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==1)
				g.drawImage(zombieSprites[0][1], (int)z.getX(), (int)z.getY(), null);
			if ((z.getGirl().getTick()/60)%3==2)
				g.drawImage(zombieSprites[0][2], (int)z.getX(), (int)z.getY(), null);
//			g.drawImage(zombieSprites[0][0], (int)z.getX(), (int)z.getY(), null);
		}

	}

	/**
	 * This method loads the proper graphic from the BufferedImage 2D array and
	 * draws it on the screen. Change the code here to make the character point
	 * in the correct direction.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param h
	 *            Human to draw
	 */
	public void drawHuman(Graphics2D g, Human h, double mouseX, double mouseY) {
		// TODO: Change this! Right now this draws a human at 300,300
//		g.drawImage(humanSprites[0][0],(int) xOfGirl,(int) yOfGirl, null);

		//g.drawImage(zombieSprites[0][0],(int)z.getX(),(int)z.getY(), null);
		double x = mouseX - h.getX();
		double y = mouseY - h.getY();
		
		if(x==0)
			x=1;
		if(y==0)
			y=1;
		//double angle = Math.atan(Math.abs(yBetween/xBetween));
		if (x>=0 && Math.abs(x/y)>=1){
			if ((h.getTick()/60)%8==0 )
				g.drawImage(humanSprites[0][0], (int)h.getX(), (int)h.getY(), null);
				
			else if ((h.getTick()/60)%8==1)
				g.drawImage(humanSprites[0][1], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==2)
				g.drawImage(humanSprites[0][2], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==3)
				g.drawImage(humanSprites[0][3], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==4)
				g.drawImage(humanSprites[0][4], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==5)
				g.drawImage(humanSprites[0][5], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==6)
				g.drawImage(humanSprites[0][6], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==7)
				g.drawImage(humanSprites[0][6], (int)h.getX(), (int)h.getY(), null);
	
		}
			
		if (x<0 && Math.abs(x/y)>=1 ){
			if ((h.getTick()/60)%8==0 )
				g.drawImage(humanSprites[1][0], (int)h.getX(), (int)h.getY(), null);
				
			else if ((h.getTick()/60)%8==1)
				g.drawImage(humanSprites[1][1], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==2)
				g.drawImage(humanSprites[1][2], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==3)
				g.drawImage(humanSprites[1][3], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==4)
				g.drawImage(humanSprites[1][4], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==5)
				g.drawImage(humanSprites[1][5], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==6)
				g.drawImage(humanSprites[1][6], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==7)
				g.drawImage(humanSprites[1][6], (int)h.getX(), (int)h.getY(), null);
		}
			
		if (y>=0 && Math.abs(x/y)<1 ){
			if ((h.getTick()/60)%8==0 )
				g.drawImage(humanSprites[2][0], (int)h.getX(), (int)h.getY(), null);
				
			else if ((h.getTick()/60)%8==1)
				g.drawImage(humanSprites[2][1], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==2)
				g.drawImage(humanSprites[2][2], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==3)
				g.drawImage(humanSprites[2][3], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==4)
				g.drawImage(humanSprites[2][4], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==5)
				g.drawImage(humanSprites[2][5], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==6)
				g.drawImage(humanSprites[2][6], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==7)
				g.drawImage(humanSprites[2][6], (int)h.getX(), (int)h.getY(), null);
		}
			
		if (y<=0 && Math.abs(x/y)<1 ){
			if ((h.getTick()/60)%8==0 )
				g.drawImage(humanSprites[3][0], (int)h.getX(), (int)h.getY(), null);
				
			else if ((h.getTick()/60)%8==1)
				g.drawImage(humanSprites[3][1], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==2)
				g.drawImage(humanSprites[3][2], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==3)
				g.drawImage(humanSprites[3][3], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==4)
				g.drawImage(humanSprites[3][4], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==5)
				g.drawImage(humanSprites[3][5], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==6)
				g.drawImage(humanSprites[3][6], (int)h.getX(), (int)h.getY(), null);
			else if ((h.getTick()/60)%8==7)
				g.drawImage(humanSprites[3][6], (int)h.getX(), (int)h.getY(), null);
		}
			


	}

	/**
	 * This method should draw the explosion graphic on the screen on top of the
	 * Human character.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param x
	 *            x coordinate to draw
	 * @param y
	 *            y coordinate to draw
	 */
	public void drawBoom(Graphics2D g, int x, int y) {
		// TODO: Fill in this method!
		g.drawImage(boomSprite,(int) x,(int) y, null);
	}

	/**
	 * Flips a BufferedImage. If you need it.
	 * 
	 * @param bi
	 *            image to flip
	 * @return flipped image
	 */
	public BufferedImage getFlippedImage(BufferedImage bi) {
		BufferedImage flipped = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		AffineTransform tran = AffineTransform.getTranslateInstance(bi.getWidth(), 0);
		AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
		tran.concatenate(flip);

		Graphics2D g = flipped.createGraphics();
		g.setTransform(tran);
		g.drawImage(bi, 0, 0, null);
		g.dispose();

		return flipped;
	}

	/**
	 * Whenever the mouse is moved on the ZombieSurvival, this method gets
	 * called.
	 */
	public void mouseMoved(MouseEvent e) {
		boolean first = (lastTime == -1L);
		long elapsedTime = System.nanoTime() - lastTime;
		lastTime = System.nanoTime();
		simulator.mouseAction((float) e.getX(), (float) e.getY(), -1);
	}

	/**
	 * Whenever the mouse is clicked on the ZombieSurvival, this method gets
	 * called.
	 */
	public void mouseClicked(MouseEvent e) {
		simulator.mouseAction((float) e.getX(), (float) e.getY(), e.getButton());
	}

	/**
	 * Whenever the mouse enters the ZombieSurvival, this method gets called.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Whenever the mouse leaves the ZombieSurvival, this method gets called.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Whenever the mouse is pressed (yes, it's just barely different than
	 * clicked) on the ZombieSurvival, this method gets called.
	 */
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Whenever the mouse press is released on the ZombieSurvival, this method
	 * gets called.
	 */
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Whenever the mouse clicked and dragged on the ZombieSurvival, this method
	 * gets called.
	 */
	public void mouseDragged(MouseEvent arg0) {
	}

}

//NOTES:
//- Edited drawZombie() method
