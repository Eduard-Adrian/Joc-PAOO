package com.paoo.joc.entity;

import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Vector2f;

import java.awt.*;

public class EnemyPoliceman extends Entity{

    private AABB sense;
    private int r;

    private int xMaxCnt = 0;
    private int xMinCnt = 0;
    private int yMaxCnt = 0;
    private int yMinCnt = 0;

    private boolean moving = true;
    private boolean updating = true;

    public EnemyPoliceman(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);

        acc = 1f;
        maxSpeed = 1.5f;
        r = 200;

        bounds.setWidth(42);
        bounds.setHeight(12);
        bounds.setXOffset(10);
        bounds.setYOffset(52);

        sense = new AABB(new Vector2f(origin.x + size / 2 - r / 2, origin.y + size / 2 - r / 2), r);
    }


    private void move (Player player, boolean moving, boolean pLeftRight, boolean pUpDown, int min, int max) {
        if (moving == true) {
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
                if (pLeftRight) pathLeftRight(min, max);
                if (pUpDown) pathUpDown(min,max);
            }
        }

    }
    private void pathLeftRight(int xMin, int xMax) {
        if (pos.x < xMax && xMaxCnt <= xMinCnt) {
            dx += acc;
            left = false;
            right = true;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else if (pos.x > xMin && xMinCnt < xMaxCnt) {
            dx -= acc;
            right = false;
            left = true;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if (pos.x >= xMax)
                xMaxCnt++;
            if (pos.x <= xMin)
                xMinCnt++;
            up = false;
            down = false;
            left = false;
            right = false;
            dx = 0;
            dy = 0;
        }
        dy = 0;
        up = false;
        down = false;

    }
    private void pathUpDown(int yMin, int yMax) {
        if (pos.y < yMax && yMaxCnt <= yMinCnt) {
            dy += acc;
            up = false;
            down = true;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else if (pos.y > yMin && yMinCnt < yMaxCnt) {
            dy -= acc;
            up = true;
            down = false;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if (pos.y >= yMax)
                yMaxCnt++;
            if (pos.y <= yMin)
                yMinCnt++;
            up = false;
            down = false;
            left = false;
            right = false;
            dx = 0;
            dy = 0;
        }
        dx = 0;
        right = false;
        left = false;

    }

    private void attack (Player player) {
        invincibleLockCounter++;
        if (invincibleLockCounter % ATTACK_DELAY == 0) {
            invincible = false;
        }
        if (!invincible && sense.colCircleBox(player.getBounds())) {
            System.out.println("In raza de atac. HP: " + player.hitPoints--);
            invincible = true;
        }
    }

    public void update(Player player, boolean updating, boolean pLeftRight, boolean pUpDown, int min, int max){
        if (updating == true) {
            super.update();

            move(player, moving, pLeftRight, pUpDown, min, max);
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
        g.setColor(Color.green);
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) (bounds.getWidth()), (int) (bounds.getHeight()));

        g.setColor(Color.blue);
        g.drawOval((int) sense.getPos().getWorldVar().x, (int) sense.getPos().getWorldVar().y, r, r);

        g.drawImage(ani.getImage(), (int)(pos.getWorldVar().x), (int)(pos.getWorldVar().y), size, size, null);
    }
}
