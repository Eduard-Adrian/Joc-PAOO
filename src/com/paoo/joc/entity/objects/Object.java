
package com.paoo.joc.entity.objects;

import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Vector2f;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Object {

    protected int width;
    protected int height;
    protected String name = "";
    protected int nrOrdine;
    protected int nrSunet;

    protected BufferedImage image;
    protected Vector2f pos;
    protected AABB playerBounds;


    public Object() {

    }

    public Object(Vector2f pos, AABB playerBounds, int nrOrdine) {
        this.pos = pos;
        this.playerBounds = playerBounds;
        this.nrOrdine = nrOrdine;
    }


    public int getNrOrdine() { return nrOrdine; }
    public int getNrSunet() { return nrSunet; }
    public String getName() { return name; }
    public BufferedImage getImage() { return image; }

    public abstract boolean isNear(AABB playerBounds);

    public void render(Graphics2D g) {
        g.drawImage(image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height, null);

    }


}
