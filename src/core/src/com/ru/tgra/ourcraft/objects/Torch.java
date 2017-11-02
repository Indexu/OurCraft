package com.ru.tgra.ourcraft.objects;

import com.ru.tgra.ourcraft.*;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;
import com.ru.tgra.ourcraft.shapes.BoxHUDGraphic;

public class Torch extends GameObject
{
    private Light light;
    private CubeMask mask;

    public Torch()
    {
        super();

        this.position = new Point3D();
        this.scale = Settings.torchSize;

        mask = new CubeMask(false, true, true, false, false, false);

        light = LightManager.createTorchLight(GameManager.player.position);
    }

    public void draw()
    {
        GraphicsEnvironment.shaderHUD.useShader();

        GraphicsEnvironment.setUICamera();

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(0.6f, -0.25f, -0.5f);
        ModelMatrix.main.addScale(scale);
        ModelMatrix.main.addRotationX(-3);
        ModelMatrix.main.addRotationY(5);
        ModelMatrix.main.addRotationZ(-3);

        GraphicsEnvironment.shaderHUD.setModelMatrix(ModelMatrix.main.getMatrix());

        BoxHUDGraphic.drawSolidCube(
            GraphicsEnvironment.shaderHUD,
            TextureManager.getTorchTexture(),
            TextureManager.getTorchUVBuffer(),
            mask
        );
    }

    public void update(float deltaTime)
    {
//        float x = GameManager.player.getCamera().forward.x + GameManager.player.getPosition().x;
//        float y = GameManager.player.getPosition().y;
//        float z = GameManager.player.getCamera().forward.z + GameManager.player.getPosition().z;
//
//        position.set(x, y, z);
        //light.setDirection(GameManager.player.getCamera().forward);
    }

    public Light getLight()
    {
        return light;
    }
}
