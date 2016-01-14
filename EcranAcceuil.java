import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.awt.event.*;

public class EcranAcceuil extends JFrame implements ActionListener {

    Image Wallpaper; //L'image de l'arrière plan "photo.png"
    JTextField pseudo1; // Texte field to enter the pseudo
    JTextField pseudo2; // Texte field to enter the pseudo
    JPanel p0; // Panneau principal
    JLabel fond; //Image sur panneau principal
    JButton game; //To play
    Image ballon;
    SoundClip son = new SoundClip ("Tovelo");

    public EcranAcceuil(){
        //Initialiser rapidement les images sans contrôler leur existence
        Toolkit T=Toolkit.getDefaultToolkit();
        Wallpaper = T.getImage("fondAccueil.jpg");
        ballon = T.getImage("ballon.gif");

        setTitle("SoccerStars");
        setLayout(null);
        setSize(1280,720);              
        setLocation(30,10);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font police = new Font("Arial", Font.BOLD,30);

        p0 = new JPanel();
        p0.setLayout(null);
        p0.setBounds(0,0,getWidth(),getHeight());
        this.add(p0);

        pseudo1 = new JTextField ("Equipe1");
        pseudo1.setSize (190, 70);
        pseudo1.setLocation (0, 0);
        pseudo1.setForeground(Color.black);
        pseudo1.setFont(police);
        pseudo1.setLayout(null);
        pseudo1.addActionListener(this);
        p0.add(pseudo1);

        pseudo2 = new JTextField ("Equipe2");
        pseudo2.setSize (190, 70);
        pseudo2.setLocation (1090, 0);
        pseudo2.setForeground(Color.black);
        pseudo2.setFont(police);
        pseudo2.setLayout(null);
        pseudo2.addActionListener(this);
        p0.add(pseudo2);

        fond = new JLabel(new ImageIcon(Wallpaper));
        fond.setSize(1280,720);
        fond.setLocation(0,0);
        p0.add(fond);

        game = new JButton(new ImageIcon(ballon));
        game.setSize(80,80);
        game.setLocation(560,500);
        game.setBackground(new Color (0, 100, 0));
        game.setLayout(null);
        game.addActionListener(this);
        p0.add(game);

        setVisible(true);
        son.start();
    }


    public void actionPerformed(ActionEvent e){
        if(e.getSource()==game) {
            String un =getTxt1();
            String deux =getTxt2();
            Jeu j = new Jeu(un,deux);
            son.stop();
            setVisible(false);
        }
    }

    public String getTxt1() {
        return pseudo1.getText();
    }

    public String getTxt2(){
        return pseudo2.getText();
    }

    public static void main (String [] args) {
        EcranAcceuil SoccerStars = new EcranAcceuil();
    }
}
