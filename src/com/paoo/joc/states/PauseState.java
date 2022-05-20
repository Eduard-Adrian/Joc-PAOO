
package com.paoo.joc.states;

import com.paoo.joc.UI;
import com.paoo.joc.entity.Player;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;

import java.awt.Graphics2D;


public class PauseState extends GameState {

    private final UI ui;


    public PauseState(GameStateManager gsm) {
        super(gsm);
        ui = new UI(gsm);

    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseInput mouse, KeyInput key) {
        key.menu.tick();
        key.escape.tick();

        if (key.menu.clicked) {
            if (gsm.isStateActive(GameStateManager.MENU)) {
                gsm.pop(GameStateManager.MENU);
                System.out.println("MenuState end.");
            } else {
                gsm.pop(GameStateManager.PAUSE);
                gsm.add(GameStateManager.MENU);
                System.out.println("MenuState start.");
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        ui.render(g);
    }

}
