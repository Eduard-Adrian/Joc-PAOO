package com.paoo.joc.states;


import com.paoo.joc.GamePanel;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.util.Vector2f;

import java.awt.Graphics2D;


public class GameStateManager {

    private GameState[] states;

    public static Vector2f map;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;


    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
        states = new GameState[4];

        //states[MENU] = new MenuState(this);
        states[PLAY] = new PlayState(this);

    }

    public void pop(int state) {
        states[state] = null;
    }

    public void add(int state){
        if (states[state] != null)
            return;

        if(state == PLAY){
            states[PLAY] = new PlayState(this);
        }
        if(state == MENU) {
            states[MENU] = new MenuState(this);
        }
        if(state == PAUSE){
            states[PAUSE] = new PauseState(this);
        }
        if(state == GAMEOVER){
            states[GAMEOVER] = new GameOverState(this);
        }
    }

    public void addAndPop(int stateAdd, int stateRemove) {
        pop(stateRemove);
        add(stateAdd);
    }

    public boolean isStateActive (int state) {
        return states[state] != null;
    }

    public void update() {
        //System.out.println();
        //System.out.print("Currently active states: ");
        for (int i = 0; i < states.length; i++){
            if (states[i] != null) {
                //System.out.print(i + " ");
                states[i].update();
            }
        }
    }

    public void input(MouseInput mouse, KeyInput key) {
        for (int i = 0; i < states.length; i++){
            if (states[i] != null) {
                states[i].input(mouse, key);
            }
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < states.length; i++){
            if (states[i] != null) {
                states[i].render(g);
            }
        }
    }
}
