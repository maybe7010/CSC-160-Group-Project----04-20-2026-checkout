/*
 * Joel Robetson
 * Ethan Smith
 * 4/ /26 (Date Uploaded)
 * A GUI designed as a proof of concept for player movement and interaction with map tiles
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

public class PlayerMovement{
    
    public Random rg = new Random();
    public final int playerWidth; // Player  Width
    public final int playerHeight; // Player Height
    public int x = 512; // Default Player Position
    public int y = 512; // Default Player Position
    public final int SPEED = 5; // Player Speed
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2; //Player Sprites
    public String direction = "down"; // Tracks player direction
    public int spriteCount = 0;
    public int spriteNum = 1;
    
    public int prevXTile = -1; // Stores the player's previous x tile
    public int prevYTile = -1; // Stores the player's previous y tile
    
    public int xTile; // Stores the player's current x tile
    public int yTile; // Stores the player's current y tile
    public int xCenter; // Stores the player's center x position
    public int yCenter; // Stores the player's center y position

    // Track active keys for movement
    public boolean wActive = false;
    public boolean aActive = false;
    public boolean sActive = false;
    public boolean dActive = false;
    
    private int chanceCurve = 0;
    private int cooldown = 20;
    
    // Data for a Map
    Map m;

    // Constructor
    public PlayerMovement(Map m) {
        this.m = m;
        // Smaller collision box and sprite render size for easier doorway passage
        playerHeight = m.tileSize * 3 / 4;
        playerWidth = m.tileSize * 3 / 4;
        getPlayerImage();
    }

    
    // Movement Method
    public void updatePosition()
    {
        // Logic for moving based on input
        double moveX = 0;
        double moveY = 0;
    
        if (wActive) moveY -= 1;
        if (aActive) moveX -= 1;
        if (sActive) moveY += 1;
        if (dActive) moveX += 1;
    
        // Diagonal movement logic
        if (moveX != 0 && moveY != 0) {
            moveX *= 0.8;
            moveY *= 0.8;
        }
    
        int dx = (int)(moveX * SPEED);
        int dy = (int)(moveY * SPEED);
    
        // Checks X movement separately for collision
        if (!checkForCollision(x + dx, y)) {
            x += dx;
        }
        
        // Check Y movement separately for collision
        if (!checkForCollision(x, y + dy)) {
            y += dy;
        }
    }
    
    public boolean checkForCollision(int nextX, int nextY)
    {
        int leftTile = nextX / 64;
        int rightTile = (nextX + playerWidth - 1) / 64;
        int topTile = nextY / 64;
        int bottomTile = (nextY + playerHeight - 1) / 64;
    
        if(leftTile < 0 || topTile < 0 || rightTile >= m.screenCol || bottomTile >= m.screenRow)
        {
            return true;
        }

        for (int row = topTile; row <= bottomTile; row++)
        {
            for (int col = leftTile; col <= rightTile; col++)
            {
    
                if (m.tileM.mapTileNum[col][row] == 1)
                {
                    return true; // hit a wall
                }
            }
        }
    
        return false;
    }
    
    public void checkTile()
    {
        xCenter = x + playerWidth / 2;
        yCenter = y + playerHeight / 2;
        xTile = (xCenter / 64);
        yTile = (yCenter / 64);
    }

    public void getPlayerImage()
    {
        try {
            up1 = ImageIO.read(new File("sprites/player_sprites/player_up_1.png")); //assets source original artist unknown
            up2 = ImageIO.read(new File("sprites/player_sprites/player_up_2.png"));
            down1 = ImageIO.read(new File("sprites/player_sprites/player_down_1.png"));
            down2 = ImageIO.read(new File("sprites/player_sprites/player_down_2.png"));
            left1 = ImageIO.read(new File("sprites/player_sprites/player_left_1.png"));
            left2 = ImageIO.read(new File("sprites/player_sprites/player_left_2.png"));
            right1 = ImageIO.read(new File("sprites/player_sprites/player_right_1.png"));
            right2 = ImageIO.read(new File("sprites/player_sprites/player_right_2.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void draw(Graphics2D gg)
    {
        BufferedImage img = null;
        if (direction.equals("left"))
        {
            if (spriteNum == 1)
            {
                img = left1;
            }
            if (spriteNum == 2)
            {
                img = left2;
            }
        }
        if(direction.equals("right"))
        {
            if (spriteNum == 1)
            {
                img = right1;
            }
            if (spriteNum == 2)
            {
                img = right2;
            }
        }
        if(direction.equals("up"))
        {
            if (spriteNum == 1)
            {
                img = up1;
            }
            if (spriteNum == 2)
            {
                img = up2;
            }
        }
        if(direction.equals("down"))
        {
            if (spriteNum == 1)
            {
                img = down1;
            }
            if (spriteNum == 2)
            {
                img = down2;
            }
        }
        gg.drawImage(img, x, y, playerWidth, playerHeight, null );
    }
    
    public void tileSwitchEvent() 
    {
    
        // Tests if tile changed
        if (xTile != prevXTile || yTile != prevYTile)
        {
            // Makes battles more likely the longer you go without one
            chanceCurve++;
            cooldown--;
    
            // Updates the previous tile
            prevXTile = xTile;
            prevYTile = yTile;
    
            // Now we can actually trigger events

            if (m.tileM.mapTileNum[xTile][yTile] == 4)
            {
                    Data.getInventory().addKey("Key", 0);
                    System.out.println("You found a key!");
                    m.tileM.mapTileNum[xTile][yTile] = 2; // Change chest to floor
            }
             if (m.tileM.mapTileNum[xTile][yTile] == 5 || m.tileM.mapTileNum[xTile][yTile] == 6)
            {
                    System.out.println("Boss coming soon...");
            }
             if (((m.tileM.mapTileNum[xTile][yTile] == 12) || (m.tileM.mapTileNum[xTile][yTile] == 52)) || (m.tileM.mapTileNum[xTile][yTile] == 32))
            {
                    m.loadNewMap(1);
                    x = 128;
                    y = 528;
            }
            if (m.tileM.mapTileNum[xTile][yTile] == 21)
            {
                    m.loadNewMap(0);
                    x = 764;
                    y = 528;
            }
            if (m.tileM.mapTileNum[xTile][yTile] == 23)
            {
                    m.loadNewMap(2);
                    x = 512;
                    y = 512;
            }
            if (m.tileM.mapTileNum[xTile][yTile] == 54)
            {
                    m.loadNewMap(3);
                    x = 512;
                    y = 512;
            }
            if (m.tileM.mapTileNum[xTile][yTile] == 56)
            {
                    m.loadNewMap(5);
                    x = 512;
                    y = 512;
            }
            if ((m.tileM.mapTileNum[xTile][yTile] == 45) || (m.tileM.mapTileNum[xTile][yTile] == 25))
            {
                    m.loadNewMap(4);
                    x = 512;
                    y = 512;
            }
            
            if (m.tileM.mapTileNum[xTile][yTile] == 2) //rather than specific tile ckecks for battle encounter
            {                                   //we might need to switch logic for chance on all tiles
               if (cooldown == 0)  
                {
                    cooldown = 20; // Reset cooldown
                    int btlTrig = rg.nextInt(100) + chanceCurve;            //~ES
                    if (btlTrig >= 100)
                    {
                        chanceCurve = 0;
                        System.out.println("Attempting to switch to Battle.");
                        m.sm.switchTo("Battle");
                        wActive = false;
                        aActive = false;
                        sActive = false;
                        dActive = false;
                    }
                }
            }
        }
    }
}
