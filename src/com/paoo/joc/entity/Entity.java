
package com.paoo.joc.entity;

import com.paoo.joc.graphics.Animation;
import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.util.AABB;
import com.paoo.joc.util.TileCollison;
import com.paoo.joc.util.Vector2f;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Entity {

    private final int UP = 2;
    private final int DOWN = 0;
    private final int RIGHT = 3;
    private final int LEFT = 1;

    protected int size;
    protected int currentAnimation;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;

    protected boolean attack;
    protected int hitPoints;
    protected boolean invincible = false;
    protected long invincibleLockCounter = 0;
    protected static int ATTACK_DELAY = 30; //30 frames

    protected boolean interact;

    protected float dx;
    protected float dy;

    public boolean xCol = false;
    public boolean yCol = false;

    protected float maxSpeed = 3f;
    protected float acc = 2f;
    protected float deacc = 0.33f;

    private boolean updating = true;

    protected AABB hitBounds;
    protected AABB bounds;

    protected Animation ani;
    protected Sprite sprite;
    public Vector2f pos;

    protected TileCollison tc;


    public Entity(Sprite sprite, Vector2f origin, int size) {
        this.sprite = sprite;
        pos = origin;
        this.size = size;

        bounds = new AABB(origin, size, size);
        hitBounds = new AABB (origin, size, size);
        hitBounds.setXOffset(size / 3f);

        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 20);

        tc = new TileCollison(this);
    }


    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }
    public void setSize(int i) { size = i; }
    public void setMaxSpeed(float f) { maxSpeed = f; }
    public void setAcc(float f) { acc = f; }
    public void setDeacc(float f) { deacc = f; }
    public void setPos(Vector2f v) { pos = v; }
    public void setUpdating(boolean b) { updating = b; }


    public AABB getBounds() { return bounds; }
    public float getDeacc() {return deacc;}
    public float getMaxSpeed() {return  maxSpeed;}
    public float getDx() {return dx;}
    public float getDy() {return dy;}
    public int getSize() { return size; }
    public boolean getUpdating() { return updating; }
    public int getHitPoints() { return hitPoints; }
    public Vector2f getPos() { return pos; }


    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate() {
        if (up) {
            if ((currentAnimation != UP || ani.getDelay() == -1)) {
                setAnimation(UP, sprite.getSpriteArray(UP), 10);
            }
        } else if (down) {
            if ((currentAnimation != DOWN || ani.getDelay() == -1)) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
            }
        } else if (left) {
            if ((currentAnimation != LEFT || ani.getDelay() == -1)) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        } else if (right) {
            if ((currentAnimation != RIGHT || ani.getDelay() == -1)) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    private void setHitBoxDirection() {
        if (up) {
            hitBounds.setYOffset(-size / 2f);
            hitBounds.setXOffset(0);
        } else if (down) {
            hitBounds.setYOffset(size / 2f);
            hitBounds.setXOffset(0);
        } else if (left) {
            hitBounds.setXOffset(-size / 2f);
            hitBounds.setYOffset(0);
        } else if (right) {
            hitBounds.setXOffset(size / 2f);
            hitBounds.setYOffset(0);
        }
    }


    public void update() {
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public abstract void render(Graphics2D g);
    public abstract void input(MouseInput mouse, KeyInput key);

}
