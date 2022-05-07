package com.paoo.joc.objects;

import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Vector2f;

import java.awt.*;

public class ObjectsList {

    static private int NR_NIVELE = 1;
    private Object[][] objList = new Object[NR_NIVELE][5];

    public ObjectsList (AABB playerBounds) {
        objList[0][0] = new ObjMoney(new Vector2f(700,1370), playerBounds, 0);
        objList[0][1] = new ObjKnife(new Vector2f(900,1370), playerBounds, 1);
        objList[0][2] = new ObjMoney(new Vector2f(1100,1370), playerBounds, 2);
        objList[0][3] = new ObjKnife(new Vector2f(1300,1370), playerBounds, 3);
        objList[0][4] = new ObjMoney(new Vector2f(1500,1370), playerBounds, 4);
    }


    public void render(Graphics2D g) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null) {
                    objList[i][j].render(g);
                }
            }
        }
    }

    public void update(AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null) {
                    objList[i][j].update(playerBounds);
                }
            }
        }
    }


    public boolean isNear(AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null && objList[i][j].isNear(playerBounds)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getNrOrdine(AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null && objList[i][j].isNear(playerBounds)) {  //returnez doar pentru obiectul cu care playerul are contact
                    return objList[0][j].getNrOrdine();
                }
            }
        }
        return -1;
    }

    public int getNrSunet(AABB playerBounds) {
        for (int i = 0; i < NR_NIVELE; i++) {
            for (int j = 0; j < objList[i].length; j++) {
                if (objList[i][j] != null && objList[i][j].isNear(playerBounds)) {  //returnez doar pentru obiectul cu care playerul are contact
                    return objList[0][j].getNrSunet();
                }
            }
        }
        return -1;
    }

    public void pop (int i, int j) {
        if (objList[i][j] != null && objList[i][j].nrOrdine == j) {
            objList[i][j] = null;
        }
    }
}
