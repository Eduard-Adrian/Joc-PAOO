package com.paoo.joc.states;



import com.paoo.joc.GamePanel;
import com.paoo.joc.entity.Enemy;
import com.paoo.joc.entity.Player;
import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.tiles.TileManager;
import com.paoo.joc.util.Vector2f;


import java.awt.Graphics2D;


public class PlayState extends GameState {

    private Player player;
    private Enemy enemy;
    private TileManager tm;

    public static Vector2f map;

    public PlayState (GameStateManager gsm){
        super(gsm);
        int xStart = 460;
        int yStart = 1500;
        map = new Vector2f(xStart - ((GamePanel.width / 2) - 64 / 2),yStart - ((GamePanel.height / 2) - 64 / 2));
        Vector2f.setWorldVar(map.x, map.y);

        tm = new TileManager("tile/final-nivel1.xml");
        player = new Player(new Sprite("Entity/player.png",32,32), new Vector2f(xStart, yStart), 64);
        enemy = new Enemy(new Sprite("Entity/enemy-cop.png", 32,32), new Vector2f(460, 1333), 64);
    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        player.update();
        enemy.update(player.getBounds());
    }

    public void render(Graphics2D g) {
        tm.render(g);
        player.render(g);
        enemy.render(g);
    }

    public void input(MouseInput mouse, KeyInput key) {
        player.input(mouse, key);
    }
}
