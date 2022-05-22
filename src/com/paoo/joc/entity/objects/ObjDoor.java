
package com.paoo.joc.entity.objects;

import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Vector2f;


import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;

public class ObjDoor extends Object{


    public ObjDoor(Vector2f pos, AABB playerBounds, int nrOrdine) {
        super(pos, playerBounds, nrOrdine);
        name = "Door";
        nrSunet = 2;
        width = 64;
        height = 64;
        try {
            image = ImageIO.read(new File("res/objects/door.png"));
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }


    public boolean isNear(AABB playerBounds) {
        if (playerBounds.getPos().x + playerBounds.getXOffset() + playerBounds.getWidth() < pos.x) return false;
        if (playerBounds.getPos().y + playerBounds.getYOffset() + playerBounds.getHeight() < pos.y) return false;
        if (playerBounds.getPos().x + playerBounds.getXOffset() > pos.x + this.width) return false;
        if (playerBounds.getPos().y + playerBounds.getYOffset() > pos.y + this.height) return false;
        return true;
    }

    public void render(Graphics2D g) {
        super.render(g);
    }


}
