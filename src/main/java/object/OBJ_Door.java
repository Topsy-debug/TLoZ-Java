package object;

import Entidades.Entidad;
import java.io.File;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Door extends Entidad{
    
    public OBJ_Door(GamePanel gp){
        super(gp);
        
        nombre = "Door";
        down = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\objects\\Door.png", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
