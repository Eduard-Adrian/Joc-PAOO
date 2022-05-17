package com.paoo.joc.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class Sound {

    Clip clip;
    String[] soundPath = new String[30];

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


}
