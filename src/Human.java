//Jacqueline Lee (jrl9rc)
//Qi Tang (qt4ur)
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Human.java
 * 
 * The player's character. This class should have fields that can represent 
 *
	 * the players' current location
	 * the location to which it is trying to go
	 * a relative speed
	 * a number of bombs
	 * a Rectangle representing the player's hitbox. 
 * 
 * Other fields for the size of the player and the hitbox may
 * be useful, along with frame information if you do animation. You should
 * create methods for the following:
 *  
	 * 1. checking to see if the player's hitbox
	 * Rectangle is colliding with any other hitbox Rectangle (just obstacle
	 * Rectangles - Zombies can handle Human collision) 
	 * 
	 * 2. movement (normal movement
	 * and what to do if there is a collision) 
	 * 
	 * 3. constructors 
	 * 
	 * 4. getters/setters 
	 * 
	 * 5.bomb counting 
	 * 
	 * 6. anything else you may need.
 * 
 * @authors
 * @compids
 * @lab
 * 
 */
public class Human {

	private double x;
	private double y;
	private double speed;
	private Rectangle hitbox;
	private int bomb; 
	private int tick;
	private boolean added;
	
	//constructor
	public Human(double x, double y, double speed){
		this.setX(x);
		this.setY(y);
		this.setBomb(3);
		this.setTick(0);
		this.setSpeed(speed);
		this.hitbox = new Rectangle((int) x, (int) y+5, 35, 55);

		this.added = false;
	}

	//move the human
	public void move(float mouseX,float mouseY, float elapsedTime){
		double xdis=this.x+25-mouseX;
		double ydis=this.y+40-mouseY;
		double dis=Math.sqrt(xdis*xdis+ydis*ydis);
		setX(this.x-xdis*this.speed*elapsedTime/dis);
		setY(this.y-ydis*this.speed*elapsedTime/dis);	
			
		Rectangle rec=new Rectangle((int)x, (int)y + 5, 35, 55);
		setHitbox(rec);

		
		if (tick%50000==0 && tick != 0) {
			setBomb(getBomb() + 1);
			setAdded(true);
		}
	}

	//check if human collides any walls
	public boolean collides (ArrayList<Rectangle> obstacles){
		for (int i=0;i<obstacles.size();i++){
			if(this.hitbox.intersects(obstacles.get(i)))
				return true;	
		}
		return false;
	}
	
	public boolean collides (Rectangle obstacle){
		if(this.hitbox.intersects(obstacle))
			return true;	
		else
			return false;
	}
	

		

	//setters
	
	public int getTick() {
		return tick;
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

	public void setTick(int tick) {
		this.tick = tick;
	}

	public boolean getAdded() {
		return added;
	}

	public void setAdded(boolean a) {
		this.added = a;
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

	public int getBomb() {
		return bomb;
	}

	public void setBomb(int bomb) {
		this.bomb = bomb;
	}


	
	

}
