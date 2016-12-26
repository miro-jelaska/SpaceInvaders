package game;

import actors.HeroShip;
import actors.InvaderProjectile;
import actors.InvaderShip;
import actors.HeroProjectile;
import collision.CollisionDetection;
import events.EventResolution;
import events.commands.InvaderShipShoot;
import utilities.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.List;

public class Game extends Canvas implements Runnable, GameTimer {
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 700;
    public static final int INVADER_COLUMN_WIDTH = 50;
    public static final int INVADER_ROW_HEIGHT = 50;
    public static final int INVADER_WINDOW_MARGIN_TOP = 50;
    public static final int INVADER_WINDOW_MARGIN_LEFT = 50;
    public static final int INVADER_NEXT_LINE_HEIGHT = 40;


    private JFrame frame;
    private boolean running = false;
    private int invaderShootingCooldownPeriod = 55;
    private long invaderShootingLastTime = 0;
    private final int INITIAL_SHOOTING_DELAY_BECAOUSE_OF_RENDER_PROBLEMS = 350;

    public int Score = 0;
    public LocalDateTime EndTime;
    public boolean IsGameOver = false;
    public boolean PlayerWon = false;

    public final HeroShip heroShip;
    public final StatusRibbon statusRibbon;
    public final GameOverScreenOverlay gameOverScreenOverlay;
    public final List<HeroProjectile> allHeroProjectiles;
    public final List<InvaderShip> allInvaderShips;

    public final List<InvaderProjectile> allInvaderProjectiles;
    private final long StartTimeInSeconds = java.time.LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    private final InputHandler input;
    private final CollisionDetection collisionDetection;
    private final EventResolution eventResolution;


    public Game(){
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        frame = new JFrame("Space Invaders :: Loading");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        frame.setVisible(true);
        frame.add(this, BorderLayout.CENTER);

        this.input = new InputHandler(this);
        this.statusRibbon = new StatusRibbon(this);
        this.gameOverScreenOverlay = new GameOverScreenOverlay(this);
        this.eventResolution = new EventResolution(this);
        this.heroShip = new HeroShip(this.eventResolution, this);
        this.allHeroProjectiles = new ArrayList<HeroProjectile>();
        this.allInvaderShips = new ArrayList<InvaderShip>();
        for (int row = 0; row < 5; row++)
            for(int column = 0; column < 7; column++)
                this.allInvaderShips.add(new InvaderShip(row, column, this));
        this.allInvaderProjectiles = new ArrayList<InvaderProjectile>();
        this.collisionDetection = new CollisionDetection(this, this.eventResolution);
    }

    public synchronized void start(){
        this.running = true;
        new Thread(this).start();
    }

    public long GetRuntimeInSeconds(){
        return
            this.IsGameOver
            ? this.EndTime.toEpochSecond(ZoneOffset.UTC) - StartTimeInSeconds
            : java.time.LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - StartTimeInSeconds;
    }

    public long GetCurrentUpdateCount(){
        return totalUpdateCount;
    }
    private long totalUpdateCount = 0;


    private static final int MILLIS_IN_SECOND = 1000;
    private static final int MAX_FPS_LAG_AFTER_UPS = 5;
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerUpdate = Math.pow(10D, 9) / 60;
        double delta = 0;

        int framesCount = 0;
        int updatesCount = 0;
        long lastUpsAndFpsReading_inMillis = System.currentTimeMillis();

        while(running){
            long now = System.nanoTime();

            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;
            boolean shouldRender = false;

            // The game is too slow and this here forces at least 1 render for every 5 update.
            if(delta > MAX_FPS_LAG_AFTER_UPS)
                delta = MAX_FPS_LAG_AFTER_UPS;

            while(delta >= 1){
                if(!IsGameOver){
                    processInput();
                    update();

                    updatesCount++;
                    this.totalUpdateCount++;
                }
                delta--;
                shouldRender = true;
            }

            if(shouldRender){
                render();

                framesCount++;
            }

            boolean isReadyToDisplayUpsAndFpsReadings = (System.currentTimeMillis() - lastUpsAndFpsReading_inMillis) > MILLIS_IN_SECOND;
            if(isReadyToDisplayUpsAndFpsReadings){
                lastUpsAndFpsReading_inMillis += MILLIS_IN_SECOND;
                frame.setTitle("Space Invaders (ups: " + updatesCount + " | fps: " + framesCount + ")");
                framesCount = 0;
                updatesCount = 0;
            }
        }
    }
    private void processInput(){
        if(input.right.isKeyDown()){
            heroShip.MoveRight();
        }
        if(input.left.isKeyDown()){
            heroShip.MoveLeft();
        }

        if(input.space.isKeyDown()){
            heroShip.Shoot();
        }
    }

    private void update(){
        for(InvaderShip invader: allInvaderShips)
            invader.Update();

        for(HeroProjectile heroProjectile : allHeroProjectiles)
            heroProjectile.Update();

        for(InvaderProjectile projectile: allInvaderProjectiles)
            projectile.Update();

        boolean isPastInitialDely = (this.GetCurrentUpdateCount() - INITIAL_SHOOTING_DELAY_BECAOUSE_OF_RENDER_PROBLEMS) > 0;
        boolean isPastCooldownTime = (this.GetCurrentUpdateCount() - invaderShootingLastTime) > invaderShootingCooldownPeriod;
        if(isPastInitialDely && isPastCooldownTime){
            eventResolution.Push(new InvaderShipShoot());
            invaderShootingLastTime = this.GetCurrentUpdateCount();
        }

        collisionDetection.Detect();
        eventResolution.Resolve();
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();
        graphics.setColor(new Color(35, 31, 32));
        graphics.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D graphics2D = (Graphics2D) graphics;

        statusRibbon.Paint(graphics2D);
        for(HeroProjectile heroProjectile : allHeroProjectiles)
            heroProjectile.Paint(graphics2D);
        heroShip.Paint(graphics2D);
        for(InvaderProjectile projectile: allInvaderProjectiles)
            projectile.Paint(graphics2D);
        for(InvaderShip invaderShip: allInvaderShips)
            invaderShip.Paint(graphics2D);
        if(this.IsGameOver)
            gameOverScreenOverlay.Paint(graphics2D);

        bs.show();
        graphics2D.dispose();
    }

    public static void main(String[] args){
        new Game().start();
    }
}