package com.ru.tgra.ourcraft.models;

public class Color
{
    public float r;
    public float g;
    public float b;
    public float a;

    public Color()
    {
        this.r = 0f;
        this.g = 0f;
        this.b = 0f;
        this.a = 0f;
    }

    public Color(float red, float green, float blue, float alpha)
    {
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
    }

    public Color(Color color)
    {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public float getRed()
    {
        return r;
    }

    public void setRed(float r)
    {
        this.r = r;
    }

    public float getGreen()
    {
        return g;
    }

    public void setGreen(float g)
    {
        this.g = g;
    }

    public float getBlue()
    {
        return b;
    }

    public void setBlue(float b)
    {
        this.b = b;
    }

    public float getAlpha()
    {
        return a;
    }

    public void setAlpha(float a)
    {
        this.a = a;
    }
}
