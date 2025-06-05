package src.affects;

import java.util.Set;
import src.Particle;
import src.Player;

public class Stun implements Affect
{
    private Player player;
    private static final int ID = 1;
    private int stunTime;
    private boolean grantsStunImmunity;
    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles)
    {
        this.player = player;
        this.grantsStunImmunity = true;
    }
    public void noStunImmunity()
    {
        this.grantsStunImmunity = false;
    }
    public boolean isDepleted()
    {
        return stunTime == 0;
    }
    public void create()
    {
        player.addReasonToBeStunImmune();
    }
    public void update()
    {
        stunTime--;
        player.speed *= 0.01;
    }
    public void delete()
    {
        player.removeReasonToBeStunImmune();
        if(this.grantsStunImmunity)
        {
            StunImmunity stunImmunity = new StunImmunity();
            stunImmunity.inform(player, null, null);
            player.addAffect(stunImmunity);
        }
    }
    public void setStunTime(int stunTime)
    {
        this.stunTime = stunTime;
    }
    public static void stun(Player playerToBeStunned, int stunLength, boolean noStunImmunity)
    {
        Stun stun = new Stun();
        stun.inform(playerToBeStunned, null, null);
        stun.setStunTime(stunLength);
        if(noStunImmunity)
        {
            stun.noStunImmunity();
        }
        playerToBeStunned.addAffect(stun);
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof SpeedBoostAffect;
    }
    @Override
    public int hashCode() {
        return ID;
    }
}
