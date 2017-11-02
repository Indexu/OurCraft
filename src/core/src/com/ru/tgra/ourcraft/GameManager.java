package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.objects.*;
import com.ru.tgra.ourcraft.utilities.*;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<Block> blocksToRemove;

    public static WorldGenerator worldGenerator;
    public static Block.BlockType[][][] worldBlocks;
    public static Chunk[][] chunks;

    public static boolean mainMenu;
    public static boolean loaded;

    public static Point3D worldCenter;
    public static Torch torch;

    public static Player player;
    public static Skybox skybox;
    public static boolean noclip;

    public static void init()
    {
        blocksToRemove = new ArrayList<>();
        worldGenerator = new WorldGenerator();
        worldCenter = new Point3D(Settings.worldX / 2, Settings.worldY / 2, Settings.worldZ / 2);
        noclip = false;
        loaded = false;
        mainMenu = true;
    }

    public static void createWorld()
    {
        worldBlocks = worldGenerator.getWorldBlocks();
        chunks = worldGenerator.getChunks();

        createPlayer();
        createTorch();
        createSkybox();

        AudioManager.playPianoMusic();
    }

    public static void drawWorld()
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

        for (int x = startX; x <= endX; x++)
        {
            for (int y = startY; y <= endY; y++)
            {
                chunks[x][y].drawBlocks();
            }
        }
    }

    public static void addBlock(Point3D pos, Block.BlockType type)
    {
        int x = (int) pos.x;
        int y = (int) pos.y;
        int z = (int) pos.z;

        int playerX = Math.round(player.getPosition().x);
        int playerY = Math.round(player.getPosition().y);
        int playerZ = Math.round(player.getPosition().z);

        if ((x == playerX && (y == playerY || y == playerY - 1) && z == playerZ && type != Block.BlockType.TORCH) ||
            (type == Block.BlockType.TORCH && !BlockUtils.isValidPlaceForTorch(x, y, z, worldBlocks)))
        {
            return;
        }

        int chunkX = MathUtils.getChunkX(x, Settings.chunkWidth);
        int chunkY = MathUtils.getChunkY(z, Settings.chunkHeight);

        setWorldBlocksBlock(pos, type);

        Block block  = new Block(
                MathUtils.cartesianHash(x, y, z),
                pos,
                Settings.blockSize,
                BlockUtils.createBlockMask(x, y, z, worldBlocks),
                type,
                chunkX,
                chunkY
        );

        redoMasksForAdjacentBlocks(block);

        chunks[block.getChunkX()][block.getChunkY()].addBlock(block);

        AudioManager.playPlaceBlock();
    }

    public static void removeBlocks()
    {
        if (0 < blocksToRemove.size())
        {
            AudioManager.playDestroyBlock();
        }

        for (Block block : blocksToRemove)
        {
            setWorldBlocksBlock(block.getPosition(), Block.BlockType.EMPTY);
            redoMasksForAdjacentBlocks(block);
            chunks[block.getChunkX()][block.getChunkY()].removeBlock(block.getID());
        }

        blocksToRemove.clear();
    }

    public static Block getBlock(int x, int y, int z)
    {
        int chunkX = MathUtils.getChunkX(x, Settings.chunkWidth);
        int chunkY = MathUtils.getChunkY(z, Settings.chunkHeight);

        int ID = MathUtils.cartesianHash(x, y, z);

        return chunks[chunkX][chunkY].getBlock(ID);
    }

    private static void createTorch()
    {
        torch = new Torch();
    }

    private static void createPlayer()
    {
        int x = Settings.worldX / 2;
        int y;
        int z = Settings.worldZ / 2;

        for (y = worldGenerator.getMaxY() - 1; 0 <= y; y--)
        {
            if (worldBlocks[x][y][z] != Block.BlockType.EMPTY)
            {
                y += 5;
                break;
            }
        }

        player = new Player(1, new Point3D(x, y, z), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed);
    }

    private static void createSkybox()
    {
        skybox = new Skybox();
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
        if (worldBlocks[x][y][z] == Block.BlockType.TORCH)
        {
            return;
        }

        CubeMask mask = BlockUtils.createBlockMask(x, y, z, worldBlocks);

        int chunkX = MathUtils.getChunkX(x, Settings.chunkWidth);
        int chunkY = MathUtils.getChunkY(z, Settings.chunkHeight);

        int ID = MathUtils.cartesianHash(x, y, z);

        if (mask.isInvisible())
        {
            chunks[chunkX][chunkY].removeBlock(ID);
        }
        else
        {
            chunks[chunkX][chunkY].assertBlock(ID, x, y, z, worldBlocks[x][y][z]);
            chunks[chunkX][chunkY].setBlockMask(ID, mask);
        }
    }
}
