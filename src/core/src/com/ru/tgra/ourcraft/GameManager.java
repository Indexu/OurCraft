package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.objects.*;
import com.ru.tgra.ourcraft.utilities.*;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<Block> blocksToRemove;

    public static WorldGenerator worldGenerator;
    public static Block.BlockType[][][] worldBlocks;
    public static Chunk[][] chunks;

    public static boolean mainMenu;

    public static int drawCount;

    public static Light headLight;
    public static Camera minimapCamera;
    public static Player player;
    public static boolean noclip;
    public static boolean won;

    public static void init()
    {
        gameObjects = new ArrayList<>();
        blocksToRemove = new ArrayList<>();
        worldGenerator = new WorldGenerator();
        noclip = false;
        won = false;
    }

    public static void createWorld()
    {
        gameObjects.clear();

        worldGenerator.generateWorld();
        createPlayer();
        createHeadLight();

        worldBlocks = worldGenerator.getWorldBlocks();
        chunks = worldGenerator.getChunks();

        System.out.println("GameObjects: " + gameObjects.size());
    }

    public static void drawWorld(int viewportID)
    {
        int chunkX = (int) player.getPosition().x / Settings.chunkWidth;
        int chunkY = (int) player.getPosition().z / Settings.chunkHeight;

        int startX = chunkX - Settings.chunkDrawRadius;
        startX = (startX < 0 ? 0 : startX);

        int endX = chunkX + Settings.chunkDrawRadius;
        endX = (chunks.length-1 < endX ? chunks.length-1 : endX);

        int startY = chunkY - Settings.chunkDrawRadius;
        startY = (startY < 0 ? 0 : startY);

        int endY = chunkY + Settings.chunkDrawRadius;
        endY = (chunks[0].length-1 < endY ? chunks[0].length-1 : endY);

        System.out.format(" | chunkX: %d | chunkY: %d", chunkX, chunkY);
        System.out.format(" | startX: %d | endX: %d | startY: %d | endY: %d", startX, endX, startY, endY);

        int chunkDraw = 0;

        for (int x = startX; x <= endX; x++)
        {
            for (int y = startY; y <= endY; y++)
            {
                chunkDraw++;
                chunks[x][y].drawBlocks(viewportID);
            }
        }

        System.out.format(" | Chunks drawn: %d", chunkDraw);
    }

    public static void removeBlocks()
    {
        for (Block block : blocksToRemove)
        {
            setWorldBlocksBlock(block.getPosition(), Block.BlockType.EMPTY);
            redoMasksForAdjacentBlocks(block);
            chunks[block.getChunkX()][block.getChunkY()].removeBlock(block.getID());
        }

        blocksToRemove.clear();
    }

    private static void createHeadLight()
    {
        headLight = new Light();
        headLight.setID(4);
        headLight.setColor(Settings.helmetLightColor);
        headLight.setSpotFactor(Settings.helmetLightSpotFactor);
        headLight.setConstantAttenuation(Settings.helmetConstantAttenuation);
        headLight.setLinearAttenuation(Settings.helmetLightLinearAttenuation);
        headLight.setQuadraticAttenuation(Settings.helmetLightQuadraticAttenuation);
    }

    private static void createMinimap()
    {
        minimapCamera = new Camera();
        minimapCamera.setOrthographicProjection(-5, 5, -5, 5, 3f, 100);
    }

    private static void createPlayer()
    {
        player = new Player(1, new Point3D(0, Settings.worldScale*2, 0), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, Settings.playerMinimapMaterial);
        gameObjects.add(player);
    }

    private static void setWorldBlocksBlock(Point3D pos, Block.BlockType val)
    {
        int x = (int) pos.x;
        int y = (int) pos.y;
        int z = (int) pos.z;

        worldBlocks[x][y][z] = val;
    }

    private static void redoMasksForAdjacentBlocks(Block block)
    {
        int x = (int) block.getPosition().x;
        int y = (int) block.getPosition().y;
        int z = (int) block.getPosition().z;

        // Up
        if (y+1 != worldGenerator.getMaxY() && worldBlocks[x][y+1][z] != Block.BlockType.EMPTY)
        {
            redoBlock(x, y + 1, z);
        }

        // Down
        if (y != 0 && worldBlocks[x][y-1][z] != Block.BlockType.EMPTY)
        {
            redoBlock(x, y - 1, z);
        }

        // North
        if (x+1 != worldGenerator.getMaxX() && worldBlocks[x+1][y][z] != Block.BlockType.EMPTY)
        {
            redoBlock(x + 1, y, z);
        }

        // South
        if (x != 0 && worldBlocks[x-1][y][z] != Block.BlockType.EMPTY)
        {
            redoBlock(x - 1, y, z);
        }

        // East
        if (z+1 != worldGenerator.getMaxZ() && worldBlocks[x][y][z+1] != Block.BlockType.EMPTY)
        {
            redoBlock(x, y, z + 1);
        }

        // West
        if (z != 0 && worldBlocks[x][y][z-1] != Block.BlockType.EMPTY)
        {
            redoBlock(x, y, z - 1);
        }
    }

    private static void redoBlock(int x, int y, int z)
    {
        CubeMask mask = BlockUtils.createBlockMask(x, y, z, worldBlocks);

        int chunkX = MathUtils.getChunkX(x, Settings.chunkWidth);
        int chunkY = MathUtils.getChunkY(z, Settings.chunkHeight);

        int ID = MathUtils.cartesianHash(x, y, z);

        chunks[chunkX][chunkY].assertBlock(ID, x, y, z, worldBlocks[x][y][z]);
        chunks[chunkX][chunkY].setBlockMask(ID, mask);
    }
}
