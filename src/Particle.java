package src;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.JLabel;
import src.builders.Display;
import src.builders.Ticker;

public class Particle extends Ticker.Entity
{
    private static final Random RANDOM = new Random();
    private static Display display;
    private static Set<Particle> activeParticles = new HashSet<>();
    private static Set<Particle> sleepingParticles = new HashSet<>();
    private final JLabel image;
    private final int id;
    private static int numOfParticles = 0;
    
    private double x = 0;
    private double y = 0;

    private double xV = 0;
    private double yV = 0;

    private long timer = 0;

    public Particle()
    {
        image = display.addImage("./images/spark.png", 10, 10, 10, 10);
        image.setVisible(false);
        id = numOfParticles;
        numOfParticles++;
    }
    public static void setDisplay(Display display, Set<Particle> activeParticles, Set<Particle> sleepingParticles)
    {
        Particle.display = display;
        Particle.activeParticles = activeParticles;
        Particle.sleepingParticles = sleepingParticles;
    }
    @Override
    public void update() {
        if(sleepingParticles.contains(this))
        {
            return;
        }
        if(timer == 0)
        {
            image.setVisible(false);
            sleepingParticles.add(this);
            activeParticles.remove(this);
        }
        else
        {
            timer--;
            this.x += xV;
            this.y += yV;
            image.setLocation((int) x, (int) y);
        }
    }
    public void spawn(int x, int y, int duration)
    {
        if(activeParticles.contains(this))
        {
            return;
        }
        this.x = x;
        this.y= y;
        image.setLocation((int) x, (int) y);
        image.setVisible(true);
        xV = 10*RANDOM.nextDouble() - 5;
        yV = 10*RANDOM.nextDouble() - 5;
        timer = (long)(RANDOM.nextInt(100)*Math.pow(2.0, RANDOM.nextDouble(2)))*duration;
        sleepingParticles.remove(this);
        activeParticles.add(this);
    }
    public void spawn(int x, int y)
    {
        spawn(x + 50, y + 50, 1);
    }
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Particle))
        {
            return false;
        }
        Particle other = (Particle)obj;
        return other.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
    
}
