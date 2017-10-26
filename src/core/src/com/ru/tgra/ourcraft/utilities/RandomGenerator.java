package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.models.Point3D;

import java.util.Random;

public class RandomGenerator
{
    private static Random rand = new Random();

    public static float randomFloatInRange(float min, float max)
    {
        return rand.nextFloat() * (max - min) + min;
    }

    public static int randomIntegerInRange(int min, int max)
    {
        return rand.nextInt(max - min + 1) + min;
    }

    public static boolean nextBool()
    {
        return rand.nextBoolean();
    }

    public static Point3D randomPointInXZ(float xStart, float xEnd, float zStart, float zEnd)
    {
        float x = randomFloatInRange(xStart, xEnd);
        float z = randomFloatInRange(zStart, zEnd);

        return new Point3D(x, 0f, z);
    }
}
