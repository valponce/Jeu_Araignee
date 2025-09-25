import java.awt.Graphics;

public class Pion {
    private int x,y;

    public Pion(int px,int py){
        x=px;
        y=py;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

   public void dessinerPion(Graphics g){
        g.drawOval(x-25, y-25,50,50);
   }
}
