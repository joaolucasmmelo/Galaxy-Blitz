package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy1 {
    private Image enemy1Icon;
    private int x, y;
    private int life;
    private int largura, altura;
    private boolean isVisible;
    private int velocidade = 3;

    private Image enemy1ShotIcon;
    private List<Shot> enemy1Shots;
    private long lastShotTime = System.currentTimeMillis();

    public Enemy1(int x, int y){
        this.life = 2;
        this.x = x;
        this.y = y;
        isVisible = true;
        enemy1Shots = new ArrayList<>();
    }

    public Rectangle getBounds(){
         return new  Rectangle(this.x, this.y, 80, 50);
    }

    public void load() {
        if (this.life == 2){
            enemy1Icon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\enemy1.png").getImage();
        }
        if (this.life == 1){
            enemy1Icon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\enemy11.png").getImage();
        }

        enemy1ShotIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\shot.png").getImage();

        this.largura = enemy1Icon.getWidth(null);
        this.altura = enemy1Icon.getHeight(null);
    }

    public void update() {
        this.x -= velocidade;
        if (x >= 0 && x <= 1280) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= 2000) {
                simpleShot();
                lastShotTime = currentTime;
            }
        }
    }

    public void simpleShot(){
         this.enemy1Shots.add(new Shot(x+largura, y + (altura/2)));
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public void setLife(int life){
        this.life = life;
    }

    public int getLife(){
        return this.life;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getEnemyIcon() {
        return enemy1Icon;
    }
}
