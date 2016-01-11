import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ball extends PaletAndBall {
    public Ball() {
        RADIUS = 20;
        xobject = 700;
        yobject = 500;

        try {
            imageObject = ImageIO.read(new File("ballbis.png"));
        } catch(IOException e) {
            System.out.println("Image de la balle non trouvee");
        }
    }
    public boolean isBall() {
        return true;
    }
}
