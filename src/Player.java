package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import src.Abilities.Ability;
import src.Abilities.SpeedBoost;
import src.Abilities.Swap;
import src.builders.CollisionDetector;
import src.builders.Display;
import src.builders.Ticker;

public class Player extends Ticker.Entity
{
    private static final Set<Player> allPlayers = new HashSet<>();
    private static final double SPEED = 2;
    private static final double FRICTION = .9;
    public double speed = 1;
    public double friction = .9;

    private final int id;

    private static Display display;

    private final Map<Integer, Ability> abilities = new HashMap<>();

    private double x;
    private double y;

    private double xV;
    private double yV;

    private final int up;
    private final int right;
    private final int down;
    private final int left;

    private boolean tagged;
    private final JLabel untaggedImage;
    private static JLabel taggedImage;

    private static final int TAG_RADIUS = 80;

    private static CollisionDetector<Player> collisionDetectorPlayers;

    private double timeAsIt = -0.1;
    private double tagInvinsibility = 4;
    private int reasonsToBeInvisible = 0;

    public Player(int x, int y, int up, int right, int down, int left, JLabel untaggedImage)
    {
        this.x = x;
        this.y = y;

        xV = 0;
        yV = 0;

        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;

        this.untaggedImage = untaggedImage;
        this.tagged = false;

        id = allPlayers.size();
        allPlayers.add(this);

        collisionDetectorPlayers.add(this, x, y);
        display.setZOrder(untaggedImage, 0);
    }

    public static void setDisplay(Display display, JLabel taggedImage, CollisionDetector<Player> collisionDetectorPlayers)
    {
        Player.display = display;
        Player.taggedImage = taggedImage;
        Player.collisionDetectorPlayers = collisionDetectorPlayers;

        display.setZOrder(taggedImage, 0);
    }

    public void update()
    {
        speed = SPEED;
        friction = FRICTION;

        for(Integer ability: abilities.keySet())
        {
            if (display.keyBoard.isPressed(ability))
            {
                abilities.get(ability).activate();
            }
            abilities.get(ability).idol();
        }
        // initiate adjusted speed and friction
        double adjustedSpeed = speed * (1 + timeAsIt) * (tagInvinsibility > 0 && !tagged ? 2 : 1);

        // TODO: add ability adjust to speed and friction
        // initiate adjusted speed and friction
        double yM = display.keyBoard.isPressed(up) ? -1.0 : 0.0;
        yM += (display.keyBoard.isPressed(down) ? 1.0 : 0.0);
        double xM = display.keyBoard.isPressed(right) ? 1 : 0;
        xM += (display.keyBoard.isPressed(left) ? -1 : 0);
        
        double hy = Math.pow(yM*yM + xM*xM, 0.5);
        if (hy != 0)
        {
            yM /= hy;
            xM /= hy;
            xV += xM * (1 - friction);
            yV += yM * (1 - friction);
        }

        x += xV*adjustedSpeed;
        y += yV*adjustedSpeed;

        collisionDetectorPlayers.move(this, (int)x, (int)y);
        Set<Player> collidedPlayers = collisionDetectorPlayers.findCollisions(this, TAG_RADIUS);

        xV *= friction;
        yV *= friction;

        if(tagged)
        {
            taggedImage.setLocation((int)x, (int)y);
            timeAsIt += 0.00002;
            for(Player tagged: collidedPlayers)
            {
                if (tagged.canBeTaggged())
                {
                    tagged.tag();
                    this.untag();
                    break;
                }
            }
        }
        else
        {
            untaggedImage.setLocation((int)x, (int)y);
            tagInvinsibility -= 0.01;
            if(tagInvinsibility < 0)
            {
                tagInvinsibility = 0;
            }
        }
    }
    public void tag()
    {
        tagged = true;
        untaggedImage.setLocation((int)x, (int)y);
        untaggedImage.setVisible(false);
        timeAsIt = -0.1;
    }
    public void untag()
    {
        tagged = false;
        untaggedImage.setVisible(true);
        timeAsIt = 0;
        tagInvinsibility = 4;
    }
    public boolean isTagged()
    {
        return tagged;
    }
    public boolean canBeTaggged()
    {
        return !this.tagged && this.tagInvinsibility == 0;
    }
    public double getX()
    {
        return this.x;
    }
    public double getY()
    {
        return this.y;
    }
    public void setX(double x)
    {
        this.x = x;
        adjustPosition();
    }
    public void setY(double y)
    {
        this.y = y;
        adjustPosition();
    }
        public double getXV()
    {
        return this.xV;
    }
    public double getYV()
    {
        return this.yV;
    }
    public void setXV(double xV)
    {
        this.xV = xV;
    }
    public void setYV(double yV)
    {
        this.yV = yV;
    }
    private void adjustPosition()
    {
        if (tagged)
        {
            taggedImage.setLocation((int)x, (int)y);
        }
        else
        {
            untaggedImage.setLocation((int)x, (int)y);
        }
        collisionDetectorPlayers.move(this, (int)x, (int)y);
    }
    public void addAbility(int abilityId, int button, Set<Particle> activeParticles, Set<Particle> sleepingParticles)
    {
        Ability ability;
        switch(abilityId)
        {
            case 0:
            case 2:
                ability = new SpeedBoost();
                break;
            case 1:
                ability = new Swap();
                break;
            default:
                throw new RuntimeException("Unidentified ability");
        }
        ability.inform(this, sleepingParticles, activeParticles, allPlayers);
        abilities.put(button, ability);
    }
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Player))
        {
            return false;
        }
        Player other = (Player)obj;
        return other.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
    
}
