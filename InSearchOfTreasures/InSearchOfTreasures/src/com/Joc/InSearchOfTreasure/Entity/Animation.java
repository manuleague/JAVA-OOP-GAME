package com.Joc.InSearchOfTreasure.Entity;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private int nrFrames;
    private int k;

    public Animation() { }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        k = 0;
        nrFrames = frames.length;
    }

    /**
     * Un delay mai mare face ca caracterele si itemele "miscatoare" sa treaca dintr-o stare in alta mai greu.
     * In cazul jocului de aici, animatiile sunt reprezentate de miscarile comorilor si a caracterului.
     * De exemplu, pentru delay=60, animatiile se vor modifica o data la o secunda(jocul e pe 60 FPS).
     * E ca o "bucla" pe acel vector de tip BufferedImage, deoarece atunci cand se ajunge la ultimul frame din vector, se trece din nou la primul frame.
     */
    public void update() {
        k++;
        if(k == 10) {
            currentFrame++;
            k = 0;
        }
        if(currentFrame == nrFrames) {
            currentFrame = 0;
        }
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

}