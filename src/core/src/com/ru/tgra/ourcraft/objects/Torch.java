package com.ru.tgra.ourcraft.objects;

import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.GraphicsEnvironment;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.TextureManager;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;

public class Torch extends Block
{
    public Torch(int ID, Point3D position, Vector3D scale, Material material, Material minimapMaterial, int chunkX, int chunkY)
    {
        super(ID, position, scale, material, minimapMaterial, new CubeMask(), BlockType.TORCH, chunkX, chunkY);

        this.ID = ID;
        this.position = position;
        this.scale = scale;
        this.material = material;
        this.minimapMaterial = minimapMaterial;
        this.chunkX = chunkX;
        this.chunkY = chunkY;

        blockType = Block.BlockType.TORCH;

        mask = new CubeMask();
        renderMask = new CubeMask();
    }

    public void draw(int viewportID)
    {
        if (!checkDraw())
        {
            return;
        }

        checkTargetedBlock();

        GameManager.drawCount++;

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position.x, position.y - 0.15f, position.z);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        if (viewportID == Settings.viewportIDMinimap)
        {
            GraphicsEnvironment.shader.setMaterial(minimapMaterial);
            //BoxGraphic.drawSolidCube(minimapMask);
        }
        else
        {
            GraphicsEnvironment.shader.setMaterial(material);

            BoxGraphic.drawSolidCube(
                    GraphicsEnvironment.shader,
                    TextureManager.getBlockTexture(blockType),
                    TextureManager.getBlockUVBuffer(blockType),
                    renderMask,
                    false
            );
        }
    }
}
