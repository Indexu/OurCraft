package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager
{
    private static Sound placeBlock;
    private static Sound destroyBlock;
    private static Music pianoMusic;

    public static void init()
    {
        placeBlock = Gdx.audio.newSound(Gdx.files.internal("audio/placeBlock.mp3"));
        destroyBlock = Gdx.audio.newSound(Gdx.files.internal("audio/destroyBlock.ogg"));
        pianoMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/piano.mp3"));
    }

    public static void playDestroyBlock()
    {
        destroyBlock.play();
    }

    public static void playPlaceBlock()
    {
        placeBlock.play();
    }

    public static void playPianoMusic()
    {
        pianoMusic.setLooping(true);
        pianoMusic.play();
        pianoMusic.setPosition(10f);
    }
}
