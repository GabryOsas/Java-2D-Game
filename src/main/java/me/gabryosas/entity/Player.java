package me.gabryosas.entity;

import me.gabryosas.GamePanel;
import me.gabryosas.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    final String[] names = {"down",  "up", "right", "left", "idle"};
    final int ANIMATION = 2;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32); //hitbox

        setDefaultValues();
        getPlayerImage();
    }
    public  void setDefaultValues(){
        worldX = (14 * gp.tileSize);
        worldY = (17 * gp.tileSize);
        speed = 4;
        direction = names[0];
    }

    public void getPlayerImage(){
        try {
            for (int i = 0, j = 0, z = 0, y = 1; i < names.length * ANIMATION; i++) {
                images.add(ImageIO.read(getClass().getResourceAsStream("/player/boy_" + names[j] + "_" + y + ".png")));
                z++;
                y++;
                if (z == ANIMATION) {
                    j++;
                    z = 0;
                }
                if (y > ANIMATION) {
                    y = 1;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

        /*Y se aumanta va sotto perché la coordinata di base si trova a (0; 0)
        X aumenta va a destra*/

        if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.rightPressed) {
            direction = "right";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else {
            direction = "idle";
        }

        collisionOn = false;
        gp.cChecker.checkTile(this); //controlliamo se il giocatore sta colpendo qualcosa (collision)

        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12){

            if (spriteNum == ANIMATION) {
                spriteNum = 1;
            } else {
                spriteNum++;
            }

            /*if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }*/
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2){
        /*g2.setColor(Color.WHITE);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);*/

        BufferedImage image = null;

        //TODO rendere il sistema dinamico in base ad ANIMATION

        int[] values = new int[ANIMATION];

        for (int i = 0, j = 0; i < names.length; i++) {
            if (direction.equalsIgnoreCase(names[i])) {
                for (int z = 0; z < values.length; z++) {
                    values[z] = (i * ANIMATION) + j;
                    j++;
                }
            }
        }

        image = images.get(values[spriteNum - 1]);

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
