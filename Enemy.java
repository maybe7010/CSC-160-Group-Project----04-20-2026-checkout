import java.util.*;

/**
 * Write a description of class Enemy here.
 *
 * @author Breanna Bean
 * @author Loren Wolf
 * @version 04/01/2026
 */
public class Enemy extends Entity
{
    public int enemyType;
    public int playerXpAdd;
    public Item lootItem;
    public String lootType;
    public int lootGold;
        
    Random rg = new Random();    
   
    
    public Enemy()
    {
        this.playerXpAdd = 0;
        
        if (this.difficulty == 1)
        {
            enemyType = 0; // Generates Slime
            name = enemyName(enemyType);
            level = rg.nextInt(3) + difficulty; // Generates levels 1-3
            hp = 5 * level;
            maxHp = hp;
            speed = getBaseSpeed(enemyType);
            
        } else if (this.difficulty == 2)
        {
            enemyType = rg.nextInt(2); // Generates Slime or Skeleton
            name = enemyName(enemyType);
            level = rg.nextInt(4) + (difficulty * 2) ; // Generates levels 4-7
            hp = 5 * level;
            maxHp = hp;
            speed = getBaseSpeed(enemyType);
            
        } else if (this.difficulty == 3)
        {
            enemyType = rg.nextInt(2) + 1; // Generates Skeleton or Zombie
            name = enemyName(enemyType);
            level = rg.nextInt(5) + (difficulty + 5); // Generates levels 8-12
            hp = 5 * level;
            maxHp = hp;
            speed = getBaseSpeed(enemyType);
            
        } 
        else if (this.difficulty == 5)
        {
            enemyType = 4;
            name = enemyName(enemyType);
            level = 13;
            hp = 5 * level + 10;
            maxHp = hp;
            speed = getBaseSpeed(enemyType);
            
        }
    }
    
    public int getBaseSpeed(int enemyType)
    {
        if (enemyType == 0)
        {
            speed = 1;     
            
        } else if (enemyType == 1)
        {
             speed = 2;
            
        } else if (enemyType == 2)
        {
            speed = 3;
            
        } 
        else if (enemyType == 4)
        {
            speed = 5;
        }
        return speed;
    }
    
    public String enemyName(int enemyType)
    {
         // Sets enemy name based off type of enemy
        if (enemyType == 0)
        {
            name = "Slime";
                        
        } else if (enemyType == 1)
        {
            name = "Skeleton";
            
        } else if (enemyType == 2)
        {
            name = "Zombie";
            
        }
        else if (enemyType == 4)
        {
            name = "Slime Monarch";
        }
        return name;
    }

    //Enemy specific attack and defence calculations
    @Override
    public int attack() {
        //formulas for adjusting game balance for attack and defend
        return (int) (rg.nextInt(level) * (this.xp/4 + 1) + level/4 + 3);
    }

    @Override
    public int defend() {
        return (int) (rg.nextInt(level) * (this.xp/4 + 1) + level/4 + 3);
    }
    
    
    public void setLootGold()
    {
        lootGold = rg.nextInt(level);
    }
    public int getLootGold()
    {
        return lootGold;
    }
    
    public int getPlayerXpAdd()
    {
        if (this.hp <= 0)
        {
            playerXpAdd = (difficulty * level) / maxHp;
        }
        return playerXpAdd;
    }
    
    public Item getLootItem()
    {
        double dropPercent = (rg.nextDouble(level) * 100.0) / difficulty;    // / difficulty;
        String lootName = "";
        int lootDamage = -1;
        
        if (dropPercent <= 20)
        {
            int weaponType = rg.nextInt(4) + 1;
            lootType = "Weapon";
            
            if (weaponType == 1)
            {
                lootName = "Sword";
                lootDamage = level/difficulty * 10;
            } else if (weaponType == 2)
            {
                lootName = "Staff";
                lootDamage = level/difficulty * 10;
            } else if (weaponType == 3)
            {
                lootName = "Mace";
                lootDamage = level/difficulty * 10;
            } else if (weaponType == 4)
            {
                lootName = "Battle Axe";
                lootDamage = level/difficulty * 10;
            }

        }
        else if ((dropPercent > 20) && (dropPercent <= 40))
        {
            int armorType = rg.nextInt(4) + 1;
            lootType = "Armor";
            
            if (armorType == 1)
            {
                lootName = "Chainmail";
                lootDamage = level/difficulty * 10;
            } else if (armorType == 2)
            {
                lootName = "Plate";
                lootDamage = level/difficulty * 10;
            } else if (armorType == 3)
            {
                lootName = "Leather";
                lootDamage = level/difficulty * 10;
            } else if (armorType == 4)
            {
                lootName = "Robes";
                lootDamage = level/difficulty * 10;
            }

        }
        else if (dropPercent > 40) // && (dropPercent <= 10))
        {
            int potionType = rg.nextInt(5) + 1;
            lootType = "Potion";
            
            if (potionType == 1)
            {
                lootName = "Healing Potion";
                lootDamage = level/difficulty * 10;
            } else if (potionType == 2)
            {
                lootName = "Speed Potion";
                lootDamage = level/difficulty * 10;
            } else if (potionType == 3)
            {
                lootName = "Damage Potion";
                lootDamage = level/difficulty * 10;
            } else if (potionType == 4)
            {
                lootName = "Poison Potion";
                lootDamage = level/difficulty * 10;
            } else if (potionType == 5)
            {
                lootName = "Mana Points Potion";
                lootDamage = level/difficulty * 10;
            }
        }
        
        lootItem = new Item(lootName, lootDamage);
        return lootItem;
    }
    
}
