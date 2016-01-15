import javax.swing.JFrame;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

public class Jeu extends JFrame implements MouseListener, ActionListener {
    private PaletAndBall[] objects;
    private int objectSelectionne;
    private boolean turn, peutCliquer, soundPlaying, scored; //turn is true for player 1, false for player 2
    private Timer t;
    private double firstX, firstY;
    private BufferedImage background;
    private Image field;
    private Graphics buffer;
    private int score1, score2;
    private String nomEquipe1, nomEquipe2, goalOrTurn;
    private SoundClip sound, sifflet;

    public Jeu(String eq1, String eq2) {
        super("SoccerStars");
        objectSelectionne = 42;
        score1 = 0;
        score2 = 0;
        nomEquipe1 = eq1;
        nomEquipe2 = eq2;
        goalOrTurn = "Your turn";

        sound = new SoundClip("Fifa");
        sifflet = new SoundClip("sifflet");
        sound.start();
        sifflet.start();

        try {
            field = ImageIO.read(new File("field.png"));
        } catch(IOException e) {
            System.out.println("Image d'arriere plan non trouvee");
        }

        setSize(1400,800); //1200*600 c'est le terrain, 200 en haut pour score, 100 chaque côté pour les cages
        background = new BufferedImage(1400,800,BufferedImage.TYPE_INT_RGB);
        buffer = background.getGraphics();

        objects = new PaletAndBall[11];
        turn = true;
        initialSetup();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);

        setVisible(true);
        setLayout(null);
        setResizable(false);

        t = new Timer(50, this); //every 50ms move() is called on everything
        t.start();

        turn = true;
        peutCliquer = true;
        soundPlaying = true;
        scored = false;
        sifflet.stop();
    }

    public void mouseClicked(MouseEvent e) {
        //void
    }

    public void initialSetup() {
        peutCliquer = true;
        objects[0] = new Palet(200,500,true,nomEquipe1);
        objects[1] = new Palet(400,300,true,nomEquipe1);
        objects[2] = new Palet(400,700,true,nomEquipe1);
        objects[3] = new Palet(600,400,true,nomEquipe1);
        objects[4] = new Palet(600,600,true,nomEquipe1);

        objects[5] = new Ball();

        objects[6] = new Palet(1200,500,false,nomEquipe2);
        objects[7] = new Palet(1000,300,false,nomEquipe2);
        objects[8] = new Palet(1000,700,false,nomEquipe2);
        objects[9] = new Palet(800,400,false,nomEquipe2);
        objects[10] = new Palet(800,600,false,nomEquipe2);
    }

    public void mousePressed(MouseEvent e) {
        firstX = e.getX();
        firstY = e.getY();
        scored = false;
        if(!peutCliquer) objectSelectionne = 42;
        else {
            objectSelectionne = 42;
            for(int i = 0; i < 11; i++) {
                if(objects[i].isInside(firstX,firstY) && !(objects[i].isBall())) {
                    objectSelectionne = i;
                    //i'm sure that if objectSelectionne != 42 it's not the ball
                }
            }
        }
        if(firstX>400 && firstX<450 && firstY>150 && firstY<200) {
            if(soundPlaying) sound.stop();
            else sound.start();
            soundPlaying = !soundPlaying;
        }
    }

    public void mouseReleased(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        if (objectSelectionne != 42 && ((Palet)objects[objectSelectionne]).getTeam() == turn) {
            objects[objectSelectionne].start(firstX,firstY,x,y);
            turn = !turn;
        }
    }

    public void mouseEntered(MouseEvent e) {
        //void
    }

    public void mouseExited(MouseEvent e) {
        //void
    }


    public void actionPerformed(ActionEvent e) {
        peutCliquer = true;
        int retour;
        for(int i = 0; i<11; i++) {
            //changement x et y avec move, gestion rebonds sur bords du terrain
            //si move renvoie 0 rien de special, si renvoie 1 l'eq1 a marqué, si 2 eq2
            retour = objects[i].move();
            if(retour != 0) {
                sifflet.start();
                scored = true;
                if(retour == 1){
                    ++score1;
                    turn = false;
                } else {
                    ++score2;
                    turn = true;
                }
                initialSetup();
            }
            //si objet en mouvement tu peux pas jouer
            if(objects[i].getSpeed() > 0) peutCliquer = false;
        }
        collisions();
        repaint();
        sifflet.stop();
        if(score1 == 2) {
            setVisible(false);
            EcranFinal vincent = new EcranFinal(nomEquipe1,nomEquipe2,1);
        } else if(score2 == 2) {
            setVisible(false);
            EcranFinal zoug = new EcranFinal(nomEquipe1,nomEquipe2,2);
        }
    }

    public void collisions() {
        for(int i = 0; i < 11; i++) {
            if(objects[i].getSpeed() > 0) { //on ne regarde collisions que si objet bouge
                for(int j = 0; j < 11; j++) {
                    if(i!=j) objects[i].collision(objects[j]);
                }
            }
        }
    }

    public void paint(Graphics g) {
        buffer.drawImage(field,0,0,null);
        buffer.drawImage(((Palet)(objects[0])).getBigImage(),100,30,null);
        buffer.drawImage(((Palet)(objects[6])).getBigImage(),1180,30,null);
        if(turn) {
            buffer.drawImage(((Palet)(objects[0])).getBigImage(),640,30,null);
            if(!scored) goalOrTurn = nomEquipe1 + ": your turn";
            else goalOrTurn = "Goaaaal";
        }
        else {
            buffer.drawImage(((Palet)(objects[6])).getBigImage(),640,30,null);
            if(!scored) goalOrTurn = nomEquipe2 + ": your turn";
            else goalOrTurn = "Goaaaal";
        }

        buffer.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        buffer.setColor(Color.WHITE);
        buffer.drawString(Integer.toString(score1), 230, 150);
        buffer.drawString(Integer.toString(score2), 1130, 150);
        buffer.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        buffer.drawString(goalOrTurn, 600, 160);
        buffer.fillRect(400,150,50,50); //pour sound

        for(int i = 0; i<11; i++) {
            buffer.drawImage(objects[i].getImage(),(int)(objects[i].getx()-objects[i].getRadius()),(int)(objects[i].gety()-objects[i].getRadius()),null);
        }

        g.drawImage(background,0,0,this);
    }

    public static void main(String[] args) {
        String eq1 = "", eq2;
        try {
            eq1 = args[0];
            eq2 = args[1];
        } catch(ArrayIndexOutOfBoundsException e) {
            //noms par défaut
            if(eq1 == "") eq1 = "Equipe1";
            eq2 = "Equipe2";
        }
        Jeu j = new Jeu(eq1,eq2);
    }
}
