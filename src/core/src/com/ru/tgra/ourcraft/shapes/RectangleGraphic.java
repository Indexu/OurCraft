package com.ru.tgra.ourcraft.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class RectangleGraphic
{
    private static FloatBuffer vertexBuffer;
    private static FloatBuffer vertexBufferCenter;
    private static FloatBuffer vertexBufferOffset;
    private static int vertexPointer;

    public static void create(int vertexPointer)
    {
        RectangleGraphic.vertexPointer = vertexPointer;

        float[] center = {-0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, 0.5f,
                0.5f, -0.5f};

        vertexBufferCenter = BufferUtils.newFloatBuffer(8);
        vertexBufferCenter.put(center);
        vertexBufferCenter.rewind();

        float[] offset = {-0.5f, 0.0f,
                -0.5f, 1f,
                0.5f, 1f,
                0.5f, 0f};

        vertexBufferOffset = BufferUtils.newFloatBuffer(8);
        vertexBufferOffset.put(offset);
        vertexBufferOffset.rewind();

        vertexBuffer = vertexBufferCenter;
    }

    public static void drawSolid()
    {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,
                false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
    }

    public static void drawOutline()
    {
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,
                false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, 4);
    }

    public static void setVertexBuffer(boolean center)
    {
        vertexBuffer = (center ? vertexBufferCenter : vertexBufferOffset);
    }
}
