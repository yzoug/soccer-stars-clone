import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ball extends PaletAndBall {
    public Ball() {
        RADIUS = 10;
        xobject = 700;
        yobject = 500;

        try {
            imageObject = ImageIO.read(new File("ball.png"));
        } catch(IOException e) {
            System.out.println("Image de la balle non trouvee");
        }
    }
    public boolean isBall() {
        return true;
    }
    public int move() {
        super.move(); //the return of super.move() isn't taken into account (always 0)
        //gestion des buts
        if(yobject<465 && yobject>535) {
            if((xobject-RADIUS)<105) return 2;
            else if((xobject+RADIUS)>1295) return 1;
        }
        return 0;
    }
}
