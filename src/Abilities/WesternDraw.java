package src.Abilities;

import java.util.Random;
import java.util.Set;

import src.Particle;
import src.Player;
import src.affects.Stun;
import src.builders.CollisionDetector;
import src.builders.Display;
import src.obsticles.obsticle;

public class WesternDraw implements Ability
{
    private static final Random RANDOM = new Random();
    private int cooldown;
    private Player player;
    private Set<Player> players;
    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players, CollisionDetector<obsticle> obsticles, Display display)
    {
        cooldown = 0;
        this.player = player;
        this.players = players;
    }
    public void activate()
    {
        if(cooldown != 0)
        {
            return;
        }
        if(RANDOM.nextBoolean())
        {
            Stun.stun(player, 150);
        }
        else
        {
            for(Player p: players)
            {
                if(p != player)
                {
                    Stun.stun(p, 250);
                }
            }
        }
        cooldown = 111;
    }
    public void idol()
    {
        if(cooldown != 0)
        {
            cooldown--;
        }
    }
}
