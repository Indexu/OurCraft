package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Vector3D;
import com.ru.tgra.ourcraft.objects.Block;
import com.ru.tgra.ourcraft.objects.Player;

public class CollisionsUtil
{
    public static void playerBlockCollisions(Player player)
    {
        Point3D position = player.getPosition();
        float radius = player.getRadius();

        int x = player.getWorldX();
        int y = player.getWorldY();
        int z = player.getWorldZ();

        float unitX = (position.x - ((float) (int) position.x) + 0.5f) % 1f;
        float unitY = (position.y - ((float) (int) position.y) + 0.5f) % 1f;
        float unitZ = (position.z - ((float) (int) position.z) + 0.5f) % 1f;

        int maxX = GameManager.worldBlocks.length - 1;
        int maxY = GameManager.worldBlocks[0].length - 1;
        int maxZ = GameManager.worldBlocks[0][0].length - 1;

        if (0 <= x && x < maxX
                && 0 <= y && y < maxY
                && 0 <= z && z < maxZ)
        {
            float left = unitX + radius;
            float right = unitX - radius;
            float top = unitZ + radius;
            float bottom = unitZ - radius;
            float below = unitY - radius;
            float above = unitY + radius;
            boolean collided = false;

            // Check right
            if (right < 0 && 0 < x &&
                ((GameManager.worldBlocks[x - 1][y][z] != Block.BlockType.EMPTY && GameManager.worldBlocks[x - 1][y][z] != Block.BlockType.TORCH) ||
                (0 < y && GameManager.worldBlocks[x - 1][y - 1][z] != Block.BlockType.EMPTY && GameManager.worldBlocks[x - 1][y - 1][z] != Block.BlockType.TORCH)))
            {
                position.x -= right;
                collided = true;
            }
            // Check left
            else if (1 < left &&
                    ((GameManager.worldBlocks[x + 1][y][z] != Block.BlockType.EMPTY && GameManager.worldBlocks[x + 1][y][z] != Block.BlockType.TORCH) ||
                    (0 < y && GameManager.worldBlocks[x + 1][y - 1][z] != Block.BlockType.EMPTY && GameManager.worldBlocks[x + 1][y - 1][z] != Block.BlockType.TORCH)))
            {
                position.x -= (left % 1);
                collided = true;
            }

            // Check bottom
            if (bottom < 0 && 0 < z && ((GameManager.worldBlocks[x][y][z - 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x][y][z - 1] != Block.BlockType.TORCH) ||
                (0 < y && GameManager.worldBlocks[x][y - 1][z - 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x][y - 1][z - 1] != Block.BlockType.TORCH)))
            {
                position.z -= bottom;
                collided = true;
            }
            // Check top
            else if (1 < top &&
                    ((GameManager.worldBlocks[x][y][z + 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x][y][z + 1] != Block.BlockType.TORCH) ||
                    (0 < y && GameManager.worldBlocks[x][y - 1][z + 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x][y - 1][z + 1] != Block.BlockType.TORCH)))
            {
                position.z -= (top % 1);
                collided = true;
            }

            // Check below
            if (below < 0 && 1 < y &&
                GameManager.worldBlocks[x][y - Settings.playerHeight][z] != Block.BlockType.EMPTY &&
                GameManager.worldBlocks[x][y - Settings.playerHeight][z] != Block.BlockType.TORCH)
            {
                position.y -= below;
                player.resetGravity();
            }
            // Check above
            else if (1 < above && y + 1 < GameManager.worldBlocks[x].length &&
                    GameManager.worldBlocks[x][y + 1][z] != Block.BlockType.EMPTY &&
                    GameManager.worldBlocks[x][y + 1][z] != Block.BlockType.TORCH)
            {
                position.y -= (above % 1);
                player.resetGravity();
            }

            // Check diagonal
            if (!collided)
            {
                // Top left
                if (x + 1 < maxX && z + 1 < maxZ &&
                    ((GameManager.worldBlocks[x + 1][y][z + 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x + 1][y][z + 1] != Block.BlockType.TORCH) ||
                    ((0 < y && GameManager.worldBlocks[x + 1][y - 1][z + 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x + 1][y - 1][z + 1] != Block.BlockType.TORCH))))
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (z + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Top right
                if (0 < x && z + 1 < maxZ &&
                    ((GameManager.worldBlocks[x - 1][y][z + 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x - 1][y][z + 1] != Block.BlockType.TORCH) ||
                    ((0 < y && GameManager.worldBlocks[x - 1][y - 1][z + 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x - 1][y - 1][z + 1] != Block.BlockType.TORCH))))
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (z + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom left
                if (x + 1 < maxX && 0 < z &&
                    ((GameManager.worldBlocks[x + 1][y][z - 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x + 1][y][z - 1] != Block.BlockType.TORCH) ||
                    (0 < y && GameManager.worldBlocks[x + 1][y - 1][z - 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x + 1][y - 1][z - 1] != Block.BlockType.TORCH)))
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (z - 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom right
                if (0 < x && 0 < z &&
                    ((GameManager.worldBlocks[x - 1][y][z - 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x - 1][y][z - 1] != Block.BlockType.TORCH) ||
                    (0 < y && GameManager.worldBlocks[x - 1][y - 1][z - 1] != Block.BlockType.EMPTY && GameManager.worldBlocks[x - 1][y - 1][z - 1] != Block.BlockType.TORCH)))
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (z - 0.5f));
                    cornerCollision(position, v, radius);
                }
            }
        }
    }

    // Raycast source: https://gamedev.stackexchange.com/questions/18436/most-efficient-aabb-vs-ray-collision-algorithms
    public static boolean lineIntersectsWithBlock(Point3D lineOrigin, Vector3D lineDirection, Block block)
    {
        // r.dir is unit direction vector of ray
        Vector3D dirfrac = new Vector3D();
        dirfrac.x = 1.0f / lineDirection.x;
        dirfrac.y = 1.0f / lineDirection.y;
        dirfrac.z = 1.0f / lineDirection.z;

        // lb is the corner of AABB with minimal coordinates - left bottom, rt is maximal corner
        // r.org is origin of ray
        float t1 = (block.getLeftBottom().x - lineOrigin.x) * dirfrac.x;
        float t2 = (block.getRightTop().x - lineOrigin.x) * dirfrac.x;
        float t3 = (block.getLeftBottom().y - lineOrigin.y) * dirfrac.y;
        float t4 = (block.getRightTop().y - lineOrigin.y) * dirfrac.y;
        float t5 = (block.getLeftBottom().z - lineOrigin.z) * dirfrac.z;
        float t6 = (block.getRightTop().z - lineOrigin.z) * dirfrac.z;

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        // if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
        if (tmax < 0)
        {
            return false;
        }

        // if tmin > tmax, ray doesn't intersect AABB
        if (tmin > tmax)
        {
            return false;
        }

        if (tmin == t1)
        {
            block.setTargetFace(Block.TargetFace.SOUTH);
        }
        else if (tmin == t2)
        {
            block.setTargetFace(Block.TargetFace.NORTH);
        }
        else if (tmin == t3)
        {
            block.setTargetFace(Block.TargetFace.BOTTOM);
        }
        else if (tmin == t4)
        {
            block.setTargetFace(Block.TargetFace.TOP);
        }
        else if (tmin == t5)
        {
            block.setTargetFace(Block.TargetFace.WEST);
        }
        else if (tmin == t6)
        {
            block.setTargetFace(Block.TargetFace.EAST);
        }

        return true;
    }

    private static void cornerCollision(Point3D position, Vector3D cornerVector, float radius)
    {
        float distance = cornerVector.length();

        if (distance < radius)
        {
            cornerVector.divide(distance);
            cornerVector.scale(radius - distance);

            position.add(cornerVector);
        }
    }
}
