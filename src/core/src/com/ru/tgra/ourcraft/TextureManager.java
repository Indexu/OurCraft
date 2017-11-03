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
    private static Texture brickTexture;
    private static Texture cobblestoneTexture;
    private static Texture gravelTexture;
    private static Texture oakLogTexture;
    private static Texture oakPlankTexture;
    private static Texture stonebrickTexture;
    private static Texture sandTexture;
    private static Texture diamondTexture;
    private static Texture torchTexture;
    private static Texture skyboxTexture;

    private static FloatBuffer UVGrassBuffer;
    private static FloatBuffer UVLogBuffer;
    private static FloatBuffer UVAllBuffer;
    private static FloatBuffer UVTorchBuffer;

    public static void init()
    {
        initTextures();

        initUVAllBuffer();
        initUVGrassBuffer();
        initUVTorchBuffer();
        initUVLogBuffer();
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

            case COBBLESTONE:
                return cobblestoneTexture;

            case GRAVEL:
                return gravelTexture;

            case SAND:
                return sandTexture;

            case OAK_PLANK:
                return oakPlankTexture;

            case OAK_LOG:
                return oakLogTexture;

            case STONE_BRICK:
                return stonebrickTexture;

            case BRICK:
                return brickTexture;

            case DIAMOND:
                return diamondTexture;

            default:
                return null;
        }
    }

    public static FloatBuffer getBlockUVBuffer(Block.BlockType type)
    {
        switch (type)
        {
            case GRASS:
                return UVGrassBuffer;

            case OAK_LOG:
                return UVLogBuffer;

            case DIAMOND:
            case BRICK:
            case STONE_BRICK:
            case OAK_PLANK:
            case SAND:
            case GRAVEL:
            case COBBLESTONE:
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
        brickTexture = new Texture(Gdx.files.internal("textures/blocks/brick.png"));
        cobblestoneTexture = new Texture(Gdx.files.internal("textures/blocks/cobblestone.png"));
        gravelTexture = new Texture(Gdx.files.internal("textures/blocks/gravel.png"));
        oakLogTexture = new Texture(Gdx.files.internal("textures/blocks/log_oak.png"));
        oakPlankTexture = new Texture(Gdx.files.internal("textures/blocks/planks_oak.png"));
        stonebrickTexture = new Texture(Gdx.files.internal("textures/blocks/stonebrick.png"));
        sandTexture = new Texture(Gdx.files.internal("textures/blocks/sand.png"));
        diamondTexture = new Texture(Gdx.files.internal("textures/blocks/diamond_block.png"));

        torchTexture = new Texture(Gdx.files.internal("textures/blocks/torch_on.png"));
        skyboxTexture = new Texture(Gdx.files.internal("textures/skybox/sky_photo3.jpg"));
    }

    private static void initUVAllBuffer()
    {
        float[] uvArray =
        {
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

    private static void initUVGrassBuffer()
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

        UVGrassBuffer = BufferUtils.newFloatBuffer(48);
        BufferUtils.copy(uvArray, 0, UVGrassBuffer, 48);
        UVGrassBuffer.rewind();
    }

    private static void initUVLogBuffer()
    {
        float[] uvArray =
        {
            0.0f, 1.0f,
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.5f, 1.0f,

            0.0f, 1.0f,
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.5f, 1.0f,

            // Bottom
            0.5f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.5f, 1.0f,

            // Top
            0.5f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.5f, 1.0f,

            0.0f, 0.0f,
            0.5f, 0.0f,
            0.5f, 1.0f,
            0.0f, 1.0f,

            0.0f, 0.0f,
            0.5f, 0.0f,
            0.5f, 1.0f,
            0.0f, 1.0f,
        };

        UVLogBuffer = BufferUtils.newFloatBuffer(48);
        BufferUtils.copy(uvArray, 0, UVLogBuffer, 48);
        UVLogBuffer.rewind();
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
}
