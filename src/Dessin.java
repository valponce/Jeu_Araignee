import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

        // on caste en Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        if (Turn==1){
            g2.setColor(Color.red);
        }
        else {g2.setColor(Color.blue);}

        
        g2.setStroke(new BasicStroke( 10.0f ));
        g2.drawRect(100,100,300,300);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke( 3.0f ));
        // dessin de la grille
        // lignes
        g2.drawLine(100, 400, 400, 400);
        g2.drawLine(100, 200, 400, 200);
        g2.drawLine(100, 300, 400, 300);
        g2.drawLine(100, 100, 400, 100); 

        //colones
        g2.drawLine(400, 100, 400, 400);
        g2.drawLine(300, 100, 300, 400);
        g2.drawLine(200, 100, 200, 400);
        g2.drawLine(100, 100, 100, 400);

        
        // dessin du pion sélectionné en mouvement
        if (onMove){
           if (typePion==1){
                g2.setColor(Color.red);
                g2.drawOval(x-25, y-25,50,50);
           }
           else {g2.setColor(Color.blue);
            g2.drawRect(x-25, y-25,50,50);}
        }
        
        
        // dessin des pions placés
        for (int i=0;i<9;i++){
            if (tableau[i]==1){
                g2.setColor(Color.red);
                g2.drawOval(xcases[i]-25, ycases[i]-25,50,50);
            }
            else {if (tableau[i]==2)
                    {g2.setColor(Color.blue);
                     g2.drawRect(xcases[i]-25, ycases[i]-25,50,50);}
            }
            
        }
    }
}

