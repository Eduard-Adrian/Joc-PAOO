package com.paoo.joc;


import com.paoo.joc.entity.Player;
import com.paoo.joc.entity.objects.ObjMoney;
import com.paoo.joc.states.GameStateManager;
import com.paoo.joc.states.MenuState;
import com.paoo.joc.states.PlayState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

public class UI {

    private GameStateManager gsm;
    private Font arial32 = new Font("Arial", Font.PLAIN, 32);
    private BufferedImage moneyBagImg;
    private BufferedImage heartImg;
    private ObjMoney money;

    public static int currentSelection = 0;
    public static int currentSettingsSel = 0;

    private boolean musicToggleUI;
    private boolean soundsToggleUI;
    private boolean difficultyToggleUI;

    public double scoreL1 = Math.max(1, 120 - levelTime) * Player.coins;
    public double scoreL2 = 0;
    public double scoreL3 = 0;
    public double score = scoreL1 + scoreL2 + scoreL3;
    public static double gameTime;
    public static double levelTime;
    private DecimalFormat df = new DecimalFormat("#0.00");

    public UI(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            heartImg = ImageIO.read(new File("res/objects/heart.png"));
        } catch (Exception e) {
            System.out.println("ERROR: e");
        }

    }

    public void render (Graphics2D g) {
        //MENU STATE
        if (gsm.isStateActive(GameStateManager.MENU )) {
            if (!MenuState.showControlsUI && !MenuState.showSettingsUI) {
                drawMenuUI(g);
            } else if (MenuState.showControlsUI) {
                drawControlsUI(g);
            } else if (MenuState.showSettingsUI) {
                drawSettingsUI(g);
            }
        }
        //PLAY STATE
        if (gsm.isStateActive(GameStateManager.PLAY) && !gsm.isStateActive(GameStateManager.MENU)) {
            drawPlayUI(g);
            if (!gsm.isStateActive(GameStateManager.PAUSE)) {
                //Timp in joc
                levelTime += (double) 1/60;
                g.setColor(Color.decode("#272727"));
                g.drawString("Time: " + df.format(levelTime), 50, 50);
            }
        }
        //PAUSE STATE
        if (gsm.isStateActive(GameStateManager.PAUSE) && !gsm.isStateActive(GameStateManager.MENU)) {
            drawPauseUI(g);
        }
        //GAME OVER STATE
        if (gsm.isStateActive(GameStateManager.GAMEOVER)) {
            drawGameOverUI(g);
        }

    }


    private void drawPauseUI(Graphics2D g) {
        //FUNDAL TRANSPARENT
        Graphics2D gTransparent = (Graphics2D) g.create();
        gTransparent.setColor(Color.decode("#7b9ca1"));
        float alpha = 0.55f;
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        gTransparent.setComposite(alcom);
        gTransparent.fillRect(0,0, GamePanel.width, GamePanel.height);
        gTransparent.dispose();

        //TEXT
        g.setColor(Color.decode("#272727"));

        g.setFont(g.getFont().deriveFont(Font.BOLD , 72f));
        int x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("GAME PAUSED", g).getWidth() / 2;    //centru
        int y = 160;
        g.drawString("GAME PAUSED", x, y);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 56f));
        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("press M to EXIT to MENU", g).getWidth() / 2;
        y += 140 + (int) g.getFontMetrics().getStringBounds("", g).getHeight();
        g.drawString("press M to EXIT to MENU", x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("press ESC to unpause", g).getWidth() / 2;
        y += 16 + (int) g.getFontMetrics().getStringBounds("", g).getHeight();
        g.drawString("press ESC to unpause", x, y);
    }

    private void drawMenuUI(Graphics2D g) {

        //FUNDAL
        g.setColor(Color.decode("#7b9ca1"));
        g.fillRect(0,0, GamePanel.width, GamePanel.height);

        //SELECTOR MENIU
        Graphics2D gSelector = (Graphics2D) g.create();
        gSelector.setColor(Color.decode("#566D70"));
        float alpha = 0.5f;
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        gSelector.setComposite(alcom);

        //TITLE
        String title = "Epic Home Invasion";
        g.setColor(Color.decode("#272727"));
        g.setFont(g.getFont().deriveFont(Font.BOLD , 64f));
        int x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(title, g).getWidth() / 2;    //centru
        int y = 124;
        g.drawString(title, x, y);

        //SUBTITLE
        String subTitle1 = "Conquer the world of house robberies";
        String subTitle2 = "Part 4 - revenge of Marv Murchins - GOTY edition";
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 26f));
        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle1, g).getWidth() / 2;
        y += 8 + (int) g.getFontMetrics().getStringBounds(title, g).getHeight();
        g.drawString(subTitle1, x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getWidth() / 2;
        y += (int) g.getFontMetrics().getStringBounds(subTitle1, g).getHeight();
        g.drawString(subTitle2, x, y);

        if (!PlayState.isInGame) {
            //NEW GAME
            g.setFont(g.getFont().deriveFont(Font.BOLD));
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getWidth() / 2;
            y += 100 + (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight();
            g.drawString("NEW GAME", x, y);
            if (currentSelection == 0) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

            //LOAD GAME
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("LOAD GAME", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight();
            g.drawString("LOAD GAME", x, y);
            if (currentSelection == 1) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("LOAD GAME", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("LOAD GAME", g).getHeight());
            }

            //CONTROLS
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("LOAD GAME", g).getHeight();
            g.drawString("CONTROLS", x, y);
            if (currentSelection == 2) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

            //SETTINGS
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getHeight();
            g.drawString("SETTINGS", x, y);
            if (currentSelection == 3) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

            //EXIT
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("EXIT", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getHeight();
            g.drawString("EXIT", x, y);
            if (currentSelection == 4) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("EXIT", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

        } else {
            //CONTINUE
            g.setFont(g.getFont().deriveFont(Font.BOLD));
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("CONTINUE", g).getWidth() / 2;
            y += 100 + (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight();
            g.drawString("CONTINUE", x, y);
            if (currentSelection == 0) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("CONTINUE", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

            //SAVE GAME
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("SAVE GAME", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("CONTINUE", g).getHeight();
            g.drawString("SAVE GAME", x, y);
            if (currentSelection == 1) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("SAVE GAME", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

            //CONTROLS
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("LOAD GAME", g).getHeight();
            g.drawString("CONTROLS", x, y);
            if (currentSelection == 2) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

            //SETTINGS
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getHeight();
            g.drawString("SETTINGS", x, y);
            if (currentSelection == 3) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }

            //EXIT
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("EXIT", g).getWidth() / 2;
            y += 4 + (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getHeight();
            g.drawString("EXIT", x, y);
            if (currentSelection == 4) {
                gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds("EXIT", g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds("NEW GAME", g).getHeight());
            }
        }


    }

    public void drawControlsUI(Graphics2D g) {

        //FUNDAL
        g.setColor(Color.decode("#7b9ca1"));
        g.fillRect(0,0, GamePanel.width, GamePanel.height);

        //TITLE
        g.setColor(Color.decode("#272727"));
        g.setFont(g.getFont().deriveFont(Font.BOLD , 64f));
        int x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getWidth() / 2;
        int y = 124;
        g.drawString("CONTROLS", x, y);

        //SUBTITLE
        g.setFont(g.getFont().deriveFont(Font.BOLD, 26f));
        String subTitle1 = "WASD - Movement";
        String subTitle2 = "SPACEBAR - Attack";
        String subTitle3 = "E - Interact";
        String subTitle4 = "ESC - Pause";
        String subTitle5 = "M - Menu";
        String subTitle6 = "ENTER - Menu Interact";



        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle1, g).getWidth() / 2;
        y += 108 + (int) g.getFontMetrics().getStringBounds("CONTROLS", g).getHeight();
        g.drawString(subTitle1, x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getWidth() / 2;
        y += (int) g.getFontMetrics().getStringBounds(subTitle1, g).getHeight();
        g.drawString(subTitle2, x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle3, g).getWidth() / 2;
        y += (int) g.getFontMetrics().getStringBounds(subTitle2, g).getHeight();
        g.drawString(subTitle3, x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle4, g).getWidth() / 2;
        y += (int) g.getFontMetrics().getStringBounds(subTitle3, g).getHeight();
        g.drawString(subTitle4, x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle5, g).getWidth() / 2;
        y += (int) g.getFontMetrics().getStringBounds(subTitle4, g).getHeight();
        g.drawString(subTitle5, x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle6, g).getWidth() / 2;
        y += (int) g.getFontMetrics().getStringBounds(subTitle5, g).getHeight();
        g.drawString(subTitle6, x, y);

    }

    public void drawSettingsUI(Graphics2D g) {
        //FUNDAL
        g.setColor(Color.decode("#7b9ca1"));
        g.fillRect(0,0, GamePanel.width, GamePanel.height);

        //SELECTOR MENIU
        Graphics2D gSelector = (Graphics2D) g.create();
        gSelector.setColor(Color.decode("#566D70"));
        float alpha = 0.5f;
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        gSelector.setComposite(alcom);

        //TITLE
        g.setColor(Color.decode("#272727"));
        g.setFont(g.getFont().deriveFont(Font.BOLD , 64f));
        int x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getWidth() / 2;
        int y = 124;
        g.drawString("SETTINGS", x, y);

        //SUBTITLE
        g.setFont(g.getFont().deriveFont(Font.BOLD, 26f));
        String subTitle1 = "Music: ON";
        String subTitle2 = "Sounds: ON";
        String subTitle3 = "Difficulty: EASY";
        String subTitle4 = "Music: OFF";
        String subTitle5 = "Sounds: OFF";
        String subTitle6 = "Difficulty: HARD";


        if (musicToggleUI) {
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle1, g).getWidth() / 2;
            y += 108 + (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getHeight();
            g.drawString(subTitle1, x, y);
        } else {
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle4, g).getWidth() / 2;
            y += 108 + (int) g.getFontMetrics().getStringBounds("SETTINGS", g).getHeight();
            g.drawString(subTitle4, x, y);
        }
        if (currentSettingsSel == 0) {
            gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle4, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds(subTitle4, g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds(subTitle4, g).getHeight());
        }


        if (soundsToggleUI) {
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getWidth() / 2;
            y += (int) g.getFontMetrics().getStringBounds(subTitle1, g).getHeight();
            g.drawString(subTitle2, x, y);
        } else {
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle5, g).getWidth() / 2;
            y += (int) g.getFontMetrics().getStringBounds(subTitle4, g).getHeight();
            g.drawString(subTitle5, x, y);
        }
        if (currentSettingsSel == 1) {
            gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle5, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds(subTitle5, g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds(subTitle5, g).getHeight());
        }


        if (difficultyToggleUI) {
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle3, g).getWidth() / 2;
            y += (int) g.getFontMetrics().getStringBounds(subTitle3, g).getHeight();
            g.drawString(subTitle3, x, y);
        } else {
            x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle6, g).getWidth() / 2;
            y += (int) g.getFontMetrics().getStringBounds(subTitle5, g).getHeight();
            g.drawString(subTitle6, x, y);
        }
        if (currentSettingsSel == 2) {
            gSelector.fillRect(x - 4, y - (int) g.getFontMetrics().getStringBounds(subTitle6, g).getHeight() + 6 , (int) g.getFontMetrics().getStringBounds(subTitle6, g).getWidth() + 8, (int) g.getFontMetrics().getStringBounds(subTitle6, g).getHeight());
        }


    }

    private void drawPlayUI(Graphics2D g) {
        money = new ObjMoney();
        moneyBagImg = money.getImage();

        g.setFont(arial32);
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        g.setColor(Color.decode("#272727"));

        //Puncte adunate
        g.drawImage(moneyBagImg,  40,  60, 64, 64, null);
        g.drawString(String.valueOf(Player.getCoins()), 105, 105);

        g.drawImage(heartImg,  20,  GamePanel.height - 100, 96, 96, null);

        //Atentionare low hp
        if (Player.hitPoints >= 40) {
            g.drawString(String.valueOf(Player.hitPoints), 110, GamePanel.height - 40);
        } else {
            g.setColor(Color.red);
            g.drawString(String.valueOf(Player.hitPoints), 110, GamePanel.height - 40);
            g.drawString("!!!", 150, GamePanel.height - 40);
        }

    }

    private void drawGameOverUI(Graphics2D g) {
        //FUNDAL
        g.setColor(Color.decode("#7b9ca1"));
        g.fillRect(0,0, GamePanel.width, GamePanel.height);

        //TITLE
        g.setColor(Color.decode("#272727"));
        g.setFont(g.getFont().deriveFont(Font.BOLD , 64f));
        int x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds("GAME OVER", g).getWidth() / 2;
        int y = 124;
        g.drawString("GAME OVER", x, y);

        //SUBTITLE
        g.setFont(g.getFont().deriveFont(Font.BOLD, 26f));
        String subTitle1 = "Press M to EXIT back to MENU";
        String subTitle2 = "Score: " + df.format(score);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle1, g).getWidth() / 2;
        y += 222 + (int) g.getFontMetrics().getStringBounds("GAME OVER", g).getHeight();
        g.drawString(subTitle1, x, y);

        x = GamePanel.width / 2 - (int) g.getFontMetrics().getStringBounds(subTitle2, g).getWidth() / 2;
        y += (int) g.getFontMetrics().getStringBounds(subTitle1, g).getHeight();
        g.drawString(subTitle2, x, y);

    }


    public void onOffMusicUI() {
        musicToggleUI = !musicToggleUI;
    }
    public void onOffSoundsUI() {
        soundsToggleUI = !soundsToggleUI;
    }
    public void changeDifficultyUI() {
        difficultyToggleUI = !difficultyToggleUI;
    }
}
