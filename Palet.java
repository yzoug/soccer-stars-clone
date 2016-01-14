import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Palet extends PaletAndBall {
    private boolean team; //player that owns the palet. True for player one, false for 2
    private String teamName;
    private BufferedImage grandeImagePalet;

    public Palet(int X, int Y, boolean team, String teamName) {
        RADIUS = 40;
        xobject = X;
        yobject = Y;
        this.team = team;
        this.teamName = teamName;
        try {
            imageObject = ImageIO.read(new File("palets/"+teamName+".png"));
            grandeImagePalet = ImageIO.read(new File("palets/"+teamName+"BIG.png"));
        } catch(IOException e) {
            System.out.println("Image pour l'equipe non trouvee");
            try {
                if(team) {
                    imageObject = ImageIO.read(new File("palets/Equipe1.png"));
                    grandeImagePalet = ImageIO.read(new File("palets/Equipe1BIG.png"));
                } else {
                    imageObject = ImageIO.read(new File("palets/Equipe2.png"));
                    grandeImagePalet = ImageIO.read(new File("palets/Equipe2BIG.png"));
                }
            } catch(IOException ex) {
                System.out.println("Image pour palet par defaut non trouvee");
            }
        }
    }

    public boolean getTeam() {
        return team;
    }

    public BufferedImage getBigImage() {
        return grandeImagePalet;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isBall() {
        return false;
    }
}	
