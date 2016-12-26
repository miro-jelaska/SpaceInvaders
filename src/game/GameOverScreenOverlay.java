package game;

import utilities.GraphicalShape;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.Area;

public class GameOverScreenOverlay implements GraphicalShape{
    private final Game game;
    public GameOverScreenOverlay(Game game) {
        this.game = game;
    }

    @Override
    public void Paint(Graphics2D graphics) {
        graphics.setColor(new Color(0, 0, 0, 175));
        Area background = GetGraphicalShape();
        graphics.fill(background);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Arial Black", Font.PLAIN, 25));
        String message = game.PlayerWon ? "YOU WON" : "YOU LOST";
        graphics.drawString(message, Game.CANVAS_WIDTH/2 - 60, (int)(Game.CANVAS_HEIGHT*0.4));
    }


    @Override
    public Area GetGraphicalShape() {
        Area area = new Area(new Rectangle(0, StatusRibbon.HEIGHT, Game.CANVAS_WIDTH, Game.CANVAS_HEIGHT - StatusRibbon.HEIGHT));
        return area;
    }
}
