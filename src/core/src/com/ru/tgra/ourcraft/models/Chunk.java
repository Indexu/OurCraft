package com.ru.tgra.ourcraft.models;

import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.objects.Block;
import com.ru.tgra.ourcraft.objects.Torch;

import java.util.HashMap;
import java.util.Map;

public class Chunk
{
    private Map<Integer, Block> blockMap;
    private int chunkX;
    private int chunkY;
    private int chunkZ;

    public Chunk(Map<Integer, Block> blockMap, int chunkX, int chunkY, int chunkZ)
    {
        this.blockMap = blockMap;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
    }

    public void drawBlocks()
    {
        for (Block block : blockMap.values())
        {
            block.draw();
        }
    }

    public Block getBlock(int ID)
    {
        return blockMap.get(ID);
    }

    public void addBlock(Block block)
    {
        blockMap.put(block.getID(), block);
    }

    public void removeBlock(int ID)
    {
        blockMap.remove(ID);
    }

    public void setBlockMask(int ID, CubeMask mask)
    {
        Block block = blockMap.get(ID);

        if (block != null)
        {
            block.setMask(mask);
        }
    }

    public void assertBlock(int ID, int x, int y, int z, Block.BlockType type)
    {
        if (!blockMap.containsKey(ID))
        {
            Point3D position = new Point3D(x, y, z);

            Block block = new Block
            (
                ID,
                position,
                Settings.blockSize,
                new CubeMask(),
                type,
                chunkX,
                chunkY,
                chunkZ
            );

            addBlock(block);
        }
    }
}
