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
    private CubeMask renderMask;
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

        renderMask = new CubeMask(mask);
        minimapMask = new CubeMask(false, false, false, false, true, false);
    }

    public void draw(int viewportID)
    {
        if (!checkDraw())
        {
            return;
        }

        GameManager.drawCount++;

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
            BoxGraphic.drawSolidCube(GraphicsEnvironment.shader, TextureManager.getBlockTexture(blockType), renderMask);
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

    private boolean checkDraw()
    {
        // Skip if invisible
        if (mask.isInvisible())
        {
            return false;
        }

        Vector3D vectorToPlayer = Vector3D.difference(position, GameManager.player.position);
        float distanceToPlayer = vectorToPlayer.length();

        // Skip if behind player
        if (Settings.proximityDistance < distanceToPlayer &&
                vectorToPlayer.dot(GameManager.player.getCamera().forward) < Settings.dotProductCutoff)
        {
            return false;
        }

        // Skip if outside draw distance
        if (Settings.drawDistance < distanceToPlayer)
        {
            return false;
        }

        // Construct the render mask
        constructRenderMask();

        return !renderMask.isInvisible();
    }

    private void constructRenderMask()
    {
        renderMask.setMask(mask);

        if (mask.isBottom() && position.y < GameManager.player.position.y)
        {
            renderMask.setBottom(false);
        }

        if (mask.isTop() && GameManager.player.position.y < position.y)
        {
            renderMask.setTop(false);
        }

        if (mask.isNorth() && GameManager.player.position.x < position.x)
        {
            renderMask.setNorth(false);
        }

        if (mask.isSouth() && position.x < GameManager.player.position.x)
        {
            renderMask.setSouth(false);
        }

        if (mask.isEast() && GameManager.player.position.z < position.z)
        {
            renderMask.setEast(false);
        }

        if (mask.isWest() && position.z < GameManager.player.position.z)
        {
            renderMask.setEast(false);
        }
    }
}
