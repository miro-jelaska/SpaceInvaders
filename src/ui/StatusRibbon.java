package ui;

import game.Game;
import utilities.GraphicalShape;

import java.awt.*;
import java.awt.geom.Area;

public class StatusRibbon implements GraphicalShape{
    private static final Color COLOR_GRAY = Color.decode("#282828");
    private static final Color COLOR_GREEN = Color.decode("#A6E22E");
    private static final Color COLOR_YELLOW = Color.decode("#E6DB74");
    public static final int HEIGHT = 30;
    public static final int WIDTH = Game.CANVAS_WIDTH;
    private final Game game;
    public StatusRibbon(Game game) {
        this.game = game;
    }

    @Override
    public void Paint(Graphics2D graphics) {
        graphics.setColor(COLOR_GRAY);
        Area background = new Area(new Rectangle(0, 0, Game.CANVAS_WIDTH, HEIGHT));
        graphics.fill(background);
        graphics.setFont(new Font("Consolas", Font.PLAIN, 12));
        graphics.setColor(COLOR_YELLOW);
        graphics.drawString(String.valueOf(game.Score), 20, 20);
        graphics.drawString(GetFormatedTimeFromSeconds(game.GetRuntimeInSeconds()), WIDTH - 50, 20);
        graphics.setColor(COLOR_GREEN);
        graphics.drawString("Space Invaders", WIDTH/2 - 40, 20);
    }
    private String GetFormatedTimeFromSeconds(long seconds){
        String secondPart = (seconds%60) < 10 ? "0" + String.valueOf(seconds%60) : String.valueOf(seconds%60);
        return String.valueOf(seconds/60) + ":" + secondPart;
    }
}
