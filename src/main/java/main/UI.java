package main;

import Entidades.Entidad;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import object.OBJ_Heart;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font nintendoNES;
    BufferedImage fullHeart, halfHeart, noneHeart;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    
    
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    
    public UI(GamePanel gp){
        
        this.gp = gp;
        
        nintendoNES = new Font("Consolas", Font.PLAIN, 40);
//        try {
//            
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        catch (FontFormatException e) {
//            e.printStackTrace();
//        }
       //CREATE HUD OBJECT
        Entidad heart = new OBJ_Heart(gp);
        fullHeart = heart.image;
        halfHeart = heart.image2;
        noneHeart = heart.image3;
        
        
    }
    
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    
    public void draw(Graphics2D g2){
        
        this.g2 = g2;
        
        g2.setFont(nintendoNES);
        g2.setColor(Color.white);
        
        //TITLE STATE
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        
        //Play state
        if (gp.gameState == gp.playState){
            drawPlayerLife();
        }
        //Pause state
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //Dialogue state
        if (gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
    }
    
    public void drawPlayerLife(){
//        gp.Link.life = 3;
        
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        
        //DRAW EMPTY HEART
        while (i < gp.Link.maxLife/2){
            g2.drawImage(noneHeart, x, y, null);
            i++;
            x += gp.tileSize;
        }
        //RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        
        //DRAW CURRENT LIFE
        while(i < gp.Link.life){
            g2.drawImage(halfHeart, x, y, null);
            i++;
            if (i < gp.Link.life){
                g2.drawImage(fullHeart, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    
    public void drawTitleScreen(){
        
        g2.setColor(new Color(70,120,80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,75F));
        String text = "The Legend Of Link";
        int x = getCenteredText(text);
        int y = gp.tileSize * 3;
        
        //SHADOW
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        
        //MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        
        //LINK IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize*2)/2;
        y += gp.tileSize *2;
        g2.drawImage(gp.Link.down, x, y, gp.tileSize*2, gp.tileSize*2, null);
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
        
        text = "NEW GAME";
        x = getCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x - gp.tileSize, y);
        }
        
        text = "LOAD GAME";
        x = getCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x - gp.tileSize, y);
        }
        
        text = "QUIT";
        x = getCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2){
            g2.drawString(">", x - gp.tileSize, y);
        }
    }
    
    public void drawPauseScreen(){
        
        g2.setFont(nintendoNES.deriveFont(Font.PLAIN, 80F));
        
        String text = "PAUSED";
        
        int x = getCenteredText(text);
        int y = gp.screenHeight / 2;
         
        g2.drawString(text, x, y);
    }
    
    public void drawDialogueScreen(){
        
        //WINDOW
        int x = gp.tileSize *2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        
        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        
        for (String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
        
    }
    public void drawSubWindow(int x, int y, int width, int height){
        
        
        Color c = new Color(0,0,0, 180);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height - 10, 25, 25 );
    }
    
    public int getCenteredText(String text){
        
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
 