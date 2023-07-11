package main;

import Entidades.Entidad;

public class CollisionCheck {

    GamePanel gp;
    
    public CollisionCheck(GamePanel gp){
        this.gp = gp;
    }
    
    public void checkTile(Entidad entidad){
        
        int entidadLeftWorldX = entidad.worldX + entidad.solidArea.x;
        int entidadRightWorldX = entidad.worldX + entidad.solidArea.x + entidad.solidArea.width;
        int entidadTopWorldY = entidad.worldY + entidad.solidArea.y;
        int entidadBottomWorldY = entidad.worldY + entidad.solidArea.y + entidad.solidArea.height;
        
        int entidadLeftCol = entidadLeftWorldX / gp.tileSize;
        int entidadRightCol = entidadRightWorldX / gp.tileSize;
        int entidadTopRow = entidadTopWorldY / gp.tileSize;
        int entidadBottomRow = entidadBottomWorldY / gp.tileSize;
        
        int tileNum1, tileNum2;
        
        switch (entidad.direction) {
            case "up":
                entidadTopRow = (entidadTopWorldY - entidad.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entidadLeftCol][entidadTopRow];
                tileNum2 = gp.tileM.mapTileNum[entidadRightCol][entidadTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entidad.collisionOn = true;
                }
                break;
            case "down":
                entidadBottomRow = (entidadBottomWorldY + entidad.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entidadLeftCol][entidadBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entidadRightCol][entidadBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entidad.collisionOn = true;
                }
                break;
            case "left":
                entidadLeftCol = (entidadLeftWorldX - entidad.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entidadLeftCol][entidadTopRow];
                tileNum2 = gp.tileM.mapTileNum[entidadLeftCol][entidadBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entidad.collisionOn = true;
                }
                break;
            case "right":
                entidadRightCol = (entidadRightWorldX + entidad.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entidadRightCol][entidadTopRow];
                tileNum2 = gp.tileM.mapTileNum[entidadRightCol][entidadBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entidad.collisionOn = true;
                }
                break;    
            default:
                throw new AssertionError();
        }
    }
    
    public int checkObject(Entidad entidad, boolean Link){
        
        int index = 999;
        
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                entidad.solidArea.x = entidad.worldX + entidad.solidArea.x;
                entidad.solidArea.y = entidad.worldY + entidad.solidArea.y;
                
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
                
                switch (entidad.direction) {
                    case "up": entidad.solidArea.y -= entidad.speed; break;
                    case "down": entidad.solidArea.y += entidad.speed; break; 
                    case "right": entidad.solidArea.x -= entidad.speed; break;
                    case "left": entidad.solidArea.x += entidad.speed; break;
                        }
                        if(entidad.solidArea.intersects(gp.obj[i].solidArea)){
                            if(gp.obj[i].collision == true){
                                entidad.collisionOn = true;
                            }
                            if(Link == true){
                                index = i;
                            }
                }
                entidad.solidArea.x = entidad.solidAreaDefaultX;
                entidad.solidArea.y = entidad.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
            
        }
        
        return index;
    }
    
    //NPC or MONSTER
    public int checkEntity(Entidad entidad, Entidad[] target){
        int index = 999;
        
        for(int i = 0; i < target.length; i++){
            
            if(target[i] != null){
                
                entidad.solidArea.x = entidad.worldX + entidad.solidArea.x;
                entidad.solidArea.y = entidad.worldY + entidad.solidArea.y;
                
                target[i].solidArea.x = target[i].worldX + gp.obj[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + gp.obj[i].solidArea.y;
                
                switch (entidad.direction) {
                    
                    case "up": entidad.solidArea.y -= entidad.speed-2; break;
                    case "down": entidad.solidArea.y += entidad.speed+2; break;
                    case "right": entidad.solidArea.x -= entidad.speed-2; break;
                    case "left": entidad.solidArea.x += entidad.speed+2; break;
                }
                if(entidad.solidArea.intersects(target[i].solidArea)){
                    if(target[i] != entidad){
                        entidad.collisionOn = true;
                        index = i;
                    }
                }
                
                entidad.solidArea.x = entidad.solidAreaDefaultX;
                entidad.solidArea.y = entidad.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
            
        }
        
        return index;
    }
    
    public boolean checkPlayer(Entidad entidad){
        boolean contactPlayer = false;
        
        entidad.solidArea.x = entidad.worldX + entidad.solidArea.x;
                entidad.solidArea.y = entidad.worldY + entidad.solidArea.y;
                
                gp.Link.solidArea.x = gp.Link.worldX + gp.Link.solidArea.x;
                gp.Link.solidArea.y = gp.Link.worldY + gp.Link.solidArea.y;
                
                switch (entidad.direction) {
                    case "up": entidad.solidArea.y -= entidad.speed-2; break;
                    case "down":entidad.solidArea.y += entidad.speed+2; break;
                    case "right": entidad.solidArea.x -= entidad.speed-2; break;
                    case "left": entidad.solidArea.x += entidad.speed+2; break;
                        
                }
                if(entidad.solidArea.intersects(gp.Link.solidArea)){
                    entidad.collisionOn = true;
                    contactPlayer = true;
                }
                entidad.solidArea.x = entidad.solidAreaDefaultX;
                entidad.solidArea.y = entidad.solidAreaDefaultY;
                gp.Link.solidArea.x = gp.Link.solidAreaDefaultX;
                gp.Link.solidArea.y = gp.Link.solidAreaDefaultY;
                
                return contactPlayer;
    }
}
