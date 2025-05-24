package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;

public class Shot {
    private Image shotIcon;
    private int x, y;
    private int largura, altura;
    private boolean isVisible;

    private static final int LARGURA = 1220;
    private static int VELOCIDADE = 20;

     public Shot(int x, int y){
         this.x = x-10;
         this.y = y;
         isVisible = true;
     }

     public void load() {
         shotIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\shot22.png").getImage();

         this.largura = shotIcon.getWidth(null);
         this.altura = shotIcon.getHeight(null);
     }

     public void update(){
         this.x += VELOCIDADE;
         if (this.x > LARGURA){
             isVisible = false;
         }
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
        Shot.VELOCIDADE = VELOCIDADE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getShotIcon() {
        return shotIcon;
    }
}
