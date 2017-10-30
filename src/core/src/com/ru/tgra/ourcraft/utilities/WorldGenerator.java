package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.models.Chunk;
import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.objects.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WorldGenerator
{
    private Block.BlockType[][][] worldBlocks;
    private Chunk[][] chunks;
    private OpenSimplexNoise noise;
    private int[][] heightMap;
    private int maxX;
    private int maxY;
    private int maxZ;

    public WorldGenerator()
    {
        worldBlocks = new Block.BlockType[Settings.worldWidth][Settings.worldScale * 2][Settings.worldHeight];
        heightMap = new int[Settings.worldHeight][Settings.worldWidth];
        noise = new OpenSimplexNoise();
        //noise = new OpenSimplexNoise(new Date().getTime());
        maxX = Settings.worldWidth;
        maxY = Settings.worldScale * 2;
        maxZ = Settings.worldHeight;
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

    public Block.BlockType[][][] getWorldBlocks()
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
        for (int x = 0; x < maxX; x++)
        {
            for (int z = 0; z < maxZ; z++)
            {
                for (int y = 0; y < maxY; y++)
                {
                    if (y < heightMap[x][z])
                    {
                        worldBlocks[x][y][z] = Block.BlockType.DIRT;
                    }
                    else if (y == heightMap[x][z])
                    {
                        worldBlocks[x][y][z] = Block.BlockType.GRASS;
                    }
                    else
                    {
                        worldBlocks[x][y][z] = Block.BlockType.EMPTY;
                    }
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
        Map<Integer, Block> blockMap = new HashMap<>();

        for (int x = startX; x < (startX + Settings.chunkWidth); x++)
        {
            for (int y = 0; y < maxY; y++)
            {
                for (int z = startZ; z < (startZ + Settings.chunkHeight); z++)
                {
                    if (worldBlocks[x][y][z] != Block.BlockType.EMPTY && worldBlocks[x][y][z] != null)
                    {
                        CubeMask mask = BlockUtils.createBlockMask(x, y, z, worldBlocks);

                        if (!mask.isInvisible())
                        {
                            Point3D position = new Point3D(x, y, z);
                            int ID = MathUtils.cartesianHash(x, y, z);

                            int chunkX = MathUtils.getChunkX(x, Settings.chunkWidth);
                            int chunkY = MathUtils.getChunkY(z, Settings.chunkHeight);

                            Block block = new Block
                            (
                                ID,
                                position,
                                Settings.blockSize,
                                Settings.grassMaterial,
                                Settings.wallMinimapMaterial,
                                mask,
                                worldBlocks[x][y][z],
                                chunkX,
                                chunkY
                            );

                            blockMap.put(ID, block);
                        }
                    }
                }
            }
        }

        int chunkX = startX / Settings.chunkWidth;
        int chunkY = startZ / Settings.chunkHeight;

        chunks[chunkX][chunkY] = new Chunk(blockMap, chunkX, chunkY);
    }

    public int getMaxX()
    {
        return maxX;
    }

    public int getMaxY()
    {
        return maxY;
    }

    public int getMaxZ()
    {
        return maxZ;
    }
}
