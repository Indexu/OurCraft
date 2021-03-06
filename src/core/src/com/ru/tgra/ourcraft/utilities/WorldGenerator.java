package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.models.Chunk;
import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.objects.Block;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorldGenerator
{
    private Block.BlockType[][][] worldBlocks;
    private Chunk[][][] chunks;
    private OpenSimplexNoise noise;
    private int[][] overworldHeightMap;
    private int maxX;
    private int maxY;
    private int maxZ;
    private int lowestY;
    private int highestY;
    private long totalTime = 0;
    private long million = 1000000;

    public WorldGenerator()
    {
        worldBlocks = new Block.BlockType[Settings.worldX][Settings.worldY][Settings.worldZ];
        overworldHeightMap = new int[Settings.worldX][Settings.worldZ];
        //noise = new OpenSimplexNoise();
        maxX = Settings.worldX;
        maxY = Settings.worldY;
        maxZ = Settings.worldZ;
    }

    public Block.BlockType[][][] getWorldBlocks()
    {
        return worldBlocks;
    }

    public Chunk[][][] getChunks()
    {
        return chunks;
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

    public void generateOverworldHeightMap()
    {
        long startTime = System.nanoTime();
        generateHeightMap(overworldHeightMap);
        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Generated heightmap in " + endTime / million + "ms");
    }

    public void smoothenOverworldHeightMap()
    {
        long startTime = System.nanoTime();
        smoothenHeightMap(overworldHeightMap);
        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Smoothened heightmap in " + endTime / million + "ms");
    }

    private void generateHeightMap(int[][] heightmap)
    {
        float scalar = Settings.worldScale / 2;

        noise = new OpenSimplexNoise(new Date().getTime());

        for (int x = 0; x < Settings.worldX; x++)
        {
            for (int y = 0; y < Settings.worldZ; y++)
            {
                double value = noise.eval(x / Settings.worldFeatureSize, y / Settings.worldFeatureSize, 0.0);
                value += 1.0f;
                value *= scalar;

                int val = (int) value;

                heightmap[x][y] = val;
            }
        }
    }

    public void smoothenHeightMap(int[][] heightMap)
    {
        lowestY = Integer.MAX_VALUE;
        highestY = Integer.MIN_VALUE;

        for (int i = 0; i < Settings.worldSmoothness; i++)
        {
            for (int x = 0; x < Settings.worldX; x++)
            {
                for (int y = 0; y < Settings.worldZ; y++)
                {
                    int sum = 0;
                    int count = 0;

                    // Up
                    if (y + 1 < Settings.worldZ)
                    {
                        sum += heightMap[x][y + 1];
                        count++;

                        // Up-Right
                        if (x + 1 < Settings.worldX)
                        {
                            sum += heightMap[x + 1][y + 1];
                            count++;
                        }

                        // Up-Left
                        if (0 < x)
                        {
                            sum += heightMap[x - 1][y + 1];
                            count++;
                        }
                    }

                    // Down
                    if (0 < y)
                    {
                        sum += heightMap[x][y - 1];
                        count++;

                        // Down-Right
                        if (x + 1 < Settings.worldX)
                        {
                            sum += heightMap[x + 1][y - 1];
                            count++;
                        }

                        // Down-Left
                        if (0 < x)
                        {
                            sum += heightMap[x - 1][y - 1];
                            count++;
                        }
                    }

                    // Right
                    if (x + 1 < Settings.worldX)
                    {
                        sum += heightMap[x + 1][y];
                        count++;
                    }

                    // Left
                    if (0 < x)
                    {
                        sum += heightMap[x - 1][y];
                        count++;
                    }

                    heightMap[x][y] = (sum / count);

                    if (i == Settings.worldSmoothness - 1)
                    {
                        if (heightMap[x][y] < lowestY)
                        {
                            lowestY = heightMap[x][y];
                        }

                        if (highestY < heightMap[x][y])
                        {
                            highestY = heightMap[x][y];
                        }
                    }
                }
            }
        }
    }

    public void createWorldBlockArray()
    {
        long startTime = System.nanoTime();

        for (int x = 0; x < maxX; x++)
        {
            for (int z = 0; z < maxZ; z++)
            {
                for (int y = 0; y < maxY; y++)
                {
                    if (y <= overworldHeightMap[x][z])
                    {
                        worldBlocks[x][y][z] = Block.BlockType.DIRT;
                    }
                    else
                    {
                        worldBlocks[x][y][z] = Block.BlockType.EMPTY;
                    }
                }
            }
        }

        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created world block array in " + endTime / million + "ms");
    }

    public void generateChunks()
    {
        long startTime = System.nanoTime();

        int chunkX = Settings.worldX / Settings.chunkX;
        int chunkY = Settings.worldY / Settings.chunkY;
        int chunkZ = Settings.worldZ / Settings.chunkZ;

        chunks = new Chunk[chunkX][chunkY][chunkZ];

        for (int x = 0; x < Settings.worldX; x += Settings.chunkX)
        {
            for (int y = 0; y < Settings.worldY; y += Settings.chunkY)
            {
                for (int z = 0; z < Settings.worldZ; z += Settings.chunkZ)
                {
                    createChunk(x, y, z);
                }
            }
        }

        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Generated chunks in " + endTime / million + "ms");

        System.out.println("\nWorld generated in " + totalTime / million + "ms\n");
    }

    private void createChunk(int startX, int startY, int startZ)
    {
        Map<Integer, Block> blockMap = new HashMap<>();

        for (int x = startX; x < (startX + Settings.chunkX); x++)
        {
            for (int y = startY; y < (startY + Settings.chunkY); y++)
            {
                for (int z = startZ; z < (startZ + Settings.chunkZ); z++)
                {
                    if (worldBlocks[x][y][z] != Block.BlockType.EMPTY && worldBlocks[x][y][z] != null)
                    {
                        CubeMask mask = BlockUtils.createBlockMask(x, y, z, worldBlocks);

                        if (!mask.isInvisible())
                        {
                            Point3D position = new Point3D(x, y, z);
                            int ID = MathUtils.cartesianHash(x, y, z);

                            int chunkX = MathUtils.getChunkX(x, Settings.chunkX);
                            int chunkY = MathUtils.getChunkY(y, Settings.chunkY);
                            int chunkZ = MathUtils.getChunkZ(z, Settings.chunkZ);

                            Block block = new Block
                            (
                                ID,
                                position,
                                Settings.blockSize,
                                mask,
                                worldBlocks[x][y][z],
                                chunkX,
                                chunkY,
                                chunkZ
                            );

                            blockMap.put(ID, block);
                        }
                    }
                }
            }
        }

        int chunkX = startX / Settings.chunkX;
        int chunkY = startY / Settings.chunkY;
        int chunkZ = startZ / Settings.chunkZ;

        chunks[chunkX][chunkY][chunkZ] = new Chunk(blockMap, chunkX, chunkY, chunkZ);
    }

    public void createStone()
    {
        long startTime = System.nanoTime();

        for (int x = 0; x < maxX; x++)
        {
            for (int z = 0; z < maxZ; z++)
            {
                for (int y = lowestY - 2; 0 < y; y--)
                {
                    worldBlocks[x][y][z] = Block.BlockType.STONE;
                }
            }
        }

        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created stone blocks in " + endTime / million + "ms");
    }

    public void createBedrock()
    {
        long startTime = System.nanoTime();

        for (int x = 0; x < maxX; x++)
        {
            for (int z = 0; z < maxZ; z++)
            {
                worldBlocks[x][0][z] = Block.BlockType.BEDROCK;
            }
        }

        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created bedrock blocks in " + endTime / million + "ms");
    }

    public void offsetOverWorld()
    {
        long startTime = System.nanoTime();

        int offsetAmount = maxY - highestY - 1;
        lowestY = Integer.MAX_VALUE;

        for (int x = 0; x < maxX; x++)
        {
            for (int z = 0; z < maxZ; z++)
            {
                int y = maxY-1;

                while (worldBlocks[x][y][z] == Block.BlockType.EMPTY)
                {
                    y--;
                }

                for (int i = 0; i < offsetAmount; i++)
                {
                    if (i + 1 == offsetAmount)
                    {
                        worldBlocks[x][y][z] = Block.BlockType.GRASS;

                        if (highestY < y)
                        {
                            highestY = y;
                        }

                        if (y < lowestY)
                        {
                            lowestY = y;
                        }
                    }
                    else
                    {
                        worldBlocks[x][y][z] = Block.BlockType.DIRT;
                    }

                    y++;
                }
            }
        }

        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Offset overworld in " + endTime / million + "ms");
    }

    public void createCaverns()
    {
        long startTime = System.nanoTime();

        int currentY = highestY - lowestY + lowestY;

        double[][] cavernHeightMap = new double[Settings.worldX][Settings.worldZ];

        while(2 + Settings.cavernMinHeight < currentY)
        {
            noise = new OpenSimplexNoise(new Date().getTime());

            for (int x = 0; x < Settings.worldX; x++)
            {
                for (int y = 0; y < Settings.worldZ; y++)
                {
                    cavernHeightMap[x][y] = noise.eval(x / Settings.worldFeatureSize, y / Settings.worldFeatureSize, 0.0);
                }
            }

            for (int i = 0; i < Settings.worldSmoothness; i++)
            {
                for (int x = 0; x < Settings.worldX; x++)
                {
                    for (int y = 0; y < Settings.worldZ; y++)
                    {
                        double sum = 0;
                        int count = 0;

                        // Up
                        if (y + 1 < Settings.worldZ)
                        {
                            sum += cavernHeightMap[x][y + 1];
                            count++;

                            // Up-Right
                            if (x + 1 < Settings.worldX)
                            {
                                sum += cavernHeightMap[x + 1][y + 1];
                                count++;
                            }

                            // Up-Left
                            if (0 < x)
                            {
                                sum += cavernHeightMap[x - 1][y + 1];
                                count++;
                            }
                        }

                        // Down
                        if (0 < y)
                        {
                            sum += cavernHeightMap[x][y - 1];
                            count++;

                            // Down-Right
                            if (x + 1 < Settings.worldX)
                            {
                                sum += cavernHeightMap[x + 1][y - 1];
                                count++;
                            }

                            // Down-Left
                            if (0 < x)
                            {
                                sum += cavernHeightMap[x - 1][y - 1];
                                count++;
                            }
                        }

                        // Right
                        if (x + 1 < Settings.worldX)
                        {
                            sum += cavernHeightMap[x + 1][y];
                            count++;
                        }

                        // Left
                        if (0 < x)
                        {
                            sum += cavernHeightMap[x - 1][y];
                            count++;
                        }

                        cavernHeightMap[x][y] = (sum / count);
                    }
                }
            }

            int cavernHeight = RandomGenerator.randomIntegerInRange(Settings.cavernMinHeight, Settings.cavernMaxHeight);
            int stopY = currentY - cavernHeight;

            for (; stopY < currentY; currentY--)
            {
                for (int x = 0; x < maxX; x++)
                {
                    for (int z = 0; z < maxZ; z++)
                    {
                        if ((currentY < lowestY && cavernHeightMap[x][z] < -0.15) || (lowestY < currentY && cavernHeightMap[x][z] < -0.25))
                        {
                            worldBlocks[x][currentY][z] = Block.BlockType.EMPTY;
                            changeSurroundingType(x, currentY, z, Block.BlockType.STONE);
                        }
                    }
                }
            }
        }

        long endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created caverns in " + endTime / million + "ms");
    }

    private void changeSurroundingType(int x, int y, int z, Block.BlockType blockType)
    {
        // Up
        if (y+1 < maxY && worldBlocks[x][y+1][z] != Block.BlockType.EMPTY)
        {
            worldBlocks[x][y+1][z] = blockType;
        }

        // Down
        if (0 < y && worldBlocks[x][y-1][z] != Block.BlockType.EMPTY)
        {
            worldBlocks[x][y-1][z] = blockType;
        }

        // North
        if (x+1 < maxX && worldBlocks[x+1][y][z] != Block.BlockType.EMPTY)
        {
            worldBlocks[x+1][y][z] = blockType;
        }

        // South
        if (0 < x && worldBlocks[x-1][y][z] != Block.BlockType.EMPTY)
        {
            worldBlocks[x-1][y][z] = blockType;
        }

        // East
        if (z+1 < maxZ && worldBlocks[x][y][z+1] != Block.BlockType.EMPTY)
        {
            worldBlocks[x][y][z+1] = blockType;
        }

        // West
        if (0 < z && worldBlocks[x][y][z-1] != Block.BlockType.EMPTY)
        {
            worldBlocks[x][y][z-1] = blockType;
        }
    }
}
