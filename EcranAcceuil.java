import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.awt.event.*;

public class EcranAcceuil extends JFrame implements ActionListener {

    private Image wallpaper, ballon;
    private JTextField pseudo1;
    private JTextField pseudo2;
    private JPanel p0;
    private JLabel fond;
    private JButton game;
    private SoundClip son = new SoundClip("Tovelo");

    public EcranAcceuil(){
        Toolkit T=Toolkit.getDefaultToolkit();
        wallpaper = T.getImage("fondAccueil.jpg");
        ballon = T.getImage("ballon.gif");

        setTitle("SoccerStars");
        setLayout(null);
        setSize(1280,720);              
        setLocation(30,10);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font police = new Font("Arial", Font.BOLD,30);

        p0 = new JPanel();
        p0.setLayout(null);
        p0.setBounds(0,0,getWidth(),getHeight());
        this.add(p0);

        pseudo1 = new JTextField ("Equipe1");
        pseudo1.setSize (150, 80);
        pseudo1.setLocation (350, 420);
        pseudo1.setForeground(Color.black);
        pseudo1.setFont(police);
        pseudo1.setLayout(null);
        pseudo1.addActionListener(this);
        p0.add(pseudo1);

        pseudo2 = new JTextField ("Equipe2");
        pseudo2.setSize (150, 80);
        pseudo2.setLocation (740, 420);
        pseudo2.setForeground(Color.black);
        pseudo2.setFont(police);
        pseudo2.setLayout(null);
        pseudo2.addActionListener(this);
        p0.add(pseudo2);

        fond = new JLabel(new ImageIcon(wallpaper));
        fond.setSize(1280,720);
        fond.setLocation(0,0);
        p0.add(fond);

        game = new JButton(new ImageIcon(ballon));
        game.setSize(80,80);
        game.setLocation(590,420);
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
