package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.ourcraft.objects.Block;

import java.nio.FloatBuffer;

public class TextureManager
{
    private static Texture grassTexture;
    private static Texture dirtTexture;

    private static FloatBuffer UVTopBottomSideBuffer;
    private static FloatBuffer UVAllBuffer;

    public static void init()
    {
        initTextures();
        initUVAllBuffer();
        initUVTopBottomSideBuffer();
    }

    public static Texture getBlockTexture(Block.BlockType type)
    {
        switch (type)
        {
            case GRASS:
                return grassTexture;

            case DIRT:
                return dirtTexture;

            default:
                return null;
        }
    }

    public static FloatBuffer getBlockUVBuffer(Block.BlockType type)
    {
        switch (type)
        {
            case GRASS:
                return UVTopBottomSideBuffer;

            case DIRT:
                return UVAllBuffer;

            default:
                return null;
        }
    }

    public static void initTextures()
    {
        grassTexture = new Texture(Gdx.files.internal("textures/blocks/grass.png"));
        dirtTexture = new Texture(Gdx.files.internal("textures/blocks/dirt.png"));
    }

    private static void initUVAllBuffer()
    {
        float[] uvArray =
                {
                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,

                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,

                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,

                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,

                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,

                        0.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,
                };

        UVAllBuffer = BufferUtils.newFloatBuffer(48);
        BufferUtils.copy(uvArray, 0, UVAllBuffer, 48);
        UVAllBuffer.rewind();
    }

    private static void initUVTopBottomSideBuffer()
    {
        float[] uvArray =
                {
                        0.0f, 1.0f,
                        0.0f, 0.5f,
                        0.5f, 0.5f,
                        0.5f, 1.0f,

                        0.0f, 1.0f,
                        0.0f, 0.5f,
                        0.5f, 0.5f,
                        0.5f, 1.0f,

                        // Bottom
                        0.5f, 1.0f,
                        0.5f, 0.5f,
                        1.0f, 0.5f,
                        1.0f, 1.0f,

                        // Top
                        0.0f, 0.5f,
                        0.0f, 0.0f,
                        0.5f, 0.0f,
                        0.5f, 0.5f,

                        0.5f, 1.0f,
                        0.0f, 1.0f,
                        0.0f, 0.5f,
                        0.5f, 0.5f,

                        0.5f, 1.0f,
                        0.0f, 1.0f,
                        0.0f, 0.5f,
                        0.5f, 0.5f,
                };

        UVTopBottomSideBuffer = BufferUtils.newFloatBuffer(48);
        BufferUtils.copy(uvArray, 0, UVTopBottomSideBuffer, 48);
        UVTopBottomSideBuffer.rewind();
    }
}
