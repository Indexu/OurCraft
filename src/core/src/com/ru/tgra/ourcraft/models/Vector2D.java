package com.ru.tgra.ourcraft.models;

public class Vector2D
{

    public float x;
    public float y;

    public Vector2D()
    {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v)
    {
        this.x = v.x;
        this.y = v.y;
    }

    public void scale(float S)
    {
        x *= S;
        y *= S;
    }

    public void add(Vector2D v2)
    {
        x += v2.x;
        y += v2.y;
    }

    public float dot(Vector2D v2)
    {
        return x*v2.x + y*v2.y;
    }

    public float dotSelf()
    {
        return x*x + y*y;
    }

    public float length()
    {
        return (float)Math.sqrt(dotSelf());
    }

    public void normalize()
    {
        float len = length();

        if (len != 0)
        {
            x = x / len;
            y = y / len;
        }
    }

    public Vector2D getPerp()
    {
        return new Vector2D(y, -x);
    }

    public static Vector2D difference(Point2D P2, Point2D P1)
    {
        return new Vector2D(P2.x-P1.x, P2.y-P1.y);
    }

    @Override
    public String toString()
    {
        return "x: " + x + " | y: " + y;
    }
}
