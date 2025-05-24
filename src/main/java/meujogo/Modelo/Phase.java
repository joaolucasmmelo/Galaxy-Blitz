package meujogo.Modelo;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Phase extends JPanel implements ActionListener {
    private final Image background;
    private int x1, x2;
    private final Player player;

    public Phase() {
        setFocusable(true);
        setDoubleBuffered(true);

        background = new ImageIcon("D:\\\\Java\\\\Projects\\\\Galaxy Blitz\\\\src\\\\Media\\\\background.png").getImage();
        player = new Player();
        player.load();

        addKeyListener(new TecladoAdapter());

        x1 = 0;
        x2 = background.getWidth(null);

        Timer timer = new Timer(15, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(background, x1, 0, this);
        g.drawImage(background, x2, 0, this);
        g.drawImage(player.getPlayerIcon(), player.getX(), player.getY(), this);

        player.update();
        List<Shot> shots = player.getShots();
        for (int i =0; i < shots.size(); i++){
            Shot s = shots.get(i);
            s.load();
            g.drawImage(s.getShotIcon(), s.getX(), s.getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 5;
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

        repaint();
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
