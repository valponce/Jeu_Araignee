import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

class Dessin extends JPanel {
    private int largeurPanel,hauteurPanel;
    
    // historique des actions des joueurs, d'abord les positions initilaes dans l'ordre
    //puis pour chaque mouvement la case de départ et d'arriver
    public ArrayList<Integer> lastPosition;

    // Etat de la grille en temps réel: 0=case libre, 1= pion du joueur1, 2=pion du joueur2
    public int[] tableau;

    // Déplacement d'un pion en cours ou non, pour savoir si la figure doit suivre la souris ou non
    public boolean onMove;

    //Entier 1 ou 2 pour savoir qui doit jouer
    public int Turn;
    
    //numero de la case sélectionnée
    public int caseSelectionne;

    //Couleurs pour les pions des joueurs
    public Color cJ1,cJ2;

    //coordonnées du coin droit de la grille
    public int x1,y1;

    //coordonnées actuelles du pion en mouvement
    public int x;
    public int y;

    // coordonnées x et y du centre des cases
    public int[] xcases =new int[9];
    public int[] ycases=new int[9];

    Dessin() {
        super();
        cJ1=Color.RED;
        cJ2=Color.BLUE;

        //Grille numérotée de gauche à droite et de haut en bas
        tableau = new int[9];
        Turn=1;
        lastPosition = new ArrayList<Integer> ();
        redimensionFrame(400,400);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //adaptation à la taille de la fenêtre
        redimensionFrame(getWidth(), getHeight());

        // on caste en Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        if (Turn==1){
            g2.setColor(cJ1);
        }
        else {g2.setColor(cJ2);}
  
       
        
        g2.setStroke(new BasicStroke( 10.0f ));
        g2.drawRect(x1,y1,300,300);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke( 3.0f ));
        // dessin de la grille
        // lignes
        g2.drawLine(x1, y1,x1+300, y1);
        g2.drawLine(x1,y1+100, x1+300, y1+100);
        g2.drawLine(x1, y1+200, x1+300, y1+200);
        g2.drawLine(x1, y1+300, x1+300, y1+300); 

        //colones
        g2.drawLine(x1+300, y1, x1+300, y1+300);
        g2.drawLine(x1+200, y1, x1+200, y1+300);
        g2.drawLine(x1+100, y1, x1+100, y1+300);
        g2.drawLine(x1, y1, x1, y1+300);

        
        // dessin du pion sélectionné en mouvement
        if (onMove){
           if (Turn==1){
                g2.setColor(cJ1);
                g2.drawOval(x-25, y-25,50,50);
           }
           else {g2.setColor(cJ2);
            g2.drawRect(x-25, y-25,50,50);}
        }
        
        
        // dessin des pions placés
        for (int i=0;i<9;i++){
            if (tableau[i]==1){
                g2.setColor(cJ1);
                g2.drawOval(xcases[i]-25, ycases[i]-25,50,50);
            }
            else {if (tableau[i]==2)
                    {g2.setColor(cJ2);
                     g2.drawRect(xcases[i]-25, ycases[i]-25,50,50);}
            }
            
        }
    }

    public void redimensionFrame(int newl,int newh){
        //on redimensionn seulement si changement de taille
        if (newl!=largeurPanel){
            //coordonnée x du coin haut gauche de la grille
            x1=(newl-300)/2;
        
            // Calcul des x des centres des cases
            for (int i=0;i<9;i+=3){
                xcases[i]=x1+50;
                xcases[i+1]=x1+150;
                xcases[i+2]=x1+250;
            }
            largeurPanel=newl;
        }
        if (newh!=hauteurPanel){
            //coordonnée y du coin haut gauche de la grille
            y1=(newh-300)/2;

            // Calcul des y des centres des cases
            for (int i=0;i<3;i++){
                ycases[0+i]=y1+50;
                ycases[3+i]=y1+150;
                ycases[6+i]=y1+250;
            }
            hauteurPanel=newh;
        }
    }
}

