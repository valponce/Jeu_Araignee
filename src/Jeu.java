import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Jeu extends JFrame implements MouseListener{
    JPanel p;
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
        p = (JPanel) getContentPane();
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        add(l);

        //placement des objets et affichage fenêtre 
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g){
        super.paint(g);
        // dessin de la grille
        // lignes
        g.drawLine(100, 400, 400, 400);
        g.drawLine(100, 200, 400, 200);
        g.drawLine(100, 300, 400, 300);
        g.drawLine(100, 100, 400, 100); 

        //colones
        g.drawLine(400, 100, 400, 400);
        g.drawLine(300, 100, 300, 400);
        g.drawLine(200, 100, 200, 400);
        g.drawLine(100, 100, 100, 400);
        p.addMouseListener(this);
    }

    public void mousePressed(MouseEvent e){
        Pion pion = new Pion(e.getX(),e.getY(),p);
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
