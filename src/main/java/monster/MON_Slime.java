package monster;

import Entidades.Entidad;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;

public class MON_Slime extends Entidad {
     public MON_Slime(GamePanel gp){
         super(gp);
         
        type = 2; 
        nombre = "Chuchu";
        speed = 2;
        maxLife = 4;
        life = maxLife;
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 33;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
     }
    public void getImage(){
        
        up = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu5.png", gp.tileSize, gp.tileSize);
        up2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu6.png", gp.tileSize, gp.tileSize);
        down = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu.png", gp.tileSize, gp.tileSize);
        down2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu2.png", gp.tileSize, gp.tileSize);
        left = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu.png", gp.tileSize, gp.tileSize);
        left2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu2.png", gp.tileSize, gp.tileSize);
        right = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu3.png", gp.tileSize, gp.tileSize);
        right2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\monst\\Chuchu4.png", gp.tileSize, gp.tileSize);
    }
    
    public void setAction(){
        actionLockCounter++;
        
        if (actionLockCounter == 120){
        
            Random random = new Random();
            int i = random.nextInt(100)+1; //Picks one number from 1 to 100

            if (i <= 25){
                direction = "up";
            }
            if (i > 25 && i <= 50){
                direction = "down";
            }
            if (i > 50 && i <= 75){
                direction = "left";
            }
            if (i > 75 && i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    
    public void damageReaction(){
        
        actionLockCounter = 0;
        direction = gp.Link.direction;
            
    }
}
