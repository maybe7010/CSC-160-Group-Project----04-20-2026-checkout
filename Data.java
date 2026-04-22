// TTFOUGSIAD
// 04/01/2026
// Java class that hosts values that need to be created and saved before the game starts and other data that needs to be called in other parts of the game

public class Data {

  public static Inventory Inventory = new Inventory();

  public static Inventory getInventory() {
    return Inventory;
  }

  public static void addWeapon(String n, int d) {
    getInventory().addWeapon(n, d);
  }

  // overloading here to help make the menu a little cleaner by passing in an
  // index to allow putting items where we want them
  public static void addWeapon(int i, String n, int d) {
    getInventory().addWeapon(i, n, d);
  }

  public static void addArmor(String n, int d) {
    getInventory().addArmor(n, d);
  }

  // same here
  public static void addArmor(int i, String n, int d) {
    getInventory().addArmor(i, n, d);
  }

  public static void addPotion(String n, int d) {
    getInventory().addPotion(n, d);
  }

  // and here
  public static void addPotion(int i, String n, int d) {
    getInventory().addPotion(i, n, d);
  }

  public static void addKey(String n, int d) {
    getInventory().addKey(n, d);
  }

  public static void addKey(int i, String n, int d) {
    getInventory().addKey(i, n, d);
  }

  public void generateTestParty() {

  }
}
