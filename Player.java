import java.util.*;

import javax.swing.ImageIcon;

/**
 * @author Breanna Bean
 * @author Loren Wolf
 * @version 04/07/2026
 */
public class Player extends Entity {
  // Integers to store number of upgrades/skills in each path
  public int numAtkUpgrades, numDefUpgrades;
  public String playerType; // "Fighter", "Barbarian", "Wizard", "Cleric"
  public Random rg = new Random();

  // LinkedList<Move> to keep track of each player's move options, utilizes Move
  // class.
  public LinkedList<Move> genericMoves;
  public LinkedList<Move> specialMoves;

  public Item weaponEquip;
  public Item armorEquip;

  // public static Player [] playerParty = new Player[4];

  // additional player stats
  public int gold, restsLeft, pots;

  // Arrays to store skill names
  public String[] attackUpGrades = { "Strength", "Power", "Might", "Godlike Strength" };
  public String[] defenseUpGrades = { "Heavy Bones", "StoneSkin", "Scale Amor", "Holy Aura" };

  // this section here is for the sprites that each character would have, for now
  // its all the same fighter we already made, will probably change later
  // also probably won't use all of these instace variable, putting them here just
  // in case

  public ImageIcon rightSprite1;
  public ImageIcon rightSprite2;

  public ImageIcon leftSprite1;
  public ImageIcon leftSprite2;

  public ImageIcon downSprite1;
  public ImageIcon downSprite2;

  public ImageIcon upSprite1;
  public ImageIcon upSprite2;

  public Player(String name, String playerType) {
    // super (name, maxHp, xp)

    super(name, 100, 0);

    this.numAtkUpgrades = 0;
    this.numDefUpgrades = 0;

    // initialize Moves linked lists
    genericMoves = new LinkedList<>();
    specialMoves = new LinkedList<>();

    // set additional stats
    this.gold = 5;
    this.restsLeft = 1;
    this.pots = 0;

    // Default set playerType to Fighter for first demo
    // playerType = "Fighter";

    // let the player choose a trait when creating a new character
    choosePlayer(playerType);
    chooseTrait();
  }

  public void choosePlayer(String playerType) {
    this.playerType = playerType;

    if (playerType.equals("Barbarian")) {
      speed = speed + 10;
      // Character specific moves to add needed here
      this.addSpecialMove("Blunt Force", 25, 5);

      weaponEquip = new Item("Ax", 10);
      armorEquip = new Item("Leather", 5);

      this.downSprite1 = new ImageIcon("sprites/player_sprites/player_down_1.png");
      this.rightSprite1 = new ImageIcon("sprites/player_sprites/player_right_1.png");

    } else if (playerType.equals("Fighter")) {
      speed = speed;

      // Character specific moves to add needed here
      this.addSpecialMove("Cleave", 15, 5);

      weaponEquip = new Item("Sword", 10);
      armorEquip = new Item("Plate Armor", 10);

      this.downSprite1 = new ImageIcon("sprites/player_sprites/player_down_1.png");

    } else if (playerType.equals("Cleric")) {
      speed = speed - 10;

      // Character specific moves to add needed here
      this.addSpecialMove("Heal", 20, 5);

      weaponEquip = new Item("Mace", 5);
      armorEquip = new Item("Chainmail", 7);

      this.downSprite1 = new ImageIcon("sprites/player_sprites/player_down_1.png");
      this.upSprite1 = new ImageIcon("sprites/player_sprites/player_up_1.png");

    } else if (playerType.equals("Wizard")) {
      speed = speed - 20;

      // Character specific moves to add needed here
      this.addSpecialMove("Mustard Gas", 55, 5); // Change to FireBall -do-do-do-du-do- later :)

      weaponEquip = new Item("Staff", 2);
      armorEquip = new Item("Robes", 2);

      this.downSprite1 = new ImageIcon("sprites/player_sprites/player_down_1.png");
      this.leftSprite1 = new ImageIcon("sprites/player_sprites/player_left_1.png");

    }
  }

