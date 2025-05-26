package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;

public class Enemy1 {
    private Image shotIcon;
    private int x, y;
    private int largura, altura;
    private boolean isVisible;

    //private static final int LARGURA = 1220;
    private static int VELOCIDADE = 2;

     public Enemy1(int x, int y){
         this.x = x-20;
         this.y = y;
         isVisible = true;
     }

     public Rectangle getBounds(){
         return new  Rectangle(x, y, altura, largura);
     }

     public void load() {
         shotIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\enemy22.png").getImage();

         this.largura = shotIcon.getWidth(null);
         this.altura = shotIcon.getHeight(null);
     }

     public void update(){
         this.x -= VELOCIDADE;
         /*if (this.x > LARGURA){
             isVisible = false;
         }*/
     }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        Enemy1.VELOCIDADE = VELOCIDADE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getEnemyIcon() {
        return shotIcon;
    }
}
