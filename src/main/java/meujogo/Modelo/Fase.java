package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Fase extends JPanel implements ActionListener {
    private Image background;
    private int x1, x2;
    private int speed = 5;
    private Timer timer;
    private Player player;

    public Fase() {
        setFocusable(true);
        setDoubleBuffered(true);

        background = new ImageIcon("D:\\\\Java\\\\Projects\\\\Galaxy Blitz\\\\src\\\\Media\\\\background.png").getImage();
        player = new Player();
        player.load();

        addKeyListener(new TecladoAdapter());

        x1 = 0;
        x2 = background.getWidth(null);

        timer = new Timer(15, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, x1, 0, this);
        g.drawImage(background, x2, 0, this);
        g.drawImage(player.getPlayerIcon(), player.getX(), player.getY(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x1 -= speed;
        x2 -= speed;

        if (x1 + background.getWidth(null) <= 0) {
            x1 = x2 + background.getWidth(null);
        }
        if (x2 + background.getWidth(null) <= 0) {
            x2 = x1 + background.getWidth(null);
        }

        player.update();

        repaint();
    }

    private class TecladoAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent tecla){
            player.keyPressed(tecla);
        }
        @Override
        public void keyReleased(KeyEvent tecla){
            player.keyReleased(tecla);
        }
    }
}