  // Player specific Methods
  @Override
  public int attack() {

    return rg.nextInt(level) * (playerXp / hp) * 5;
    // where the battle system comes in
    // return (int) (rg.nextInt(level) * (xp/4 + numAtkUpgrades * 3 + 3) + xp/10 +
    // numAtkUpgrades * 2 + numDefUpgrades + 1);
  }

  @Override
  public int defend() {

    return rg.nextInt(level) * (playerXp / speed) + (playerXp / hp);
    // return (int) (rg.nextInt(level) * (xp/4 + numDefUpgrades * 3 + 3) + xp/10 +
    // numDefUpgrades * 2 + numAtkUpgrades + 1);
  }

  public void setWeaponEquip(Item a) {
    weaponEquip = a;
  }

  public void setArmorEquip(Item a) {
    armorEquip = a;
  }

  public Item getWeaponEquip() {
    return weaponEquip;
  }

  public Item getArmorEquip() {
    return armorEquip;
  }

  public int getPlayerXp() {
    return this.playerXp;
  }

  public void toLevelUp() {
    if (this.xp < 100) {
      level = level;
    } else if (this.xp >= 100) {
      level++;
      this.xp = this.xp - 100;
    }
  }

  public void addGenericMoves() {
    this.genericMoves.add(new Move("Attack", this.attack(), 100, 0));
    this.genericMoves.add(new Move("Potion", this.attack(), 100, 0));
    this.genericMoves.add(new Move("Run", 0, 0, 0));
    this.genericMoves.add(new Move("Guard", 0, 50, 0));

  }

  public void addSpecialMove(String n, int d, int mpCost) {
    this.specialMoves.add(new Move(n, d, 100, mpCost));

  }

  // returns the specific character sprite state you want
  public ImageIcon getImageIcon(String n) {

    if (n.equals("right1")) {
      return this.rightSprite1;
    } else if (n.equals("right2")) {
      return this.rightSprite2;
    } else if (n.equals("left1")) {
      return this.leftSprite1;
    } else if (n.equals("left2")) {
      return this.leftSprite2;
    } else if (n.equals("down1")) {
      return this.downSprite1;
    } else if (n.equals("down2")) {
      return this.downSprite2;
    } else if (n.equals("up1")) {
      return this.upSprite1;
    } else if (n.equals("up2")) {
      return this.upSprite2;
    }

    return null;

  }

  public void usePotion(Item potion) {

    if (potion == null) {
      System.out.println("Potion use failed, potion is null, returning.");
      return;
    }

    Player actor = this;
    if (actor == null || actor.hp <= 0) {
      System.out.println("The player is either null or dead! returning.");
    }

    int healAmount = Math.max(10, potion.getDamage() * 5);

    actor.hp += healAmount;
    if (actor.hp > actor.maxHp) {
      actor.hp = actor.maxHp;
    }

  }

  // Method that lets the player choose a trait
  public void chooseTrait() {
    // GameLogic.clearConsole();
    // GameLogic.printHeading("Choose a trait:");
    // System.out.println("(1) " + attackUpGrades[numAtkUpgrades]);
    // System.out.println("(2) " + defenseUpGrades[numDefUpgrades]);
    // //get the players choice:
    // int input = GameLogic.readInt("-> ", 2);
    // GameLogic.clearConsole();

    // //deal with both cases
    // if(input == 1){
    // GameLogic.printHeading("You chose " + attackUpGrades[numAtkUpgrades] + "!");
    // numAtkUpgrades++;
    // }else{
    // GameLogic.printHeading("You chose" + defenseUpGrades[numDefUpgrades] + " !");
    // numDefUpgrades++;
    // }
    // GameLogic.anythingToContinue();

    // do nothing for now until GameLogic is added to Master Branch in repository.
    // --- 04.12.2026

  }

  // public static void generatePlayerParty()
  // {
  // playerParty[0] = new Player("Player 1", "Fighter");
  // playerParty[1] = new Player("Player 2", "Barbarian");
  // playerParty[2] = new Player("Player 3", "Cleric");
  // playerParty[3] = new Player("Player 4", "Wizard");
  // }

}
