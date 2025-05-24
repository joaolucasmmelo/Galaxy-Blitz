package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int x, y;
    private int altura, largura;
    private int velocidade;
    private boolean up, down, left, right, shift;
    private Image playerIcon;
    private List<Shot> shots;

    private boolean shotCountVer = false;

    public Player() {
        this.x = 100;
        this.y = 100;

        shots = new ArrayList<Shot>();
    }

    public void load() {
        playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave1.png").getImage();

        altura = playerIcon.getHeight(null);
        largura = playerIcon.getWidth(null);
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
            velocidade = 6;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave_boost_blur1.png").getImage();
        }
        else {
            velocidade = 3;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave1.png").getImage();
        }
    }

    public void simpleShot(){
        this.shots.add(new Shot(x+largura, y + (altura/2)));
    }

    public void keyPressed(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = true;
        if (code == KeyEvent.VK_SHIFT) shift = true;

        if (code == KeyEvent.VK_P && shotCountVer == false){
            simpleShot();
            shotCountVer =true;
        }
    }

    public void keyReleased(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = false;
        if (code == KeyEvent.VK_SHIFT) shift = false;

        if (code == KeyEvent.VK_P) shotCountVer = false;
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

    public List<Shot> getShots() {
        return shots;
    }
}
