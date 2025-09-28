import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
    Dessin d;
    int taille;
    int nbpions,s1,s2;
    JMenuItem nouvellePartie,Quitter,colorJ1,colorJ2,undo;
    JLabel label,score1,score2;
    String nom1,nom2;
    
    
    public static void main(String[] args) throws Exception {
        Jeu j= new Jeu();
    }

    public Jeu() {
        super();

        nbpions=0;
        //score des joueurs
        s1=0;
        s2=0;
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
        if (nom2==null){nom1="Joueur2";}
        
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

    public boolean ongrid(int px,int py){
        if (d.x1<=px && px<=d.x1+taille && d.y1<=py && py<=d.y1+taille){
            return true;
        }
        else return false;
    }

    //renvoie le numéro de la case correspondant aux coordonnées x,y
    public int appartient(int x,int y){
        // largeur= largeur de la grille, rajouter le décalage
        int b0,b1;
        if (x-d.x1<taille/3){
            b0=0;
        }
        else if (x-d.x1<2*taille/3){
                b0=1;
            }
            else {b0=2;}

        if (y-d.y1<taille/3){
            b1=0;
        }
        else if (y-d.y1<2*taille/3){
                b1=1;
            }
            else {b1=2;}
        return b0+3*b1;

    }

    public boolean MouvPossible(int lastx,int lasty,int newx,int newy){
        //retour sur la même case impossible
        if (newx==lastx && newy==lasty){return false;}

        if ((newx==lastx+100 || newx==lastx-100 || newx==lastx) && (newy==lasty+100 || newy==lasty-100|| newy==lasty)){
            return true;
        }
        else return false;
    }

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

    public void newGame(){
        for (int i=0;i<9;i++){
            d.tableau[i]=0;
            }
        d.repaint();
        nbpions=0;
        d.lastPosition.clear();
        d.onMove=false;
    }

    public void mousePressed(MouseEvent e){
        d.caseSelectionne=appartient(e.getX(),e.getY());
        
        if (nbpions<6){
            int numero=appartient(e.getX(),e.getY());
             //si la case est libre, on y dessine le pion du joueur dont c'est le tour
            if (d.tableau[numero]==0 && ongrid(e.getX(),e.getY())){
                d.tableau[numero]=d.Turn;
                d.repaint();
                nbpions++;

                //Dernière action effectuée
                d.lastPosition.add(numero);
                System.out.println(" "+d.lastPosition.get(d.lastPosition.size()-1));
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
        int lastx=d.xcases[d.caseSelectionne];
        int lasty=d.ycases[d.caseSelectionne];
        int newx=d.xcases[numero];
        int newy=d.ycases[numero];
    
        
        if (nbpions>=6 && d.onMove){
        //on relâche la souirs après avoir déplacé un pion
        d.onMove=false;
        
        // si la case d'arrivée est libre et le mouvement autorisée
        if (d.tableau[numero]==0 && MouvPossible(lastx, lasty,newx,newy) && ongrid(e.getX(),e.getY())){
            d.tableau[numero]=d.Turn;
            d.repaint();

            //Dernière action effectuée: on stocke la position 
            //du début du mouvement et la position finale à la suite
            d.lastPosition.add(d.caseSelectionne);
            d.lastPosition.add(numero);
           
            //changement de joueuer
            d.Turn= (d.Turn)%2+1;
        }
        else {// on redessine le pion à sa position initiale
            d.tableau[d.caseSelectionne]=d.Turn;}
            d.repaint();   
            
        }
        if (vainqueur()){
            String message;
            if (d.Turn%2+1==1){
                message=nom1+" Vous avez gagné!";
                s1++;
                score1.setText("Score de " + nom1 + ": "+s1);}
            else {message=nom2+" Vous avez gagné!";
                s2++;
                score2.setText("Score de " + nom2 + ": "+s2);}
            JOptionPane.showMessageDialog(this, message, "Gagnant", JOptionPane.INFORMATION_MESSAGE);
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
