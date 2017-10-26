package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager
{
    private static Sound heartbeat;
    private static long heartbeatID;
    private static Sound spear;
    private static Sound stab;
    private static Sound boneCrushing;
    private static Sound portal;
    private static Music horrorAmbience;

    public static void init()
    {
//        heartbeat = Gdx.audio.newSound(Gdx.files.internal("audio/heartbeat.mp3"));
//        spear = Gdx.audio.newSound(Gdx.files.internal("audio/spear.mp3"));
//        stab = Gdx.audio.newSound(Gdx.files.internal("audio/stab.mp3"));
//        boneCrushing = Gdx.audio.newSound(Gdx.files.internal("audio/bone_crushing.mp3"));
//        portal = Gdx.audio.newSound(Gdx.files.internal("audio/portal.mp3"));
//        horrorAmbience = Gdx.audio.newMusic(Gdx.files.internal("audio/horror_ambience.mp3"));
    }

    public static void playHeartbeat()
    {
        heartbeatID = heartbeat.play();
        heartbeat.setLooping(heartbeatID, true);
    }

    public static void stopHeartbeat()
    {
        heartbeat.stop(heartbeatID);
    }

    public static void playSpear(float volume)
    {
        spear.play(volume);
    }

    public static void playPortal()
    {
        portal.play();
    }

    public static void stopPortal()
    {
        portal.stop();
    }

    public static void playDeath()
    {
        boneCrushing.play();
        stab.play();
    }

    public static void setHeartbeatSpeed(float speed)
    {
        heartbeat.setPitch(heartbeatID, speed);
    }

    public static void setHeartbeatVolume(float volume)
    {
        heartbeat.setVolume(heartbeatID, volume);
    }

    public static void playHorrorAmbience()
    {
        horrorAmbience.setLooping(true);
        horrorAmbience.play();
    }
}
