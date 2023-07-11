package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    
    public TileManager(GamePanel gp){
        this.gp = gp;
        
        tile = new Tile[15];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
        getTileImage();
        loadMap();
        
    }
    
    public void getTileImage(){
            setup(0, "D:\\Programming\\Java\\FantasyGame\\src\\res\\tiles\\grassTile.png", false);
            setup(1, "D:\\Programming\\Java\\FantasyGame\\src\\res\\tiles\\rockTile.png", false);
            setup(2, "D:\\Programming\\Java\\FantasyGame\\src\\res\\tiles\\waterTile.png", true);
            setup(3, "D:\\Programming\\Java\\FantasyGame\\src\\res\\tiles\\dirt2Tile.png", false);
            setup(4, "D:\\Programming\\Java\\FantasyGame\\src\\res\\tiles\\treeTile.png", true);
            setup(5, "D:\\Programming\\Java\\FantasyGame\\src\\res\\tiles\\dirtTile.png", false);
    }
    public void setup(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();
        File img = new File(imagePath);
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(img);
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            
        } catch (IOException e) {
        }
    }
    
    public void loadMap(){

        try {
            
            InputStream is = new FileInputStream("D:\\Programming\\Java\\FantasyGame\\src\\res\\maps\\world01.txt");
            Reader reader = new InputStreamReader(is);
            try (BufferedReader br = new BufferedReader(reader)){
                int col = 0;
                int row = 0;
                
                while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                    
                    String line = br.readLine();
                    
                    while(col < gp.maxWorldCol){
                        
                        String numbers[] = line.split(" ");
                        
                        int num = Integer.parseInt(numbers[col]);
                        
                        mapTileNum[col][row] = num;
                        col++;
                    }
                    if (col == gp.maxWorldCol){
                        col = 0;
                        row++;
                    }
                }
            }
            
        } catch (Exception e) {
        }
    }
    
    public void draw(Graphics2D g2){
        
        int worldCol = 0;
        int worldRow = 0;

        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            
            int tileNum = mapTileNum[worldCol][worldRow];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.Link.worldX + gp.Link.screenX;
            int screenY = worldY - gp.Link.worldY + gp.Link.ScreenY;
            
            if (worldX + gp.tileSize > gp.Link.worldX - gp.Link.screenX && 
                worldX - gp.tileSize < gp.Link.worldX + gp.Link.worldX && 
                worldY + gp.tileSize > gp.Link.worldY - gp.Link.ScreenY && 
                worldY - gp.tileSize < gp.Link.worldY + gp.Link.ScreenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol){               
                worldCol = 0;
                worldRow++;
 
            }
        }
       
    }
}
