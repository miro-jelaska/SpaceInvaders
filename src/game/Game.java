package game;

import actors.HeroShip;
import actors.InvaderProjectile;
import actors.InvaderShip;
import actors.Projectile;
import collision.CollisionDetection;
import collision.CollisionResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;

public class Game extends Canvas implements Runnable {
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 700;
    public static final int INVADER_COLUMN_WIDTH = 50;
    public static final int INVADER_ROW_HEIGHT = 50;
    public static final int INVADER_WINDOW_MARGIN_TOP = 50;
    public static final int INVADER_NEXT_LINE_HEIGHT = 10;

    private static long currentUpdateCount;
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private boolean running = false;

    public final HeroShip heroShip;
    public final List<Projectile> allHeroProjectiles;
    public final List<InvaderShip> allInvaderShips;
    public final List<InvaderProjectile> allInvaderProjectiles;

    private final InputHandler input;
    private final CollisionDetection collisionDetection;
    private final CollisionResolution collisionResolution;


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
        allHeroProjectiles = new ArrayList<Projectile>();
        allInvaderShips = new ArrayList<InvaderShip>();
        for (int row = 0; row < 5; row++)
            for(int column = 0; column < 8; column++)
                allInvaderShips.add(new InvaderShip(row, column));
        allInvaderProjectiles = new ArrayList<InvaderProjectile>();
        this.collisionResolution = new CollisionResolution(this);
        this.collisionDetection = new CollisionDetection(this, this.collisionResolution);
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
        if(input.space.isKeyDown()){
            Projectile maybeProjectile = heroShip.Shoot();
            if(maybeProjectile != null)
                allHeroProjectiles.add(maybeProjectile);
        }

        for(InvaderShip invader: allInvaderShips)
            invader.Update();

        for(Projectile projectile: allHeroProjectiles)
            projectile.Update();

        for(InvaderProjectile projectile: allInvaderProjectiles)
            projectile.Update();

        collisionDetection.Detect();
        collisionResolution.Resolve();
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
        for(Projectile projectile: allHeroProjectiles)
            projectile.paintComponent(g);
        for(InvaderShip invaderShip: allInvaderShips)
            invaderShip.paintComponent(g);
        for(InvaderProjectile projectile: allInvaderProjectiles)
            projectile.paintComponent(g);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args){
        new Game().start();
    }
}