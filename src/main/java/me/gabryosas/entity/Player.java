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

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }
    public  void setDefaultValues(){
        x = 100;
        y = 100;
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
           y -= speed;
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
        } else {
            direction = "idle";
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

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
