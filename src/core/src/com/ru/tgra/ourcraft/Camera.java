package com.ru.tgra.ourcraft;

import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Vector3D;

import java.nio.FloatBuffer;

public class Camera
{
    public Point3D eye;

    public Vector3D u;
    public Vector3D v;
    public Vector3D n;

    private FloatBuffer matrixBuffer;

    private boolean orthographic;

    private float left, right, bottom, top, near, far;

    public Camera()
    {
        matrixBuffer = BufferUtils.newFloatBuffer(16);

        eye = new Point3D();
        u = new Vector3D(1, 0 , 0);
        v = new Vector3D(0, 1 , 0);
        n = new Vector3D(0, 0 , 1);

        this.left = -1;
        this.right = 1;
        this.bottom = -1;
        this.top = 1;
        this.near = -1;
        this.far = 1;
    }

    public void look(Point3D eye, Point3D center, Vector3D up)
    {
        this.eye = eye;

        n = Vector3D.difference(eye, center);
        u = up.cross(n);
        n.normalize();
        u.normalize();
        v = n.cross(u);
    }

    public void setEye(float x, float y, float z)
    {
        eye.set(x, y, z);
    }

    public void slide(float deltaU, float deltaV, float deltaN)
    {
        eye.x += deltaU * u.x + deltaV * v.x + deltaN * n.x;
        //eye.y += deltaU * u.y + deltaV * v.y + deltaN * n.y;
        eye.z += deltaU * u.z + deltaV * v.z + deltaN * n.z;
    }

    public void roll(float angle)
    {
        rotate(angle, u, v);

        /*
        float rads = angle * (float)Math.PI / 180.0f;
        float c = (float)Math.cos(rads);
        float s = (float)Math.sin(rads);

        Vector3D t = new Vector3D(u);

        u.set(t.x * c - v.x * s, t.y * c - v.y * s, t.z * c - v.z * s);
        v.set(t.x * s + v.x * c, t.y * s + v.y * c, t.z * s + v.z * c);
        */
    }

    public void yaw(float angle)
    {
        rotate(angle, u, n);

        /*
        float rads = angle * (float)Math.PI / 180.0f;
        float c = (float)Math.cos(rads);
        float s = (float)Math.sin(rads);

        Vector3D t = new Vector3D(u);

        u.set(t.x * c - n.x * s, t.y * c - n.y * s, t.z * c - n.z * s);
        n.set(t.x * s + n.x * c, t.y * s + n.y * c, t.z * s + n.z * c);
        */
    }

    public void pitch(float angle)
    {
        rotate(angle, n, v);

        /*

        float rads = angle * (float)Math.PI / 180.0f;
        float c = (float)Math.cos(rads);
        float s = (float)Math.sin(rads);

        Vector3D t = new Vector3D(n);

        n.set(t.x * c - v.x * s, t.y * c - v.y * s, t.z * c - v.z * s);
        v.set(t.x * s + v.x * c, t.y * s + v.y * c, t.z * s + v.z * c);
        */
    }

    public void setOrthographicProjection(float left, float right, float bottom, float top, float near, float far)
    {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;

        orthographic = true;
    }

    public void setPerspectiveProjection(float fov, float ratio, float near, float far)
    {
        this.top = near * (float) Math.tan(((double)fov/2.0) * Math.PI / 180.0); // N * tan(fov/2)
        this.bottom = -top;
        this.right = ratio * top;
        this.left = -right;
        this.near = near;
        this.far = far;

        orthographic = false;
    }

    public FloatBuffer getViewMatrix()
    {
        Vector3D minusEye = new Vector3D(-eye.x, -eye.y, -eye.z);

        float[] pm = new float[16];

        pm[0] = u.x; pm[4] = u.y; pm[8] = u.z; pm[12] = minusEye.dot(u);
        pm[1] = v.x; pm[5] = v.y; pm[9] = v.z; pm[13] = minusEye.dot(v);
        pm[2] = n.x; pm[6] = n.y; pm[10] = n.z; pm[14] = minusEye.dot(n);
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        matrixBuffer.put(pm);
        matrixBuffer.rewind();

        return matrixBuffer;
    }

    public FloatBuffer getProjectionMatrix()
    {
        if (orthographic)
        {
            buildOrthographicProjection3D();
        }
        else
        {
            buildPerspectiveProjection3D();
        }

        return matrixBuffer;
    }

    private void buildOrthographicProjection3D() {
        float[] pm = new float[16];

        pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left) / (right - left);
        pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom) / (top - bottom);
        pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 2.0f / (near - far); pm[14] = (near + far) / (near - far);
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        matrixBuffer.put(pm);
        matrixBuffer.rewind();
    }

    private void buildPerspectiveProjection3D() {
        float[] pm = new float[16];

        pm[0] = (2 * near) / (right - left); pm[4] = 0.0f; pm[8] = (right + left) / (right - left); pm[12] = 0.0f;
        pm[1] = 0.0f; pm[5] = (2 * near) / (top - bottom); pm[9] = (top + bottom) / (top - bottom); pm[13] = 0.0f;
        pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = -(far + near) / (far - near); pm[14] = (-2 * far * near) / (far - near);
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = -1.0f; pm[15] = 0.0f;

        matrixBuffer.put(pm);
        matrixBuffer.rewind();

    }

    private void rotate(float angle, Vector3D v1, Vector3D v2)
    {
        float rads = angle * (float)Math.PI / 180.0f;
        float c = (float)Math.cos(rads);
        float s = (float)Math.sin(rads);

        Vector3D t = new Vector3D(v1);

        v1.set(t.x * c - v2.x * s, t.y * c - v2.y * s, t.z * c - v2.z * s);
        v2.set(t.x * s + v2.x * c, t.y * s + v2.y * c, t.z * s + v2.z * c);
    }
}
