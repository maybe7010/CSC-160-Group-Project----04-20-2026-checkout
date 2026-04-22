import java.util.*;

/**
 * Class Entity contains - creates Entity with name, level, current health, and
 * speed stats.
 *
 * @author Breanna Bean
 * @author Loren Wolf
 * @version 03/29/2026
 */
public abstract class Entity // implements PartyMember
{
  protected String name;
  protected int level;
  protected int maxHp;
  protected int hp;
  protected int xp;
  protected int mp;
  protected int speed;
  protected int difficulty;
  protected int playerXp;

  public TileManager curTile = ScreenManager.map.getTileM();
  public String mapName;

  // protected int specialAttack;

  // constructor for an Entity with no input parameters
  public Entity() {
    name = "";
    level = 0;
    maxHp = 100;
    hp = maxHp;
    speed = 1;
    mapName = curTile.mapFilePath;
    difficulty = getDifficulty(mapName);

  }

  // Constructor for an Entity with inputs
  public Entity(String entityName, int maxHp, int xp) {
    this.name = entityName;
    this.maxHp = maxHp;
    hp = maxHp;
    this.playerXp = xp;
    this.speed = 100;
    level = 1;
    mp = 20;
    mapName = curTile.mapFilePath;
    difficulty = getDifficulty(mapName);

  }

  // Methods each Entity will need
  public abstract int attack();

  public abstract int defend();

  public int getPlayerXp() {
    return this.playerXp;
  }

  public int getDifficulty(String mapName) {
    if (mapName == "dungeon_1_1.txt") {
      return 1;
    } else if (mapName == "dungeon_1_2.txt") {
      return 1;
    } else if (mapName == "dungeon_1_3.txt") {
      return 2;
    } else if (mapName == "dungeon_1_4.txt") {
      return 3;
    } else if (mapName == "dungeon_1_5.txt") {
      return 2;
    } else if (mapName == "dungeon_1_6.txt") {
      return 5;
    }
    return 0;
  }

  // public int partySize = 1;
  // public Party[] party = new Party[partySize];
  // public Party[] finalBoss = new Party[1];

  // // Uses PartyMember interface. (To pull in Battle)
  // public void setPartySize(int a) //defaults to party size of 1 unless changed
  // here
  // {
  // partySize = a;
  // }

  // public int getPartySize()
  // {
  // return partySize;
  // }

  // public void makeParty(Party a)
  // {
  // party[0] = a;
  // }

  // public void makeParty(Party a, Party b, Party c, Party d)
  // {
  // a = (Entity) a;

  // party[0] = a;
  // party[1] = b;
  // party[2] = c;
  // party[3] = d;
  // }

  // public Party getPartyMember(int indOf, PartyMember[] party)
  // {
  // return party[indOf];
  // }

}
