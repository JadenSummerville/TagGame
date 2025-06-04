package src.affects;

import java.util.Set;

import src.Particle;
import src.Player;

public class Stun implements Affect
{
    private Player player;
    private static final int ID = 1;
    private int stunTime;
    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles)
    {
        this.player = player;
    }
    public boolean isDepleted()
    {
        return stunTime == 0;
    }
    public void create(){}
    public void update()
    {
        stunTime--;
        player.speed *= 0.01;
    }
    public void delete(){}
    public void setStunTime(int stunTime)
    {
        this.stunTime = stunTime;
    }
    public static void stun(Player playerToBeStunned, int stunLength)
    {
        Stun stun = new Stun();
        stun.inform(playerToBeStunned, null, null);
        stun.setStunTime(stunLength);
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
