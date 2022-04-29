package com.paoo.joc.entity;

import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.states.PlayState;
import com.paoo.joc.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;

public class Player extends Entity{

    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);

        acc = 2f;
        maxSpeed = 3f;

        bounds.setWidth(42);
        bounds.setHeight(12);
        bounds.setXOffset(10);
        bounds.setYOffset(52);
    }

    public void move() {
        if(up) {
            dy -= acc;
            if(dy < -maxSpeed){
                dy = -maxSpeed;
            }
        } else {
            if (dy < 0) {
                dy += deacc;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }

        if(down) {
            dy += acc;
            if(dy > maxSpeed){
                dy = maxSpeed;
            }
        } else {
            if (dy > 0) {
                dy -= deacc;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }

        if(left) {
            dx -= acc;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        } else {
            if (dx < 0) {
                dx += deacc;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }

        if(right) {
            dx += acc;
            if(dx > maxSpeed){
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }

    }

    public void update() {
        super.update();
        move();
        if (!tc.collisionTile(dx,0)){
            PlayState.map.x += dx;
            pos.x += dx;
        }
        if (!tc.collisionTile(0,dy)){
            PlayState.map.y += dy;
            pos.y += dy;
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.blue); //suprafata de coliziune a playerului
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
        g.drawImage(ani.getImage(),(int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public void input(MouseInput mouse, KeyInput key) {

        if(mouse.getB() == 1){  //verificare input mouse
            System.out.println("Player pos: " + pos.x + ", " + pos.y);
        }

        if(key.up.down) {
            up = true;
        } else {
            up = false;
        }
        if(key.down.down) {
            down = true;
        } else {
            down = false;
        }
        if(key.left.down) {
            left = true;
        } else {
            left = false;
        }
        if(key.right.down) {
            right = true;
        } else {
            right = false;
        }

        if(key.attack.down) {
            attack = true;
        } else {
                attack = false;
        }
    }
}
