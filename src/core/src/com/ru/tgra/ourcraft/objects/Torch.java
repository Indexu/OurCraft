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
        this.position = GameManager.player.getPosition();
        this.scale = Settings.torchSize;

        mask = new CubeMask();

        torchLight = LightManager.createTorchLight(new Point3D(position.x, position.y + 1, position.z));
    }

    public void draw(int viewportID)
    {
        GameManager.drawCount++;

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position.x, position.y - 0.15f, position.z);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
        GraphicsEnvironment.shader.setLight(torchLight);

        if (viewportID == Settings.viewportIDMinimap)
        {
            //BoxGraphic.drawSolidCube(minimapMask);
        }
        else
        {
            BoxGraphic.drawSolidCube(
                    GraphicsEnvironment.shader,
                    TextureManager.getTorchTexture(),
                    TextureManager.getTorchUVBuffer(),
                    mask,
                    false
            );
        }
    }

    @Override
    public void update(float deltaTime)
    {
        // Do nothing
    }
}
