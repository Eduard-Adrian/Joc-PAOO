
package com.paoo.joc.tiles;

import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.util.Camera;
import com.paoo.joc.util.InvalidTileException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;

public class TileManager {

    public static ArrayList<TileMap> tm;
    public Camera cam;


    public TileManager() {
        tm = new ArrayList<>();
    }

    public TileManager (String path, Camera cam) {
        tm = new ArrayList<TileMap>();
        addTileMap (path, 64, 64, cam);
    }

    public TileManager (String path, int blockWidth, int blockHeight, Camera cam) {
        tm = new ArrayList<TileMap>();
        addTileMap (path, blockWidth, blockHeight, cam);
    }


    private void addTileMap(String path, int blockWidth, int blockHeight, Camera cam) {
        String imagePath;
        this.cam = cam;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers = 0;
        Sprite sprite;

        String[] data = new String[20];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
            tileColumns = Integer.parseInt(eElement.getAttribute("columns"));


            sprite = new Sprite("tile/" + imagePath + ".png", tileWidth, tileHeight);
            //tileColumns = sprite.getSpriteSheetWidth() / tileWidth;

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for (int i = 0; i < layers; i++){
                node = list.item(i);
                eElement = (Element) node;
                if (i <= 0){
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();


                //verificare citire xml
                if (data[i].contains("2684354793 ")) {
                    throw new InvalidTileException("Invalid tile.");
                }
                //System.out.println("------------------------------------------------------------------------------------\n" + data[i]);


                if (i == 1 || i == 0) { //layers 0 si 1 sunt cu tileuri fara proprietati
                    tm.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                } else { // restul layers au proprietati de coliziuni/blocuri de fundal
                    tm.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }

                cam.setLimit(width * blockWidth, height * blockHeight);

            }
        } catch (Exception e) {
            System.out.println("ERROR: Could not read tilemap.  " + e);
        }

    }

    public void render(Graphics2D g) {
        if (cam == null)
            return;
        for (int i = 0; i < tm.size(); i++) {
            tm.get(i).render(g, cam.getBounds());
        }
    }


}
