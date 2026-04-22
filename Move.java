
/**
 * Move class to create LinkedList<Move> in Player class.
 *
 * @author Breanna Bean
 * @version 04/08/2026
 */
public class Move
{
    public String moveName;
    public int moveDamageDealt;
    public int moveDamagePercentTaken;
    public int mpCost;
    
    public Move(String moveName, int moveDamageDealt, int moveDamagePercentTaken, int mpCost)
    {
        this.moveName = moveName;
        this.moveDamageDealt = moveDamageDealt;
        this.moveDamagePercentTaken = moveDamagePercentTaken;
        this.mpCost = mpCost;
    }
    
    public String getMoveName()
    {
        return moveName;
    }
    
    public int getMoveDamageDealt()
    {
        return moveDamageDealt;
    }
    
    public int getMoveDamagePercentTaken()
    {
        return moveDamagePercentTaken;
    }
    
    public int getMpCost()
    {
        return mpCost;
    }
}