package object;

import Entidades.Entidad;
import java.io.File;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Heart extends Entidad{

    
    public OBJ_Heart(GamePanel gp){
        super(gp);    
        
        nombre = "Heart";
        image = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\objects\\FullHeart.png", gp.tileSize, gp.tileSize);
        image2 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\objects\\HalfHeart.png", gp.tileSize, gp.tileSize);
        image3 = setup("D:\\Programming\\Java\\FantasyGame\\src\\res\\objects\\EmptyHeart.png", gp.tileSize, gp.tileSize);
    }
}
