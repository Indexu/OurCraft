package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.models.Chunk;
import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.objects.Block;

import java.util.HashMap;
import java.util.Map;

public class WorldGenerator
{
    private boolean[][][] worldBlocks;
    private Chunk[][] chunks;
    private OpenSimplexNoise noise;
    private int[][] heightMap;

    public WorldGenerator()
    {
        worldBlocks = new boolean[Settings.worldWidth][Settings.worldScale * 2][Settings.worldHeight];
        heightMap = new int[Settings.worldHeight][Settings.worldWidth];
        noise = new OpenSimplexNoise();
        //noise = new OpenSimplexNoise(new Date().getTime());
    }

    public void generateWorld()
    {
        generateHeightMap();
        createWorldBlockArray();
        generateChunks();

        System.out.println("Chunks X: " + chunks.length);
        System.out.println("Chunks Y: " + chunks[0].length);
        System.out.println("Chunks: " + (chunks.length * chunks[0].length));
    }

    public boolean[][][] getWorldBlocks()
    {
        return worldBlocks;
    }

    public Chunk[][] getChunks()
    {
        return chunks;
    }

    private void generateHeightMap()
    {
        for (int x = 0; x < Settings.worldWidth; x++)
        {
            for (int y = 0; y < Settings.worldHeight; y++)
            {
                double value = noise.eval(x / Settings.worldFeatureSize, y / Settings.worldFeatureSize, 0.0);
                value += 1.0f;
                value *= Settings.worldScale;

                heightMap[x][y] = (int) value;

                //System.out.format("Value: %f | HeightMap: %d\n", value, heightMap[y][x]);
            }
        }
    }

    private void createWorldBlockArray()
    {
        for (int x = 0; x < Settings.worldWidth; x++)
        {
            for (int y = 0; y < Settings.worldHeight; y++)
            {
                for (int i = 0; i < heightMap[x][y]; i++)
                {
                    worldBlocks[x][i][y] = true;
                }
            }
        }
    }

    private void generateChunks()
    {
        int chunkX = Settings.worldWidth / Settings.chunkWidth;
        int chunkY = Settings.worldHeight / Settings.chunkHeight;

        chunks = new Chunk[chunkX][chunkY];

        for (int x = 0; x < Settings.worldWidth; x += Settings.chunkWidth)
        {
            for (int y = 0; y < Settings.worldHeight; y += Settings.chunkHeight)
            {
                createChunk(x, y);
            }
        }
    }

    private void createChunk(int startX, int startZ)
    {
        int maxX = Settings.worldWidth;
        int maxY = Settings.worldScale * 2;
        int maxZ = Settings.worldHeight;

        Map<Integer, Block> blockMap = new HashMap<>();

        for (int x = startX; x < (startX + Settings.chunkWidth); x++)
        {
            for (int y = 0; y < maxY; y++)
            {
                for (int z = startZ; z < (startZ + Settings.chunkHeight); z++)
                {
                    if (worldBlocks[x][y][z])
                    {
                        CubeMask mask = new CubeMask();

                        // Up
                        if (y+1 != maxY && worldBlocks[x][y+1][z])
                        {
                            mask.setTop(false);
                        }

                        // Down
                        if (y == 0 || worldBlocks[x][y-1][z])
                        {
                            mask.setBottom(false);
                        }

                        // North
                        if (x+1 == maxX || worldBlocks[x+1][y][z])
                        {
                            mask.setNorth(false);
                        }

                        // South
                        if (x == 0 || worldBlocks[x-1][y][z])
                        {
                            mask.setSouth(false);
                        }

                        // East
                        if (z+1 == maxZ || worldBlocks[x][y][z+1])
                        {
                            mask.setEast(false);
                        }

                        // West
                        if (z == 0 || worldBlocks[x][y][z-1])
                        {
                            mask.setWest(false);
                        }

                        if (!mask.isInvisible())
                        {
                            Point3D position = new Point3D(x, y, z);
                            int ID = MathUtils.cartesianHash(x, y, z);

                            Block block = new Block
                            (
                                    ID,
                                    position,
                                    Settings.blockSize,
                                    Settings.grassMaterial,
                                    Settings.wallMinimapMaterial,
                                    mask,
                                    Block.BlockType.GRASS
                            );

                            blockMap.put(ID, block);
                        }
                    }
                }
            }
        }

        chunks[startX / Settings.chunkWidth][startZ / Settings.chunkHeight] = new Chunk(blockMap);
    }
}
