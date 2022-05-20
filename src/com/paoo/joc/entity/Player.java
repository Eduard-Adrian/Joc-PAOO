package com.paoo.joc.entity;

import com.paoo.joc.util.Sound;
import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.entity.objects.ObjectsList;
import com.paoo.joc.util.Vector2f;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Objects;

public class Player extends Entity{

    public static int hitPoints = 10;
    public static int coins = 0;

    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);

        acc = 2f;
        maxSpeed = 3f;

        bounds.setWidth(42);
        bounds.setHeight(12);
        bounds.setXOffset(10);
        bounds.setYOffset(52);
    }

    public static int getCoins() { return coins; }

    private void move(boolean moving) {
        if (moving) {
            if (up) {
                dy -= acc;
                if (dy < -maxSpeed) {
                    dy = -maxSpeed;
                }
            } else {
                if (dy < 0) {
                    dy += deacc;
                    if (dy > 0) {
                        dy = 0;
                    }
                }
            }
            if (down) {
                dy += acc;
                if (dy > maxSpeed) {
                    dy = maxSpeed;
                }
            } else {
                if (dy > 0) {
                    dy -= deacc;
                    if (dy < 0) {
                        dy = 0;
                    }
                }
            }
            if (left) {
                dx -= acc;
                if (dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            } else {
                if (dx < 0) {
                    dx += deacc;
                    if (dx > 0) {
                        dx = 0;
                    }
                }
            }
            if (right) {
                dx += acc;
                if (dx > maxSpeed) {
                    dx = maxSpeed;
                }
            } else {
                if (dx > 0) {
                    dx -= deacc;
                    if (dx < 0) {
                        dx = 0;
                    }
                }
            }
        }

    }

    private void attack(EnemyOldman[] enemyOldman, EnemyPoliceman enemyPoliceman) {
        invincibleLockCounter++;
        if (invincibleLockCounter % ATTACK_DELAY == 0) {
            invincible = false;
        }
        for (int i = 0; i < enemyOldman.length; i++) {
            if (enemyOldman[i] != null) {
                if (attack && hitBounds.collides(enemyOldman[i].getBounds())) {
                    if (!invincible) {
                        enemyOldman[i].hitPoints -= 20;
                        System.out.println("Atacat: " + enemyOldman[i].hitPoints + " HP.");
                        invincible = true;
                    }
                }
            }
        }
        if (enemyPoliceman != null) {
            if (attack && hitBounds.collides(enemyPoliceman.getBounds())) {
                if (!invincible) {
                    enemyPoliceman.hitPoints -= 10;
                    System.out.println("Atacat: " + enemyPoliceman.hitPoints + " HP.");
                    invincible = true;
                }
            }
        }

    }

    private void interact(ObjectsList objList, Sound sound) {
        if (interact && objList.isNear(this.getBounds())) {
            System.out.println("Interactiune cu obiectul " + (objList.getNrOrdine(this.getBounds()) + 1));
            switch (objList.getNrSunet(this.getBounds())) {
                case 1:
                    sound.playSoundEffect(1);
                    break;
                case 2:
                    sound.playSoundEffect(2);
                    break;
                default:
                    break;
            }

            if (Objects.equals(objList.getName(this.getBounds()), "Money")) {
                coins += 100;
            }

            if (Objects.equals(objList.getName(this.getBounds()), "Knife")) {
                System.out.println("Urmatorul nivel.");
            }

            objList.pop(0, objList.getNrOrdine(this.getBounds()));
        }
    }

    public void update(EnemyOldman[] enemyOldman, EnemyPoliceman enemyPoliceman , ObjectsList objList, Sound sound , boolean updating) {
        if (updating) {
            super.update();

            attack(enemyOldman, enemyPoliceman);
            interact(objList, sound);
            move(this.getMoving());

            if (!tc.collisionTile(dx, 0)) {
                pos.x += dx;
                xCol = false;
            } else {
                xCol = true;
            }
            if (!tc.collisionTile(0, dy)) {
                pos.y += dy;
                yCol = false;
            } else {
                yCol = true;
            }
        }

    }


    @Override
    public void render(Graphics2D g) {
        //g.setColor(Color.blue); //suprafata de coliziune a playerului
        //g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

        if (attack) {
            g.setColor(Color.red);
            g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), (int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) (hitBounds.getWidth()), (int) (hitBounds.getHeight()));
        }

        g.drawImage(ani.getImage(),(int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public void input(MouseInput mouse, KeyInput key) {
        if(mouse.getB() == 1){
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

        if(key.interact.down) {
            interact = true;
        } else {
            interact = false;
        }


        if (up && down){
            up = false;
            down = false;
        }
        if (left && right){
            left = false;
            right = false;
        }

    }
}
