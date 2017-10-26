package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.ourcraft.objects.Block;

public class TextureManager
{
    private static Texture grassTexture;

    public static void init()
    {
        grassTexture = new Texture(Gdx.files.internal("textures/blocks/grass_top.png"));
    }

    public static Texture getBlockTexture(Block.BlockType type)
    {
        switch (type)
        {
            case GRASS:
                return grassTexture;

            default:
                return null;
        }
    }
}
