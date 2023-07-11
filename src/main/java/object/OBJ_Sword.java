package object;

import Entidades.Entidad;
import java.io.File;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Sword extends Entidad{
    
    public OBJ_Sword(GamePanel gp){
        super(gp);
        
        nombre = "Master Sword";
        down = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\objects\\Sword1.png", gp.tileSize, gp.tileSize);
    }
}
