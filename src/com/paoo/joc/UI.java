package com.paoo.joc;


import com.paoo.joc.entity.objects.ObjMoney;

import java.awt.*;
import java.awt.image.BufferedImage;


public class UI {

    Font arial32;
    BufferedImage image;
    ObjMoney money;

    public UI() {
        arial32 = new Font("Arial", Font.PLAIN, 32);
        money = new ObjMoney();
        image = money.getImage();

    }

    public void render (Graphics2D g) {
        g.setFont(arial32);
        g.setColor(Color.black);
        g.drawImage(image,  5,  10, 48, 48, null);
        g.drawString("Money = ", 50, 49);


    }

}
