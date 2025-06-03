package src.menu;

import java.io.FileWriter;

import javax.swing.JLabel;

import src.builders.Display;

public class AbilitySlider
{
    private final Display display;
    private static final String FILE_NAME = "tag";

    private int pointer;
    private JLabel[] images;

    public AbilitySlider(Display display, String[] links)
    {
        this.display = display;

        images = new JLabel[links.length];

        for (int i = 0; i != links.length; i++)
        {
            images[i] = display.addImage(links[i], 100, 100, 300, 300);
            images[i].setVisible(false);
        }
    }
    public void turnOn(int up, int right, int left)
    {
        pointer = 0;
        images[0].setVisible(true);
        while(true)
        {
            int button = readButton();
            if(left == button)
            {
                images[pointer].setVisible(false);
                pointer--;
                if (pointer == -1)
                {
                    pointer = images.length - 1;
                }
                images[pointer].setVisible(true);
            }
            else if(right == button)
            {
                images[pointer].setVisible(false);
                pointer++;
                if (pointer == images.length)
                {
                    pointer = 0;
                }
                images[pointer].setVisible(true);
            }
            else if(up == button)
            {
                appendToFile(pointer + " ");
                images[pointer].setVisible(false);
                return;
            }
        }
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
}
