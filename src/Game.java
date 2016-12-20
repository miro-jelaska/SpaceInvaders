import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Game extends Canvas implements Runnable {
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 700;
    private static long currentUpdateCount;
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private boolean running = false;
    private BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

    private InputHandler input;
    private HeroShip heroShip;
    private List<Projectile> projectiles;

    public Game(){
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        frame = new JFrame("game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        frame.setVisible(true);
        frame.add(this, BorderLayout.CENTER);
        input = new InputHandler(this);
        heroShip = new HeroShip();
        projectiles = new ArrayList<Projectile>();
    }


    public static long GetCurrentUpateCount(){
        return currentUpdateCount;
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
        this.currentUpdateCount = lastTime;
        double delta = 0;
        while(running){
            long now = System.nanoTime();

            delta+= (now - lastTime) / nsPerUpdate;
            lastTime = now;
            boolean shouldRender = false;

            while(delta >= 1){
                updates++;
                this.currentUpdateCount++;
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
        if(input.right.isKeyDown()){
            heroShip.MoveRight();
        }
        if(input.left.isKeyDown()){
            heroShip.MoveLeft();
        }
        if(input.space.isKeyDown() && input.space.getKeyDownUpdateTime() == 0){
            Projectile maybeProjectile = heroShip.Shoot();
            if(maybeProjectile != null)
                projectiles.add(maybeProjectile);
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
        g.setColor(new Color(35, 31, 32));
        g.fillRect(0, 0, getWidth(), getHeight());

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