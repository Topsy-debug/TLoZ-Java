package Entidades;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public final class Personaje extends Entidad{

    KeyHandler keyH;
    
    public final int screenX;
    public final int ScreenY;
    
    int standCounter = 0;
    
    public Personaje(GamePanel gp, KeyHandler keyH){
        
        super(gp);
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        ScreenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        
        attackArea.width = 36;
        attackArea.height = 36;
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        getGuardImage();
    }
    
    public void setDefaultValues(){
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 5;
        direction = "up";
        
        //PLAYER STATUS
        maxLife = 10;
        life = maxLife;
    }
    
    public void update(){
        if (attacking == true){
            attacking();
        }
        else if (keyH.spacePressed == true){
            guarding = true;
        }
            
        else if (keyH.upPressed == true || 
            keyH.downPressed == true || 
            keyH.rightPressed == true || 
            keyH.leftPressed == true || 
            keyH.enterPressed == true ){
            
            
            if (keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";    
            }    
            
                //Check TILE COLLISION
                collisionOn = false;
                gp.cCheck.checkTile(this);

                //Check OBJECT COLLISION
                int objIndex = gp.cCheck.checkObject(this, true);
                pickUpObject(objIndex);
                
                //Check NPC COLLISION
                int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
                
                //Check MONSTER COLLISION
                int monstIndex = gp.cCheck.checkEntity(this, gp.monster);
                contactMonster(monstIndex);
                
                //Check EVENT
                gp.eHandler.checkEvent();
                
                //IF COLLISION IS FALSE, PLAYER CAN MOVE
                if (collisionOn == false && keyH.enterPressed == false){

                    switch (direction) {
                        case "up": worldY -= speed;
                            break;
                        case "down": worldY += speed;
                            break;
                        case "left": worldX -= speed;
                            break;
                        case "right": worldX += speed;
                            break;    
                    }
                }
                gp.keyH.enterPressed = false;
                guarding = false;
                
                spriteCounter++;
            if (spriteCounter > 10){
                if (spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 1;
                }    
                guarding = false;
                spriteCounter = 0;
            }
        }
        //Outside of Key IF STATEMENT
        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    
    public void attacking(){
        
        spriteCounter++;
        if (spriteCounter <= 5){
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            
            //Save current WorldX and WorldY
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            
            //Adjust player's worldX and worldY
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //Attack changes to a Solid Area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            //Check MONSTER COLLISION
            int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);
            
            //After checking collision restore original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
            
        }
        if (spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    
    public void pickUpObject(int i){
        
        if(i != 999){
        
        }
    }
    
    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            else {
                attacking = true;
            }    
        } 
        
        
    }
    
    public void contactMonster(int i){
        if (i != 999){
            
            if (invincible == false){
                life -= 1;
                invincible = true;
            }
        }
    }
    
    public void damageMonster(int i){
        if (i != 999){
            if (gp.monster[i].invincible == false){
                
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                
                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                }
            }
        }
        
    }
    
    public void getPlayerImage(){
        up = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkUp.png", gp.tileSize, gp.tileSize);
        up2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkUp2.png", gp.tileSize, gp.tileSize);
        down = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\Linkdown.png", gp.tileSize, gp.tileSize);
        down2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\Linkdown2.png", gp.tileSize, gp.tileSize);
        left = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkLeft.png", gp.tileSize, gp.tileSize);
        left2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkLeft2.png", gp.tileSize, gp.tileSize);
        right = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkRight.png", gp.tileSize, gp.tileSize);
        right2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkRight2.png", gp.tileSize, gp.tileSize);
    }
    
    public void getPlayerAttackImage(){
        attackUp = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackUp.png", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackUp2.png", gp.tileSize, gp.tileSize*2);
        attackDown = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackDown.png", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackDown2.png", gp.tileSize, gp.tileSize*2);
        attackLeft = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackLeft.png", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackLeft2.png", gp.tileSize*2, gp.tileSize);
        attackRight = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackRight.png", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkAttackRight2.png", gp.tileSize*2, gp.tileSize);
        
    }
//    public BufferedImage setup(String imageName){
//        UtilityTool uTool = new UtilityTool();
//        BufferedImage image = null;
//        
//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\"+imageName+".png"));
//            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//        } catch (IOException e) {
//        }
//        return image;
//    }
    public void getGuardImage(){
        guardUp = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkUp.png", gp.tileSize, gp.tileSize);
        guardUp2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkUp2.png", gp.tileSize, gp.tileSize);
        guardDown = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkGuardDown.png", gp.tileSize, gp.tileSize);
        guardDown2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkGuardDown2.png", gp.tileSize, gp.tileSize);
        guardLeft = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkGuardLeft.png", gp.tileSize, gp.tileSize);
        guardRight = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\player\\LinkGuardRight.png", gp.tileSize, gp.tileSize);
        
    }
    
    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x,y,gp.tileSize,gp.tileSize);

        BufferedImage image = null;
        //TEMP VARIABLE
        int tempScreenX = screenX;
        int tempScreenY = ScreenY;
        
        switch (direction) {
            
            case "up":
                if (attacking == false){
                    if (spriteNum == 1){image = up;}
                    if (spriteNum == 2){image = up2;}
                }
                if (attacking == true){
                    tempScreenY = ScreenY - gp.tileSize;
                    if (spriteNum == 1){image = attackUp;}
                    if (spriteNum == 2){image = attackUp2;}
                }
                if (guarding == true){
                    image = guardUp;
                }
                break;
                
            case "down":
                if (attacking == false){
                    if (spriteNum == 1){image = down;}
                    if (spriteNum == 2){image = down2;}
                }
                if (attacking == true){
                    if (spriteNum == 1){image = attackDown;}
                    if (spriteNum == 2){image = attackDown2;}
                }
                if (guarding == true){
                    image = guardDown;
                }
                break;
                
            case "left":
                if (attacking == false){
                    if (spriteNum == 1){image = left;}
                    if (spriteNum == 2){image = left2;}
                }
                if (attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1){image = attackLeft;}
                    if (spriteNum == 2){image = attackLeft2;}
                }
                if (guarding == true){
                    image = guardLeft;
                }
                break;
                
            case "right":
                if (attacking == false){
                    if (spriteNum == 1){image = right;}
                    if (spriteNum == 2){image = right2;}
                }
                if (attacking == true){
                    if (spriteNum == 1){image = attackRight;}
                    if (spriteNum == 2){image = attackRight2;}
                }
                if (guarding == true){
                    image = guardRight;
                }
                break;    
        }
        if (invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
    }
}
