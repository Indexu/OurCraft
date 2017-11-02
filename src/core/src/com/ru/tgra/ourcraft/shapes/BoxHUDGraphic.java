package com.ru.tgra.ourcraft.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.ourcraft.Shader;
import com.ru.tgra.ourcraft.ShaderHUD;
import com.ru.tgra.ourcraft.models.CubeMask;

import java.nio.FloatBuffer;

public class BoxHUDGraphic {

    private static FloatBuffer vertexBuffer;

    public static void create() {

        //VERTEX ARRAY IS FILLED HERE
        float[] vertexArray = {-0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f};

        vertexBuffer = BufferUtils.newFloatBuffer(72);
        vertexBuffer.put(vertexArray);
        vertexBuffer.rewind();
    }

    public static void drawSolidCube(ShaderHUD shader, Texture diffuseTexture, FloatBuffer uvBuffer, CubeMask mask)
    {
        shader.setDiffuseTexture(diffuseTexture);

        Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);

        if (uvBuffer != null)
        {
            Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
        }

        // North
        if (mask.isWest())
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);
        }

        // South
        if (mask.isEast())
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 4, 4);
        }

        // Bottom
        if (mask.isBottom())
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 8, 4);
        }

        // Top
        if (mask.isTop())
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 12, 4);
        }

        // West
        if (mask.isSouth())
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 16, 4);
        }

        // East
        if (mask.isNorth())
        {
            Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 20, 4);
        }
    }

}
