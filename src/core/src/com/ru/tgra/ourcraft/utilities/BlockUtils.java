package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.models.Point2D;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.objects.Block;

public class BlockUtils
{
    public static CubeMask createBlockMask(int x, int y, int z, Block.BlockType[][][] worldBlocks)
    {
        CubeMask mask = new CubeMask();

        // Up
        if (y+1 != GameManager.worldGenerator.getMaxY() && (worldBlocks[x][y+1][z] != Block.BlockType.EMPTY && worldBlocks[x][y+1][z] != Block.BlockType.TORCH))
        {
            mask.setTop(false);
        }

        // Down
        if (y == 0 || (worldBlocks[x][y-1][z] != Block.BlockType.EMPTY && worldBlocks[x][y-1][z] != Block.BlockType.TORCH))
        {
            mask.setBottom(false);
        }

        // North
        if (x+1 == GameManager.worldGenerator.getMaxX() || (worldBlocks[x+1][y][z] != Block.BlockType.EMPTY && worldBlocks[x+1][y][z] != Block.BlockType.TORCH))
        {
            mask.setNorth(false);
        }

        // South
        if (x == 0 || (worldBlocks[x-1][y][z] != Block.BlockType.EMPTY && worldBlocks[x-1][y][z] != Block.BlockType.TORCH))
        {
            mask.setSouth(false);
        }

        // East
        if (z+1 == GameManager.worldGenerator.getMaxZ() || (worldBlocks[x][y][z+1] != Block.BlockType.EMPTY && worldBlocks[x][y][z+1] != Block.BlockType.TORCH))
        {
            mask.setEast(false);
        }

        // West
        if (z == 0 || (worldBlocks[x][y][z-1] != Block.BlockType.EMPTY && worldBlocks[x][y][z-1] != Block.BlockType.TORCH))
        {
            mask.setWest(false);
        }

        return mask;
    }

    public static Point3D getTargetArea(Block targetBlock)
    {
        Point3D pos = new Point3D(targetBlock.getPosition());

        switch (targetBlock.getTargetFace())
        {
            case TOP:
                pos.y += 1;
                break;

            case BOTTOM:
                pos.y -= 1;
                break;

            case NORTH:
                pos.x += 1;
                break;

            case SOUTH:
                pos.x -= 1;
                break;

            case EAST:
                pos.z += 1;
                break;

            case WEST:
                pos.z -= 1;
                break;
        }

        return pos;
    }

    public static void constructRenderMask(CubeMask mask, CubeMask renderMask, Point3D pos, Point3D playerPos)
    {
        renderMask.setMask(mask);

        if (mask.isBottom() && pos.y < playerPos.y)
        {
            renderMask.setBottom(false);
        }

        if (mask.isTop() && playerPos.y < pos.y)
        {
            renderMask.setTop(false);
        }

        if (mask.isNorth() && playerPos.x < pos.x)
        {
            renderMask.setNorth(false);
        }

        if (mask.isSouth() && pos.x < playerPos.x)
        {
            renderMask.setSouth(false);
        }

        if (mask.isEast() && playerPos.z < pos.z)
        {
            renderMask.setEast(false);
        }

        if (mask.isWest() && pos.z < playerPos.z)
        {
            renderMask.setWest(false);
        }
    }

    public static boolean isValidPlaceForTorch(int x, int y, int z, Block.BlockType[][][] worldBlocks)
    {
        return !(worldBlocks[x][y-1][z] == Block.BlockType.EMPTY || worldBlocks[x][y-1][z] == Block.BlockType.TORCH);
    }
}
