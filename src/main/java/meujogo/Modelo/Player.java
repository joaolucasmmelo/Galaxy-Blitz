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
        playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave2.png").getImage();

        altura = playerIcon.getHeight(null);
        largura = playerIcon.getWidth(null);
    }

    public void update(){
        x+=dx;
        y+=dy;
    }

    public void keyPressed(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();

        if (codigo == KeyEvent.VK_UP) {
            dy = -7;
        }
        if (codigo == KeyEvent.VK_DOWN) {
            dy = 7;
        }
        if (codigo == KeyEvent.VK_LEFT) {
            dx = -7;
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            dx = 7;
        }
    }

    public void keyReleased(KeyEvent tecla){
        int codigo = tecla.getKeyCode();

        if (codigo == KeyEvent.VK_UP){
            dy=0;
        }
        if (codigo == KeyEvent.VK_DOWN){
            dy=0;
        }
        if (codigo == KeyEvent.VK_LEFT){
            dx=0;
        }
        if (codigo == KeyEvent.VK_RIGHT){
            dx=0;
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
