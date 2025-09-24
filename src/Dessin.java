import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class Dessin extends JPanel {
    public ArrayList<Pion> pions;

    Dessin() {
        super();
        pions = new ArrayList<Pion> ();
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
        
        // dessin des pions placés
        for (Pion p: pions){
            p.dessinerPion(g);
        }
    }

    public void addPion(int px,int py){
        pions.add(new Pion(px,py));
    }
}

