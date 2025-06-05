package src.obsticles;

import java.util.HashSet;
import java.util.Set;
import src.Player;
import src.affects.Stun;
import src.builders.CollisionDetector;
import src.builders.Display;

public class TossedBones implements obsticle {
    private Display display;
    private CollisionDetector<obsticle> allObsticles;
    private int x;
    private int y;
    private Set<Player> parent;
    public void collide(Player collidingPlayer)
    {
        double hyp = Math.pow(Math.pow(collidingPlayer.getX() - x, 2) + Math.pow(collidingPlayer.getY() - y, 2), 0.5);
        if(hyp > 140 || parent.contains(collidingPlayer))
        {
            return;
        }
        Stun.stun(collidingPlayer, 300, false);
    }
    public void inform(Display display, CollisionDetector<obsticle> allObsticles)
    {
        this.display = display;
        this.allObsticles = allObsticles;
    }
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void imune(Player... players)
    {
        parent = new HashSet<>();
        for(Player p: players)
        {
            parent.add(p);
        }
    }
}
