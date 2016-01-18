import java.awt.Graphics;
import java.awt.image.BufferedImage;

abstract public class PaletAndBall {

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
        if(speed > 100) speed = 100;
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
        if(speed > 100) speed = 100;
        this.speed = speed;   
    }

    //is called at every action perfomed by each palet on every other palet
    public void collision(PaletAndBall p) {
        double distance = Math.pow((Math.pow(p.getx() - xobject,2) + Math.pow(p.gety()-yobject,2)),0.5);
        double speedPalet = p.getSpeed();
        if(distance<=RADIUS + p.getRadius()) {
            //*
            while(distance<=RADIUS+p.getRadius()) { //on fait en sorte de ne JAMAIS afficher des palets qui se chevauchent
                xobject -= (10*Math.sin(direction)); 
                yobject -= (10*Math.cos(direction));
                distance = Math.pow((Math.pow(p.getx() - xobject,2) + Math.pow(p.gety()-yobject,2)),0.5);
            }
            //*/
            if(p.isBall()) {
                p.setSpeed(speedPalet * 0.8 + 0.8 * speed);
                speed = 0.8 * speed + 0.8 * speedPalet; 
            } else if(isBall()) {
                p.setSpeed(speedPalet * 0.8 + 0.8 * speed); 
                speed = speed * 0.8 + 0.8 * speedPalet;
            } else {
                p.setSpeed(speed/2+speedPalet/2);
                speed = speedPalet/2 + speed/2;
            }
            direction = 2*direction - p.getDirection();
            p.setDirection(p.getx(),p.gety(),xobject,yobject);
        }
    }

    public int move() {
        if(speed <= 0) speed = 0;
        else speed -= ((speed/20) + 1); //non linear decrease in speed

        xobject += (speed*Math.sin(direction)); 
        yobject += (speed*Math.cos(direction));

        //rebonds sur les bords de terrain
        minX = xobject - RADIUS;
        maxX = xobject + RADIUS;
        minY = yobject - RADIUS;
        maxY = yobject + RADIUS;

        if(isBall() && yobject>405 && yobject<585) {
            if((xobject-RADIUS)<105) return 2;
            else if((xobject+RADIUS)>1295) return 1;
        }

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

