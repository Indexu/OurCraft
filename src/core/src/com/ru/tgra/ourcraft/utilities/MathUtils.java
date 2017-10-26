package com.ru.tgra.ourcraft.utilities;

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
}
