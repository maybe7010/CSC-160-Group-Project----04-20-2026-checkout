
/**
 * Write a description of interface PartyMember here.
 *
 * @author Breanna Bean
 * @author Philip Raiford - base code for implementing party members.
 * @version 04/13/2026
 */
public class Party
{
    public static int partySize = 1; 
    public static Party[] party = new Party[partySize];
    
    public static Player [] playerParty = new Player[4];
    public static Party[] finalBoss = new Party[1];

    public void setPartySize(int a) //defaults to party size of 1 unless changed here
    {
        partySize = a;
    }

    public int getPartySize()
    {
        return partySize;
    }

    public void makeParty(Party a)
    {
        party[0] = a;
    }

    // public void makeParty(Party a, Party b, Party c, Party d)
    // {
        // party[0] = a;
        // party[1] = b;
        // party[2] = c;
        // party[3] = d;
    // }

    public static void generatePlayerParty()
    {
        playerParty[0] = new Player("Player 1", "Fighter");
        playerParty[1] = new Player("Player 2", "Barbarian");
        playerParty[2] = new Player("Player 3", "Cleric");
        playerParty[3] = new Player("Player 4", "Wizard");
    }

    public void generateEnemyTestParty()
    {
        Enemy [] enemyParty = new Enemy[4];
        enemyParty[0] = new Enemy();
        enemyParty[1] = new Enemy();
        enemyParty[2] = new Enemy();
        enemyParty[3] = new Enemy();
    }
    
    public static Player[] getParty()
    {
        return playerParty;
    }
    
    public Player getPlayerPartyMember(int indOf)
    {
        return playerParty[indOf];
    }
}
