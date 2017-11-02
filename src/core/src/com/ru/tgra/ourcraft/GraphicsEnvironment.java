package com.ru.tgra.ourcraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.ru.tgra.ourcraft.models.Color;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Rectangle;
import com.ru.tgra.ourcraft.models.Vector3D;

public class GraphicsEnvironment
{
    public static Shader shader;
    public static Shader2D shader2D;
    public static ShaderHUD shaderHUD;
    public static Rectangle viewport;
    public static Camera UICamera;

    public static void init()
    {
        shader = new Shader();
        shader.setBrightness(1.0f);

        shader2D = new Shader2D();
        shaderHUD = new ShaderHUD();

        shaderHUD.setGlobalAmbience(Settings.globalAmbianceHUD);

        UICamera = new Camera();
        UICamera.look(new Point3D(0, 0, 0), new Point3D(0, 0, -1), new Vector3D(0,1,0));

        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        enableBlending();
        //setFullscreen();

        shader.useShader();

        shader.setFogStart(Settings.fogStart);
        shader.setFogEnd(Settings.fogEnd);

        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
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

        shader.setViewMatrix(GameManager.player.getCamera().getViewMatrix());
        shader.setProjectionMatrix(GameManager.player.getCamera().getProjectionMatrix());
        shader.setEyePosition(GameManager.player.getCamera().eye);
    }

    public static void setUICamera()
    {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl.glViewport
        (
            (int) viewport.x,
            (int) viewport.y,
            (int) viewport.width,
            (int) viewport.height
        );

        shaderHUD.setViewMatrix(UICamera.getViewMatrix());
        shaderHUD.setProjectionMatrix(UICamera.getProjectionMatrix());
        shaderHUD.setGlobalAmbience(Settings.globalAmbianceHUD);
    }

    private static void setFullscreen()
    {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    private static void enableBlending()
    {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }
}
