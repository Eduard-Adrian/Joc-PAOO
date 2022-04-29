package com.paoo.joc.util;

import com.paoo.joc.entity.Entity;
import com.paoo.joc.tiles.TileMapObj;
import com.paoo.joc.tiles.blocks.Block;
import com.paoo.joc.tiles.blocks.HoleBlock;

public class TileCollison {

    private Entity e;
    private Block block;

    public TileCollison(Entity e){
        this.e = e;
    }

    public boolean collisionTile(float ax, float ay){
        for (int c = 0; c < 4; c++) {
            int xt = (int) ((e.getBounds().getPos().x + ax) + (c % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / 64;
            int yt = (int) ((e.getBounds().getPos().y + ay) + ((int) (c / 2)) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / 64;

            if (TileMapObj.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {
                Block block = TileMapObj.tmo_blocks.get(String.valueOf(xt) + "," + String.valueOf(yt));
                if (block instanceof HoleBlock) {
                    return collisionHole(ax, ay, xt, yt, block);
                }
                return block.update(e.getBounds());
            }

        }
        return false;
    }

    private boolean collisionHole(float ax, float ay, int xt, int yt, Block block) {
        int nextXt = (int) ((((e.getBounds().getPos().x + ax) + e.getBounds().getXOffset()) / 64) + e.getBounds().getWidth() / 64);
        int nextYt = (int) ((((e.getBounds().getPos().y + ay) + e.getBounds().getYOffset()) / 64) + e.getBounds().getHeight() / 64);

        if ((nextXt == yt + 1) || (nextXt == xt + 1) || (nextYt == yt - 1) || (nextXt == xt - 1)) {
            if (TileMapObj.tmo_blocks.containsKey(String.valueOf(nextXt) + "," + String.valueOf(nextYt))) {
                Block blockNeighbor = TileMapObj.tmo_blocks.get(String.valueOf(nextXt) + "," + String.valueOf(nextYt));
                return blockNeighbor.update(e.getBounds());
            }
        } else {
            if (block.isInside(e.getBounds())) {
                block.update(e.getBounds());
            }
        }
        return false;
    }
}
