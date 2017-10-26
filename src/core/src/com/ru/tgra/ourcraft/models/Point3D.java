package com.ru.tgra.ourcraft.models;

public class Point3D {

	public float x;
	public float y;
	public float z;

	public Point3D()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Point3D(Point3D p)
	{
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	public Point3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(Vector3D v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
	}

	public void set(Point3D p)
	{
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		return String.format("x: %.3f, y: %.3f, z: %.3f", x, y, z);
	}
}
