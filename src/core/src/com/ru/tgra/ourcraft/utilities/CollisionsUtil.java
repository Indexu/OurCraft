package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Vector3D;
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

        float unitX = (position.x - ((float)(int)position.x) + 0.5f) % 1f;
        float unitY = (position.y - ((float)(int)position.y) + 0.5f) % 1f;
        float unitZ = (position.z - ((float)(int)position.z) + 0.5f) % 1f;

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
            float above = unitY - radius;
            boolean collided = false;

            // Check right
            if (right < 0 && x != 0 && GameManager.worldBlocks[x-1][y][z])
            {
                position.x -= right;
                collided = true;
            }
            // Check left
            else if (1 < left && GameManager.worldBlocks[x+1][y][z])
            {
                position.x -= (left % 1);
                collided = true;
            }

            // Check bottom
            if (bottom < 0 && z != 0 && GameManager.worldBlocks[x][y][z-1])
            {
                position.z -= bottom;
                collided = true;
            }
            // Check top
            else if (1 < top && GameManager.worldBlocks[x][y][z+1])
            {
                position.z -= (top % 1);
                collided = true;
            }

            // Check below
            if (below < 0 && y != 0 && GameManager.worldBlocks[x][y-1][z])
            {
                position.y -= below;
            }
            // Check above
            else if (1 < above && GameManager.worldBlocks[x][y+1][z])
            {
                position.y -= (above % 1);
            }

            // Check diagonal
            if (!collided)
            {
                // Top left
                if (GameManager.worldBlocks[x+1][y][z+1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (z + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Top right
                if (x != 0 && GameManager.worldBlocks[x-1][y][z+1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (z + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom left
                if (z != 0 && GameManager.worldBlocks[x+1][y][z-1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (z - 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom right
                if (x != 0 && z != 0 && GameManager.worldBlocks[x-1][y][z-1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (z - 0.5f));
                    cornerCollision(position, v, radius);
                }
            }
        }
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
