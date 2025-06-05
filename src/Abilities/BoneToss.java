package src.Abilities;

import java.util.ArrayList;
import java.util.Set;
import javax.swing.JLabel;
import src.Particle;
import src.Player;
import src.builders.CollisionDetector;
import src.builders.Display;
import src.obsticles.TossedBones;
import src.obsticles.obsticle;

public class BoneToss implements Ability
{
    private Player player;
    private Set<Particle> sleepingParticles;
    private Set<Particle> activeParticles;
    private CollisionDetector<obsticle> obsticles;
    private Display display;

    private double xV;
    private double yV;

    private final static double XOFFSET = -10;
    private final static double YOFFSET = -10;

    private final static double XCOLLIDEOFFSET = 130;
    private final static double YCOLLIDEOFFSET = 130;

    private double x;
    private double y;

    private int cooldown;
    private int fireTime;
    private boolean shooting;

    private JLabel boneAttackImage;
    private TossedBones tossedBones;

    public void inform(Player player, Set<Particle> sleepingParticles, Set<Particle> activeParticles, Set<Player> players, CollisionDetector<obsticle> obsticles, Display display)
    {
        this.player = player;
        this.sleepingParticles = sleepingParticles;
        this.activeParticles = activeParticles;
        this.obsticles = obsticles;
        this.display = display;
        cooldown = 0;
        fireTime = 0;

        shooting = false;

        this.boneAttackImage = display.addImage("./images/Bone_Attack.png", 100, 100, 0, 0);
        boneAttackImage.setVisible(false);
    }
    public void activate()
    {
        if(cooldown != 0)
        {
            return;
        }
        x = player.getX();
        y = player.getY();
        xV = player.getXV();
        yV = player.getYV();
        double hyp = Math.pow(xV*xV+yV*yV, 0.5);
        if(hyp == 0)
        {
            return;
        }
        xV = xV/hyp*7;
        yV = yV/hyp*7;
        shooting = true;
        this.boneAttackImage = display.rotateLabel(boneAttackImage, player.getXV(), player.getYV(), (int)(x + XOFFSET), (int)(y + YOFFSET));
        tossedBones = new TossedBones();
        tossedBones.inform(display, obsticles);
        tossedBones.imune(player);
        obsticles.add(tossedBones, (int)(player.getX() + XCOLLIDEOFFSET), (int)(player.getY() + YCOLLIDEOFFSET));
        cooldown = 500;//TODO choose an appropriate value. 500?
        fireTime = 150;
        boneAttackImage.setVisible(true);
    }
    public void idol()
    {
        if(shooting)
        {
            x += xV;
            y += yV;
            tossedBones.setPosition((int)x, (int)y);
            display.rotateLabel(boneAttackImage, xV, yV, (int)(x + XOFFSET), (int)(y + YOFFSET));
            if(x > 0 && y > 0)
            {
                obsticles.move(tossedBones, (int)(x + XCOLLIDEOFFSET), (int)(y + YCOLLIDEOFFSET));
            }
            else if(obsticles.contains(tossedBones))
            {
                obsticles.remove(tossedBones);
            }
            if (sleepingParticles.size() != 0)
            {
                Particle nextParticle = new ArrayList<>(sleepingParticles).get(0);
                nextParticle.spawn((int) x, (int) y);
            }
        }
        if(fireTime == 0)
        {
            shooting = false;
            this.boneAttackImage = display.rotateLabel(boneAttackImage, player.getXV(), player.getYV(), -300, -300);
            if(obsticles.contains(tossedBones))
            {
                obsticles.remove(tossedBones);
            }
        }
        else
        {
            fireTime--;
        }
        if(cooldown != 0)
        {
            cooldown--;
        }
    }
}
