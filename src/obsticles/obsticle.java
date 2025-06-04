package src.obsticles;

import src.Player;
import src.builders.CollisionDetector;
import src.builders.Display;

public interface obsticle
{
    void collide(Player collidingPlayer);
    void inform(Display display, CollisionDetector<obsticle> allObsticles);
}
