package src.obsticles;

import java.util.Random;

import javax.swing.JLabel;
import src.Player;
import src.builders.CollisionDetector;
import src.builders.Display;

public class Rocko implements obsticle{
    private static final Random RANDOM = new Random();
    private Display display;
    private CollisionDetector<obsticle> allObsticles;
    private JLabel image;

    private int x;
    private int y;

    private final double size = 90*RANDOM.nextDouble() + 10;

    public void collide(Player collidingPlayer)
    {
        if(touching(collidingPlayer.getX(), collidingPlayer.getY(), collidingPlayer.TAG_RADIUS/2))
        {
            double angle = getAngle(x, y, (int)collidingPlayer.getX(), (int)collidingPlayer.getY());
            int[] nextPosition = getPointAtAngle(angle, collidingPlayer.TAG_RADIUS/2);
            collidingPlayer.setX(nextPosition[0]);
            collidingPlayer.setY(nextPosition[1]);
        }
    }
    public void inform(Display display, CollisionDetector<obsticle> allObsticles)
    {
        this.display = display;
        this.image = display.addImage("./images/rocko.png", (int)(size * 2.5), (int)(size * 2.5), 0, 0);
        this.allObsticles = allObsticles;
    }
    public boolean touching(double x, double y, double sizeOfOther)
    {
        double distance = Math.pow(Math.pow(x-this.x, 2) + Math.pow(y-this.y, 2), 0.5);
        return distance < size + sizeOfOther;
    }
    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
        image.setLocation((int)(x + 40. - 1.3*(size - 10)), (int)(y + 40. - 1.3*(size - 10)));
        allObsticles.move(this, x, y);
    }
    private static double getAngle(int x1, int y1, int x2, int y2) {
    int dx = x2 - x1;
    int dy = y2 - y1;

    if (dx == 0 && dy == 0) {
        return 0.0; // Arbitrary angle when points are the same
    }

    return Math.atan2(dy, dx);
    }

    private int[] getPointAtAngle(double angle, double otherSize) {
    int x2 = (int) Math.round(x + Math.cos(angle) * (size + otherSize));
    int y2 = (int) Math.round(y + Math.sin(angle) * (size + otherSize));
    return new int[] { x2, y2 };
    }
}
