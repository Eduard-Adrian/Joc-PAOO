
package com.paoo.joc.graphics;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;

    private int w;
    private int h;
    private int wSprite;
    private int hSprite;


    public Sprite(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        SPRITESHEET = loadSprite(file);
        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {
        this.w = w;
        this.h = h;
        SPRITESHEET = loadSprite(file);
        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }


    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    public void setWidth(int i) {
        w = i;
        wSprite = SPRITESHEET.getWidth() / w;
    }
    public void setHeight(int i) {
        h = i;
        hSprite = SPRITESHEET.getHeight() / h;
    }

    public int getWidth() {
        return w;
    }
    public int getHeight() {
        return h;
    }
    public BufferedImage getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }
    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];
    }


    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
            System.out.println("ERROR: " + e);
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];
            for (int y = 0; y < hSprite; y++) {
                for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }


}