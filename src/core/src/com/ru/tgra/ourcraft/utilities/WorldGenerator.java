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
    private Chunk[][] chunks;
    private OpenSimplexNoise noise;
    private int[][] overworldHeightMap;
    private int maxX;
    private int maxY;
    private int maxZ;
    private int lowestY;
    private int highestY;

    public WorldGenerator()
    {
        worldBlocks = new Block.BlockType[Settings.worldX][Settings.worldY][Settings.worldZ];
        overworldHeightMap = new int[Settings.worldX][Settings.worldZ];
        //noise = new OpenSimplexNoise();
        maxX = Settings.worldX;
        maxY = Settings.worldY;
        maxZ = Settings.worldZ;
    }

    public void generateWorld()
    {
        long totalTime = 0;
        long startTime;
        long endTime;
        long million = 1000000;

        startTime = System.nanoTime();
        generateHeightMap(overworldHeightMap);
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Generated heightmap in " + endTime / million + "ms");

        startTime = System.nanoTime();
        smoothenHeightMap(overworldHeightMap);
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Smoothened heightmap in " + endTime / million + "ms");

        startTime = System.nanoTime();
        createWorldBlockArray();
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created world block array in " + endTime / million + "ms");

        startTime = System.nanoTime();
        offsetOverWorld();
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Offset the overworld in " + endTime / million + "ms");

        startTime = System.nanoTime();
        createStone();
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created stones in " + endTime / million + "ms");

        startTime = System.nanoTime();
        createCaverns();
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created caves in " + endTime / million + "ms");

        startTime = System.nanoTime();
        createBedrock();
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Created bedrock in " + endTime / million + "ms");

        startTime = System.nanoTime();
        generateChunks();
        endTime = System.nanoTime() - startTime;
        totalTime += endTime;

        System.out.println("Generated chunks in " + endTime / million + "ms");

        System.out.println("\nWorld generated in " + totalTime / million + "ms\n");

        System.out.println("Lowest Y: " + lowestY);
        System.out.println("Highest Y: " + highestY);
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

    private void generateHeightMap(int[][] heightmap)
    {
        float scalar = Settings.worldY / 2;

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

    private void smoothenHeightMap(int[][] heightMap)
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

    private void createWorldBlockArray()
    {
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
    }

    private void generateChunks()
    {
        int chunkX = Settings.worldX / Settings.chunkWidth;
        int chunkY = Settings.worldZ / Settings.chunkHeight;

        chunks = new Chunk[chunkX][chunkY];

        for (int x = 0; x < Settings.worldX; x += Settings.chunkWidth)
        {
            for (int y = 0; y < Settings.worldZ; y += Settings.chunkHeight)
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

    private void createStone()
    {
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
    }

    private void createBedrock()
    {
        for (int x = 0; x < maxX; x++)
        {
            for (int z = 0; z < maxZ; z++)
            {
                worldBlocks[x][0][z] = Block.BlockType.BEDROCK;
            }
        }
    }

    private void offsetOverWorld()
    {
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
    }

    private void createCaverns()
    {
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
