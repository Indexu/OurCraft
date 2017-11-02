package com.ru.tgra.ourcraft.objects;

import com.ru.tgra.ourcraft.*;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;

public class Torch extends GameObject
{
    private Light torchLight;
    private CubeMask mask;

    public Torch()
    {
        super();

        this.ID = ID;
        this.position = new Point3D();
        this.scale = Settings.torchSize;

        mask = new CubeMask();

        torchLight = LightManager.createTorchLight(new Point3D(position.x, position.y + 1, position.z));
    }

    public void draw()
    {
        GameManager.drawCount++;

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
        GraphicsEnvironment.shader.setLight(torchLight);

        BoxGraphic.drawSolidCube(
            GraphicsEnvironment.shader,
            TextureManager.getTorchTexture(),
            TextureManager.getTorchUVBuffer(),
            mask,
            false
        );
    }

    @Override
    public void update(float deltaTime)
    {
        float x = GameManager.player.getCamera().forward.x + GameManager.player.getPosition().x;
        float y = GameManager.player.getPosition().y;
        float z = GameManager.player.getCamera().forward.z + GameManager.player.getPosition().z;

        position.set(x, y, z);
    }
}
