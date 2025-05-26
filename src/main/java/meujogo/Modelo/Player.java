package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int x, y;
    private int altura, largura;
    private int velocidade, life = 3;
    private boolean up, down, left, right, shift;
    private Image playerIcon;
    private Image boostIcon;
    private Image lifeIcon;
    private List<Shot> shots;
    private boolean shotCountVer = false;


    private Image gasIcon;
    private List<BoostInfo> boostsAtivos = new ArrayList<>();

    private boolean boostAtivo = false;
    private int gas = 3;

    private class BoostInfo {
        long startTime;
        boolean ended;

        BoostInfo(long startTime) {
            this.startTime = startTime;
            this.ended = false;
        }
    }

    public Player() {
        this.x = 100;
        this.y = 100;

        shots = new ArrayList<Shot>();
    }

    public Rectangle getBounds(){
        return new  Rectangle(x, y, altura, largura);
    }

    public void load() {
        playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();

        altura = playerIcon.getHeight(null);
        largura = playerIcon.getWidth(null);
    }

    public void update() {
        // Movimento
        if (up) y -= velocidade;
        if (down) y += velocidade;
        if (left) x -= velocidade;
        if (right) x += velocidade;

        checkGasStatus();
        checkLife();

        long now = System.currentTimeMillis();

        // Verifica boost ativo
        if (shift && !boostAtivo && gas > 0) {
            boostAtivo = true;
            gas--;
            boostsAtivos.add(new BoostInfo(now));
        }

        // Atualiza status dos boosts
        List<BoostInfo> boostsParaRemover = new ArrayList<>();

        for (BoostInfo b : boostsAtivos) {
            // Desativa boost após 5 segundos
            if (!b.ended && now - b.startTime >= 3000) {
                boostAtivo = false;
                b.ended = true;
            }

            // Recarga boost após 15 segundos desde o fim
            if (b.ended && now - b.startTime >= 28000) {
                if (gas < 3) gas++;
                boostsParaRemover.add(b);
            }
        }
        boostsAtivos.removeAll(boostsParaRemover);

        // Atualiza velocidade e sprite
        if (boostAtivo) {
            velocidade = 6;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave_blur.png").getImage();
            boostIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\fire.png").getImage();

        } else {
            velocidade = 3;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();
            boostIcon = null;
        }

        if (!verLife && System.currentTimeMillis() - lastLifeLossTime >= 3000) {
            verLife = true;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();
        }
        if (!verLife){
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave_damage.png").getImage();
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

        if (code == KeyEvent.VK_SHIFT){
            shift = true;
        }

        if (code == KeyEvent.VK_P && !shotCountVer){
            simpleShot();
            shotCountVer = true;
        }
    }

    public void keyReleased(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = false;

        if (code == KeyEvent.VK_SHIFT){
            shift = false;
        }

        if (code == KeyEvent.VK_P) shotCountVer = false;
    }

    public void checkGasStatus(){
        if (gas == 3){
            gasIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\gas_3.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
        if (gas == 2){
            gasIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\gas_2.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
        if (gas == 1){
            gasIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\gas_1.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
        if (gas == 0){
            gasIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\gas_0.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
    }

    public void checkLife(){
        if (life == 3){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life3.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
        if (life == 2){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life2.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
        if (life == 1){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life1.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
        if (life == 0){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life0.png").getImage();
            altura = gasIcon.getHeight(null);
            largura = gasIcon.getWidth(null);
        }
    }

    boolean verLife = true;
    private long lastLifeLossTime = 0;

    public void lostLife() {
        long now = System.currentTimeMillis();

        if (verLife && life > 0) {
            this.life -= 1;
            verLife = false;
            lastLifeLossTime = now;
        }
    }

    public void gainLife(){
        this.life += 1;
    }

    public int getLife() {
        return this.life;
    }
    public boolean getBoostAtivo() {
        return boostAtivo;
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

    public Image getBoostIcon() {
        return boostIcon;
    }

    public Image getGasIcon() {
        return gasIcon;
    }

    public Image getLifeIcon() {
        return lifeIcon;
    }

    public List<Shot> getShots() {
        return shots;
    }
}
