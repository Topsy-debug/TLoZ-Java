package Entidades;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPC_Mago extends Entidad{
    public NPC_Mago(GamePanel gp){
        super(gp);
        
        direction = "down";
        speed = 1;
        
        getNpcImage();
        setDialogue();
    }
    
    public void getNpcImage(){
        up = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\MagoBack.png", gp.tileSize, gp.tileSize);
        up2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\MagoBack2.png", gp.tileSize, gp.tileSize);
        down = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\Mago.png", gp.tileSize, gp.tileSize);
        down2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\Mago1.png", gp.tileSize, gp.tileSize);
        left = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\Mago.png", gp.tileSize, gp.tileSize);
        left2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\Mago1.png", gp.tileSize, gp.tileSize);
        right = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\Mago2.png", gp.tileSize, gp.tileSize);
        right2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\npc\\Mago21.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue(){
        
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So you are here...";
        dialogues[2] = "I used to be the greatest Wizard \nof these lands, but now I just \ncan give you mana and restore your \nhealth";
        dialogues[3] = "If you want to restore your health \ngo to the pond above you.";
        dialogues[4] = "Good luck.";
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
    
    public void speak(){
        super.speak();
    }
}
