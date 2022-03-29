package com.paoo.joc.states;



import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;

import java.awt.Graphics2D;
import java.awt.Color;

public class PlayState extends GameState {

    public PlayState (GameStateManager gsm){
        super(gsm);
    }

    public void update() {

    }

    public void render(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect(20,20,100,100);
    }

    public void input(MouseInput mouse, KeyInput key) {
        if(key.up.down){
            System.out.println("aaaaaaaaaaaaaa");
        }
    }
}
