package me.gabryosas.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public List<BufferedImage> images = new ArrayList<>();
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea; //creiamo un rettangolo invisibile / astratto
    public boolean collisionOn = false;


}
