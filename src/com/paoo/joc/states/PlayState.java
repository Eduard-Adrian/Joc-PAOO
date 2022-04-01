package com.paoo.joc.states;



import com.paoo.joc.entity.Player;
import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.tiles.TileManager;
import com.paoo.joc.util.Vector2f;


import java.awt.Graphics2D;


public class PlayState extends GameState {

    private Player player;
    private TileManager tm;

    public PlayState (GameStateManager gsm){
        super(gsm);
        tm = new TileManager("tile/untitled.xml");

        player = new Player(new Sprite("Entity/b.png",64,64), new Vector2f(100,100), 64);
    }

    public void update() {
        player.update();
    }

    public void render(Graphics2D g) {
        tm.render(g);
        player.render(g);
    }

    public void input(MouseInput mouse, KeyInput key) {
        player.input(mouse, key);
    }
}
