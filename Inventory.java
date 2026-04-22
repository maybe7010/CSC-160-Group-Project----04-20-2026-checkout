// Philip Raiford - Software Design Group Project
// 03/31/2026
// Helper class to help manage the inventory in the Player class, includes sort methods, add/remove items, etc.

import java.util.*;

public class Inventory {

  // private LinkedList<Item> inventory;
  private LinkedList<Item> weaponsItems;
  private LinkedList<Item> armorItems;
  private LinkedList<Item> potionItems;
  private LinkedList<Item> keyItems;

  public Inventory() {

    weaponsItems = new LinkedList<>();
    armorItems = new LinkedList<>();
    potionItems = new LinkedList<>();
    keyItems = new LinkedList<>();

  }

  public LinkedList<Item> getWeapons() {
    return this.weaponsItems;
  }

  public LinkedList<Item> getArmor() {
    return this.armorItems;
  }

  public LinkedList<Item> getPotions() {
    return this.potionItems;
  }

  public LinkedList<Item> getKeys() {
    return this.keyItems;
  }

  public void addWeapon(String n, int d) {
    this.weaponsItems.add(new Item(n, d));
    ScreenManager.menu.buildWeapons();
  }

  // overloading here to help make the menu a little cleaner since it doesn't sort
  public void addWeapon(int i, String n, int d) {
    this.weaponsItems.add(i, new Item(n, d));
    ScreenManager.menu.buildWeapons();
  }

  public void addArmor(String n, int d) {
    this.armorItems.add(new Item(n, d));
    ScreenManager.menu.buildArmor();
  }

  // overloading here to help make the menu a little cleaner since it doesn't sort
  public void addArmor(int i, String n, int d) {
    this.armorItems.add(i, new Item(n, d));
    ScreenManager.menu.buildArmor();
  }

  public void addPotion(String n, int d) {
    this.potionItems.add(new Item(n, d));
    ScreenManager.menu.buildPotions();
  }

  // the same here again
  public void addPotion(int i, String n, int d) {
    this.potionItems.add(i, new Item(n, d));
    ScreenManager.menu.buildPotions();
  }

  public void addKey(String n, int d) {
    this.keyItems.add(new Item(n, d));
    ScreenManager.menu.buildKeys();
  }

  public void addKey(int i, String n, int d) {
    this.potionItems.add(i, new Item(n, d));
    ScreenManager.menu.buildKeys();
  }

  public void generateTestInventory() {

    this.weaponsItems.add(new Item("Sword", 5));
    this.weaponsItems.add(new Item("Mace", 7));

    this.armorItems.add(new Item("Chainmail", 10));
    this.armorItems.add(new Item("Plate Armor", 15));
    this.armorItems.add(new Item("Gambeson", 7));

    this.potionItems.add(new Item("Minor Healing Potion", 3));
    this.potionItems.add(new Item("Minor Healing Potion", 3));
    this.potionItems.add(new Item("Minor Healing Potion", 3));
    this.potionItems.add(new Item("Major Healing Potion", 10));

    this.keyItems.add(new Item("Key 1", 0));
    this.keyItems.add(new Item("Key 2", 0));

    ScreenManager.menu.buildInventory();

  }

  public void generateStartingInventory() {

    this.addPotion("Minor Healing Potion", 25);
    this.addPotion("Minor Healing Potion", 25);
    this.addPotion("Minor Healing Potion", 25);

  }

  public void addTestWeapon() {
    this.weaponsItems.add(new Item("Hammer", 19));
    ScreenManager.menu.buildWeapons();
  }

  public void addTestArmor() {
    this.armorItems.add(new Item("Holy Mail", 20));
    ScreenManager.menu.buildArmor();
  }

  public void addTestPotion() {
    this.potionItems.add(new Item("Poison", 15));
    ScreenManager.menu.buildPotions();
  }

  public void addTestKey() {
    this.keyItems.add(new Item("Floormaster's Key", 0));
    ScreenManager.menu.buildKeys();
  }

  //
  //
  // // Probably don't need these, really just using the premade LinkedList
  // methods
  //
  // public void add(Item e) {
  // this.inventory.add(e);
  // }
  //
  // public void remove(Item e) {
  // this.inventory.remove(e);
  // }
  //
  // public boolean contains(Item e) {
  // this.inventory.contains(e);
  // }
  //
}
