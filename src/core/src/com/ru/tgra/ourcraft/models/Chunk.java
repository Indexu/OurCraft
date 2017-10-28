package com.ru.tgra.ourcraft.models;

import com.ru.tgra.ourcraft.objects.Block;

import java.util.Map;

public class Chunk
{
    private Map<Integer, Block> blockMap;

    public Chunk(Map<Integer, Block> blockMap)
    {
        this.blockMap = blockMap;
    }

    public void drawBlocks(int viewportID)
    {
        for (Block block : blockMap.values())
        {
            block.draw(viewportID);
        }
    }

    public void addBlock(Block block)
    {
        blockMap.put(block.getID(), block);
    }

    public void removeBlock(int ID)
    {
        blockMap.remove(ID);
    }
}
