package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.ourcraft.models.Color;
import com.ru.tgra.ourcraft.models.Enums;
import com.ru.tgra.ourcraft.models.Point2D;
import com.ru.tgra.ourcraft.shapes.RectangleGraphic;

import java.nio.FloatBuffer;

public class Shader2D
{
    private int renderingProgramID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private int vertexPointer;
    private int colorLoc;

    private FloatBuffer vertexBuffer;
    private FloatBuffer modelMatrix;
    private FloatBuffer projectionMatrix;

    private int modelMatrixLoc;
    private int projectionMatrixLoc;

    private SpriteBatch batch;
    private BitmapFont robotoNormal;
    private BitmapFont robotoLarge;
    private BitmapFont robotoExtraLarge;
    private BitmapFont arialNormal;

    public Shader2D()
    {
        initProgram();
        initProjectionMatrix();
        initModelMatrix();
        initVertexBuffer();
        initColor();
        initFonts();
        initShapes();
    }

    public void OrthographicProjection2D(float left, float right, float bottom, float top)
    {
        float[] pm = new float[16];

        pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left) / (right - left);
        pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom) / (top - bottom);
        pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        projectionMatrix = BufferUtils.newFloatBuffer(16);
        projectionMatrix.put(pm);
        projectionMatrix.rewind();

        Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);
    }

    public int getVertexPointer()
    {
        return vertexPointer;
    }

    public void setColor(Color color)
    {
        Gdx.gl.glUniform4f(colorLoc, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void setClearColor(Color color)
    {
        Gdx.gl.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void clear()
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void drawText(Point2D position, String text, Color color, Enums.Fonts font, Enums.Size size)
    {
        BitmapFont fontToUse = robotoNormal;

        if (font == Enums.Fonts.ARIAL)
        {
            fontToUse = arialNormal;
        }
        else if (font == Enums.Fonts.ROBOTO)
        {
            switch (size)
            {
                default:
                case NORMAL:
                    fontToUse = robotoNormal;
                    break;

                case LARGE:
                    fontToUse = robotoLarge;
                    break;

                case EXTRA_LARGE:
                    fontToUse = robotoExtraLarge;
                    break;
            }
        }

        GlyphLayout layout = new GlyphLayout(fontToUse, text);

        float offsetX = layout.width / 2;
        float offsetY = layout.height / 2;

        float fontX = position.x - offsetX;
        float fontY = position.y + offsetY;

        batch.begin();

        fontToUse.setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        fontToUse.draw(batch, text, fontX, fontY);

        batch.end();

//        Gdx.gl.glUseProgram(renderingProgramID);
//        Gdx.gl.glEnableVertexAttribArray(vertexPointer);
    }

    public void useShader()
    {
        //Gdx.gl.glLinkProgram(renderingProgramID);
        Gdx.gl.glUseProgram(renderingProgramID);
    }

    public void setModelMatrix(FloatBuffer matrix)
    {
        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
    }

    /*
     * Private helpers
     */

    private void initProgram()
    {
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal("shaders/simple2D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("shaders/simple2D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        System.out.println("Vertex shader 2D compile messages:");
        System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
        System.out.println("Fragment shader 2D compile messages:");
        System.out.println(Gdx.gl.glGetShaderInfoLog(fragmentShaderID));

        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);
//        Gdx.gl.glUseProgram(renderingProgramID);
    }

    private void initProjectionMatrix()
    {
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        OrthographicProjection2D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());
    }

    private void initModelMatrix()
    {
        modelMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");

        float[] mm = new float[16];

        mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
        mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
        mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
        mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

        modelMatrix = BufferUtils.newFloatBuffer(16);
        modelMatrix.put(mm);
        modelMatrix.rewind();

        Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
    }

    private void initVertexBuffer()
    {
        vertexPointer = Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(vertexPointer);

        float[] array = {-0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f};

        vertexBuffer = BufferUtils.newFloatBuffer(8);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    private void initColor()
    {
        colorLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");
    }

    private void initFonts()
    {
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 18;
        robotoNormal = generator.generateFont(parameter);

        parameter.size = 32;
        robotoLarge = generator.generateFont(parameter);

        parameter.size = 46;
        robotoExtraLarge = generator.generateFont(parameter);

        FreeTypeFontGenerator arialGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arial.ttf"));

        parameter.size = 32;
        parameter.spaceX = -2;
        arialNormal = arialGenerator.generateFont(parameter);

        arialGenerator.dispose();
        generator.dispose();
    }

    private void initShapes()
    {
        RectangleGraphic.create(vertexPointer);
    }

    public int getModelMatrixLoc()
    {
        return modelMatrixLoc;
    }
}
