
package com.paoo.joc.tiles;

import com.paoo.joc.util.AABB;


import java.awt.Graphics2D;

public abstract class TileMap {
    public abstract void render(Graphics2D g, AABB cam);

}
