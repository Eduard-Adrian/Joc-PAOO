package com.paoo.joc.states;



import com.paoo.joc.GamePanel;
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

    public static Vector2f map;

    public PlayState (GameStateManager gsm){
        super(gsm);
        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);

        tm = new TileManager("tile/untitled.xml");
        player = new Player(new Sprite("Entity/b.png",64,64), new Vector2f(0 + (GamePanel.width / 2) - 64 / 2,0 + (GamePanel.height / 2) - 64 / 2), 64);
    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
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
