package com.ru.tgra.ourcraft.models;

public class Point2D
{

    public float x;
    public float y;

    public Point2D()
    {
        this.x = 0;
        this.y = 0;
    }

    public Point2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D point)
    {
        this.x = point.x;
        this.y = point.y;
    }

    public void setPoint(Point2D point)
    {
        x = point.x;
        y = point.y;
    }

    public void setPoint(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D v)
    {
        x += v.x;
        y += v.y;
    }

    public boolean isBetween(Point2D p1, Point2D p2)
    {
        if (Math.abs(p1.x - p2.x) < Math.abs(p1.y - p2.y))
        {
            if (p1.y < p2.y)
            {
                return (p1.y < y && y < p2.y);
            }

            return (p2.y < y && y < p1.y);
        }

        if (p1.x < p2.x)
        {
            return (p1.x < x && x < p2.x);
        }

        return (p2.x < x && x < p1.x);
    }

    public Vector2D vectorBetweenPoints(Point2D point)
    {
        return new Vector2D(point.x - x, point.y - y);
    }

    public static Point2D additionVector(Point2D p, Vector2D v)
    {
        return new Point2D(p.x + v.x, p.y + v.y);
    }

    @Override
    public String toString()
    {
        return "x: " + x + " | y: " + y;
    }
}
