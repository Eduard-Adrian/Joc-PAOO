package com.paoo.joc.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;


public class Sound {

    Clip clip;
    String[] soundPath = new String[30];
    static FloatControl fc;
    static FloatControl fcS;

    private static float volumeM;
    private static float volumeS;
    public static boolean musicToggle;
    public static boolean soundsToggle;

    public Sound() {
        soundPath[0] = "res/Audio/BlueBoyAdventure.wav";
        soundPath[1] = "res/Audio/coin.wav";
        soundPath[2] = "res/Audio/powerup.wav";

    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath[i]));
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

    }

    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }

    public void playMusic(int i) {
        setFile(i);
        play();
        loop();
    }

    public void playSoundEffect(int i) {
        setFile(i);
        play();
    }
    public void stopMusic() {
        stop();
    }

    public static void checkMusicVolume() {
        if (fc != null) {
            if (!musicToggle) {
                volumeM = -80f;
            } else {
                volumeM = 0f;
            }
            fc.setValue(volumeM);
        }
    }
    public static void checkSoundsVolume() {
        if (fcS != null) {
            if (!soundsToggle) {
                volumeS = -80f; //-80 minim
            } else {
                volumeS = 0f; //6 maxim
            }
            fcS.setValue(volumeS);
        }
    }


}
