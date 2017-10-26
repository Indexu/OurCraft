package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Vector3D;
import com.ru.tgra.ourcraft.objects.Player;

public class CollisionsUtil
{
    public static void playerWallCollisions(Player player)
    {
        Point3D position = player.getPosition();
        float radius = player.getRadius();

        int x = player.getMazeX();
        int y = player.getMazeY();

        float unitX = (position.x - ((float)(int)position.x) + 0.5f) % 1f;
        float unitZ = (position.z - ((float)(int)position.z) + 0.5f) % 1f;

        if (0 <= x && x < GameManager.mazeWalls.length-1 && 0 <= y && y < GameManager.mazeWalls[0].length-1)
        {
            float left = unitX + radius;
            float right = unitX - radius;
            float top = unitZ + radius;
            float bottom = unitZ - radius;
            boolean collided = false;

            // Check right
            if (right < 0 && x != 0 && GameManager.mazeWalls[x-1][y])
            {
                position.x -= right;
                collided = true;
            }
            // Check left
            else if (1 < left && x != GameManager.mazeWalls.length-1 && GameManager.mazeWalls[x+1][y])
            {
                position.x -= (left % 1);
                collided = true;
            }

            // Check bottom
            if (bottom < 0 && y != 0 && GameManager.mazeWalls[x][y-1])
            {
                position.z -= bottom;
                collided = true;
            }
            // Check top
            else if (1 < top && y != GameManager.mazeWalls[0].length-1 && GameManager.mazeWalls[x][y+1])
            {
                position.z -= (top % 1);
                collided = true;
            }

            // Check diagonal
            if (!collided)
            {
                int sideLength = GameManager.mazeWalls.length-1;
                // Top left
                if (x != sideLength && y != sideLength &&  GameManager.mazeWalls[x+1][y+1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (y + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Top right
                if (x != 0 && y != sideLength && GameManager.mazeWalls[x-1][y+1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (y + 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom left
                if (x != sideLength && y != 0 && GameManager.mazeWalls[x+1][y-1])
                {
                    Vector3D v = new Vector3D(position.x - (x + 0.5f), 0f, position.z - (y - 0.5f));
                    cornerCollision(position, v, radius);
                }

                // Bottom right
                if (x != 0 && y != 0 && GameManager.mazeWalls[x-1][y-1])
                {
                    Vector3D v = new Vector3D(position.x - (x - 0.5f), 0f, position.z - (y - 0.5f));
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
