package utilities;

import java.awt.*;
import java.awt.geom.Area;

public interface GraphicalShape {
    void Paint(Graphics2D graphics);
    Area GetGraphicalShape();
}
