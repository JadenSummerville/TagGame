package src;

import javax.swing.*;
import src.builders.Display;
import src.builders.Ticker;
import src.menu.AbilitySlider;
import src.menu.Menu;

public class StartScreen {
    public static void main(String[] args) {
        Display display = new Display(800,800);
        Ticker ticker = new Ticker();

        JLabel numberOfPlayersImage = display.addImage("./images/N.png", 100, 100, 100, 100);

        JLabel up = display.addImage("./images/Up.png.png", 100, 100, 100, 100);
        up.setVisible(false);
        JLabel down = display.addImage("./images/Down.png.png", 100, 100, 100, 100);
        down.setVisible(false);
        JLabel right = display.addImage("./images/Right.png.png", 100, 100, 100, 100);
        right.setVisible(false);
        JLabel left = display.addImage("./images/Left.png.png", 100, 100, 100, 100);
        left.setVisible(false);

        String[] links = {"./images/speed_boost.png", "./images/swap.png", "./images/bones.png", "./images/Western_draw.png", "./images/N.png"};
        AbilitySlider abilitySlider = new AbilitySlider(display, links);
        Menu menu = new Menu(display, numberOfPlayersImage, up, right, down, left, abilitySlider);
        ticker.addEntity(menu);

        display.start();
        ticker.start();
    }
}
