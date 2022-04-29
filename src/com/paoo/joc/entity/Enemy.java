package com.paoo.joc.entity;

import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Vector2f;

import java.awt.*;

public class Enemy extends Entity{

    private AABB sense;
    private int r;

    public Enemy(Sprite sprite, Vector2f origin, int size){
        super(sprite, origin, size);

        acc = 1f;
        maxSpeed = 1.5f;
        r = 125;

        bounds.setWidth(42);
        bounds.setHeight(12);
        bounds.setXOffset(10);
        bounds.setYOffset(52);

        sense = new AABB(new Vector2f(origin.x - size / 2, origin.y - size / 2), r);
    }

    public void update(AABB player){
        if (sense.colCircleBox(player)) {
            System.out.println("fafafafafafafafa");
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
