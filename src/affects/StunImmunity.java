package src.affects;

import java.util.Set;

import src.Particle;
import src.Player;

public class StunImmunity implements Affect
{
    private Player player;
    private int cooldown = 0;
    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles)
    {
        this.player = player;
        this.cooldown = 250;
    }
    public boolean isDepleted()
    {
        return this.cooldown == 0;
    }
    public void create()
    {
        player.addReasonToBeStunImmune();
    }
    public void update()
    {
        this.cooldown--;
    }
    public void delete()
    {
        player.removeReasonToBeStunImmune();
    }
}
