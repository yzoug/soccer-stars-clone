import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.awt.event.*;

public class EcranFinal extends JFrame implements ActionListener {

    private Image victoire, jogar, menou;
    private JPanel p0;
    private JLabel but; 
    private JButton replay; 
    private JButton menu;
    private String eq1, eq2;
    private boolean eqWinner;
    private Jeu j;
    private EcranAcceuil b;

    public EcranFinal(String eq1, String eq2, boolean eqWinner) {
        this.eq1 = eq1;
        this.eq2 = eq2;
        this.eqWinner = eqWinner;

        setTitle("Game over!");
        setLayout(null);
        setSize(460,360); 
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit T=Toolkit.getDefaultToolkit();
        victoire = T.getImage("victoire.gif");
        jogar = T.getImage("replay.jpg");
        menou = T.getImage("menu.jpg");
        
        p0 = new JPanel();
        p0.setLayout(null);
        p0.setBounds(0,0,getWidth(),getHeight());
        this.add(p0);
        
        but = new JLabel(new ImageIcon(victoire));
        but.setSize(460,320);
        but.setLocation(0,0);
        p0.add(but);
        
        replay = new JButton(new ImageIcon(jogar));
        replay.setSize(100,20);
        replay.setLocation(50,330);
        replay.setBackground(new Color (0, 100, 0));
        replay.setLayout(null);
        replay.addActionListener(this);
        p0.add(replay);
        
        menu = new JButton(new ImageIcon(menou));
        menu.setSize(100,20);
        menu.setLocation(250,330);
        menu.setBackground(new Color (0, 100, 0));
        menu.setLayout(null);
        menu.addActionListener(this);
        p0.add(menu);
    }
        
        
    public void actionPerformed(ActionEvent e){
        setVisible(false);
        if(e.getSource()==replay) j = new Jeu(eq1,eq2);
        else b = new EcranAcceuil();
    }
}
