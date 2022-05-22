
package com.paoo.joc.states;

import com.paoo.joc.UI;
import com.paoo.joc.entity.Player;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;


import java.awt.Graphics2D;

public class GameOverState extends GameState {

    private final UI ui;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        ui = new UI(gsm);

    }

    @Override
    public void update() {
        if (gsm.isStateActive(GameStateManager.PLAY) && !gsm.isStateActive(GameStateManager.MENU) && !gsm.isStateActive(GameStateManager.PAUSE)) {
            gsm.pop(GameStateManager.PLAY);
            gsm.add(GameStateManager.GAMEOVER);
            System.out.println("GameOverState start.");
        }
    }

    @Override
    public void input(MouseInput mouse, KeyInput key) {
        key.menu.tick();

        if (key.menu.clicked) {
            if (!gsm.isStateActive(GameStateManager.MENU)) {
                gsm.pop(GameStateManager.GAMEOVER);
                gsm.add(GameStateManager.MENU);
                PlayState.isInGame = false;
                //resetez variabilele statice
                Player.hitPoints = 100;
                UI.levelTime = 0;
                UI.gameTime = 0;
                System.out.println("GameOverState stop.");
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        ui.render(g);
    }


}
