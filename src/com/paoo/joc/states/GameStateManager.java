package com.paoo.joc.states;


import com.paoo.joc.GamePanel;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.util.Vector2f;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private GameState states[];

    public static Vector2f map;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;

    public int onTopState = 0;

    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);
        states = new GameState[4];

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
        if(state == MENU){
            states[MENU] = new MenuState(this);
        }
        if(state == PAUSE){
            states[PAUSE] = new PauseState(this);
        }
        if(state == GAMEOVER){
            states[GAMEOVER] = new GameOverState(this);
        }
    }

    public void addAndPop(int state){
        addAndPop(state, 0);
    }

    public void addAndPop(int state, int remove) {
        pop(state);
        add(state);
    }

    public boolean isStateActive (int state) {
        return states[state] != null;
    }

    public void update() {
        for (int i = 0; i < states.length; i++){
            if (states[i] != null) {
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
