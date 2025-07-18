package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Heart {
    private Image heartIcon;
    private int x, y;
    private int largura, altura;
    private boolean isVisible;
    private int velocidade = 3;

    public Heart(int x, int y) {
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public void load() {
        heartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Media/heart.png"))).getImage();

        this.largura = heartIcon.getWidth(null);
        this.altura = heartIcon.getHeight(null);
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, largura, altura);
    }

    public void update() {
        this.x -= velocidade;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Image getHeartIcon() {
        return heartIcon;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
