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
    private final Image background, gameover;
    private int x1, x2;
    private final Player player;
    private List<Enemy1> enemy1;
    private boolean inGame;

    public Phase() {
        setFocusable(true);
        setDoubleBuffered(true);

        inGame = true;
        background = new ImageIcon("D:\\\\Java\\\\Projects\\\\Galaxy Blitz\\\\src\\\\Media\\\\background.png").getImage();
        gameover = new ImageIcon("D:\\\\Java\\\\Projects\\\\Galaxy Blitz\\\\src\\\\Media\\\\game_over.png").getImage();
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
        enemy1 = new ArrayList<Enemy1>();

        for (int i : enemysQuant) {
            int x = (int)(Math.random() * 8500 + 1280);
            int y = (int)(Math.random() * 680 + 100);
            enemy1.add(new Enemy1(x, y));
        }
    }

    @Override
    public void paint(Graphics g) {
        if (inGame){
            g.drawImage(background, x1, 0, this);
            g.drawImage(background, x2, 0, this);

            g.drawImage(player.getBoostIcon(), player.getX() - 55, player.getY() - 25, this);
            g.drawImage(player.getPlayerIcon(), player.getX(), player.getY(), this);

            g.drawImage(player.getGasIcon(), 10, 10, this);
            g.drawImage(player.getLifeIcon(), 170, 18, this);

            player.update();
            List<Shot> shots = player.getShots();
            for (Shot s : shots) {
                s.load();
                g.drawImage(s.getShotIcon(), s.getX(), s.getY(), this);
            }

            for (int k = 0; k < enemy1.size(); k++){
                Enemy1 enemy = enemy1.get(k);
                enemy.load();
                g.drawImage(enemy.getEnemyIcon(), enemy.getX(), enemy.getY(), this);
            }
        }
        else {
            g.drawImage(background, x1, 0, this);
            g.drawImage(background, x2, 0, this);
            g.drawImage(gameover, 0, 0, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed;
        if (player.getBoostAtivo()){
            speed = 12;
        }
        else {
            speed = 5;
        }

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
        for (int i =0; i < shots.size(); i++){
            Shot s = shots.get(i);
            if (s.isVisible()){
                s.update();
            }
            else {
                shots.remove(i);
            }
        }

        for (int k = 0; k < enemy1.size(); k++){
            Enemy1 enemy = enemy1.get(k);
            if (enemy.isVisible()){
                enemy.update();
            }
            else {
                enemy1.remove(k);
            }
        }

        colideCheck();
        repaint();
    }

    public void colideCheck(){
        Rectangle naveShape = player.getBounds();
        Rectangle Enemy1Shape;
        Rectangle shotShape;

        for (int i = 0; i < enemy1.size(); i++){
            Enemy1 tempEnemy1 = enemy1.get(i);
            Enemy1Shape = tempEnemy1.getBounds();

            if (naveShape.intersects(Enemy1Shape)){
                player.lostLife();
                if (player.getLife() == 0){
                    inGame = false;
                }
            }
        }

        List<Shot> shots = player.getShots();
        for (int j = 0; j < shots.size(); j++) {
            Shot tempShot = shots.get(j);
            shotShape = tempShot.getBounds();

            for (int k = 0; k < enemy1.size(); k++) {
                Enemy1 tempEnemy1 = enemy1.get(k);
                Rectangle enemy1Shape = tempEnemy1.getBounds();

                if (shotShape.intersects(enemy1Shape)) {
                    tempEnemy1.setVisible(false);
                    tempShot.setVisible(false);
                }
            }
        }
    }

    private class TecladoAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent tecla){
            player.keyPressed(tecla);
        }

        public void keyReleased(KeyEvent tecla){
            player.keyReleased(tecla);
        }
    }
}
