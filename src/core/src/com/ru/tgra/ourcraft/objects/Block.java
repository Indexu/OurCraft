package com.ru.tgra.ourcraft.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.GraphicsEnvironment;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.TextureManager;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;

public class Block extends GameObject
{
    public static enum BlockType
    {
        BEDROCK,
        GRASS,
        STONE
    }

    private CubeMask mask;
    private CubeMask minimapMask;
    private BlockType blockType;

    public Block(Point3D position, Vector3D scale, Material material, Material minimapMaterial, CubeMask mask, BlockType type)
    {
        super();

        this.position = position;
        this.scale = scale;
        this.material = material;
        this.minimapMaterial = minimapMaterial;
        this.mask = mask;
        blockType = type;

        minimapMask = new CubeMask(false, false, false, false, true, false);
    }

    public void draw(int viewportID)
    {
        if (mask.isInvisible())
        {
            return;
        }

        Vector3D vectorToPlayer = Vector3D.difference(position, GameManager.player.position);

        if (vectorToPlayer.dot(GameManager.player.getCamera().forward) < -0.2f)
        {
            return;
        }

        float distanceToPlayer = vectorToPlayer.length();

        if (30f < distanceToPlayer)
        {
            return;
        }

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
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
            BoxGraphic.drawSolidCube(GraphicsEnvironment.shader, TextureManager.getBlockTexture(blockType), mask);
        }
    }

    public void update(float deltaTime)
    {
        // Do nothing
    }

    public CubeMask getMask()
    {
        return mask;
    }

    public void setMask(CubeMask mask)
    {
        this.mask = mask;
    }
}
