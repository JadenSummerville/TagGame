package src.Abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import src.Particle;
import src.Player;

public class SpeedBoost implements Ability
{
    public Player player;
    private double cooldown;
    private double activationTimer;
    private Set<Particle> sleepingParticles;
    private Set<Particle> activeParticles;
    private Set<Player> players;

    private boolean boosted = false;

    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players)
    {
        this.player = player;
        this.activeParticles = activeParticles;
        this.sleepingParticles = sleepingParticles;
        this.players = players;
    }
    public void activate()
    {
        if(cooldown == 0)
        {
            boosted = true;
            activationTimer = 500;
        }
    }
    public void idol()
    {
        if(!boosted)
        {
            if(cooldown != 0)
            {
                cooldown--;
            }
            return;
        }
        player.speed *= 2;
        player.friction = player.friction/10+0.9; // 1 - (1 - player.friction) / 2
        if (sleepingParticles.size() != 0)
        {
            Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
            nextParticle.spawn((int) player.getX(), (int) player.getY());
        }
        activationTimer--;
        if(activationTimer == 0)
        {
            boosted = false;
            cooldown = 750;
        }
    }
}
