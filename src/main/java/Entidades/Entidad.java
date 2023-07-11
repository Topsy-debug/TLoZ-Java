package Entidades;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import tile.Tile;

public class Entidad {
    public GamePanel gp;
    
    public int worldX,worldY;
    public int speed;
    public String nombre;
    
    public BufferedImage up, up2, down, down2, left, left2, right, right2; 
    public BufferedImage attackUp, attackUp2, attackDown, attackDown2, attackRight, attackRight2, attackLeft, attackLeft2;
    public BufferedImage guardUp, guardUp2, guardDown, guardDown2, guardLeft, guardRight;
    public String direction = "down";
    
    public int spriteCounter = 0;
    public int spriteNum = 1;
    
    public boolean invincible = false;
    public int invincibleCounter = 0;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean guarding = false;
    
    
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public int type;//0 = Link, 1 = NPC, 2 = Monster
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    
    //Character status
    public int maxLife;
    public int life;
    
    public Entidad(GamePanel gp){
        this.gp = gp;
    }
    
    public void setAction(){}
    
    public void damageReaction(){}
    
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 5;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        
        switch (gp.Link.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    
    public void update(){
        
        setAction();
        
        collisionOn = false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this, false);
        gp.cCheck.checkEntity(this,gp.npc);
        gp.cCheck.checkEntity(this,gp.monster);
        boolean contactPlayer = gp.cCheck.checkPlayer(this);
        
        if (this.type == 2 && contactPlayer == true){
            if(gp.Link.invincible == false){
                gp.Link.life -=1;
                gp.Link.invincible = true;
            }
        }
        
        if (collisionOn == false){
                    switch (direction) {
                        case "up": worldY -= (speed);
                            break;
                        case "down": worldY += speed;
                            break;
                        case "left": worldX -= speed;
                            break;
                        case "right": worldX += speed;
                            break;    
                    }
                }

                spriteCounter++;
            if (spriteCounter > 10){
                if (spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 1;
                }

                spriteCounter = 0;
            }
            
            if (invincible == true){
                invincibleCounter++;
            if (invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        
        int screenX = worldX - gp.Link.worldX + gp.Link.screenX;
        int screenY = worldY - gp.Link.worldY + gp.Link.ScreenY;
            
        if (worldX + gp.tileSize > gp.Link.worldX - gp.Link.screenX && 
            worldX - gp.tileSize < gp.Link.worldX + gp.Link.worldX && 
            worldY + gp.tileSize > gp.Link.worldY - gp.Link.ScreenY && 
            worldY - gp.tileSize < gp.Link.worldY + gp.Link.ScreenY){
            
            switch (direction) {
            
                case "up":

                    if (spriteNum == 1){
                    image = up;}
                    if (spriteNum == 2){
                        image = up2;
                    }
                    break;

                case "down":

                    if (spriteNum == 1){
                    image = down;}
                    if (spriteNum == 2){
                        image = down2;
                    }
                    break;

                case "left":

                    if (spriteNum == 1){
                    image = left;}
                    if (spriteNum == 2){
                        image = left2;
                    }
                    break;

                case "right":

                    if (spriteNum == 1){
                    image = right;}
                    if (spriteNum == 2){
                        image = right2;
                    }
                    break;    
            }
            
            //Monster health bar
            if (type == 2 && hpBarOn == true){
                
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale * life;
                
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY - 16, gp.tileSize+2, 12);
                
                g2.setColor(new Color(255, 0 ,30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
                
                hpBarCounter++;
                if(hpBarCounter > 60){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }
            
            if (invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            if (dying == true){
                dyingAnimation(g2);
            }
            
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2, 1F);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        
        dyingCounter++;
        
        int i = 5;
        
        if (dyingCounter <= i){changeAlpha(g2, 0);}
        if (dyingCounter >i && dyingCounter <=i*2){changeAlpha(g2, 1);}
        if (dyingCounter >i*2 && dyingCounter <=i*3){changeAlpha(g2, 0);}
        if (dyingCounter >i*3 && dyingCounter <=i*4){changeAlpha(g2, 1);}
        if (dyingCounter >i*4 && dyingCounter <=i*5){changeAlpha(g2, 0);}
        if (dyingCounter >i*5 && dyingCounter <=i*6){changeAlpha(g2, 1);}
        if (dyingCounter >i*6 && dyingCounter <=i*7){changeAlpha(g2, 0);}
        if (dyingCounter >i*7 && dyingCounter <=i*8){changeAlpha(g2, 1);}
        if (dyingCounter > i*8){dying = false;alive = false;}
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        File img = new File(imagePath); 
        
        try {
            image = ImageIO.read(img);
            image = uTool.scaleImage(image, width, height);
            
        } catch (IOException e) {
        }
        return image;
    }
}
