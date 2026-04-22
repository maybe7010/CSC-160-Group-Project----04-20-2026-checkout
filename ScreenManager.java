
/**
 * TTTFOUGSINAD
 * Class ScreenManager juggles the different UI's and switches between them, exists as the base of the UI system and the JFrame it sits on
 *
 * @author Philip Raiford
 * @version created: 03/27/2026
 */

import java.awt.*;
import javax.swing.*;

public class ScreenManager extends JFrame {

  public static Battle battle;
  public static Menu menu;
  public static Map map;

  JPanel base;

  public ScreenManager() {

    setTitle("Test Screen");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    setSize(1024, 768);

    base = new JPanel(new CardLayout());
    add(base, BorderLayout.CENTER);

    battle = new Battle(this);
    map = new Map(this);
    menu = new Menu(this);

    base.add(battle, "Battle"); // base.getComponent(0);
    base.add(menu, "Menu"); // base.getComponent(1);
    base.add(map, "Map"); // base.getComponent(2);

    // This is for testing the Inventory
    // Data.getInventory().generateTestInventory();
    Data.getInventory().generateStartingInventory();

    Party.generatePlayerParty();

    // for testing battle ui
    battle.buildTestSkills();

    this.pack();
    switchTo("Map");

  }

  public void switchTo(String name) {
    CardLayout layout = (CardLayout) base.getLayout();
    if (name.equals("Battle")) {
      layout.show(base, name);
      battle.switchToBattle();
    } else if (name.equals("Menu")) {
      layout.show(base, name);
      menu.switchToMenu();
    } else if (name.equals("Map")) {
      layout.show(base, name);
      map.switchToMap();
    }
  }

  public static void main(String[] args) {
    ScreenManager manager = new ScreenManager();
    manager.setVisible(true);
  }

}
