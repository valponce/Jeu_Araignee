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
    int largeur;
    int nbCoup;
    
    public static void main(String[] args) throws Exception {
        Jeu j= new Jeu();
    }

    public Jeu() {
        super();

        largeur=300;
        nbCoup=0;
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

    //renvoie le numéro de la case correspondant aux coordonnées x,y
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
        d.caseSelectionne=appartient(e.getX(),e.getY());
    }
    
    
    public void mouseClicked(MouseEvent e){
    }

     public void mouseReleased(MouseEvent e) {
       
        int numero=appartient(e.getX(),e.getY());
        // si la case correspondante est vide
        if (d.tableau[numero]==0){
            // si on déplace un pion ok, sinon on vérifie s'il n'y a pas déjà trop de pions
            if (!d.onMove && nbCoup >=6){
                System.out.println("Phase 2:déplacement");
            }
            else {d.tableau[numero]=d.Turn;
            d.onMove=false;
            d.repaint();}

            // changement de joueur
            d.Turn= (d.Turn)%2+1;
            nbCoup++;
        }
        else {
            if (d.onMove){
                d.tableau[d.caseSelectionne]=d.typePion;
            }
            d.onMove=false;
            d.repaint();
            //si on clique juste: typePion=0 si pas de mouvement avant ou le dernier pion déplacer
            // caseselectionne= numero= la case occupée donc soit on efface le pion soit on le remplce par le même type que le dernier pion deplacé
            // si numero!caseselectionne -> ok (après mouvement)
            // si = -> remet à sa valeur initiale  
           }
        
        
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    } 

    public void mouseDragged(MouseEvent e) {
        if (nbCoup>=6){
            // on vérifie que le pion sélectionné appartient bien au joueur dont c'est le tour
            if (d.tableau[d.caseSelectionne]==d.Turn){
                d.onMove=true;
                d.typePion=d.tableau[d.caseSelectionne];
                d.tableau[d.caseSelectionne]=0;
            }
            d.x=e.getX();
            d.y=e.getY();
            d.repaint();
        }
    }

    public void mouseMoved(MouseEvent e) {
        
    }

    
}
