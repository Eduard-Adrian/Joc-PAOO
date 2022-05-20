package com.paoo.joc.states;

import com.paoo.joc.UI;
import com.paoo.joc.input.KeyInput;
import com.paoo.joc.input.MouseInput;
import com.paoo.joc.util.Sound;

import java.awt.Graphics2D;

public class MenuState extends GameState {

    private final UI ui;
    public static boolean showControlsUI = false;
    public static boolean showSettingsUI = false;


    public MenuState(GameStateManager gsm) {
        super(gsm);

        ui = new UI(gsm);

    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseInput mouse, KeyInput key) {
        key.menu.tick();
        key.up.tick();
        key.down.tick();
        key.enter.tick();
        key.escape.tick();
        key.interact.tick();

        if (key.menu.clicked) {
            UI.currentSettingsSel = 0;
            UI.currentSelection = 0;
            if (gsm.isStateActive(GameStateManager.MENU) && PlayState.isInGame) {
                gsm.pop(GameStateManager.MENU);
                System.out.println("MenuState end.");
            } else if (!gsm.isStateActive(GameStateManager.MENU)){
                gsm.add(GameStateManager.MENU);
                System.out.println("MenuState start.");
            }
        }

        if (!showControlsUI && !showSettingsUI && (!gsm.isStateActive(GameStateManager.PLAY) || (gsm.isStateActive(GameStateManager.MENU) && gsm.isStateActive(GameStateManager.PLAY)))) {
            if (UI.currentSelection > 4) {
                UI.currentSelection = 0;
            }
            if (UI.currentSelection < 0) {
                UI.currentSelection = 4;
            }
            if (key.down.clicked) {
                UI.currentSelection++;
            }
            if (key.up.clicked) {
                UI.currentSelection--;
            }
        }

        if (showSettingsUI && (!gsm.isStateActive(GameStateManager.PLAY) || (gsm.isStateActive(GameStateManager.MENU) && gsm.isStateActive(GameStateManager.PLAY)))) {
            if (UI.currentSettingsSel > 2) {
                UI.currentSettingsSel = 0;
            }
            if (UI.currentSettingsSel < 0) {
                UI.currentSettingsSel = 2;
            }
            if (key.down.clicked) {
                UI.currentSettingsSel++;
            }
            if (key.up.clicked) {
                UI.currentSettingsSel--;
            }
        }

        //NEW GAME
        if ((key.enter.clicked || key.interact.clicked) && UI.currentSelection == 0 && !PlayState.isInGame) {
            gsm.add(GameStateManager.PLAY);
            gsm.pop(GameStateManager.MENU);
        }

        //CONTINUE
        if ((key.enter.clicked || key.interact.clicked) && UI.currentSelection == 0 && PlayState.isInGame) {
            gsm.pop(GameStateManager.MENU);
        }

        //LOAD GAME
        if ((key.enter.clicked || key.interact.clicked) && UI.currentSelection == 1 && !PlayState.isInGame) {
            System.out.println("Ati selectat LOAD GAME.");
        }

        //SAVE GAME
        if ((key.enter.clicked || key.interact.clicked) && UI.currentSelection == 1 && PlayState.isInGame) {
            System.out.println("Ati selectat SAVE GAME.");
        }

        //CONTROLS
        if ((key.enter.clicked || key.interact.clicked) && UI.currentSelection == 2) {
            showControlsUI = true;
            System.out.println("Ati selectat CONTROLS.");
        }
        if ((key.escape.clicked || key.menu.clicked) && showControlsUI) {
            showControlsUI = false;
        }

        //SETTINGS
        if ((key.enter.clicked || key.interact.clicked) && UI.currentSelection == 3) {
            showSettingsUI = true;
            System.out.println("Ati selectat SETTINGS.");
            if ((key.enter.clicked || key.interact.clicked) && UI.currentSettingsSel == 0) {
                ui.onOffMusicUI();
                Sound.musicToggle = !Sound.musicToggle;
                Sound.checkMusicVolume();
                System.out.println("Sound.musicToggle: " + Sound.musicToggle);
            }
            if ((key.enter.clicked || key.interact.clicked) && UI.currentSettingsSel == 1) {
                ui.onOffSoundsUI();
                Sound.soundsToggle = !Sound.soundsToggle;
                Sound.checkSoundsVolume();
                System.out.println("Sound.soundsToggle: " + Sound.soundsToggle);
            }
            if ((key.enter.clicked || key.interact.clicked) && UI.currentSettingsSel == 2) {
                ui.changeDifficultyUI();
            }
        }
        if ((key.escape.clicked || key.menu.clicked) && showSettingsUI) {
            showSettingsUI = false;
        }

        //EXIT
        if ((key.enter.clicked || key.interact.clicked) && UI.currentSelection == 4) {
            System.exit(0);
        }


    }

    @Override
    public void render(Graphics2D g) {
        ui.render(g);
    }
}
