import meujogo.Modelo.Phase;
import javax.swing.JFrame;

public class Container extends JFrame {
    public Container(){
        add(new Phase());
        setTitle("Meu jogo");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false); //mudar para possível depois
        setVisible(true);
    }

    public static void main(String[] args) {
        new Container();
    }
}
