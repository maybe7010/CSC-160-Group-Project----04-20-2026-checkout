// Philip Raiford - Software Design Group Project
// 03/31/2026
// Extendable item class that hosts methods and instace variables for items that would be created by the game

public class Item {

  private String name;
  private int damage;

  public Item(String n, int d) {
    name = n;
    damage = d;
  }

  public String getName() {
    return name;
  }

  public int getDamage() {
    return damage;
  }

}
