package src.obsticles;

import src.Player;
import src.builders.CollisionDetector;
import src.builders.Display;

public class CollisionDetectorObsticle implements  obsticle
{
    public void collide(Player collidingPlayer){}
    public void inform(Display display, CollisionDetector<obsticle> allObsticles){}
}
