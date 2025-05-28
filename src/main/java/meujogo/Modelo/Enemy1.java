package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;

public class Enemy1 {
    private Image shotIcon;
    private int x, y;
    private int largura, altura;
    private boolean isVisible;

    //private static final int LARGURA = 1220;
    private int velocidade = 4;

     public Enemy1(int x, int y){
         this.x = x-20;
         this.y = y;
         isVisible = true;
     }

     public Rectangle getBounds(){
         return new  Rectangle(this.x, this.y, 80, 50);
     }

     public void load() {
         shotIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\enemy22.png").getImage();

         this.largura = shotIcon.getWidth(null);
         this.altura = shotIcon.getHeight(null);
     }

     public void update(){
         this.x -= velocidade;
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

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
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
