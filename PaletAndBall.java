import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

abstract public class PaletAndBall extends JPanel {

    protected double xobject,yobject;    //coordinates of the center of the palet at all times 
    protected double minX,maxX,minY,maxY; //coordinates of the min and max of the palet at all times
    protected double RADIUS; //40 for palets (donc image 80x80), 10 for ball
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
        speed = Math.pow(Math.pow(xMouse - firstX,2)+Math.pow(yMouse - firstY, 2),0.5);
        if(speed > 200) speed = 200;
        if(firstX == xMouse) ++firstX;
        setDirection(firstX, firstY, xMouse, yMouse);
    }

    public void setDirection(double firstX, double firstY, double xMouse, double yMouse) {
        double dir = Math.atan(Math.abs(xMouse - firstX)/Math.abs(yMouse - firstY));
        if(xMouse - firstX > 0) {
            if(yMouse - firstY > 0) {
                direction = dir - Math.PI;
            } else {
                direction = 0 - dir;
            }
        } else {
            if(yMouse - firstY > 0) {
                direction = Math.PI - dir;
            } else {
                direction = dir ;
            }
        }
    }

    public void setSpeed(double speed) {
        if(speed > 200) speed = 200;
        this.speed = speed;   
    }

    //is called at every action perfomed by each palet on every other palet
    public void collision(PaletAndBall p) {
        double distance = Math.pow((Math.pow(p.getx() - xobject,2) + Math.pow(p.gety()-yobject,2)),0.5);
        if(distance<=RADIUS + p.getRadius()) {
            /*
            while(distance<=RADIUS+p.getRadius()) { //on fait en sorte de ne JAMAIS afficher des palets qui se chevauchent
                xobject += (10*Math.sin(direction)); 
                yobject += (10*Math.cos(direction));
            }
            //*/
            p.setDirection(p.getx(),p.gety(),xobject,yobject);
            p.setSpeed(speed);
            direction = -(2*direction - p.getDirection());
        }
    }

    public int move() {
        if(speed <= 0) speed = 0;
        else speed -= ((speed/10) + 1); //non linear decrease in speed

        xobject += (speed*Math.sin(direction)); 
        yobject += (speed*Math.cos(direction));

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
        //comme Palet.java ne redéfinit pas la classe
        //c'est celle là qui est utilisée (et retourne
        //tjrs 0). Pour Ball.java, le return de celle là
        //est ignoré et c'est en fonction de but ou pas
        //qu'un chiffre est retourné.
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

