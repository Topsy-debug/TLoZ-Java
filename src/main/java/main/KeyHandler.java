package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, out, JPressed, enterPressed;
    
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        //TITLE STATE 
        if(gp.gameState == gp.titleState){
            if (code == KeyEvent.VK_UP){
                gp.ui.commandNum -= 1;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_DOWN){
                gp.ui.commandNum += 1;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 1){
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 3){
                    System.exit(0);
                }
            }
        }
        
        //PLAY ESTATE
        else if (gp.gameState == gp.playState){
            
            if (code == KeyEvent.VK_W){
                upPressed = true;
            }
            if (code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S){
                downPressed = true;
            }
            if (code == KeyEvent.VK_D){
                rightPressed = true;
            }
            //Set pause
            if (code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.pauseState;
            } 
            if (code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
            if (code == KeyEvent.VK_J){
                JPressed = true;
            }
            if (code == KeyEvent.VK_K){
                System.exit(0);
                gp.gameState = gp.titleState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }
        //PAUSE ESTATE
        if(gp.gameState == gp.pauseState){
            //continue playing
            if (code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        }
        
        //DIALOGUE STATE
        if (gp.gameState == gp.dialogueState){
            if (code == KeyEvent.VK_SPACE){
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
                enterPressed = false;
            }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
    
}
