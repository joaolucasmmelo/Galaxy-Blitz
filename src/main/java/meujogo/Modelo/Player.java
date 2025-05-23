package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;
    private int velocidade = 5;
    private boolean up, down, left, right, shift;
    private Image playerIcon;

    public Player() {
        this.x = 100;
        this.y = 100;
    }

    public void load() {
        playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();
    }

    public void update() {
        if (up){
            y -= velocidade;
        }
        if (down){
            y += velocidade;
        }
        if (left){
            x -= velocidade;
        }
        if (right){
            x += velocidade;
        }
        if (shift){
            velocidade = 12;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave_boost_blur.png").getImage();
        }
        else {
            velocidade = 5;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();
        }
    }

    public void convertMoviment(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = true;
        if (code == KeyEvent.VK_SHIFT) shift = true;
    }

    public void keyReleased(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = false;
        if (code == KeyEvent.VK_SHIFT) shift = false;
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
