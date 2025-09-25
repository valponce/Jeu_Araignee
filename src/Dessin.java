import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class Dessin extends JPanel {
    public ArrayList<Pion> pions;
    int[] tableau;
    boolean onMove;
    int Turn;
    int typePion;
    int caseSelectionne;

    //coordonnées actuelles du pion en mouvement
    int x;
    int y;

    // coordonnées x et y du centre des cases
    int[] xcases={150,250,350,150,250,350,150,250,350};
    int[] ycases={150,150,150,250,250,250,350,350,350};

    

    Dessin() {
        super();
        tableau = new int[9];
        Turn=1;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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

        
        // dessin du pion sélectionné en mouvement
        if (onMove){
           if (typePion==1){
           g.drawOval(x-25, y-25,50,50);
           }
           else {g.drawRect(x-25, y-25,50,50);}
        }
        
        
        // dessin des pions placés
        for (int i=0;i<9;i++){
            if (tableau[i]==1){
                g.drawOval(xcases[i]-25, ycases[i]-25,50,50);
            }
            else {if (tableau[i]==2)
                 {g.drawRect(xcases[i]-25, ycases[i]-25,50,50);}
            }
            
        }
    }
}

