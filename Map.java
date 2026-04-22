//Ethan Smith
//Class for displaying map and player movement, as well as collision
//4/8/26
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Map extends JPanel implements KeyListener 
{
    public ScreenManager sm;

    public String [] fileInputNames = {"dungeon_1_1.txt", "dungeon_1_2.txt", "dungeon_1_3.txt", "dungeon_1_4.txt", "dungeon_1_5.txt", "dungeon_1_6.txt"};

    public final int originalTileSize = 16; //16x16 tile 
    public int scale = 4; //scale for resolution
    public final int tileSize = originalTileSize * scale; //64x64 tile
    public final int screenCol = 16;
    public final int screenRow = 12;
    public final int screenWidth = tileSize * screenCol; //1024px
    public final int screenHeight = tileSize * screenRow; //768px
    public final int mapNumber = 6;

    TileManager tileM = new TileManager(this);
    PlayerMovement playM = new PlayerMovement(this);
    public Map(ScreenManager screen) {
        sm = screen;
        addKeyListener(this);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setFocusable(true);

        ActionListener alg = new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    playM.updatePosition();
                    playM.checkTile();
                    playM.tileSwitchEvent();
                    playM.spriteCount++;
                    repaint();
                }
            };

        Timer t = new Timer(16, alg);
        t.start();
    }

    public void loadNewMap(int indOf)
    {             
        try {
            tileM.mapFilePath = "maps/" + fileInputNames[indOf];
            tileM.getMapData();
        } catch (Exception e1)
        {
            System.out.println("Index array out of bounds. No change to file index of.");
        }
    }

    public void switchToMap() {
        requestFocusInWindow();
    }

    public void switchToMenu()
    {

    }

    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            playM.wActive = true;
            playM.direction = "up";
        }
        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            playM.aActive = true;
            playM.direction = "left";
        }
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            playM.sActive = true;
            playM.direction = "down";
        }
        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            playM.dActive = true;
            playM.direction = "right";
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.out.println("Attempting to switch to Menu.");
            sm.switchTo("Menu");  
            playM.wActive = false;
            playM.aActive = false;
            playM.sActive = false;
            playM.dActive = false;
        }
    } 

    public void keyTyped(KeyEvent e) 
    {

    }

    public void keyReleased(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            playM.wActive = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            playM.aActive = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            playM.sActive = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            playM.dActive = false;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        tileM.draw(gg);
        playM.draw(gg);
    }

    public TileManager getTileM()
    {
        return tileM;
    }
}
