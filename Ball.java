import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ball extends PaletAndBall {
	public Ball() {
		xobject = 700;
		yobject = 500;
		RADIUS = 15;

		try {
			imageObject = ImageIO.read(new File("ball.png"));
		} catch(IOException e) {
			System.out.println("Image de la balle non trouvee");
		}
	}
	public boolean isBall() {
		return true;
	}
}
