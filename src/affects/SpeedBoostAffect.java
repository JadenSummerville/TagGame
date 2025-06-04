package src.affects;

import java.util.ArrayList;
import java.util.Set;

import src.Particle;
import src.Player;

public class SpeedBoostAffect implements Affect
{
    private Player player;
    private Set<Particle> sleepingParticles;
    private Set<Particle> activeParticles;

    private static final int ID = 0;

    private int affectLength = 600;

    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles)
    {
        this.player = player;
        this.sleepingParticles = sleepingParticles;
        this.activeParticles = activeParticles;
    };
    public boolean isDepleted()
    {
        return affectLength == 0;
    };
    public void create(){};
    public void update()
    {
        affectLength--;
        if(!player.isTagged() && affectLength != 0)
        {
            affectLength--;
        }
        player.speed *= 2;
        player.friction = player.friction/10+0.9; // 1 - (1 - player.friction) / 2
        if (sleepingParticles.size() != 0)
        {
            Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
            nextParticle.spawn((int) player.getX(), (int) player.getY());
        }
    };
    public void delete(){};

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SpeedBoostAffect;
    }
    @Override
    public int hashCode() {
        return ID;
    }
}
