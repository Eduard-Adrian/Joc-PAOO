
package com.paoo.joc.states;


import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PauseState extends GameState {
    public PauseState(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseInput mouse, KeyInput key) {

    }

    @Override
    public void render(Graphics2D g) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("res\\pause.png"));
                g.drawImage(image,0,-110,null);

            } catch (IOException e) {
                System.out.println("ERROR: " + e);
            }
        }

}
