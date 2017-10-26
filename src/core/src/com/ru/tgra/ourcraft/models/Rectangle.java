package com.ru.tgra.ourcraft.models;

public class Rectangle
{
    public float x;
    public float y;
    public float width;
    public float height;

    public Rectangle()
    {
        x = 0.0f;
        y = 0.0f;
        width = 0.0f;
        height = 0.0f;
    }

    public Rectangle(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
