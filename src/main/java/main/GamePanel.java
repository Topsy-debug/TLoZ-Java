package main;

import Entidades.Entidad;
import Entidades.Personaje;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; //16x16 tile size
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48x48 tile size
    public final int maxScreenCol = 16; //Columns 
    public final int maxScreenRow = 12; //Rows
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels
    
    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    
    //FPS
    int FPS = 60;
    
    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    public CollisionCheck cCheck = new CollisionCheck(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;    
    
    //ENTITY AND OBJECT
    public Personaje Link = new Personaje(this, keyH);
    public Entidad obj[] = new Entidad[10];
    public Entidad npc[] = new Entidad[10];
    public Entidad monster[] = new Entidad[20];
    ArrayList<Entidad> entityList = new ArrayList<>();
    
    
    //GAME ESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        
//        playMusic(0);
        gameState = titleState;
    }
    
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime(); 
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
         while (gameThread != null){
             currentTime = System.nanoTime();
             
             delta += (currentTime - lastTime)/drawInterval;
             timer += (currentTime - lastTime);
             lastTime = currentTime;
             
             if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
             }
             if (timer >= 1000000000){
//                 System.out.println("FPS: " + drawCount);
                 drawCount = 0;
                 timer = 0;  
             }
         }   
    }
    
    public void update(){
        if (gameState == playState){
            Link.update();
            for (int i = 0; i < npc.length; i++){
                if (npc[i] != null){
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; i++){
                if (monster[i] != null){
                    if (monster[i].alive == true && monster[i].dying == false){
                        monster[i].update();
                    }
                    if (monster[i].alive == false){
                        monster[i] = null;
                    }
                }
            }
        }
        if (gameState == pauseState){
            //NOTHING 
        }
    }
    
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        //TITLE SCREEN
        if (gameState == titleState){
            ui.draw(g2);
        }
        
        //OTHER SCREENS
        else {
            
            //ADD ENTITIES TO THE LIST
            tileM.draw(g2);   
            entityList.add(Link);
            
            for(int i = 0; i < npc.length; i++){
                if (npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            
            for (int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            
            for (int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            //SORT
            Collections.sort(entityList, new Comparator<Entidad>() {
                @Override
                public int compare(Entidad e1, Entidad e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            entityList.clear();
            
            //UI
            ui.draw(g2);
        }
        g2.dispose();
    }
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
