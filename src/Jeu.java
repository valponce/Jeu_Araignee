import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;



public class Jeu extends JFrame implements MouseListener{
    Dessin d;
    public static void main(String[] args) throws Exception {
        Jeu j= new Jeu();
    }

    public Jeu() {
        // setIconImage() permet de modifier l'icône de la JFrame.
        super();
        setSize(1000, 1000);

        // création d'une barre de menu
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu menu = new JMenu();
        menu.setText("Mon Menu");
        menubar.add(menu);

        JLabel l = new JLabel("Jeu de l'Araignée");
        
        // Ajout du Jpanel d
        d= new Dessin();
        d.addMouseListener(this);
        setContentPane(d);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        add(l);

        //placement des objets et affichage fenêtre 
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mousePressed(MouseEvent e){
        d.addPion(e.getX(),e.getY());
        d.repaint();
    }
    
    
    public void mouseClicked(MouseEvent e){
    }

     public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    } 

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    
}
