import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

class Undo implements ActionListener {
    Jeu j;
    Dessin d;

    public Undo(Dessin panel,Jeu jeu){
        d=panel;
        j=jeu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //fonctionne pour nbpion<6 mais pas pour mouveent
        int n=d.lastPosition.size();
        if (n>0){
            d.tableau[n-1]=0;
            d.lastPosition.remove(n-1);
            d.Turn= (d.Turn)%2+1;
            d.repaint();
            j.nbpions--;
        }
    }
}
