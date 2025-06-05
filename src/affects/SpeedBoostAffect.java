package src.affects;

import java.util.ArrayList;
import java.util.Set;
import src.Particle;
import src.Player;

public class SpeedBoostAffect implements Affect
{
    private Player player;
    private Set<Particle> sleepingParticles;

    private static final int ID = 0;

    private double speedBoost;
    private double turnRate;

    private int affectLength;

    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles)
    {
        this.player = player;
        this.sleepingParticles = sleepingParticles;
    };
    public boolean isDepleted()
    {
        return affectLength == 0;
    };
    public void create(){};
    public void update()
    {
        affectLength--;
        player.speed *= this.speedBoost;
        player.friction = this.turnRate; // 1 - (1 - player.friction) / 2
        if (sleepingParticles.size() != 0)
        {
            Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
            nextParticle.spawn((int) player.getX(), (int) player.getY());
        }
    };
    public void delete(){};
    public void setBoost(double speedBoost, double turnRate, int affectLength)
    {
        this.speedBoost = speedBoost;
        this.turnRate = turnRate;
        this.affectLength = affectLength;
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
