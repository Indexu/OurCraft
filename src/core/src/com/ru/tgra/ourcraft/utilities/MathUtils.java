package com.ru.tgra.ourcraft.utilities;

import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.models.Point3D;

public class MathUtils
{
    public static float clamp(float number, float min, float max)
    {
        if (number < min)
        {
            return min;
        }

        if (max < number)
        {
            return max;
        }

        return number;
    }

    public static boolean isBetween(float number, float lower, float higher)
    {
        return (lower <= number && number <= higher);
    }

    public static boolean sameSignFloats(float f1, float f2)
    {
        boolean f1Negative = ((Float.floatToIntBits(f1)) >> 31) == -1;
        boolean f2Negative = ((Float.floatToIntBits(f2)) >> 31) == -1;

        return f1Negative == f2Negative;
    }

    public static int cartesianHash(int x, int y, int z)
    {
        return x + (y << 10) + (z << 20);
    }

    public static int getChunkX(int x, int chunkWidth)
    {
        return (x / chunkWidth);
    }

    public static int getChunkY(int y, int chunkHeight)
    {
        return (y / chunkHeight);
    }
}
