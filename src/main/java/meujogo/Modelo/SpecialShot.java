package meujogo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SpecialShot {
    private Image powerShotIcon;
    private boolean toggleFrame = false;
    private long lastSwitchTime = 0;
    private final Player player;

    public SpecialShot(Player player) {
        this.player = player;
        load();
    }

    public void load() {
        String imagePath;
        if (toggleFrame){
            imagePath = "/Media/power_shot0.png";
        } else {
            imagePath = "/Media/power_shot1.png";
        }
        powerShotIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath))).getImage();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSwitchTime >= 500) {
            toggleFrame = !toggleFrame;
            load();
            lastSwitchTime = currentTime;
        }
    }

    public Rectangle getBounds() {
        int largura = powerShotIcon.getWidth(null);
        int altura = powerShotIcon.getHeight(null);
        return new Rectangle(player.getX(), player.getY() + 15, largura, altura);
    }

    public Image getPowerShotIcon() {
        return powerShotIcon;
    }
}
