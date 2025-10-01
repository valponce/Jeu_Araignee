import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Undo implements ActionListener {
    private Jeu j;
    private Dessin d;

    public Undo(Dessin panel,Jeu jeu){
        d=panel;
        j=jeu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int n=d.lastPosition.size();
        if (n>0){
            d.tableau[d.lastPosition.get(n-1)]=0;
            d.lastPosition.remove(n-1);
            d.Turn= (d.Turn)%2+1;
            if (n<=6){
                j.setNbpions(j.getNbpions()-1);
            }
            else {// replace le pion à la position avant le mouvement
                d.tableau[d.lastPosition.get(n-2)]=d.Turn;
                d.lastPosition.remove(n-2);}
            d.repaint();

        }
    }
}
