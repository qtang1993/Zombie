//Jacqueline Lee (jrl9rc)
//Qi Tang (qt4ur)

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ZombieSurvival.java
 * 
 * The ZombieSurvival is the field of play for the game. It passes messages
 * between the Human and the Zombies. It also picks up the commands from the
 * mouse and does the appropriate action. This is the class that will have the
 * main method to start the game.
 * 
 * @authors
 * @compid
 * @lab
 */
public class ZombieSurvival {

	// The SurvivalField needs a canvas to draw on
	private SimpleCanvas canvas;

	// The InfoFrame that you use for output instead of System.out
	private InfoFrame output;

	// Default board size
	private final int BOARDHEIGHT = 700;
	private final int BOARDWIDTH = 700;

	// --------------------------------------------------------
	// Fields
	// You should setup fields to keep up with:
	// - a whole bunch of Zombies
	// - a single Human
	// - a whole bunch of obstacles, represented as Rectangles
	// - some way to know if the game is over
	// - a way to keep track of the score
	// - how many zombies you should start with
	// --------------------------------------------------------
	private ArrayList<Zombie> Zombies;
	private Human girl;
	private ArrayList<Rectangle> obstacles;
	private boolean gameover;
	private double score;
	private int startZombies;
	private double mouseX;
	private double mouseY;
	private boolean flag;
	private boolean levelFlag;
	private int level;
	private boolean boom;
	private int count;

	// -----------------------------------------
	// Methods

	/**
	 * The Constructor - This method should instantiate a new canvas, create a
	 * new player character, and create the first four zombies in random
	 * locations around the board.
	 */
	public ZombieSurvival() throws Exception {
		// Create canvas object with 500x500 spatial dimensions.
		canvas = new SimpleCanvas(BOARDWIDTH, BOARDHEIGHT, this);

		// Initialize your output frame
		output = new InfoFrame(this);

		// TODO: Here is where you should create your initial zombies and your
		// Human
		// 20 is a good speed for the human - 10 for the zombie, but experiment!
		// You should also load your course file here to get the obstacles
		// on screen.

		Zombies = new ArrayList<Zombie>();
		obstacles = new ArrayList<Rectangle>();
		gameover = false;
		score = 0;
		startZombies = 4;
		mouseX = 0;
		mouseY = 0;
		flag = true;
		levelFlag = false;
		level = 1;
		girl = new Human(350, 350, 40);
		boom=false;
		count=0;

		for (int i = 0; i < startZombies; i++) {
			Zombie z = new Zombie(20, girl);
			this.Zombies.add(z);
		}
		loadObstacles("level1.csv");

	}

	/**
	 * This method takes a file name that contains information about obstacles
	 * in the game. It should be formatted: x,y,width,height
	 * 
	 * @param filename
	 *            Name of the file
	 * @throws Exception
	 */
	public void loadObstacles(String filename) throws Exception {
		// TODO: fill in this method to read the csv file and
		// populate a list of obstacle Rectangles
		File file = new File(filename);
		Scanner read = new Scanner(file);

		while (read.hasNextLine()) {
			String[] a = read.nextLine().split(",");
			Rectangle r = new Rectangle(Integer.parseInt(a[0]),
					Integer.parseInt(a[1]), Integer.parseInt(a[2]),
					Integer.parseInt(a[3]));
			obstacles.add(r);
		}
	}

	/**
	 * This method should control all of your mouse actions. The mouse activity
	 * is picked up by the SimpleCanvas and then it should call this method,
	 * passing either the button that was pressed or some other flag.
	 */
	public void mouseAction(float x, float y, int button) {
		// TODO: Change this method to help the player move!
		//mobe
		if (button == -1) {
			mouseX = x;
			mouseY = y;
			if (girl.getAdded() == true && gameover == false) {
				output.println("You've earned another bomb! You now have "
						+ girl.getBomb() 
						+ "!");
				girl.setAdded(false);
			}
		}
		
		//bomb 
		if (button == 1 || button == 3) {
			if (girl.getBomb() > 0 && gameover == false) {
				boom=true;
				output.println("BOOM! You dropped a bomb! You now have "
						+ girl.getBomb() + " bombs!");
				detonateBomb();
			} else
				output.println("Out of bombs!");
		}

	}

	/**
	 * This method controls the bomb action. It should check to see if the
	 * player has any bombs. If so, that count should be decremented by one.
	 * Then every zombie within a 50 pixel radius of the player should be
	 * eliminated.
	 */
	public void detonateBomb() {
		if (girl.getBomb() > 0) {
			girl.setBomb(girl.getBomb() - 1);
		}

		for (int i = 0; i < Zombies.size(); i++) {
			double xOfGirl = girl.getX() + 25; double yOfGirl = girl.getY() + 40;
			double xZombie = Zombies.get(i).getX() + 25; double yZombie = Zombies.get(i).getY() + 40;
			double dist = Math.sqrt(Math.pow((xOfGirl - xZombie),2) + Math.pow((yOfGirl - yZombie), 2));
			if (dist <= 100) {
				Zombies.remove(i);
				i--;
				score +=100;
				output.println("You killed a Zombie! 100 points have been added to your score. Score is " + (int)score+ "!");
			}
		}
		
	}

