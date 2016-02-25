//Jacqueline Lee (jrl9rc)
//Qi Tang (qt4ur)

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Zombie.java
 * 
 * The class representing the Zombie object in the game. This class needs to
 * have some representation of current location, a reference to its target (aka
 * the Human), a speed, and a Rectangle representing its hitbox. It might also
 * help to have variables representing the size of both the Zombie and its
 * hitbox. You should create methods for the following: 1. checking to see if
 * this Zombie's hitbox Rectangle is colliding with any other hitbox Rectangle
 * (either the Human or an obstacle Rectangle) 2. movement (normal movement and
 * what to do if there is a collision) 3. constructors 4. getters/setters 5.
 * anything else you may need.
 * 
 * @authors
 * @compids
 * @lab
 */
public class Zombie {
	private double x;
	private double y;
	private double speed;
	private Rectangle hitbox;

	private Human player;		//reference to its target
	
	public Zombie(int speed, Human girl){
		Random rand=new Random();
		this.x=rand.nextInt(600);
		this.y=rand.nextInt(600);
		this.speed=speed;
		hitbox=new Rectangle((int)x+15,(int)y + 5,35,75);
		
		player = girl;
	}

	
	
	public void move(float elapsedTime) {	
		//figure out distance to move, and direction, and sets x and y
		//System.out.println("Speed: " + this.speed + ", elapsedTime: " + elapsedTime);
		double xOfGirl = player.getX();
		double yOfGirl = player.getY();

		double xdis=this.x-xOfGirl;
		double ydis=this.y-yOfGirl;
		double dis=Math.sqrt(xdis*xdis+ydis*ydis);
		this.setX(this.x-xdis*this.speed*elapsedTime/dis);
		setY(this.y-ydis*this.speed*elapsedTime/dis);
		
	
		Rectangle rec=new Rectangle((int)x+15, (int)y+5, 35,75);
		setHitbox(rec);
	}
	
	public void slide(float elapsedTime, Rectangle obstacle){
			
			//figure out the intersection
			
			//depending on the comparison we do different action
			Rectangle r= this.getHitbox().intersection(obstacle);
			double width=r.getWidth();
			double height=r.getHeight();
			//compare width and height 
			if(width>height ){
				//move left and right
				setY(this.y-height);
				if (this.collides(obstacle))
					setY(this.y+2*height);
			}
			else{
				//move up and down
				setX(this.x-width);
				if (this.collides(obstacle))
					setX(this.x+2*width);
			}
	}
	
	
	
	public boolean collides(Human girl) {
		if(girl.getHitbox().intersects(this.getHitbox())) {
			return true;
		}	
		else
			return false;
	}
	// (girl.getHitbox().intersection(this.getHitbox()).getHeight() * girl.getHitbox().intersection(this.getHitbox()).getWidth()) > 100
	
	
	public boolean collides(Rectangle obstacle) {
		if(obstacle.intersects(this.getHitbox()))
			return true;
		else
			return false;
	}
	
	public boolean collides(Zombie z) {
		if(z.getHitbox().intersects(this.getHitbox()))
			return true;
		else
			return false;
	}
	
	//check if human collides any walls
	public boolean collides (ArrayList<Rectangle> obstacles){
		for (int i=0;i<obstacles.size();i++){
			if(this.hitbox.intersects(obstacles.get(i)))
				return true;	
		}
		return false;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public Human getGirl() {
		return player;
	}

	public void setGirl(Human girl) {
		this.player = girl;
	}
	

}

//NOTES:
//- Added field called girl: Human
//- Edited constructor to get information for girl
//- Added getters and setters for fields
//- Created method move()
//- Changed constructor to accept Human girl parameter; set player = girl
//- Added collides() method x3