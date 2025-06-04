package src.affects;

import java.util.Set;
import src.Particle;
import src.Player;

public interface Affect {
    void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles);
    boolean isDepleted();
    void create();
    void update();
    void delete();
}
