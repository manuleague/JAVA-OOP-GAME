package com.Joc.InSearchOfTreasure.ManagerResurse;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {

    private static HashMap<String, Clip> clips;
    private static int gap;

    /**
     * stocam in hashmap
     */
    public static void init() {
        clips = new HashMap<>();
        gap = 0;
    }

    /**
     * incarca sirul s in locatia cu cheia n a hashmapului
     */
    public static void load(String s, String n) {
        if(clips.get(n) != null) return;
        Clip clip;
        try {
            InputStream in = Sound.class.getResourceAsStream(s);
            InputStream bin = new BufferedInputStream(in);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bin);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            clips.put(n, clip);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void play(String s) {
        play(s, gap);
    }

    public static void play(String s, int i) {
        Clip c = clips.get(s);
        if(c == null)
            return;
        if(c.isRunning())
            c.stop();
        c.setFramePosition(i);
        while(!c.isRunning())
            c.start();
    }

    public static void stop(String s) {
        if(clips.get(s) == null)
            return;
        if(clips.get(s).isRunning())
            clips.get(s).stop();
    }

    public static void resumeLoop(String s) {
        Clip c = clips.get(s);
        if(c == null)
            return;
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void loop(String s, int frame, int start, int end) {
        Clip c = clips.get(s);
        if(c == null)
            return;
        if(c.isRunning())
            c.stop();
        c.setLoopPoints(start, end);
        c.setFramePosition(frame);
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static int getFrames(String s) {
        return clips.get(s).getFrameLength(); }

    public static void setVolume(String s, float f) {
        Clip c = clips.get(s);
        if(c == null)
            return;
        FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        vol.setValue(f);
    }

    public static boolean isPlaying(String s) {
        Clip c = clips.get(s);
        if(c == null)
            return false;
        return c.isRunning();
    }
}
