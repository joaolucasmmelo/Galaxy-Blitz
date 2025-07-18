package meujogo.Modelo;

import javax.sound.sampled.*;
import java.io.InputStream;

public class SoundPlayer {
    private Clip clip;

    public void playSound(String path) {
        try {
            InputStream audioStream = getClass().getResourceAsStream(path);
            if (audioStream == null) {
                System.err.println("Arquivo de áudio não encontrado: " + path);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioStream);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            System.err.println("Erro ao reproduzir som: " + path);
            e.printStackTrace();
        }
    }

    public void playLoop(String path) {
        try {
            InputStream audioStream = getClass().getResourceAsStream(path);
            if (audioStream == null) {
                System.err.println("Arquivo de áudio não encontrado: " + path);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioStream);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println("Erro ao reproduzir som em loop: " + path);
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}