package com.paoo.joc.states;


import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;

import java.awt.Graphics2D;

public abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void update();
    public abstract void input(MouseInput mouse, KeyInput key);
    public abstract void render(Graphics2D g);
}
