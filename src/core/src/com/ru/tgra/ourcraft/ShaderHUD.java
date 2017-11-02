package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.ourcraft.models.*;

import java.nio.FloatBuffer;

public class ShaderHUD
{
    private int renderingProgramID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private int positionLoc;
    private int uvLoc;
    private int globalAmbienceLoc;

    private int diffuseTextureLoc;

    private int modelMatrixLoc;
    private int viewMatrixLoc;
    private int projectionMatrixLoc;

    public ShaderHUD()
    {
        initShader();
        initProgram();
        initLocs();
    }

    public void useShader()
    {
        //Gdx.gl.glLinkProgram(renderingProgramID);
        Gdx.gl.glUseProgram(renderingProgramID);
        Gdx.gl.glEnableVertexAttribArray(positionLoc);
        Gdx.gl.glEnableVertexAttribArray(uvLoc);
    }

    public int getVertexPointer()
    {
        return positionLoc;
    }

    public int getUVPointer()
    {
        return uvLoc;
    }

    public void setDiffuseTexture(Texture tex)
    {
        tex.bind(0);
        Gdx.gl.glUniform1i(diffuseTextureLoc, 0);

        Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
    }

    public void setGlobalAmbience(Color color)
    {
        Gdx.gl.glUniform4f(globalAmbienceLoc, color.r, color.g, color.b, color.a);
    }

    public void setModelMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
    }

    public void setViewMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
    }

    public void setProjectionMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
    }

    private void initShader()
    {
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal("shaders/hud3D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("shaders/hud3D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        System.out.println("Vertex shader HUD 3D compile messages:");
        System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
        System.out.println("Fragment shader HUD 3D compile messages:");
        System.out.println(Gdx.gl.glGetShaderInfoLog(fragmentShaderID));
    }

    private void initProgram()
    {
        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);
    }

    private void initLocs()
    {
        positionLoc	= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(positionLoc);

        uvLoc = Gdx.gl.glGetAttribLocation(renderingProgramID, "a_uv");
        Gdx.gl.glEnableVertexAttribArray(uvLoc);

        modelMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
        viewMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        globalAmbienceLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_globalAmbience");

        diffuseTextureLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_diffuseTexture");
    }
}
