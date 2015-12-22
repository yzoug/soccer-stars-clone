import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Palet extends PaletAndBall {
	private boolean team; //player that owns the palet. True for player one, false for 2
	private BufferedImage imagePalet;

	public Palet(int X, int Y, boolean team) {
		xobject = X;
		yobject = Y;
		this.team = team;
		RADIUS = 30;

		try {
			if(team) imageObject = ImageIO.read(new File("palet1.png"));
			else imageObject = ImageIO.read(new File("palet2.png"));
		} catch(IOException e) {
			System.out.println("Image du palet non trouvee");
		}
	}

	public boolean getTeam() {
		return team;
	}

	public boolean isBall() {
		return false;
	}
}	
