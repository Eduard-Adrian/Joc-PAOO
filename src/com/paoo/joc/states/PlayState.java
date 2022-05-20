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

    private Player player;
    private EnemyOldman[] enemyOldman;
    private EnemyPoliceman enemyPoliceman;
    private final TileManager tm;
    private final Camera cam;
    public static Sound music;
    public static Sound soundEffect;
    private final UI ui;

    public static Vector2f map;
    public ObjectsList objList;

    public static boolean isInGame = false;


    public PlayState (GameStateManager gsm){
        super(gsm);
        isInGame = true;


        //NIVEL 1
        int xStart = 460;
        int yStart = 1500;
        map = new Vector2f(xStart - ((GamePanel.width / 2f) - 64 / 2f),yStart - ((GamePanel.height / 2f) - 64 / 2f));
        Vector2f.setWorldVar(map.x, map.y);

        cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64, GamePanel.height + 64));
        tm = new TileManager("tile/final-nivel1.xml", cam);

        player = new Player(new Sprite("Entity/player.png",32,32), new Vector2f(xStart, yStart), 64);
        cam.target(player);

        enemyOldman = new EnemyOldman[5];
        enemyOldman[0] = new EnemyOldman(new Sprite("Entity/enemy-oldman.png", 32,32), new Vector2f(460, 1333), 64);
        enemyOldman[1] = new EnemyOldman(new Sprite("Entity/enemy-oldman.png", 32,32), new Vector2f(1205, 1437), 64);
        enemyOldman[2] = new EnemyOldman(new Sprite("Entity/enemy-oldman.png", 32,32), new Vector2f(1903, 1266), 64);
        enemyOldman[3] = new EnemyOldman(new Sprite("Entity/enemy-oldman.png", 32,32), new Vector2f(625, 920), 64);
        enemyOldman[4] = new EnemyOldman(new Sprite("Entity/enemy-oldman.png", 32,32), new Vector2f(1523, 886), 64);

        enemyPoliceman = new EnemyPoliceman(new Sprite("Entity/enemy-cop.png", 32, 32), new Vector2f(1000, 1111), 64);

        objList = new ObjectsList(player.getBounds());

        music = new Sound();
        //music.playMusic(0);
        soundEffect = new Sound();

        ui = new UI(gsm);


    }

    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        player.update(enemyOldman, enemyPoliceman, objList, soundEffect , player.getUpdating());

        for (int i = 0; i < enemyOldman.length; i++) {
            if (enemyOldman[i] != null) {
                enemyOldman[i].update(player, enemyOldman[i].getUpdating());
            }
        }

        if (enemyPoliceman != null) {
            enemyPoliceman.update(player, enemyPoliceman.getUpdating(), false, true, 999, 1377);
        }

        cam.update();

        if (player.hitPoints <= 0) {
            gsm.add(GameStateManager.GAMEOVER);
        }
    }

    public void render(Graphics2D g) {
        tm.render(g);
        for (int i = 0; i < enemyOldman.length; i++) {
            if (enemyOldman[i] != null) {
                enemyOldman[i].render(g);
            }
        }
        if (enemyPoliceman != null) {
            enemyPoliceman.render(g);
        }
        player.render(g);
        cam.render(g);
        objList.render(g);
        ui.render(g);

        for (int i = 0; i < enemyOldman.length; i++) {
            if (enemyOldman[i] != null) {
                if (enemyOldman[i].getHitPoints() <= 0) {
                    Player.coins += 25;
                    enemyOldman[i] = null;
                }
            }
        }
        if (enemyPoliceman != null) {
            if (enemyPoliceman.getHitPoints() <= 0) {
                Player.coins += 50;
                enemyPoliceman = null;
            }
        }

    }

    public void input(MouseInput mouse, KeyInput key) {
        key.escape.tick();
        key.menu.tick();


        if (!gsm.isStateActive(GameStateManager.PAUSE) && !gsm.isStateActive(GameStateManager.MENU)) {
            player.input(mouse, key);
            cam.input(mouse, key);
            for (int i = 0; i < enemyOldman.length; i++) {
                if (enemyOldman[i] != null) {
                    enemyOldman[i].setUpdating(true);
                }
            }
            if (enemyPoliceman != null) {
                enemyPoliceman.setUpdating(true);
            }
            player.setUpdating(true);
        }

        if (key.escape.clicked && !gsm.isStateActive(GameStateManager.MENU)) {
            if (gsm.isStateActive(GameStateManager.PAUSE)) {
                gsm.pop(GameStateManager.PAUSE);
                System.out.println("PauseState end.");
            } else {
                gsm.add(GameStateManager.PAUSE);
                for (int i = 0; i < enemyOldman.length; i++) {
                    if (enemyOldman[i] != null) {
                        enemyOldman[i].setUpdating(false);
                    }
                }
                if (enemyPoliceman != null) {
                    enemyPoliceman.setUpdating(false);
                }
                player.setUpdating(false);
                System.out.println("PauseState start.");
            }
        }

        if (key.escape.clicked) {
            MenuState.showControlsUI = false;
            MenuState.showSettingsUI = false;
        }
        if (key.menu.clicked) {
            if (gsm.isStateActive(GameStateManager.MENU)) {
                MenuState.showControlsUI = false;
                MenuState.showSettingsUI = false;
                gsm.pop(GameStateManager.MENU);
                System.out.println("MenuState end.");
            } else {
                gsm.add(GameStateManager.MENU);
                for (int i = 0; i < enemyOldman.length; i++) {
                    if (enemyOldman[i] != null) {
                        enemyOldman[i].setUpdating(false);
                    }
                }
                if (enemyPoliceman != null) {
                    enemyPoliceman.setUpdating(false);
                }
                player.setUpdating(false);
                System.out.println("MenuState start.");
            }
        }


    }
}
