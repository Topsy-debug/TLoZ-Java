package main;

import Entidades.NPC_Mago;
import monster.MON_Slime;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Sword;
import object.OBJ_Sword2;

public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    
    public void setObject(){
        
        gp.obj[0] = new OBJ_Sword(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;
        
        gp.obj[1] = new OBJ_Sword2(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;
        
        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 34 * gp.tileSize;
        gp.obj[2].worldY = 10 * gp.tileSize;
        
        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 12 * gp.tileSize;
        gp.obj[3].worldY = 23 * gp.tileSize;
    }
    
    public void setNPC(){
        gp.npc[0] = new NPC_Mago(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }
    
    public void setMonster(){
        gp.monster[0] = new MON_Slime(gp);
        gp.monster[0].worldX = gp.tileSize*23;
        gp.monster[0].worldY = gp.tileSize*36;
        
        gp.monster[1] = new MON_Slime(gp);
        gp.monster[1].worldX = gp.tileSize*23;
        gp.monster[1].worldY = gp.tileSize*37;
    }
}
