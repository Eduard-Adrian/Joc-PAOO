package com.paoo.joc.states;

import com.paoo.joc.GamePanel;
import com.paoo.joc.Sound;
import com.paoo.joc.entity.Enemy;
import com.paoo.joc.entity.Player;
import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.objects.ObjectsList;
import com.paoo.joc.tiles.TileManager;
import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Camera;
import com.paoo.joc.util.Vector2f;

import java.awt.Graphics2D;


public class PlayState extends GameState {

    private final Player player;
    private final Enemy enemy;
    private final TileManager tm;
    private final Camera cam;
    private final Sound sound;


    public static Vector2f map;
    public ObjectsList objList;



    public PlayState (GameStateManager gsm){
        super(gsm);
        int xStart = 460;
        int yStart = 1500;
        map = new Vector2f(xStart - ((GamePanel.width / 2f) - 64 / 2f),yStart - ((GamePanel.height / 2f) - 64 / 2f));
        Vector2f.setWorldVar(map.x, map.y);

        cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64, GamePanel.height + 64));

        tm = new TileManager("tile/final-nivel1.xml", cam);
        player = new Player(new Sprite("Entity/player.png",32,32), new Vector2f(xStart, yStart), 64);
        enemy = new Enemy(new Sprite("Entity/enemy-cop.png", 32,32), new Vector2f(460, 1333), 64);
        cam.target(player);
        objList = new ObjectsList(player.getBounds());
        sound = new Sound();
        sound.playMusic(0);


    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        player.update(enemy, objList, sound , player.getUpdating());
        enemy.update(player, enemy.getUpdating());
        cam.update();
        objList.update(player.getBounds());

    }

    public void render(Graphics2D g) {
        tm.render(g);
        player.render(g);
        enemy.render(g);
        cam.render(g);
        objList.render(g);
    }

    public void input(MouseInput mouse, KeyInput key) {
        key.escape.tick();

        if (!gsm.isStateActive(GameStateManager.PAUSE)) {
            player.input(mouse, key);
            cam.input(mouse, key);
            //enemy.setMoving(true);
            enemy.setUpdating(true);
            //player.setMoving(true);
            player.setUpdating(true);
        }

        if (key.escape.clicked) {
            if (gsm.isStateActive(GameStateManager.PAUSE)) {
                gsm.pop(GameStateManager.PAUSE);
                System.out.println("PauseState end.");
            } else {
                gsm.add(GameStateManager.PAUSE);
                //enemy.setMoving(false);
                enemy.setUpdating(false);
                //player.setMoving(false);
                player.setUpdating(false);
                System.out.println("PauseState start.");
            }
        }


    }
}
