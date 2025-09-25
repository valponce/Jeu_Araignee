import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;



public class Jeu extends JFrame implements MouseListener,MouseMotionListener{
    Dessin d;
    Boolean onMove;
    int[] tableau;
    int largeur;
    public static void main(String[] args) throws Exception {
        Jeu j= new Jeu();
    }

    public Jeu() {
        super();

        onMove=false;
        tableau = new int[6];
        largeur=300;
        // setIconImage() permet de modifier l'icône de la JFrame.
        
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
        d.addMouseMotionListener(this);
        setContentPane(d);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        add(l);

        //placement des objets et affichage fenêtre 
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int appartient(int x,int y){
        // largeur= largeur de la grille, rajouter le décalage
        int b0,b1;
        if (x-100<largeur/3){
            b0=0;
        }
        else if (x-100<2*largeur/3){
                b0=1;
            }
            else {b0=2;}

        if (y-100<largeur/3){
            b1=0;
        }
        else if (y-100<2*largeur/3){
                b1=1;
            }
            else {b1=2;}
        return b0+3*b1;

    }
    public void mousePressed(MouseEvent e){
        d.addPion(e.getX(),e.getY());
        System.out.println(appartient(e.getX(),e.getY()));
        System.out.println(appartient(e.getX(),e.getY()));
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
            d.pions.set(0,new Pion(e.getX(),e.getY()));
            d.repaint();
    }

    public void mouseMoved(MouseEvent e) {
        
    }

    
}
