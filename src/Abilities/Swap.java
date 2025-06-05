package src.Abilities;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import src.Particle;
import src.Player;
import src.builders.CollisionDetector;
import src.builders.Display;
import src.obsticles.obsticle;

public class Swap implements Ability
{
    private Player player;
    private final static Random RANDOM = new Random();
    private double cooldown;
    private Set<Particle> sleepingParticles;
    private Set<Player> players;
    private final static int NUM_OF_PARTILES = 10;

    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players, CollisionDetector<obsticle> obsticles, Display display)
    {
        this.player = player;
        this.sleepingParticles = sleepingParticles;
        this.players = players;
    }
    public void activate()
    {
        if(cooldown == 0)
        {
            Player closestPlayer = player;
            double closest = Double.MAX_VALUE;
            for(Player currentPlayer: players)
            {
                if(currentPlayer.equals(player))
                {
                    continue;
                }
                double distance = Math.pow(Math.pow(currentPlayer.getX()-player.getX(), 2)+Math.pow(currentPlayer.getY()-player.getY(), 2), 0.5);
                if (distance < closest)
                {
                    closest = distance;
                    closestPlayer = currentPlayer;
                }
            }
            double o = closestPlayer.getX();
            closestPlayer.setX(player.getX());
            player.setX(o);

            o = closestPlayer.getY();
            closestPlayer.setY(player.getY());
            player.setY(o);

            for(int i = 0; i != NUM_OF_PARTILES; i++)
            {
                if (sleepingParticles.size() != 0)
                {
                    Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
                    nextParticle.spawn((int) player.getX(), (int) player.getY());
                }
                if (sleepingParticles.size() != 0)
                {
                    Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
                    nextParticle.spawn((int) closestPlayer.getX(), (int) closestPlayer.getY());
                }
            }
            if(player.isTagged())
            {
                cooldown = 100 + RANDOM.nextInt(60);
            }
            else
            {
                cooldown = 100 + RANDOM.nextInt(500);
            }
        }
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
    public void tagged()
    {
        if(cooldown > 500)
        {
            cooldown = 500;
        }
    }
}
