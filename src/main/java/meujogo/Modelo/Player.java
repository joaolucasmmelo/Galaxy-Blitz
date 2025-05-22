package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x,y;
    private int dx,dy;
    private int altura,largura;
    private Image playerIcon;

    public Player(){
        this.x = 100;
        this.y = 100;
    }

    public void load(){
        playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave11.png").getImage();

        altura = playerIcon.getHeight(null);
        largura = playerIcon.getWidth(null);
    }

    public void update(){
        x+=dx;
        y+=dy;
    }

    public void convertMoviment(KeyEvent tecla){
        char pressed = tecla.getKeyChar();

        if (pressed == 'w'){
            dy = -5;
        }
        if (pressed == 's'){
            dy = 5;
        }
        if (pressed == 'a'){
            dx = -5;
        }
        if (pressed == 'd'){
            dx = 5;
        }
    }

    public void keyReleased(KeyEvent tecla){
        char pressed = tecla.getKeyChar();

        if (pressed == 'w'){
            dy = 0;
        }
        if (pressed == 's'){
            dy = 0;
        }
        if (pressed == 'a'){
            dx = 0;
        }
        if (pressed == 'd'){
            dx = 0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getPlayerIcon() {
        return playerIcon;
    }
}
