package src.menu;

import java.awt.event.KeyEvent;
import java.io.FileWriter;
import javax.swing.JLabel;
import src.builders.Display;
import src.builders.Ticker;

public class Menu extends Ticker.Entity{
    private static int numPlayers = -1;
    private static int numOfAbilities = -1;

    private static final String FILE_NAME = "tag";

    private final Display display;

    private final JLabel numberOfPlayersImage;

    private final JLabel up;
    private final JLabel down;
    private final JLabel left;
    private final JLabel right;

    private final AbilitySlider abilitySlider;
    
    public Menu(Display display, JLabel numberOfPlayersImage, JLabel up, JLabel right, JLabel down, JLabel left, AbilitySlider abilitySlider)
    {
        this.display = display;
        this.numberOfPlayersImage = numberOfPlayersImage;

        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;

        this.abilitySlider = abilitySlider;
    }

    public void start()
    {
        //
    }

    public void update()
    {
        clearFile();
        // Read num of players
        int num = readNumberFromKeyBoard();
        numberOfPlayersImage.setBounds(0, 0, 100, 100);
        numPlayers = num;
        appendToFile(num + "\n");
        // Read num of abilities
        num = readNumberFromKeyBoard();
        numberOfPlayersImage.setVisible(false);
        numOfAbilities = num;
        appendToFile(num + "\n");
        // For each player
        for(int i = 0; i != numPlayers; i++)
        {
            up.setVisible(true);
            int upInt = readButton();
            appendToFile(upInt + " ");
            up.setVisible(false);

            right.setVisible(true);
            int rightInt = readButton();
            appendToFile(rightInt + " ");
            right.setVisible(false);

            down.setVisible(true);
            appendToFile(readButton() + " ");
            down.setVisible(false);

            left.setVisible(true);
            int leftInt = readButton();
            appendToFile(leftInt + " ");
            left.setVisible(false);

            // TODO: Add something for adding abilities
            for(int j = 0; j != numOfAbilities; j++)
            {
                abilitySlider.turnOn(upInt, rightInt, leftInt);
                appendToFile(readButton() + " ");
            }
            appendToFile("\n");
        }
        display.end();
        while(true);
    }
    private int readButton()
    {
        while(display.keyBoard.anyPressed())
        {
            System.out.print("");
        }
        while(!display.keyBoard.anyPressed())
        {
            System.out.print("");
        }
        while(display.keyBoard.anyPressed())
        {
            System.out.print("");
        }
        return display.keyBoard.lastKeyPressed().getKeyCode();
    }
    public static void appendToFile(String text)
    {
        try
        {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(text);
            writer.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    public static void clearFile()
    {
        try
        {
            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write("");
            writer.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    private int readNumberFromKeyBoard()
    {
        int num = -1;
        while(1 > num || num > 9)
        {
            num = readButton() - KeyEvent.VK_0;
        }
        return num;
    }
}
