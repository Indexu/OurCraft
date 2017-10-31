package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ru.tgra.ourcraft.models.Color;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Rectangle;
import com.ru.tgra.ourcraft.models.Vector3D;

public class GraphicsEnvironment
{
    public static Shader shader;
    public static Shader2D shader2D;
    public static Rectangle viewport;

    private static SpriteBatch batch;
    private static BitmapFont fontNormal;
    private static BitmapFont fontLarge;
    private static BitmapFont fontExtraLarge;
    private static GlyphLayout layout;

    public static void init()
    {
        shader = new Shader();
        shader.setBrightness(1.0f);

        shader2D = new Shader2D();

        initFonts();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.8f, 1.0f);
        enableBlending();
        //setFullscreen();

        shader.useShader();
    }

    public static void setViewport(float x, float y, float width, float height)
    {
        viewport = new Rectangle(x, y, width, height);
    }

    public static void setPerspectiveCamera()
    {
        Gdx.gl.glViewport
        (
                (int) viewport.x,
                (int) viewport.y,
                (int) viewport.width,
                (int) viewport.height
        );

        GameManager.player.getCamera().setPerspectiveProjection(Settings.playerFOV, viewport.width / viewport.height, 0.1f, 5000.0f);
        shader.setViewMatrix(GameManager.player.getCamera().getViewMatrix());
        shader.setProjectionMatrix(GameManager.player.getCamera().getProjectionMatrix());
        shader.setEyePosition(GameManager.player.getCamera().eye);
    }

    public static void setMinimapCamera()
    {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl.glViewport
        (
            (int) (viewport.width * 0.75f),
            (int) (viewport.height * 0.61f),
            (int) (viewport.width / 4.5f),
            (int) (viewport.height / 2.5f)
        );

        GameManager.minimapCamera.look(new Point3D(GameManager.player.getCamera().eye.x, 30.0f, GameManager.player.getCamera().eye.z), GameManager.player.getPosition(), new Vector3D(0, 0, 1));
        shader.setViewMatrix(GameManager.minimapCamera.getViewMatrix());
        shader.setProjectionMatrix(GameManager.minimapCamera.getProjectionMatrix());
    }

    private static void setFullscreen()
    {
        Graphics.DisplayMode disp = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setFullscreenMode(disp);
    }

    private static void enableBlending()
    {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private static void initFonts()
    {
//        batch = new SpriteBatch();
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/October Crow.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//        parameter.size = 18;
//        fontNormal = generator.generateFont(parameter);
//
//        parameter.size = 32;
//        fontLarge = generator.generateFont(parameter);
//
//        parameter.size = 46;
//        fontExtraLarge = generator.generateFont(parameter);
//
//        generator.dispose();
    }
}
