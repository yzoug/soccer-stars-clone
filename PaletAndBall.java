import java.awt.Graphics;
import javax.swing.JPanel;
import java.lang.Math;
import java.awt.image.BufferedImage;

abstract public class PaletAndBall extends JPanel {

    protected double xobject,yobject;    //coordinates of the center of the palet at all times 
    protected double minX,maxX,minY,maxY; //coordinates of the min and max of the palet at all times
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

    //launch the palet/ball according to the user input
    public void start(double firstX, double firstY, double xMouse, double yMouse){
        //System.out.println(firstX + " " + firstY + " " + xMouse + " " + yMouse);
        speed = Math.pow(Math.pow(xMouse - firstX,2)+Math.pow(yMouse - firstY, 2),0.5);
        if(speed > 200) speed = 200;
        if(firstX == xMouse) ++firstX;
        double dir = Math.atan(Math.abs(xMouse - firstX)/Math.abs(yMouse - firstY));
        //System.out.println("dir: "+dir);
        if(xMouse - firstX > 0) {
            if(yMouse - firstY > 0) {
                //System.out.println("case 4");
                direction = dir - Math.PI;
            } else {
                //System.out.println("case 3");
                direction = 0 - dir;
            }
        } else {
            if(yMouse - firstY > 0) {
                //System.out.println("case 2");
                direction = Math.PI - dir;
            } else {
                //System.out.println("case 1");
                direction = dir ;
            }
        }
        //System.out.println("direction: "+direction);
    }

    //the start we'll use for collisions, super straightforward
    public void startBis(double speed, double direction) {
        this.speed = speed;
        if(this.speed > 200) speed = 200;
        this.direction = direction;
    }

    public int move() {
        if(speed <= 0) speed = 0;
        else speed -= ((speed/10) + 1); //non linear decrease in speed

        xobject = xobject + (speed*Math.sin(direction)); 
        yobject = yobject + (speed*Math.cos(direction));

        //rebonds sur les bords de terrain
        minX = xobject - RADIUS;
        maxX = xobject + RADIUS;
        minY = yobject - RADIUS;
        maxY = yobject + RADIUS;
        if(minX < 105) {
            direction *= -1;
            xobject = 105 + RADIUS;
        } else if (maxX > 1295) {
            direction *= -1;
            xobject = 1295 - RADIUS;
        } else if(minY < 205) {
            direction = Math.PI - direction;
            yobject = 205 + RADIUS;
        } else if(maxY > 800) {
            direction = Math.PI - direction;
            yobject = 800 - RADIUS;
        }
        return 0;
        //les buts sont gérés par move de Ball.java
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

