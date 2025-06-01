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
    private Image specialBarIcon;
    private final List<Shot> shots;
    private final List<SpecialShot> specialShots;
    private boolean shotCountVer = false;
    private boolean specialReady = false;
    private boolean duringSpecial = false;
    private int killsVer;
    private long specialStartTime;

    SoundPlayer damageSound = new SoundPlayer();
    SoundPlayer heartSound = new SoundPlayer();
    SoundPlayer boostSound = new SoundPlayer();
    SoundPlayer shotSound = new SoundPlayer();

    private Image gasIcon;
    private final List<BoostInfo> boostsAtivos = new ArrayList<>();
    private boolean boostAtivo = false;
    private int gas = 3;

    public List<SpecialShot> getSpecialShots() {
        return specialShots;
    }

    private static class BoostInfo {
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
        shots = new ArrayList<>();
        specialShots = new ArrayList<>();
    }

    public Rectangle getBounds(){
        return new  Rectangle(this.x, this.y, 85, 35);
    }

    public void load() {
        playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();
        altura = playerIcon.getHeight(null);
        largura = playerIcon.getWidth(null);
    }

    public void update() {
        if (up) y -= velocidade;
        if (down) y += velocidade;
        if (left) x -= velocidade;
        if (right) x += velocidade;
        if (x < 0) x = 0;
        if (x > 1240) x = 1240;
        if (y < 0) y = 0;
        if (y > 680 - playerIcon.getHeight(null)) y = 680 - playerIcon.getHeight(null);
        if (x > 1270 - playerIcon.getWidth(null)) x = 1270 - playerIcon.getWidth(null);

        checkSpecialStatus();
        checkGasStatus();
        checkLife();

        long now = System.currentTimeMillis();
        if (shift && !boostAtivo && gas > 0) {
            boostAtivo = true;
            gas--;
            boostsAtivos.add(new BoostInfo(now));
            boostSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\boost_sound.WAV");
        }

        List<BoostInfo> boostsParaRemover = new ArrayList<>();
        for (BoostInfo b : boostsAtivos) {
            if (!b.ended && now - b.startTime >= 3000) {
                boostAtivo = false;
                b.ended = true;
            }

            if (b.ended && now - b.startTime >= 28000) {
                if (gas < 3) gas++;
                boostsParaRemover.add(b);
            }
        }
        boostsAtivos.removeAll(boostsParaRemover);

        if (boostAtivo) {
            velocidade = 8;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave_blur.png").getImage();
            boostIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\fire.png").getImage();
        }
        else {
            velocidade = 4;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();
            boostIcon = null;
        }

        if (!verLife && System.currentTimeMillis() - lastLifeLossTime >= 2000) {
            verLife = true;
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave.png").getImage();
        }
        if (!verLife){
            if (verDamageSound){
                damageSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\damage_sound.WAV");
                verDamageSound = false;
            }
            playerIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\nave_damage.png").getImage();
        }

        if (duringSpecial){
            velocidade = 2;
            if ((now - specialStartTime) > 5000){
                duringSpecial = false;
                killsVer = 0;
            }
        }
    }

    public void simpleShot(){
        if (!boostAtivo){
            this.shots.add(new Shot(x+largura, y + (altura/2)));
            shotSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\shot_sound.WAV");
        }
    }

    public void specialShot() {
        if (!boostAtivo){
            this.specialShots.add(new SpecialShot(this));
            shotSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\powerShot_sound.WAV");
        }
    }

    public void keyPressed(KeyEvent tecla) {
        int code = tecla.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) up = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) left = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = true;
        if (code == KeyEvent.VK_SHIFT && !duringSpecial){
            shift = true;
        }
        if (code == KeyEvent.VK_P && !shotCountVer && !duringSpecial){
            simpleShot();
            shotCountVer = true;
        }
        if (code == KeyEvent.VK_SPACE && specialReady && !boostAtivo){
            specialShot();
            duringSpecial = true;
            specialStartTime = System.currentTimeMillis();
            killsVer = 0;
            specialReady = false;
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
        }
        if (gas == 2){
            gasIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\gas_2.png").getImage();
        }
        if (gas == 1){
            gasIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\gas_1.png").getImage();
        }
        if (gas == 0){
            gasIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\gas_0.png").getImage();
        }
    }

    public void checkLife(){
        if (life == 3){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life3.png").getImage();
        }
        if (life == 2){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life2.png").getImage();
        }
        if (life == 1){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life1.png").getImage();
        }
        if (life == 0){
            lifeIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\life0.png").getImage();
        }
    }

    public void checkSpecialStatus(){
        if (0 <= killsVer && killsVer < 8){
            specialBarIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\specialbar0.png").getImage();
        }
        if (8 <= killsVer && killsVer < 16){
            specialBarIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\specialbar1.png").getImage();
        }
        if (16 <= killsVer && killsVer < 24){
            specialBarIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\specialbar2.png").getImage();
        }
        if (24 <= killsVer && killsVer < 32){
            specialBarIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\specialbar3.png").getImage();
        }
        if (32 <= killsVer && killsVer < 40){
            specialBarIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\specialbar4.png").getImage();
        }
        if (40 <= killsVer){
            specialBarIcon = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\specialbar5.png").getImage();
            if (!specialReady){
                shotSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\ready_sound.wav");
            }
            specialReady = true;
        }
    }

    public void reset() {
        this.x = 100;
        this.y = 100;
        this.velocidade = 3;
        this.gas = 3;
        this.life = 3;
        this.killsVer = 0;
        this.boostAtivo = false;
        this.shots.clear();
        this.specialShots.clear();
    }

    boolean verLife = true;
    boolean verDamageSound = false;
    private long lastLifeLossTime = 0;

    public void lostLife() {
        long now = System.currentTimeMillis();
        if (verLife && life > 0) {
            this.life -= 1;
            verLife = false;
            verDamageSound = true;
            lastLifeLossTime = now;
        }
    }

    public void gainLife(){
        this.life += 1;
        heartSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\heart_sound.WAV");
    }

    public int getLife() {
        return this.life;
    }
    public boolean getBoostAtivo() {
        return boostAtivo;
    }

    public boolean isDuringSpecial() {
        return duringSpecial;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVelocidade(int velocidade){
        this.velocidade = velocidade;
    }

    public void setKillsVer() {
        this.killsVer = killsVer + 1;
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

    public Image getSpecialBarIcon() {
        return specialBarIcon;
    }

    public Image getLifeIcon() {
        return lifeIcon;
    }

    public List<Shot> getShots() {
        return shots;
    }
}
