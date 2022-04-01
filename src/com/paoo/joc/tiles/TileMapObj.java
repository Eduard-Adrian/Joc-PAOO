package com.paoo.joc.tiles;

import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.tiles.blocks.Block;
import com.paoo.joc.tiles.blocks.HoleBlock;
import com.paoo.joc.tiles.blocks.ObjBlock;
import com.paoo.joc.util.Vector2f;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TileMapObj extends TileMap{

    public static  HashMap<String, Block> tmo_block;
    public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        Block tempBlock;
        tmo_block = new HashMap<String, Block>();

        String[] block = data.split(",");
        for (int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if (temp != 0) {
                if (temp == 94) {
                    tempBlock = new HoleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight);
                } else {
                    tempBlock = new ObjBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight), tileWidth, tileHeight);
                }
                tmo_block.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / height)), tempBlock);
            }
        }
    }

    public void render(Graphics2D g) {
        for (Block block: tmo_block.values()) {
            block.render(g);
        }
    }

}

