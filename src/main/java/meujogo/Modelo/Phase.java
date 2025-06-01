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
    private List<Heart> hearts;
    private boolean inGame;
    private boolean showGameOver;
    private boolean starting = true;
    private int selectedOption = 0;
    Integer kills = 0;
    long explosionTime;

    SoundPlayer explosionSound = new SoundPlayer();
    SoundPlayer enemyDieSound = new SoundPlayer();

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
        heartInit();
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

                displayEnemies(g);

                List<Shot> shots = player.getShots();
                for (Shot s : shots) {
                    s.load();
                    g.drawImage(s.getShotIcon(), s.getX(), s.getY(), this);
                }

                if (player.isDuringSpecial()){
                    List<SpecialShot> specialShots = player.getSpecialShots();
                    for (SpecialShot ss : specialShots) {
                        g.drawImage(ss.getPowerShotIcon(), player.getX() + 62, player.getY() + 20, this);
                    }
                }

                displayHearts(g);
                displayStats(g);
            }
            else {
                displayEnemies(g);
                displayHearts(g);
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

    public void enemyInit() {
        int enemyInitial = 1300;
        int totalEnemies = 120;
        int groups = 3;
        int enemiesPerGroup = totalEnemies / groups;
        int groupSpacing = 2500;
        int innerSpacing = groupSpacing / enemiesPerGroup;
        enemy1 = new ArrayList<>();

        for (int i = 0; i < groups; i++) {
            int baseX = enemyInitial + i * groupSpacing;
            for (int j = 0; j < enemiesPerGroup; j++) {
                int x = baseX + j * innerSpacing + (int)(Math.random() * 200 - 100);
                int y = (int)(Math.random() * (720 - 100));
                enemy1.add(new Enemy1(x, y));
            }
        }
    }

    public void heartInit() {
        hearts = new ArrayList<>();
        int groups = 3;
        int groupSpacing = 2500;
        int enemyInitial = 1300;

        for (int i = 1; i <= groups; i++) {
            int x = enemyInitial + i * groupSpacing - 200;
            x += (int)(Math.random() * 200 - 100);
            int y = (int)(Math.random() * (720 - 100));
            hearts.add(new Heart(x, y));
        }
    }

    public void displayEnemies(Graphics g) {
        for (Enemy1 enemy : enemy1) {
            enemy.load();
            g.drawImage(enemy.getEnemyIcon(), enemy.getX(), enemy.getY(), this);
        }
    }

    public void displayHearts(Graphics g) {
        for (Heart heart : hearts) {
            heart.load();
            g.drawImage(heart.getHeartIcon(), heart.getX(), heart.getY(), this);
        }
    }

    public void displayStats(Graphics g){
        g.drawImage(player.getGasIcon(), 10, 10, this);
        g.drawImage(player.getLifeIcon(), 170, 18, this);
        g.drawImage(player.getSpecialBarIcon(),5, 610, this);
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

        for (int j = 0; j < hearts.size(); j++) {
            Heart heart = hearts.get(j);
            if (heart.isVisible()) {
                heart.update();
            } else {
                hearts.remove(j--);
            }
        }

        List<SpecialShot> specialShots = player.getSpecialShots();
        for (SpecialShot ss : specialShots) {
            ss.update();
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
                    enemyDieSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\enemyDie_sound.WAV");
                    kills += 1;
                    player.setKillsVer();
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
                    if (tempEnemy1.getLife() == 2){
                        tempShot.setVisible(false);
                        tempEnemy1.setLife(1);
                    }
                    else{
                        tempEnemy1.setVisible(false);
                        tempShot.setVisible(false);
                        enemyDieSound.playSound("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\enemyDie_sound.WAV");
                        kills += 1;
                        player.setKillsVer();
                    }
                }
            }
        }

        if (player.isDuringSpecial()) {
            List<SpecialShot> specialShots = player.getSpecialShots();
            for (SpecialShot tempSpecialShot : specialShots) {
                Rectangle specialShotShape = tempSpecialShot.getBounds();

                for (Enemy1 tempEnemy1 : enemy1) {
                    Rectangle enemy1Shape = tempEnemy1.getBounds();

                    if (specialShotShape.intersects(enemy1Shape)) {
                        if (tempEnemy1.getLife() == 2) {
                            tempEnemy1.setLife(1);
                        } else {
                            tempEnemy1.setVisible(false);
                            kills += 1;
                        }
                    }
                }
            }
        }

        for (Heart tempHeart : hearts) {
            Rectangle heartShape = tempHeart.getBounds();

            if (naveShape.intersects(heartShape) && (player.getLife() < 3)) {
                player.gainLife();
                tempHeart.setVisible(false);
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
                        player.getSpecialShots().clear();
                        enemy1.clear();
                        hearts.clear();
                        heartInit();
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
