package meujogo.Modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Phase extends JPanel implements ActionListener {
    private final Image background, skull, gameoverPlayAgain, gameoverExit, gameStartPlay, gameStartExit, explosion;
    private int x1, x2;
    private final Player player;
    private List<Enemy1> enemy1;
    private boolean inGame;
    private boolean showGameOver;
    private boolean starting = true;
    private int selectedOption = 0;
    Integer kills = 0;
    long explosionTime;

    SoundPlayer explosionSound = new SoundPlayer();

    public Phase() {
        setFocusable(true);
        setDoubleBuffered(true);

        showGameOver = false;
        background = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\background.png").getImage();
        skull = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\kills.png").getImage();
        gameoverPlayAgain = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\game_over1.png").getImage();
        gameoverExit = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\game_over2.png").getImage();
        gameStartPlay = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\title1.png").getImage();
        gameStartExit = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\title2.png").getImage();
        explosion = new ImageIcon("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\kbum.png").getImage();
        player = new Player();
        player.load();

        addKeyListener(new TecladoAdapter());

        x1 = 0;
        x2 = background.getWidth(null);

        Timer timer = new Timer(15, this);
        timer.start();

        enemyInit();
    }

    public void enemyInit(){
        int[] enemysQuant = new int[100];
        enemy1 = new ArrayList<>();

        for (int i : enemysQuant) {
            int x = (int)(Math.random() * 8500 + 1280);
            int y = (int)(Math.random() * (720 - 100));
            enemy1.add(new Enemy1(x, y));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(background, x1, 0, this);
        g.drawImage(background, x2, 0, this);

        if (starting){
            if (selectedOption == 0) {
                g.drawImage(gameStartPlay, 0, 0, null);
            } else {
                g.drawImage(gameStartExit, 0, 0, null);
            }
        }
        else {
            if (inGame) {
                g.drawImage(player.getPlayerIcon(), player.getX(), player.getY(), this);
                g.drawImage(player.getBoostIcon(), player.getX() - 55, player.getY(), this);

                List<Shot> shots = player.getShots();
                for (Shot s : shots) {
                    s.load();
                    g.drawImage(s.getShotIcon(), s.getX(), s.getY(), this);
                }

                displayEnemys(g);
                displayStats(g);
            }
            else {
                displayEnemys(g);
                g.drawImage(explosion, player.getX(), player.getY(), this);
                displayStats(g);
            }

            if (showGameOver && !starting) {
                if (selectedOption == 0) {
                    g.drawImage(gameoverPlayAgain, 0, 0, null);
                } else {
                    g.drawImage(gameoverExit, 0, 0, null);
                }
            }
        }
    }

    public void displayEnemys(Graphics g) {
        for (Enemy1 enemy : enemy1) {
            enemy.load();
            g.drawImage(enemy.getEnemyIcon(), enemy.getX(), enemy.getY(), this);
        }
    }

    public void displayStats(Graphics g){
        g.drawImage(player.getGasIcon(), 10, 10, this);
        g.drawImage(player.getLifeIcon(), 170, 18, this);
        g.drawImage(skull, 1100, 10, this);
        killsCount(kills, g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!inGame) {
            if (System.currentTimeMillis() - explosionTime >= 2000) {
                showGameOver = true;
            }
            repaint();
            return;
        }

        int speed = player.getBoostAtivo() ? 12 : 5;

        x1 -= speed;
        x2 -= speed;

        if (x1 + background.getWidth(null) <= 0) {
            x1 = x2 + background.getWidth(null);
        }
        if (x2 + background.getWidth(null) <= 0) {
            x2 = x1 + background.getWidth(null);
        }

        player.update();
        List<Shot> shots = player.getShots();
        for (int i = 0; i < shots.size(); i++) {
            Shot s = shots.get(i);
            if (s.isVisible()) {
                s.update();
            } else {
                shots.remove(i--);
            }
        }

        for (int k = 0; k < enemy1.size(); k++) {
            Enemy1 enemy = enemy1.get(k);
            if (enemy.isVisible()) {
                enemy.update();
            } else {
                enemy1.remove(k--);
            }
        }

        colideCheck();
        repaint();
    }

    public void colideCheck(){
        Rectangle naveShape = player.getBounds();

        for (int i = 0; i < enemy1.size(); i++){
            Enemy1 tempEnemy1 = enemy1.get(i);
            Rectangle enemy1Shape = tempEnemy1.getBounds();

            if (naveShape.intersects(enemy1Shape)){
                if (player.getBoostAtivo()){
                    tempEnemy1.setVisible(false);
                    kills += 1;
                }
                else {
                    player.lostLife();
                    player.checkLife();
                    if (player.getLife() == 0){
                        for (Enemy1 e : enemy1){
                            e.setVelocidade(0);
                        }
                        player.setVelocidade(0);
                        explosionTime = System.currentTimeMillis();
                        explosionSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\explosion_sound.WAV");
                        inGame = false;
                    }
                }
            }
        }

        List<Shot> shots = player.getShots();
        for (Shot tempShot : shots) {
            Rectangle shotShape = tempShot.getBounds();

            for (Enemy1 tempEnemy1 : enemy1) {
                Rectangle enemy1Shape = tempEnemy1.getBounds();

                if (shotShape.intersects(enemy1Shape)) {
                    tempEnemy1.setVisible(false);
                    tempShot.setVisible(false);
                    kills += 1;
                }
            }
        }
    }

    public void killsCount(Integer kills, Graphics g){
        int nextNumberPosition = 1155;
        String ks = kills.toString();

        for (int i = 0; i < ks.length(); i++){
            String pathN = "D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\numbers\\n" + (ks.charAt(i)) + ".png";
            Image n = new ImageIcon(pathN).getImage();

            g.drawImage(n, nextNumberPosition,17, this);
            if(ks.length() > 1){
                nextNumberPosition += 30;
            }
        }
    }

    private class TecladoAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent tecla) {
            if (showGameOver) {
                if (tecla.getKeyCode() == KeyEvent.VK_LEFT || tecla.getKeyCode() == KeyEvent.VK_A) {
                    selectedOption = 0;
                } else if (tecla.getKeyCode() == KeyEvent.VK_RIGHT || tecla.getKeyCode() == KeyEvent.VK_D) {
                    selectedOption = 1;
                } else if (tecla.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (selectedOption == 0) {
                        inGame = true;
                        showGameOver = false;
                        starting = false;
                        x1 = 0;
                        x2 = background.getWidth(null);
                        kills = 0;
                        player.reset();
                        player.getShots().clear();
                        enemy1.clear();
                        enemyInit();

                        repaint();
                    } else {
                        System.exit(0);
                    }
                }
            } else {
                player.keyPressed(tecla);
            }
        }

        public void keyReleased(KeyEvent tecla) {
            if (!showGameOver) {
                player.keyReleased(tecla);
            }
        }
    }
}
