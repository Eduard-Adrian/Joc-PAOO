package com.paoo.joc.tiles.blocks;

import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjBlock extends Block{
    public ObjBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return true;
    }

    public void render (Graphics2D g) {
        super.render(g);
        g.setColor(Color.white);
        g.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, w, h);
    }
}

