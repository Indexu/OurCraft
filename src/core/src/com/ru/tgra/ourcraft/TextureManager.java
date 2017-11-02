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
    private static Texture stoneTexture;
    private static Texture bedrockTexture;
    private static Texture torchTexture;
    private static Texture skyboxTexture;

    private static FloatBuffer UVTopBottomSideBuffer;
    private static FloatBuffer UVAllBuffer;
    private static FloatBuffer UVTorchBuffer;
    private static FloatBuffer UVCubeMapBuffer;

    public static void init()
    {
        initTextures();

        initUVAllBuffer();
        initUVTopBottomSideBuffer();
        initUVCubeMapBuffer();
        initUVTorchBuffer();
    }

    public static Texture getBlockTexture(Block.BlockType type)
    {
        switch (type)
        {
            case GRASS:
                return grassTexture;

            case DIRT:
                return dirtTexture;

            case STONE:
                return stoneTexture;

            case BEDROCK:
                return bedrockTexture;

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

            case STONE:
            case BEDROCK:
            case DIRT:
                return UVAllBuffer;

            default:
                return null;
        }
    }

    public static Texture getSkyboxTexture()
    {
        return skyboxTexture;
    }

    public static Texture getTorchTexture()
    {
        return torchTexture;
    }

    public static FloatBuffer getCubeMapUVBuffer()
    {
        return UVCubeMapBuffer;
    }

    public static FloatBuffer getTorchUVBuffer()
    {
        return UVTorchBuffer;
    }

    private static void initTextures()
    {
        grassTexture = new Texture(Gdx.files.internal("textures/blocks/grass.png"));
        dirtTexture = new Texture(Gdx.files.internal("textures/blocks/dirt.png"));
        stoneTexture = new Texture(Gdx.files.internal("textures/blocks/stone.png"));
        bedrockTexture = new Texture(Gdx.files.internal("textures/blocks/bedrock.png"));
        torchTexture = new Texture(Gdx.files.internal("textures/blocks/torch_on.png"));
        skyboxTexture = new Texture(Gdx.files.internal("textures/skybox/sky_photo3.jpg"));
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

    private static void initUVTorchBuffer()
    {
        float[] uvArray =
        {
            7f/16f, 1.0f,
            7f/16f, 6f/16f,
            9f/16f, 6f/16f,
            9f/16f, 1.0f,

            7f/16f, 1.0f,
            7f/16f, 6f/16f,
            9f/16f, 6f/16f,
            9f/16f, 1.0f,

            // Bottom
            0.5f, 1.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
            1.0f, 1.0f,

            // Top
            7f/16f, 6f/16f,
            9f/16f, 6f/16f,
            9f/16f, 0.5f,
            7f/16f, 0.5f,

            9f/16f, 1.0f,
            7f/16f, 1.0f,
            7f/16f, 6f/16f,
            9f/16f, 6f/16f,

            9f/16f, 1.0f,
            7f/16f, 1.0f,
            7f/16f, 6f/16f,
            9f/16f, 6f/16f,
        };

        UVTorchBuffer = BufferUtils.newFloatBuffer(48);
        BufferUtils.copy(uvArray, 0, UVTorchBuffer, 48);
        UVTorchBuffer.rewind();
    }

    private static void initUVCubeMapBuffer()
    {
        float[] uvArray =
        {
            // Top
            0.25f, 0.0f,
            0.5f, 0.0f,
            0.5f, 0.3333f,
            0.25f, 0.3333f,

            // Front
            0.0f, 0.3333f,
            0.25f, 0.3333f,
            0.25f, 0.6666f,
            0.0f, 0.6666f,

            // Back
            0.5f, 0.3333f,
            0.75f, 0.3333f,
            0.75f, 0.6666f,
            0.25f, 0.6666f,

            // Bottom
            0.25f, 0.6666f,
            0.5f, 0.6666f,
            0.5f, 1.0f,
            0.25f, 1.0f,

            // Left
            0.25f, 0.3333f,
            0.5f, 0.3333f,
            0.5f, 0.6666f,
            0.25f, 0.6666f,

            // Right
            0.75f, 0.3333f,
            1.0f, 0.3333f,
            1.0f, 0.6666f,
            0.75f, 0.6666f,
        };

        UVCubeMapBuffer = BufferUtils.newFloatBuffer(48);
        BufferUtils.copy(uvArray, 0, UVCubeMapBuffer, 48);
        UVCubeMapBuffer.rewind();
    }
}
