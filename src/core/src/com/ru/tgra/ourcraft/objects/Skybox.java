package com.ru.tgra.ourcraft.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.GraphicsEnvironment;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.TextureManager;
import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.models.ModelMatrix;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;
import com.ru.tgra.ourcraft.shapes.SphereGraphic;

public class Skybox extends GameObject
{
    private CubeMask mask;

    public Skybox()
    {
        position = GameManager.player.getPosition();
        scale = Settings.skyboxScale;
        mask = new CubeMask();
    }

    public void draw(int viewportID)
    {
        if (viewportID == Settings.viewportIDMinimap)
        {
            return;
        }

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        GraphicsEnvironment.shader.setMaterial(Settings.skyboxMaterial);

        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

        SphereGraphic.drawSolidSphere(
            GraphicsEnvironment.shader,
            TextureManager.getSkyboxTexture()
        );

//        BoxGraphic.drawSolidCube(
//            GraphicsEnvironment.shader,
//            TextureManager.getSkyboxTexture(),
//            TextureManager.getCubeMapUVBuffer(),
//            mask,
//            true
//        );

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    }

    public void update(float deltaTime)
    {
        //position = GameManager.player.getPosition();
    }
}
