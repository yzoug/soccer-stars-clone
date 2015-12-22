import java.awt.Graphics;
import javax.swing.JPanel;
import java.lang.Math;
import java.awt.image.BufferedImage;

abstract public class PaletAndBall extends JPanel {
	
	protected double xobject,yobject;    //coordinates of palet at all times 
	protected double RADIUS; //30 for palets, 15 for ball
	protected double speed;
	protected double direction;
	protected BufferedImage imageObject;
	
	abstract public boolean isBall();

	public BufferedImage getImage(){
		return imageObject;
	}

    	public double getx(){
		return xobject;
	}	
	
	public double gety(){
		return yobject;
	}	
	
	public double getRadius(){
		return RADIUS;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public double getDirection(){
		return direction;
	}

	//launch the palet/ball according to the user input or the collision happening
	public void start(double firstX, double firstY, double xMouse, double yMouse){
		System.out.println(firstX + " " + firstY + " " + xMouse + " " + yMouse);
		speed = Math.pow(Math.pow(xMouse - firstX,2)+Math.pow(yMouse - firstY, 2),0.5);
		if(speed > 200) speed = 200;
		if(firstX == xMouse) firstX++;
		double dir = Math.atan(Math.abs(xMouse - firstX)/Math.abs(yMouse - firstY));
		System.out.println("dir: "+dir);
		if(xMouse - firstX > 0) {
			if(yMouse - firstY > 0) {
				System.out.println("case 4");
				direction = dir - Math.PI;
			} else {
				System.out.println("case 3");
				direction = 0 - dir;
			}
		} else {
			if(yMouse - firstY > 0) {
				System.out.println("case 2");
				direction = Math.PI - dir;
			} else {
				System.out.println("case 1");
				direction = dir ;
			}
		}
		System.out.println("direction: "+direction);
	}
	
	public void move() {
        xobject = xobject + (speed*Math.sin(direction)); 
        yobject = yobject + (speed*Math.cos(direction));
		//if (( x + l<0)||( y + h<0)||( x > Ecran.width) ||( y > Ecran.height)) actif=false;
		//TODO: the line above needs to check if x and y are still in the boundaries, and if not,
		//change trajectory accordingly

		if(speed <= 0) speed = 0;
		else speed -= ((speed/10) + 1); //non linear decrease in speed
		//fait à la zeub, on testera et arrangera en fonction
    }
	
	//true if mouse clicked on the palet, false otherwise
	public boolean isInside(double xMouse, double yMouse) {
		if((Math.abs(xMouse -xobject) < RADIUS) && (Math.abs(yMouse - yobject) < RADIUS)) {
			return true;
		} else {
			return false;
		}
	}
}

