package main;

import java.awt.Rectangle;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    
    public EventHandler(GamePanel gp){
        this.gp = gp;
        
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 21;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
        
        
    }
    public void checkEvent(){
        if(hit(23,7,"up") == true){heal(gp.dialogueState);}
    } 
    
    public boolean hit(int eventCol, int eventRow, String reqDirection){
        
        boolean hit = false;
        
        gp.Link.solidArea.x = gp.Link.worldX + gp.Link.solidArea.x;
        gp.Link.solidArea.y = gp.Link.worldY + gp.Link.solidArea.y;
        eventRect.x = eventCol * gp.tileSize + eventRect.x;
        eventRect.y = eventRow * gp.tileSize + eventRect.y;
        
        if(gp.Link.solidArea.intersects(eventRect)){
            if(gp.Link.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }
        
        gp.Link.solidArea.x = gp.Link.solidAreaDefaultX;
        gp.Link.solidArea.y = gp.Link.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;
        
        return hit;
    }
    
    public void heal(int gameState){
        if(gp.keyH.enterPressed ==  true){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "The Wizard has blessed you by the \npower of the pond. \nYou recover your life";
            gp.Link.life = gp.Link.maxLife;
        }
        
        gp.keyH.enterPressed = false;
    }
}
