package src.Abilities;

import java.util.ArrayList;
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
    private Set<Particle> sleepingParticles;
    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players, CollisionDetector<obsticle> obsticles, Display display)
    {
        cooldown = 0;
        this.player = player;
        this.players = players;
        this.sleepingParticles = sleepingParticles;
    }
    public void activate()
    {
        if(cooldown != 0)
        {
            return;
        }
        if(RANDOM.nextBoolean())
        {
            Stun.stun(player, 150, true);
            for(int i = 0; i != 5; i++)
            {
                if (sleepingParticles.size() != 0)
                {
                    Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
                    nextParticle.spawn((int) player.getX(), (int) player.getY());
                }
            }
        }
        else
        {
            for(Player p: players)
            {
                if(p != player)
                {
                    Stun.stun(p, 250, false);
                    for(int i = 0; i != 20; i++)
                    {
                        if (sleepingParticles.size() != 0)
                        {
                            Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
                            nextParticle.spawn((int) p.getX(), (int) p.getY());
                        }
                    }
                }
            }
        }
        cooldown = 250;
    }
    public void idol()
    {
        if(cooldown != 0)
        {
            cooldown--;
        }
    }
    public void tag()
    {
        cooldown = 0;
    }
}
