import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.geom.*;

public class Jeu extends JFrame implements MouseMotionListener, MouseListener, ActionListener {
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
    private Point pointStart, pointEnd;

    public Jeu(String eq1, String eq2) {
        super("SoccerStars");
        objectSelectionne = 42;
        score1 = 0;
        score2 = 0;
        nomEquipe1 = eq1;
        nomEquipe2 = eq2;
        goalOrTurn = "Your turn";
        pointStart = new Point(0,0);
        pointEnd = new Point(0,0);

        sound = new SoundClip("Fifa");
        sifflet = new SoundClip("sifflet");
        sound.start();
       
        try {
            field = ImageIO.read(new File("field.jpg"));
        } catch(IOException e) {
            System.out.println("Image d'arriere plan non trouvee");
        }
        
        sifflet.start();

        setSize(1400,800); //1200*600 c'est le terrain, 200 en haut pour score, 100 chaque côté pour les cages
        background = new BufferedImage(1400,800,BufferedImage.TYPE_INT_RGB);
        buffer = background.getGraphics();

        objects = new PaletAndBall[11];
        initialSetup();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        addMouseMotionListener(this);

        setVisible(true);
        setLayout(null);
        setResizable(false);
        sifflet.stop();

        t = new Timer(50, this); //every 50ms move() is called on everything
        t.start();
        
        

        turn = true;
        peutCliquer = true;
        soundPlaying = true;
        scored = false;
        
        
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

    public void mouseClicked(MouseEvent e) {
        if(firstX>400 && firstX<528 && firstY>30 && firstY<158) {
            if(soundPlaying) sound.stop();
            else sound.start();
            soundPlaying = !soundPlaying;
        }
    }

    public void mousePressed(MouseEvent e) {
        firstX = e.getX();
        firstY = e.getY();
        if(scored) {
           scored = false;
           sifflet.stop();
        }
        if(!peutCliquer) objectSelectionne = 42;
        else {
            objectSelectionne = 42;
            for(int i = 0; i < 11; i++) {
                if(objects[i].isInside(firstX,firstY) && !(objects[i].isBall()) && ((Palet)objects[i]).getTeam() == turn) {
                    objectSelectionne = i;
                    pointStart.x = (int)firstX;
                    pointStart.y = (int)firstY;
                    //i'm sure that if objectSelectionne != 42 it's not the ball
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        if (objectSelectionne != 42) {
            objects[objectSelectionne].start(firstX,firstY,x,y);
            turn = !turn;
            pointStart = new Point(0,0);
        }
    }
    
    public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		pointEnd = e.getPoint();
		repaint();	
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
                
                scored = true;
                if(retour == 1){
					sifflet.start();
                    ++score1;
                    turn = false;
                } else {
					sifflet.start();
                    ++score2;
                    turn = true;
                }
                initialSetup();
                sifflet.stop();
            }
            //si objet en mouvement tu peux pas jouer
            if(objects[i].getSpeed() > 0) peutCliquer = false;
        }
        collisions();
        repaint();
        if(score1 == 2 || score2 == 2) {
            setVisible(false);
            sound.stop();
            t.stop();
            EcranFinal zoug;
            if(score1 == 2) zoug = new EcranFinal(nomEquipe1,nomEquipe2,true);
            else zoug = new EcranFinal(nomEquipe1,nomEquipe2,false);
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
            buffer.drawImage(((Palet)(objects[0])).getBigImage(),640,15,null);
            if(!scored) goalOrTurn = nomEquipe1 + ": your turn";
            else goalOrTurn = "Goaaaal";
        } else {
            buffer.drawImage(((Palet)(objects[6])).getBigImage(),640,30,null);
            if(!scored) goalOrTurn = nomEquipe2 + ": your turn";
            else goalOrTurn = "Goaaaal";
        }

        buffer.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        buffer.setColor(Color.WHITE);
        buffer.drawString(Integer.toString(score1), 230, 120);
        buffer.drawString(Integer.toString(score2), 1130, 120);
        buffer.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        buffer.drawString(goalOrTurn, 540, 180);

        for(int i = 0; i<11; i++) {
            buffer.drawImage(objects[i].getImage(),(int)(objects[i].getx()-objects[i].getRadius()),(int)(objects[i].gety()-objects[i].getRadius()),null);
        }

		if(!pointStart.equals(new Point(0,0))) {
			//Graphics2D buffer2D = (Graphics2D) g;
			//buffer.setStroke(new BasicStroke(10));
			//buffer.draw(new Line2D.Float(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y));
			drawThickLine(buffer, pointStart.x, pointStart.y, pointEnd.x, pointEnd.y, 10, Color.BLACK);
		}
		
        g.drawImage(background,0,0,this);
    }
    
    public void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c) {
		// The thick line is in fact a filled polygon
		g.setColor(c);
		int dX = x2 - x1;
		int dY = y2 - y1;
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);

		double scale = (double)(thickness) / (2 * lineLength);

		// The x,y increments from an endpoint needed to create a rectangle...
		double ddx = -scale * (double)dY;
		double ddy = scale * (double)dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int)ddx;
		int dy = (int)ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
		xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
		xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
		xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;

		g.fillPolygon(xPoints, yPoints, 4);
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

