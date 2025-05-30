import meujogo.Modelo.Phase;
import meujogo.Modelo.SoundPlayer;
import javax.swing.JFrame;

public class Container extends JFrame {
    SoundPlayer music = new SoundPlayer();

    public Container(){
        add(new Phase());
        setTitle("Galaxy Blitz");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false); // Ver depois
        setVisible(true);
        music.playLoop("D:\\Java\\Projects\\Galaxy Blitz\\src\\Media\\sounds\\sound_track.WAV");
    }

    public static void main(String[] args) {
        new Container();
    }
}