	/**
	 * This is the main drawing function that is automatically called whenever
	 * the canvas is ready to be redrawn. The 'elapsedTime' argument is the
	 * time, in seconds, since the last time this function was called.
	 * 
	 * Other things this method should do: 1. draw the zombies, obstacles, and
	 * the human on the screen 2. tell the zombies and human to move 3. check to
	 * see if the game is over after they move 4. halt the game if the game is
	 * over 5. update the score for every cycle that the user is alive 6. add a
	 * new zombie every 5000 or so cycles 7. add a new bomb every 50000 or so
	 * cycles
	 * 
	 * To do: collides with wall (zombie, girl) collides with another zombies
	 * (zombie)
	 * @throws Exception 
	 * 
	 */
	public void draw(Graphics2D g, float elapsedTime) throws Exception {
		// TODO: Nearly ALL your game logic will go here! This is called on
		// every refresh of the screen and is the "main game loop".
		
		// This is how your draw an obstacle, replacing the new Rectangle with one from your list of obstacles
		//level 1 has one obstacles, level 2 has 2 respectively.
		if (girl.getTick()%75000 == 0 && girl.getTick() != 0) {
			level++;
			levelFlag = true;
			if (level == 5) {
				level = 4; 					
				levelFlag = false;
			}
		}
		for (int i = 0; i < obstacles.size(); i++) {
			canvas.drawObstacle(g, obstacles.get(i));
		}
		if (levelFlag == true) {
			output.println("Congratulations! You've reached level " + level
					+ "!");
			levelFlag = false;
		}
		if (level == 2) {
			obstacles.clear();
			loadObstacles("level2.csv");			
		} else if (level == 3) {
			obstacles.clear();
			loadObstacles("level3.csv");
		} else if (level == 4) {
			obstacles.clear();
			loadObstacles("level4.csv");
		}
		
		
		
		if (gameover == false ) {
			girl.setTick(girl.getTick()+1);
			score+=0.01;
			//add a new zombie every 5000 ticks.
			if (girl.getTick() % 5000 == 0) {
				Zombie z = new Zombie(20, girl);
				Zombies.add(z);

			}
		
			//MOVE AND CHECK COLLIDE
			girl.move((float)mouseX, (float)mouseY, elapsedTime);
			for (int i = 0; i < obstacles.size(); i++) {
				if (girl.collides(obstacles.get(i))) {
					Rectangle r = girl.getHitbox().intersection(obstacles.get(i));
					double rX = r.getX(); double rY = r.getY();
					double rWidth = r.getWidth(); double rHeight = r.getHeight();
					
					//east
					if((rX + rWidth) == (obstacles.get(i).getX() + obstacles.get(i).getWidth())) {
						girl.setX(girl.getX() + rWidth);
						Rectangle rec=new Rectangle((int)(girl.getX() + rWidth), (int)girl.getY()+5, 35, 55);
						girl.setHitbox(rec);

					}
					//west
					else if (rX == obstacles.get(i).getX()) {
						girl.setX(girl.getX()-rWidth);
						Rectangle rec=new Rectangle((int)(girl.getX() - rWidth), (int)girl.getY()+5, 35, 55);
						girl.setHitbox(rec);

					}
					//north
					else if (rY == obstacles.get(i).getY()) {
						girl.setY(girl.getY()-rHeight);
						Rectangle rec=new Rectangle((int)girl.getX(), (int)(girl.getY()-rHeight)+5, 35, 55);
						girl.setHitbox(rec);

					}
					else if ((rY + rHeight) == (obstacles.get(i).getY() + obstacles.get(i).getHeight())) {
						girl.setY(girl.getY()+rHeight);
						Rectangle rec=new Rectangle((int)girl.getX(), (int)(girl.getY()+rHeight)+5, 35, 55);
						girl.setHitbox(rec);

					}
				}
			}
			
			for (int i = 0; i < Zombies.size(); i++) {
				Zombies.get(i).move(elapsedTime);
				if (Zombies.get(i).collides(girl))
					this.gameover = true;
			}
			for (int i = 0; i < Zombies.size(); i++) {
				for (int j = 0; j < obstacles.size(); j++) {
					if (Zombies.get(i).collides(obstacles.get(j))) {
						Rectangle r = Zombies.get(i).getHitbox().intersection(obstacles.get(j));
						double rX = r.getX(); double rY = r.getY();
						double rWidth = r.getWidth(); double rHeight = r.getHeight();
						
						//east
						if((rX + rWidth) == (obstacles.get(j).getX() + obstacles.get(j).getWidth())) {
							Zombies.get(i).setX(Zombies.get(i).getX() + rWidth);
							Rectangle rec=new Rectangle((int)(Zombies.get(i).getX() + rWidth)+15, (int)Zombies.get(i).getY()+5, 35,75);
							Zombies.get(i).setHitbox(rec);
						}
						//west
						else if (rX == obstacles.get(j).getX()) {
							Zombies.get(i).setX(Zombies.get(i).getX()-rWidth);
							Rectangle rec=new Rectangle((int)(Zombies.get(i).getX()-rWidth)+15, (int)Zombies.get(i).getY()+5, 35,75);
							Zombies.get(i).setHitbox(rec);
						}
						//north
						else if (rY == obstacles.get(j).getY()) {
							Zombies.get(i).setY(Zombies.get(i).getY()-rHeight);
							Rectangle rec=new Rectangle((int)Zombies.get(i).getX()+15, (int)(Zombies.get(i).getY()-rHeight)+5, 35,75);
							Zombies.get(i).setHitbox(rec);
						}
						//south
						else if ((rY + rHeight) == (obstacles.get(j).getY() + obstacles.get(j).getHeight())) {
							Zombies.get(i).setY(Zombies.get(i).getY()+rHeight);
							Rectangle rec=new Rectangle((int)Zombies.get(i).getX()+15, (int)(Zombies.get(i).getY()+rHeight)+5, 35,75);
							Zombies.get(i).setHitbox(rec);

						}
					}
				}
			}
			
			for (int i = 0; i < Zombies.size(); i++) {
				for (int j = 0; j < Zombies.size(); j++) {
					if (Zombies.get(i).collides(Zombies.get(j)) && i != j) {
						Rectangle r = Zombies.get(i).getHitbox().intersection(Zombies.get(j).getHitbox());
						double rX = r.getX(); double rY = r.getY();
						double rWidth = r.getWidth(); double rHeight = r.getHeight();
						
						//east
						if(rX + rWidth == Zombies.get(i).getHitbox().getX() + Zombies.get(i).getHitbox().getWidth()) {
							Zombies.get(j).setX(Zombies.get(j).getX() + rWidth);
							Rectangle rec = new Rectangle((int)Zombies.get(j).getX() + 15, (int)Zombies.get(j).getY() + 5, 35, 75);
							Zombies.get(j).setHitbox(rec);
						//west
						} else if (rX == Zombies.get(i).getHitbox().getX()) {
							Zombies.get(j).setX(Zombies.get(j).getX() - rWidth);
							Rectangle rec = new Rectangle((int)Zombies.get(j).getX() + 15, (int)Zombies.get(j).getY() + 5, 35, 75);
							Zombies.get(j).setHitbox(rec);
						//north
						} else if (rY == Zombies.get(i).getHitbox().getY()) {
							Zombies.get(j).setY(Zombies.get(j).getY() - rHeight);
							Rectangle rec = new Rectangle((int)Zombies.get(j).getX() + 15, (int)Zombies.get(j).getY() + 5, 35, 75);
							Zombies.get(j).setHitbox(rec);
						//south
						} else if (rY + rHeight == Zombies.get(i).getHitbox().getY() + Zombies.get(i).getHitbox().getHeight()) {
							Zombies.get(j).setY(Zombies.get(j).getY() + rHeight);
							Rectangle rec = new Rectangle((int)Zombies.get(j).getX() + 15, (int)Zombies.get(j).getY() + 5, 35, 75);
							Zombies.get(j).setHitbox(rec);
						}
					}
				}
			}

			//I STILL DON'T KNOW HOW TO DO THE ZOMBIE-ZOMBIE COLLIDES...
			
			// This is how you DRAW the Human, replacing the null with the human object
			canvas.drawHuman(g, this.girl, mouseX, mouseY);
			if (boom==true && count<200){
				count++;
				canvas.drawBoom(g, (int)girl.getX()-15, (int)girl.getY()-15);
			}
			else if (count>=200){
				boom=false;
				count=0;
			}
			// This is how you draw the Zombies, replacing the null with a zombie object
			for (int i = 0; i < Zombies.size(); i++) {
				canvas.drawZombie(g, Zombies.get(i));
			}
			
		}
		//if game over
		else {
			
			canvas.drawHuman(g, this.girl, mouseX, mouseY);
			for (int i = 0; i < Zombies.size(); i++) {
				canvas.drawZombie(g, Zombies.get(i));
			}
			if (flag == true) {
				output.println("NOMNOMNOMNOMNOMNOM\n\nGame Over!");
				output.println("Your Score: " + (int)score);
				flag = false;
			}
		}	
		




	}

	/**
	 * Your standard main method
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ZombieSurvival simulator = new ZombieSurvival();
		simulator.play();

	}

	/**
	 * This method starts the game.
	 */
	public void play() {
		output.println("New Player Created! You start with " + girl.getBomb()
				+ " bombs!");
		canvas.setupAndDisplay();
	}
}

// NOTES:
// - In draw() method, changed canvas.drawZombie(g, null); to for loop with
// canvas.drawZombie(g, Zombies.get(i));
// - In constructor, added girl to zombie instantiation
// - In constructor, initialized all fields and created for loop to make zombies
// and add to Zombies
// - Added move() to the draw() method
