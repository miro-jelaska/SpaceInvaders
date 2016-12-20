import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public InputHandler(Game game){
        game.addKeyListener(this);
    }

    public class Key{
        private boolean isKeyDown = false;
        private int keyDownUpdateTime = 0;
        public void toggle(boolean isKeyDown){
            if(this.isKeyDown && isKeyDown)
                keyDownUpdateTime++;
            else
                keyDownUpdateTime = 0;
            this.isKeyDown = isKeyDown;
        }
        public boolean isKeyDown(){
            return isKeyDown;
        }
        public int getKeyDownUpdateTime(){
            return keyDownUpdateTime;
        }
    }

    public Key left = new Key();
    public Key right = new Key();
    public Key space = new Key();

    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void toggleKey(int keyCode, boolean isPressed){
        if(keyCode == KeyEvent.VK_D)
            right.toggle(isPressed);
        if(keyCode == KeyEvent.VK_A)
            left.toggle(isPressed);
        if(keyCode == KeyEvent.VK_SPACE)
            space.toggle(isPressed);
    }
}