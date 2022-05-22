
package com.paoo.joc.entity.objects;

import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Vector2f;


import java.awt.Graphics2D;

public class ObjectsList {

    static private final int NR_NIVELE = 2;
    private Object[][] objList = new Object[NR_NIVELE][10];


    public ObjectsList (AABB playerBounds) {
        objList[0][0] = new ObjGun(new Vector2f(700,1370), playerBounds, 0);
        objList[0][1] = new ObjKnife(new Vector2f(900,1370), playerBounds, 1);
        objList[0][2] = new ObjMoney(new Vector2f(775,1350), playerBounds, 2, 100);
        objList[0][3] = new ObjMoney(new Vector2f(1760,1520), playerBounds, 3, 100);
        objList[0][4] = new ObjMoney(new Vector2f(1992,1190), playerBounds, 4, 100);
        objList[0][5] = new ObjMoney(new Vector2f(120,917), playerBounds, 5, 100);
        objList[0][6] = new ObjMoney(new Vector2f(120,817), playerBounds, 6, 100);
        objList[0][7] = new ObjMoney(new Vector2f(2465,821), playerBounds, 7, 100);
        objList[0][8] = new ObjDoor(new Vector2f(3030,1350), playerBounds, 8);
    }


    public void render (Graphics2D g) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null) {
                    objList[i][j].render(g);
                }
            }
        }
    }

    public boolean isNear (AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null && objList[i][j].isNear(playerBounds)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void pop (int i, int j) {
        if (objList[i][j] != null && objList[i][j].nrOrdine == j) {
            objList[i][j] = null;
        }
    }

    public int getNrOrdine (AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null && objList[i][j].isNear(playerBounds)) {  //returnez doar pentru obiectul cu care playerul are contact
                    return objList[i][j].getNrOrdine();
                }
            }
        }
        return -1;
    }

    public int getNrSunet (AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null && objList[i][j].isNear(playerBounds)) {  //returnez doar pentru obiectul cu care playerul are contact
                    return objList[i][j].getNrSunet();
                }
            }
        }
        return -1;
    }

    public String getName (AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null && objList[i][j].isNear(playerBounds)) {  //returnez doar pentru obiectul cu care playerul are contact
                    return objList[i][j].getName();
                }
            }
        }
        return "";
    }


}
