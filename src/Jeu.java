import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class Jeu extends JFrame implements MouseListener,MouseMotionListener,ActionListener{
    Dessin d;
    int largeur;
    int nbpions;
    JMenuItem nouvellePartie;
    JMenuItem Quitter;
    
    public static void main(String[] args) throws Exception {
        Jeu j= new Jeu();
    }

    public Jeu() {
        super();

        largeur=300;
        nbpions=0;
        // setIconImage() permet de modifier l'icône de la JFrame.
        
        setSize(1000, 1000);

        // création d'une barre de menu
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu menu = new JMenu();
        menu.setText("Partie");

        nouvellePartie = new JMenuItem("Nouvelle Partie");
        nouvellePartie.addActionListener(this);
        
        Quitter = new JMenuItem("Quitter");
        Quitter.addActionListener(this);

        menu.add(nouvellePartie);
        menu.add(Quitter);
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

    public boolean MouvPossible(int lastx,int lasty,int newx,int newy){
        if ((newx==lastx+100 || newx==lastx-100 || newx==lastx) && (newy==lasty+100 || newy==lasty-100|| newy==lasty)){
            return true;
        }
        else return false;
    }

    public boolean vainqueur(){
        System.out.println(d.tableau[1]);
        System.out.println(d.tableau[4]);
        System.out.println(d.tableau[7]);
        //vérification des colonnes
        for (int i=0;i<3;i++){
            if (d.tableau[i]!=0 && d.tableau[i]==d.tableau[i+3] && d.tableau[i]==d.tableau[i+6]){
                return true;
            }
        }
        //vérification des lignes
        for (int i=0;i<9;i+=3){
            if (d.tableau[i]!=0 && d.tableau[i]==d.tableau[i+1] && d.tableau[i]==d.tableau[i+2]){
                return true;
            }
        }
        
        //vérifications des diagonales
        if (d.tableau[0]!=0 && d.tableau[0]==d.tableau[4] && d.tableau[0]==d.tableau[8]){
            return true;
        }
        if (d.tableau[2]!=0 && d.tableau[2]==d.tableau[4] && d.tableau[2]==d.tableau[6]){
            return true;
        }
        else return false;

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource()==nouvellePartie){
            newGame();
        }
        else {if (e.getSource()==Quitter){
                System.exit(0);
            }
        }
    }

    public void newGame(){
        for (int i=0;i<9;i++){
            d.tableau[i]=0;
            }
        d.repaint();
        nbpions=0;
        d.onMove=false;
    }

    public void mousePressed(MouseEvent e){
        d.caseSelectionne=appartient(e.getX(),e.getY());
        
        if (nbpions<6){
            int numero=appartient(e.getX(),e.getY());
             //si la case est libre, on y dessine le pion du joueur dont c'est le tour
            if (d.tableau[numero]==0){
                d.tableau[numero]=d.Turn;
                d.repaint();
                nbpions++;

                //changement de joueuer
                d.Turn= (d.Turn)%2+1;
            }
            else{
                System.out.println("Case occupée");
            }
        }
    }
    
    
    public void mouseClicked(MouseEvent e){
        
    }

     public void mouseReleased(MouseEvent e) {
        int numero=appartient(e.getX(),e.getY());
        int lastx=d.xcases[d.caseSelectionne];
        int lasty=d.ycases[d.caseSelectionne];
        int newx=d.xcases[numero];
        int newy=d.ycases[numero];
        //System.out.println(MouvPossible(lastx, lasty,newx,newy));
        //System.out.println(lastx+" "+lasty+" "+newx+" "+newy);
        
        
        if (nbpions>=6 && d.onMove){
        //on relâche la souirs après avoir déplacé un pion
        d.onMove=false;
        
        // si la case d'arrivée est libre et le mouvement autorisée
        if (d.tableau[numero]==0 && MouvPossible(lastx, lasty,newx,newy)){
            d.tableau[numero]=d.Turn;
            d.repaint();
            //changement de joueuer
            d.Turn= (d.Turn)%2+1;
        }
        else {// on redessine le pion à sa position initiale
            d.tableau[d.caseSelectionne]=d.Turn;}
            d.repaint();   
            
        }
        if (vainqueur()){
            JOptionPane.showMessageDialog(this, "Joueur: "+d.Turn+" Vous avez gagné", "Gagnant", JOptionPane.WARNING_MESSAGE);
            newGame();
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    } 

    public void mouseDragged(MouseEvent e) {
        if (nbpions>=6){
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
