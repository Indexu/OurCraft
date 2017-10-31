package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.ourcraft.models.*;

import java.nio.FloatBuffer;

public class Shader
{
    private int renderingProgramID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private int positionLoc;
    private int normalLoc;
    private int uvLoc;

    private int eyePosLoc;
    private int shininessFactorLoc;
    private int globalAmbienceLoc;

    private int[] lightPosLoc;
    private int[] lightColorLoc;
    private int[] lightDirectionLoc;
    private int[] lightSpotFactorLoc;
    private int[] lightConstAttLoc;
    private int[] lightLinearAttLoc;
    private int[] lightQuadAttLoc;
    private int[] lightOnLoc;

    private int usesDiffuseTexLoc;
    private int diffuseTextureLoc;

    private int materialDiffuseLoc;
    private int materialSpecularLoc;
    private int materialAmbienceLoc;
    private int materialEmissionLoc;
    private int materialTransparencyLoc;

    private int brightnessLoc;

    private int modelMatrixLoc;
    private int viewMatrixLoc;
    private int projectionMatrixLoc;

    public Shader()
    {
        initShader();
        initProgram();
        initLocs();

        Gdx.gl.glUseProgram(renderingProgramID);
    }

    public void useShader()
    {
        //Gdx.gl.glLinkProgram(renderingProgramID);
        Gdx.gl.glUseProgram(renderingProgramID);
        Gdx.gl.glEnableVertexAttribArray(positionLoc);
        Gdx.gl.glEnableVertexAttribArray(normalLoc);
        Gdx.gl.glEnableVertexAttribArray(uvLoc);
    }

    public int getVertexPointer()
    {
        return positionLoc;
    }

    public int getNormalPointer()
    {
        return normalLoc;
    }

    public int getUVPointer()
    {
        return uvLoc;
    }

    public void setMaterial(Material material)
    {
        setMaterialAmbience(material.getAmbience());
        setMaterialDiffuse(material.getDiffuse());
        setMaterialSpecular(material.getSpecular());
        setMaterialEmission(material.getEmission());
        setMaterialTransparency(material.getTransparency());
        setShininessFactor(material.getShininess());
    }

    public void setLight(Light light)
    {
        setLightPosition(light.getID(), light.getPosition());
        setLightColor(light.getID(), light.getColor());
        setLightDirection(light.getID(), light.getDirection());
        setSpotFactor(light.getID(), light.getSpotFactor());
        setConstantAttenuation(light.getID(), light.getConstantAttenuation());
        setLinearAttenuation(light.getID(), light.getLinearAttenuation());
        setQuadraticAttenuation(light.getID(), light.getQuadraticAttenuation());
        setOn(light.getID(), light.isOn());
    }

    public void setDiffuseTexture(Texture tex)
    {
        if(tex == null)
        {
            Gdx.gl.glUniform1f(usesDiffuseTexLoc, 0.0f);
        }
        else
        {
            tex.bind(0);
            Gdx.gl.glUniform1i(diffuseTextureLoc, 0);
            Gdx.gl.glUniform1f(usesDiffuseTexLoc, 1.0f);

            Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
            Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        }
    }

