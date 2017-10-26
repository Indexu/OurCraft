package com.ru.tgra.ourcraft.models;

public class CubeMask
{
    private boolean north;
    private boolean south;
    private boolean east;
    private boolean west;
    private boolean top;
    private boolean bottom;

    public CubeMask(boolean north, boolean south, boolean east, boolean west, boolean top, boolean bottom)
    {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.top = top;
        this.bottom = bottom;
    }

    public CubeMask()
    {
        this.north = true;
        this.south = true;
        this.east = true;
        this.west = true;
        this.top = true;
        this.bottom = true;
    }

    public boolean isNorth()
    {
        return north;
    }

    public void setNorth(boolean north)
    {
        this.north = north;
    }

    public boolean isSouth()
    {
        return south;
    }

    public void setSouth(boolean south)
    {
        this.south = south;
    }

    public boolean isEast()
    {
        return east;
    }

    public void setEast(boolean east)
    {
        this.east = east;
    }

    public boolean isWest()
    {
        return west;
    }

    public void setWest(boolean west)
    {
        this.west = west;
    }

    public boolean isTop()
    {
        return top;
    }

    public void setTop(boolean top)
    {
        this.top = top;
    }

    public boolean isBottom()
    {
        return bottom;
    }

    public void setBottom(boolean bottom)
    {
        this.bottom = bottom;
    }

    @Override
    public String toString()
    {
        return "CubeMask{" +
                "north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                ", top=" + top +
                ", bottom=" + bottom +
                '}';
    }
}
