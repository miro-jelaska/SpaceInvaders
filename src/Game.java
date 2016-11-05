import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.*;
import java.util.List;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private boolean running = false;
    private BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private InputHandler input;
    private HeroShip heroShip;
    private List<Projectile> projectiles;

    public Game(){
        setPreferredSize(new Dimension(500, 500));

        frame = new JFrame("game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(500, 500));
        frame.setVisible(true);
        frame.add(this, BorderLayout.CENTER);
        input = new InputHandler(this);
        heroShip = new HeroShip();
        projectiles = new ArrayList<Projectile>();
    }



    public synchronized void start(){
        running = true;
        new Thread(this).start();
    }


    public void run() {
        long lastTime = System.nanoTime();
        double nsPerUpdate = Math.pow(10D, 9) / 60;

        int frames = 0;
        int updates = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        while(running){
            long now = System.nanoTime();
            delta+= (now - lastTime) / nsPerUpdate;
            lastTime = now;
            boolean shouldRender = false;

            while(delta >= 1){
                updates++;
                update();
                delta--;
                shouldRender = true;
            }

            if(shouldRender){
                frames++;
                render();
            }

            if(System.currentTimeMillis() - lastTimer > 1000){
                lastTimer += 1000;
                frame.setTitle("SpaceInviders (ups: " + updates + " | fps: " + frames + ")");
                frames = 0;
                updates = 0;
            }
        }
    }

    public void update(){
        for(int i=0; i < pixels.length; i++){
            pixels[i]=(int)(Math.random()* Integer.MAX_VALUE);
        }

        if(input.right.isPressed()){
            heroShip.MoveRight();
        }
        if(input.left.isPressed()){
            heroShip.MoveLeft();
        }
        if(input.space.isPressed()){
            projectiles.add(heroShip.Shoot());
        }

        for(Projectile projectile: projectiles)
            projectile.Update();
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        heroShip.paintComponent(g);
        for(Projectile projectile: projectiles)
            projectile.paintComponent(g);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args){
        new Game().start();
    }
}
