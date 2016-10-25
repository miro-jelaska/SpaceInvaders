import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    public GameWindow() throws InterruptedException {
        this.setSize(300,400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        heroShip = new HeroShip();
        this.add(heroShip);
        this.repaint();
        this.addKeyListener(this);

        while (true){
            if(keyPressed[KeyEvent.VK_D]){
                heroShip.MoveRight();
                this.repaint();
            }
            if(keyPressed[KeyEvent.VK_A]){
                heroShip.MoveLeft();
                this.repaint();
            }

            Thread.sleep(5);
        }
    }
    private HeroShip heroShip;
    private boolean[] keyPressed = new boolean[200];

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
    }
}