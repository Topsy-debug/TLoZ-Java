package object;

import Entidades.Entidad;
import main.GamePanel;

public class OBJ_Key extends Entidad {
    public OBJ_Key(GamePanel gp){
        super(gp);
        
        nombre = "Key";
        down = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\objects\\Key.png", gp.tileSize, gp.tileSize);
    }
}
