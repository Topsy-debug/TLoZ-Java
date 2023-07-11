package object;

import Entidades.Entidad;
import java.io.File;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Sword2 extends Entidad{
    
    public OBJ_Sword2(GamePanel gp){
        super(gp);
        
        nombre = "Thunder Sword";
        down = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\objects\\Sword2.png", gp.tileSize, gp.tileSize);
    }
}
