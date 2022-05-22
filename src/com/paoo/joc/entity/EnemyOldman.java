
package com.paoo.joc.entity;

import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.util.AABB;
import com.paoo.joc.util.PrototypeDP;
import com.paoo.joc.util.Vector2f;


import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyOldman extends Entity implements PrototypeDP {

    private Vector2f origin = new Vector2f(0,0);

    private AABB sense;
    private int r;

    private boolean updating = true;


    public EnemyOldman(Sprite sprite, Vector2f origin, int size){
        super(sprite, origin, size);

        acc = 1f;
        maxSpeed = 1.5f;
        r = 200;
        hitPoints = 100;
        ATTACK_DELAY = 45;

        bounds.setWidth(42);
        bounds.setHeight(12);
        bounds.setXOffset(10);
        bounds.setYOffset(52);

        sense = new AABB(new Vector2f(origin.x + size / 2f - r / 2f, origin.y + size / 2f - r / 2f), r);
    }


    public void setSense(AABB sense) { this.sense = sense; }

    private void move (Player player) {
        if (sense.colCircleBox(player.getBounds())) {
            if (pos.y > player.pos.y + 32) {
                dy -= acc;
                up = true;
                down = false;
                if (dy < -maxSpeed) {
                    dy = -maxSpeed;
                }
            } else if (pos.y < player.pos.y - 32) {
                dy += acc;
                up = false;
                down = true;
                if (dy > maxSpeed) {
                    dy = maxSpeed;
                }
            } else {
                dy = 0;
                up = false;
                down = false;
            }

            if (pos.x > player.pos.x + 48) {
                dx -= acc;
                right = false;
                left = true;
                if (dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            } else if (pos.x < player.pos.x - 48) {
                dx += acc;
                left = false;
                right = true;
                if (dx > maxSpeed) {
                    dx = maxSpeed;
                }
            } else {
                dx = 0;
                right = false;
                left = false;
            }
        } else {
            up = false;
            down = false;
            left = false;
            right = false;
            dx = 0;
            dy = 0;
        }

    }

    private void attack (Player player) {
        invincibleLockCounter++;
        if (invincibleLockCounter % ATTACK_DELAY == 0) {
            invincible = false;
        }
        if (!invincible && sense.colCircleBox(player.getBounds())) {
            player.hitPoints -= 2;
            //System.out.println("In raza de atac. HP: " + player.hitPoints);
            invincible = true;
        }
    }

    public void update(Player player, boolean updating){
        if (updating) {
            super.update();

            move(player);
            attack(player);

            if (!tc.collisionTile(dx, 0)) {
                sense.getPos().x += dx;
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                sense.getPos().y += dy;
                pos.y += dy;
            }

        }
    }

    @Override
    public void render(Graphics2D g){
//        g.setColor(Color.green);      //suprafata de coliziune
//        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) (bounds.getWidth()), (int) (bounds.getHeight()));

        g.setColor(Color.red);
        g.drawOval((int) sense.getPos().getWorldVar().x, (int) sense.getPos().getWorldVar().y, r, r);

        g.drawImage(ani.getImage(), (int)(pos.getWorldVar().x), (int)(pos.getWorldVar().y), size, size, null);
    }

    @Override
    public void input(MouseInput mouse, KeyInput key) {

    }

    @Override
    public PrototypeDP getClone() {
        return new EnemyOldman(sprite, origin, size);
    }


}
