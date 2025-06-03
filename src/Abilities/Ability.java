package src.Abilities;

import java.util.Set;
import src.Particle;
import src.Player;

public interface Ability {
    void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players);
    void activate();
    void idol();
}