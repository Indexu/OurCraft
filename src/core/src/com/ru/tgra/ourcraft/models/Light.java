package com.ru.tgra.ourcraft.models;

public class Light
{
    private int ID;
    private Point3D position;
    private Color color;
    private Vector3D direction;
    private float spotFactor;
    private float constantAttenuation;
    private float linearAttenuation;
    private float quadraticAttenuation;
    private boolean spotlight;
    private boolean on;

    public Light()
    {
        ID = 0;
        position = new Point3D();
        color = new Color();
        direction = new Vector3D();
        spotFactor = 0;
        constantAttenuation = 0;
        linearAttenuation = 0;
        quadraticAttenuation = 0;
        on = true;
        spotlight = false;
    }

    public Light(int ID, Point3D position, Color color, Vector3D direction, float spotFactor, float constantAttenuation, float linearAttenuation, float quadraticAttenuation)
    {
        this.ID = ID;
        this.position = position;
        this.color = color;
        this.direction = direction;
        this.spotFactor = spotFactor;
        this.constantAttenuation = constantAttenuation;
        this.linearAttenuation = linearAttenuation;
        this.quadraticAttenuation = quadraticAttenuation;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public Point3D getPosition()
    {
        return position;
    }

    public void setPosition(Point3D position, boolean copy)
    {
        if (copy)
        {
            this.position.x = position.x;
            this.position.y = position.y;
            this.position.z = position.z;
        }
        else
        {
            this.position = position;
        }
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = new Color(color);
    }

    public Vector3D getDirection()
    {
        return direction;
    }

    public void setDirection(Vector3D direction)
    {
        this.direction = direction;
    }

    public float getSpotFactor()
    {
        return spotFactor;
    }

    public void setSpotFactor(float spotFactor)
    {
        this.spotFactor = spotFactor;
    }

    public float getConstantAttenuation()
    {
        return constantAttenuation;
    }

    public void setConstantAttenuation(float constantAttenuation)
    {
        this.constantAttenuation = constantAttenuation;
    }

    public float getLinearAttenuation()
    {
        return linearAttenuation;
    }

    public void setLinearAttenuation(float linearAttenuation)
    {
        this.linearAttenuation = linearAttenuation;
    }

    public float getQuadraticAttenuation()
    {
        return quadraticAttenuation;
    }

    public void setQuadraticAttenuation(float quadraticAttenuation)
    {
        this.quadraticAttenuation = quadraticAttenuation;
    }

    public boolean isOn()
    {
        return on;
    }

    public void setOn(boolean on)
    {
        this.on = on;
    }

    public boolean isSpotlight()
    {
        return spotlight;
    }

    public void setSpotlight(boolean spotlight)
    {
        this.spotlight = spotlight;
    }
}
