package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.objects.Block;

public class BlockUtils
{
    public static CubeMask createBlockMask(int x, int y, int z, Block.BlockType[][][] worldBlocks)
    {
        CubeMask mask = new CubeMask();

        // Up
        if (y+1 != GameManager.worldGenerator.getMaxY() && worldBlocks[x][y+1][z] != Block.BlockType.EMPTY)
        {
            mask.setTop(false);
        }

        // Down
        if (y == 0 || worldBlocks[x][y-1][z] != Block.BlockType.EMPTY)
        {
            mask.setBottom(false);
        }

        // North
        if (x+1 == GameManager.worldGenerator.getMaxX() || worldBlocks[x+1][y][z] != Block.BlockType.EMPTY)
        {
            mask.setNorth(false);
        }

        // South
        if (x == 0 || worldBlocks[x-1][y][z] != Block.BlockType.EMPTY)
        {
            mask.setSouth(false);
        }

        // East
        if (z+1 == GameManager.worldGenerator.getMaxZ() || worldBlocks[x][y][z+1] != Block.BlockType.EMPTY)
        {
            mask.setEast(false);
        }

        // West
        if (z == 0 || worldBlocks[x][y][z-1] != Block.BlockType.EMPTY)
        {
            mask.setWest(false);
        }

        return mask;
    }
}
