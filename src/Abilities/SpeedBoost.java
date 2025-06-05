package src.Abilities;

import java.util.Set;
import src.Particle;
import src.Player;
import src.affects.SpeedBoostAffect;
import src.builders.CollisionDetector;
import src.builders.Display;
import src.obsticles.obsticle;

public class SpeedBoost implements Ability
{
    public Player player;
    private double cooldown;
    private Set<Particle> sleepingParticles;
    private Set<Particle> activeParticles;

    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players, CollisionDetector<obsticle> obsticles, Display display)
    {
        this.player = player;
        this.activeParticles = activeParticles;
        this.sleepingParticles = sleepingParticles;
    }
    public void activate()
    {
        if(cooldown == 0)
        {
            SpeedBoostAffect speedBoostAffect = new SpeedBoostAffect();
            speedBoostAffect.inform(player, sleepingParticles, activeParticles);
            if(player.isTagged())
            {
                speedBoostAffect.setBoost(4.5, player.friction/10+0.9, 550);
            }
            else
            {
                speedBoostAffect.setBoost(4.5, player.friction/10+0.9, 350);
            }
            player.addAffect(speedBoostAffect);
            cooldown = 2050;
        }
    }
    public void idol()
    {
        if(cooldown == 0)
        {
            return;
        }
        cooldown--;
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
