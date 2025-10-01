import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class Jeu extends JFrame implements MouseListener,MouseMotionListener,ActionListener{
    private Dessin d;

    //taille de la grille
    private int taille;

    // nombre de pions sur la grille, score du joueur 1 et 2
    private int nbpions,s1,s2;

    private JMenuItem nouvellePartie,Quitter,colorJ1,colorJ2,undo;

    // label affiche à qui est le tour
    private JLabel label,score1,score2;

    // Noms des joueurs
    private String nom1,nom2;
    
    
    public static void main(String[] args) throws Exception {
        Jeu j= new Jeu();
    }


    public Jeu() {
        super();

        // Aucun pion sur la grille initialement
        nbpions=0;

        //scores des joueurs nuls
        s1=0;
        s2=0;
        
        //taille de la grille carrée
        taille=300;
        
        ImageIcon image = new ImageIcon("src/icon_toile.png");
        ImageIcon carre = new ImageIcon("src/carre.png");
        ImageIcon cercle = new ImageIcon("src/cercle.png");

        setIconImage(image.getImage());
        setLocationRelativeTo(null);
        setSize(1000, 1000);
        setTitle("Jeu de L'Araignée");

        nom1 = JOptionPane.showInputDialog(this, "Joueur1: Veuillez entrer votre pseudo", "Jeu de l'Araignée", JOptionPane.QUESTION_MESSAGE);
        nom2 = JOptionPane.showInputDialog(this, "Joueur 2: Veuillez entrer votre pseudo:", "Jeu de l'Araignée", JOptionPane.QUESTION_MESSAGE);
        
        if (nom1==null){nom1="Joueur1";}
        if (nom2==null){nom2="Joueur2";}
        
        JPanel toppanel = new JPanel();
        JPanel panel=new JPanel();
        toppanel.setLayout(new BoxLayout(toppanel, BoxLayout.Y_AXIS));

        // centrer horizontalement
        label = new JLabel("Au tour de "+nom1);
        label.setFont(new Font("Serif", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Score joueur 1
        score1 = new JLabel("Score de "+nom1+": "+s1);
        score1.setFont(new Font("Serif", Font.BOLD, 20));
        score1.setAlignmentX(Component.CENTER_ALIGNMENT);
        score1.setIcon(carre);

        // Score joueur 2
        score2 = new JLabel("Score de " + nom2 + ": "+s2);
        score2.setFont(new Font("Serif", Font.BOLD, 20));
        score2.setAlignmentX(Component.CENTER_ALIGNMENT);
        score2.setIcon(cercle);

        panel.setLayout(new FlowLayout(FlowLayout.CENTER,25,25));
        panel.add(score1);
        panel.add(score2);  

        toppanel.add(label);
        toppanel.add(panel);
        
        // Ajout du Jpanel d
        d= new Dessin();
        d.addMouseListener(this);
        d.addMouseMotionListener(this);
    
        // Ajout dans la fenêtre
        setLayout(new BorderLayout());
        add(toppanel, BorderLayout.NORTH);
        add(d, BorderLayout.CENTER);

        // création d'une barre de menu
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu menu = new JMenu();
        menu.setText("Partie");

        undo= new JMenuItem("Undo");
        undo.addActionListener(new Undo(d,this));

        nouvellePartie = new JMenuItem("Nouvelle Partie");
        nouvellePartie.addActionListener(this);
        
        Quitter = new JMenuItem("Quitter");
        Quitter.addActionListener(this);

        menu.add(nouvellePartie);
        menu.add(undo);
        menu.add(Quitter);
        menubar.add(menu);

        //Création d'un menu pour changer les couleurs des joueurs
        JMenu menu1 = new JMenu();
        menu1.setText("Couleur");
        menubar.add(menu1);

        colorJ1 = new JMenuItem("Couleur de "+nom1);
        colorJ2 = new JMenuItem("Couleur de "+nom2);
        menu1.add(colorJ1);
        menu1.add(colorJ2);
        colorJ1.addActionListener(this);
        colorJ2.addActionListener(this);

        //placement des objets et affichage fenêtre 
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int getNbpions() {
        return nbpions;
    }

    public void setNbpions(int n) {
        nbpions=n;
    }

    // Renvoie le numéro de la case correspondant aux coordonnées x,y ou -1 si hors de la grille
    public int appartient(int x,int y){
        // si la position est dans la grille, on cherche la bonne case
        if (x >= d.x1 && x <= d.x1 + taille && y >= d.y1 && y <= d.y1 + taille) {
            int b0 = (x - d.x1) / (taille / 3); // colonne
            int b1 = (y - d.y1) / (taille / 3); // ligne
            return b0 + 3 * b1;
        } 
        else {return -1;}
    }

    //Renvoie si le déplacement de la pièce est autorisée ou non
    public boolean MouvPossible(int lastx,int lasty,int newx,int newy){
        //retour sur la même case impossible
        if (newx==lastx && newy==lasty){return false;}

        // Autorise, les cases adjacentes même en diagonal
        if ((newx==lastx+100 || newx==lastx-100 || newx==lastx) && (newy==lasty+100 || newy==lasty-100|| newy==lasty)){
            return true;
        }
        else return false;
    }

    // Renvoie si trois pions sont alignés
    public boolean vainqueur(){
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


    public void newGame(){
        // Redessine la grille avec uniquement des cases vides
        for (int i=0;i<9;i++){
            d.tableau[i]=0;
            }
        d.repaint();

        //Efface l'historique des positions, plus de pions sur la grille
        d.lastPosition.clear();
        nbpions=0;
        // Retour à la phase 1 sans déplacement
        d.onMove=false;
    }



    // Implémentation des méthodes de ActionListener
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==nouvellePartie){
            newGame();
        }
        else {if (e.getSource()==Quitter){
                System.exit(0);
            }
            else{if (e.getSource()==colorJ1){
                d.cJ1=JColorChooser.showDialog(this,"Choisisez la couleur pour le joueur 1",d.cJ1);
                System.out.println(d.cJ1);
            }
                else
                {d.cJ2=JColorChooser.showDialog(this,"Choisisez la couleur pour le joueur 1",d.cJ2);}
            d.repaint();}
        }
        
    }



    //Implémentation des méthodes MouseListener, MouseMotionListener
    public void mousePressed(MouseEvent e){
        // Case contenant la position choisie
        d.caseSelectionne=appartient(e.getX(),e.getY());
        
        // Phase1: Placement tant qu'il n'y a pas 6 pions sur la grille
        if (nbpions<6){
            // numéro de la case contenant la position actuelle
            int numero=appartient(e.getX(),e.getY());
             
            //si la case est libre, on y dessine le pion du joueur dont c'est le tour
            if (numero!=-1 && d.tableau[numero]==0){
                d.tableau[numero]=d.Turn;
                d.repaint();
                nbpions++;

                //Mise à jour de l'historique avec la dernière action effectuée
                d.lastPosition.add(numero);
                
                //changement de joueuer
                d.Turn= (d.Turn)%2+1;
                if (d.Turn==1){label.setText("Au tour de "+nom1);}
                else {label.setText("Au tour de "+nom2);}
                
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
        System.out.println(numero+" ");
        int lastx=d.xcases[d.caseSelectionne];
        int lasty=d.ycases[d.caseSelectionne];
        
        if (nbpions>=6 && d.onMove){
            //on relâche la souri après avoir déplacé un pion
            d.onMove=false;

            //si l'arrivée est sur la grille
            if (numero!=-1){
                int newx=d.xcases[numero];
                int newy=d.ycases[numero];
                // si la case d'arrivée est libre et le mouvement est autorisée
                if (d.tableau[numero]==0 && MouvPossible(lastx, lasty,newx,newy)){
                    // on place le pion sur la grille
                    d.tableau[numero]=d.Turn;

                    //Dernière action effectuée: on stocke la position 
                    //du début du mouvement et la position finale à la suite
                    d.lastPosition.add(d.caseSelectionne);
                    d.lastPosition.add(numero);
           
                    //changement de joueuer
                    d.Turn= (d.Turn)%2+1;
                }
                else {// sinon redessine le pion à sa position initiale
                    d.tableau[d.caseSelectionne]=d.Turn;}
            }
            
            else {// sinon redessine le pion à sa position initiale
                d.tableau[d.caseSelectionne]=d.Turn;}

            // On redessine la grille
            d.repaint();   
            
        }
        // Si trois pions sont alignés
        if (vainqueur()){
            // Affichage d'une fenêtre secondaire qui annonce le gagnant et mise à jour des scores
            String message;
            if (d.Turn%2+1==1){
                message=nom1+" Vous avez gagné!";
                s1++;
                score1.setText("Score de " + nom1 + ": "+s1);}
            else {message=nom2+" Vous avez gagné!";
                s2++;
                score2.setText("Score de " + nom2 + ": "+s2);}

            JOptionPane.showMessageDialog(this, message, "Gagnant", JOptionPane.INFORMATION_MESSAGE);
            
            // Lancement d'une nouvelle partie
            newGame();
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    } 

    public void mouseDragged(MouseEvent e) {
        // Phase2: Déplacement, déjà 6 pions sur la grille
        if (nbpions>=6){
            // on vérifie que le pion sélectionné appartient bien au joueur dont c'est le tour
            if (d.tableau[d.caseSelectionne]==d.Turn){
                d.onMove=true;
                // Plus de pions à la position de départ
                d.tableau[d.caseSelectionne]=0;
            }

            // Position actuelle du pion
            d.x=e.getX();
            d.y=e.getY();
            d.repaint();
        }
    }

    public void mouseMoved(MouseEvent e) {
        
    }

    
}
