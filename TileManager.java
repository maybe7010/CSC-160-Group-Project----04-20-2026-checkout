//outside package 
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.*;
public class TileManager 
{
  // instance variables - replace the example below with your own
  public Tile[] tile;
  Map m;
  public int mapTileNum[][];
  
  //public String [] fileInputNames = {"dungeon_1_1.txt", "dungeon_1_2.txt"};
  //public int finIndOf = 0;
  
  public String mapFilePath;
  public TileManager(Map m)
  {
     this.m = m;
     mapFilePath = "maps/dungeon_1_1.txt";
     
     tile = new Tile[7];
     mapTileNum = new int[m.screenCol][m.screenRow];
     getTileImage();
     getMapData();
  }
  public void getTileImage()
  {
      try{
          tile[0] = new Tile(); 
          tile[0].image = ImageIO.read(new File("maps/map_assets/void.png"));
          tile[1] = new Tile();
          tile[1].image = ImageIO.read(new File("maps/map_assets/dungeon_wall.png"));
          tile[2] = new Tile();
          tile[2].image = ImageIO.read(new File("maps/map_assets/dungeon_floor.png"));
          tile[3] = new Tile();
          tile[3].image = ImageIO.read(new File("maps/map_assets/dungeon_door.png"));
          tile[4] = new Tile();
          tile[4].image = ImageIO.read(new File("maps/map_assets/dungeon_chest.png"));
          tile[5] = new Tile();
          tile[5].image = ImageIO.read(new File("maps/map_assets/dungeon_boss_door_top.png"));
          tile[6] = new Tile();
          tile[6].image = ImageIO.read(new File("maps/map_assets/dungeon_boss_door_bottom.png"));
      }catch(IOException e1)
      {
        e1.printStackTrace();
      }
  }
  
  public String getMapName()
  {
      return this.mapFilePath;
  }
  
  public void getMapData()
  {
        FileInputStream ins = null;

        try {
            ins = new FileInputStream(mapFilePath);
        }
        catch (FileNotFoundException e1)
        {
            System.out.print("Cannot access file.");
            System.exit(1);
        }
          Scanner fr = new Scanner(ins);

          int col = 0;
          int row = 0; 

          while(col < m.screenCol && row < m.screenRow)
          {
              while (col < m.screenCol)
              {
                if (fr.hasNextInt())
                {

                    mapTileNum[col][row] = fr.nextInt();
                    col++;
                }
              }
              if(col == m.screenCol)
              {
                  col = 0;
                  row++;
              }
          }

        fr.close();
  }
  public void draw(Graphics2D gg)
    {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        
        while(col < m.screenCol && row < m.screenRow)
        {
            int tileNum = mapTileNum[col][row];
            if ((((tileNum == 12 || tileNum == 21) || (tileNum == 23 || tileNum == 32)) ||((tileNum == 25 || tileNum == 52) || (tileNum == 45 || tileNum == 54))) || tileNum == 56)
            {
                tileNum = 3;
            }
            
            gg.drawImage(tile[tileNum].image, x, y, m.tileSize, m.tileSize, null);
        
            col++;
            x += m.tileSize;
            if (col == m.screenCol)
            {
                col = 0;
                x = 0;
                row++;
                y += m.tileSize;
            }
        }
    }
}