    public void setMaterialDiffuse(Color color)
    {
        Gdx.gl.glUniform4f(materialDiffuseLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialSpecular(Color color)
    {
        Gdx.gl.glUniform4f(materialSpecularLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialAmbience(Color color)
    {
        Gdx.gl.glUniform4f(materialAmbienceLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialEmission(Color color)
    {
        Gdx.gl.glUniform4f(materialEmissionLoc, color.r, color.g, color.b, color.a);
    }

    public void setMaterialTransparency(float transparency)
    {
        Gdx.gl.glUniform1f(materialTransparencyLoc, transparency);
    }

    public void setShininessFactor(float f)
    {
        Gdx.gl.glUniform1f(shininessFactorLoc, f);
    }

    public void setBrightness(float f)
    {
        Gdx.gl.glUniform1f(brightnessLoc, f);
    }

    public void setLightPosition(int lightID, Point3D position)
    {
        Gdx.gl.glUniform4f(lightPosLoc[lightID], position.x, position.y, position.z, 1.0f);
    }

    public void setLightColor(int lightID, Color color)
    {
        Gdx.gl.glUniform4f(lightColorLoc[lightID], color.r, color.g, color.b, color.a);
    }

    public void setLightDirection(int lightID, Vector3D direction)
    {
        Gdx.gl.glUniform4f(lightDirectionLoc[lightID], direction.x, direction.y, direction.z, 0f);
    }

    public void setSpotFactor(int lightID, float f)
    {
        Gdx.gl.glUniform1f(lightSpotFactorLoc[lightID], f);
    }

    public void setConstantAttenuation(int lightID, float f)
    {
        Gdx.gl.glUniform1f(lightConstAttLoc[lightID], f);
    }

    public void setLinearAttenuation(int lightID, float f)
    {
        Gdx.gl.glUniform1f(lightLinearAttLoc[lightID], f);
    }

    public void setQuadraticAttenuation(int lightID, float f)
    {
        Gdx.gl.glUniform1f(lightQuadAttLoc[lightID], f);
    }

    public void setOn(int lightID, boolean on)
    {
        float f = (on ? 1.0f : 0.0f);
        Gdx.gl.glUniform1f(lightOnLoc[lightID], f);
    }

    public void setGlobalAmbience(Color color)
    {
        Gdx.gl.glUniform4f(globalAmbienceLoc, color.r, color.g, color.b, color.a);
    }

    public void setEyePosition(Point3D position)
    {
        Gdx.gl.glUniform4f(eyePosLoc, position.x, position.y, position.z, 1.0f);
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

        vertexShaderString = Gdx.files.internal("shaders/simple3D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("shaders/simple3D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        System.out.println("Vertex shader 3D compile messages:");
        System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
        System.out.println("Fragment shader 3D compile messages:");
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

        normalLoc = Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
        Gdx.gl.glEnableVertexAttribArray(normalLoc);

        uvLoc = Gdx.gl.glGetAttribLocation(renderingProgramID, "a_uv");
        Gdx.gl.glEnableVertexAttribArray(uvLoc);

        modelMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
        viewMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        eyePosLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_eyePosition");

        brightnessLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_brightness");

        lightPosLoc = new int[Settings.numberOfLights];
        lightColorLoc = new int[Settings.numberOfLights];
        lightDirectionLoc = new int[Settings.numberOfLights];
        lightSpotFactorLoc = new int[Settings.numberOfLights];
        lightConstAttLoc = new int[Settings.numberOfLights];
        lightLinearAttLoc = new int[Settings.numberOfLights];
        lightQuadAttLoc = new int[Settings.numberOfLights];
        lightOnLoc = new int[Settings.numberOfLights];

        for (int i = 0; i < Settings.numberOfLights; i++)
        {
            lightPosLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition[" + i + "]");
            lightColorLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lights[" + i + "].color");
            lightDirectionLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lights[" + i + "].direction");
            lightSpotFactorLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lights[" + i + "].spotFactor");
            lightConstAttLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lights[" + i + "].constantAttenuation");
            lightLinearAttLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lights[" + i + "].linearAttenuation");
            lightQuadAttLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lights[" + i + "].quadraticAttenuation");
            lightOnLoc[i] = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lights[" + i + "].on");
        }

        globalAmbienceLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_globalAmbience");

        usesDiffuseTexLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesDiffuseTexture");
        diffuseTextureLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_diffuseTexture");

        materialDiffuseLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
        materialSpecularLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialSpecular");
        materialAmbienceLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialAmbience");
        materialEmissionLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialEmission");
        materialTransparencyLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialTransparency");
        shininessFactorLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_shininessFactor");
    }
}
