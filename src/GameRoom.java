package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JLabel;
import src.builders.CollisionDetector;
import src.builders.Display;
import src.builders.Ticker;

public class GameRoom
{
    private static Display display = new Display();
    private static Ticker ticker = new Ticker();
    private static final CollisionDetector<Player> collisionDetector = new CollisionDetector<>(4000, 2000);
    private static final Random RANDOM = new Random();

    private static int numOfPlayers;
    private static int numOfAbilities;
    private static final String FILE_NAME = "tag";

    private static Set<Particle> activeParticles = new HashSet<>();
    private static Set<Particle> sleepingParticles = new HashSet<>();

    private static JLabel tagged;

    private static final List<Player> players = new ArrayList<>();
    public static void main(String[] args) {
        // Read number of players and abilities
        numOfPlayers = Integer.parseInt(readAndDeleteFirstLine());
        numOfAbilities = Integer.parseInt(readAndDeleteFirstLine());

        // Set partciles
        Particle.setDisplay(display, activeParticles, sleepingParticles);
        for(int i = 0; i != 100; i++)
        {
            Particle particle = new Particle();
            sleepingParticles.add(particle);
            ticker.addEntity(particle);
        }
        
        // Spawn players
        tagged = display.addImage("./images/tagged.png", 100, 100, 100, 100);
        Player.setDisplay(display, tagged, collisionDetector);
        for(int i = 0; i != numOfPlayers; i++)
        {
            String[] data = readAndDeleteFirstLine().split(" ");
            players.add(
                new Player
                    (
                        222*i, 222*i, Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), display.addImage("./images/untagged.png", 100, 100, 100, 100)
                    )
                );
            // Add abilities to player
            for(int j = 0; j != (data.length - 4)/2; j++)
            {
                players.get(i).addAbility(Integer.parseInt(data[2*j + 4]), Integer.parseInt(data[2*j + 5]), activeParticles, sleepingParticles);
            }
        }
        for(Player player: players)
        {
            ticker.addEntity(player);
        }
        players.get(RANDOM.nextInt(players.size())).tag();

        // Set each particle to back
        //for(Particle particle: sleepingParticles)
        //{
        //    display.setZOrder(particle.getImage(), 0);
        //}
        display.start();
        ticker.start();
    }


    
    public static String readAndDeleteFirstLine()
    {
        try
        {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String firstLine = reader.readLine(); // ✅ This reads the first line

        // Read the remaining lines
        List<String> remainingLines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            remainingLines.add(line);
        }
        reader.close();

        // Write back the remaining lines (i.e., delete the first line)
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (String l : remainingLines) {
            writer.write(l);
            writer.newLine();
        }
        writer.close();

        return firstLine;
    }
    catch(IOException e)
    {
        System.err.println(e.getMessage());
    }
    return "";
    }
}
