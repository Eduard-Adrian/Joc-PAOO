package com.paoo.joc.states;

import com.paoo.joc.GamePanel;
import com.paoo.joc.UI;
import com.paoo.joc.entity.EnemyPoliceman;
import com.paoo.joc.util.Sound;
import com.paoo.joc.entity.EnemyOldman;
import com.paoo.joc.entity.Player;
import com.paoo.joc.graphics.Sprite;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.entity.objects.ObjectsList;
import com.paoo.joc.tiles.TileManager;
import com.paoo.joc.util.AABB;
import com.paoo.joc.util.Camera;
import com.paoo.joc.util.Vector2f;

import java.awt.Graphics2D;


public class PlayState extends GameState {

    private final Player player;
    private final EnemyOldman enemyOldMan;
    private final EnemyPoliceman enemyPoliceman;
    private final TileManager tm;
    private final Camera cam;
    private final Sound music;
    private final Sound soundEffect;
    private UI ui;

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

        enemyOldMan = new EnemyOldman(new Sprite("Entity/enemy-oldman.png", 32,32), new Vector2f(460, 1333), 64);
        enemyPoliceman = new EnemyPoliceman(new Sprite("Entity/enemy-cop.png", 32, 32), new Vector2f(1000, 1111), 64);
        player = new Player(new Sprite("Entity/player.png",32,32), new Vector2f(xStart, yStart), 64);

        cam.target(player);
        objList = new ObjectsList(player.getBounds());

        music = new Sound();
        //music.playMusic(0);
        soundEffect = new Sound();

        ui = new UI();


    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        player.update(enemyOldMan, enemyPoliceman, objList, soundEffect , player.getUpdating());
        enemyOldMan.update(player, enemyOldMan.getUpdating());
        enemyPoliceman.update(player, enemyPoliceman.getUpdating(), false, true, 999, 1377);
        cam.update();

    }

    public void render(Graphics2D g) {
        tm.render(g);
        enemyOldMan.render(g);
        enemyPoliceman.render(g);
        player.render(g);
        cam.render(g);
        objList.render(g);
        ui.render(g);

    }

    public void input(MouseInput mouse, KeyInput key) {
        key.escape.tick();

        if (!gsm.isStateActive(GameStateManager.PAUSE)) {
            player.input(mouse, key);
            cam.input(mouse, key);
            enemyOldMan.setUpdating(true);
            enemyPoliceman.setUpdating(true);
            player.setUpdating(true);
        }

        if (key.escape.clicked) {
            if (gsm.isStateActive(GameStateManager.PAUSE)) {
                gsm.pop(GameStateManager.PAUSE);
                System.out.println("PauseState end.");
            } else {
                gsm.add(GameStateManager.PAUSE);
                enemyOldMan.setUpdating(false);
                enemyPoliceman.setUpdating(false);
                player.setUpdating(false);
                System.out.println("PauseState start.");
            }
        }


    }
}
