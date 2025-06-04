package src.Abilities;

import java.util.Set;
import src.Particle;
import src.Player;
import src.builders.CollisionDetector;
import src.builders.Display;
import src.obsticles.obsticle;

public interface Ability {
    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players, CollisionDetector<obsticle> obsticles, Display display);
    public void activate();
    public void idol();
}