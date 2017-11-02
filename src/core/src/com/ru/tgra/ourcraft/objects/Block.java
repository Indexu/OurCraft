package com.ru.tgra.ourcraft.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.GraphicsEnvironment;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.TextureManager;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;
import com.ru.tgra.ourcraft.utilities.BlockUtils;
import com.ru.tgra.ourcraft.utilities.CollisionsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Block extends GameObject
{
    public enum BlockType
    {
        DIRT,
        BEDROCK,
        GRASS,
        STONE,
        EMPTY,
        TORCH
    }

    public enum TargetFace
    {
        TOP,
        BOTTOM,
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    protected CubeMask mask;
    protected CubeMask minimapMask;
    protected CubeMask renderMask;

    protected BlockType blockType;
    protected Vector3D vectorFromPlayer;
    protected float distanceFromPlayer;
    protected int chunkX;
    protected int chunkY;
    protected TargetFace targetFace;

    protected Point3D leftBottom;
    protected Point3D rightTop;

    public Block(int ID, Point3D position, Vector3D scale, Material material, Material minimapMaterial, CubeMask mask, BlockType type, int chunkX, int chunkY)
    {
        super();

        this.ID = ID;
        this.position = position;
        this.scale = scale;
        this.material = material;
        this.minimapMaterial = minimapMaterial;
        this.mask = mask;
        blockType = type;
        this.chunkX = chunkX;
        this.chunkY = chunkY;

        renderMask = new CubeMask(mask);
        minimapMask = new CubeMask(false, false, false, false, true, false);

        vectorFromPlayer = new Vector3D();
        distanceFromPlayer = Float.MAX_VALUE;

        leftBottom = new Point3D(position);
        leftBottom.x -= (scale.x * 0.5f);
        leftBottom.y -= (scale.y * 0.5f);
        leftBottom.z -= (scale.z * 0.5f);

        rightTop = new Point3D(position);
        rightTop.x += (scale.x * 0.5f);
        rightTop.y += (scale.y * 0.5f);
        rightTop.z += (scale.z * 0.5f);

        targetFace = null;
    }

    public void draw()
    {
        if (!checkDraw())
        {
            return;
        }

        checkTargetedBlock();

        GameManager.drawCount++;

        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());

        BoxGraphic.drawSolidCube(
            GraphicsEnvironment.shader,
            TextureManager.getBlockTexture(blockType),
            TextureManager.getBlockUVBuffer(blockType),
            renderMask,
            false
        );
    }

    public void drawOutline()
    {
        ModelMatrix.main.loadIdentityMatrix();
        ModelMatrix.main.addTranslation(position);
        ModelMatrix.main.addScale(scale);

        GraphicsEnvironment.shader.setModelMatrix(ModelMatrix.main.getMatrix());
        GraphicsEnvironment.shader.setMaterial(Settings.targetedBlockMaterial);

        BoxGraphic.drawOutlineCube(GraphicsEnvironment.shader, renderMask);
    }

    public void update(float deltaTime)
    {
        // Do nothing
    }

    public void destroy()
    {
        if (blockType != BlockType.BEDROCK)
        {
            GameManager.blocksToRemove.add(this);
        }
    }

    public CubeMask getMask()
    {
        return mask;
    }

    public void setMask(CubeMask mask)
    {
        this.mask = mask;
    }

    protected boolean checkDraw()
    {
        // Skip if invisible
        if (mask.isInvisible())
        {
            return false;
        }

        vectorFromPlayer.setDifference(position, GameManager.player.position);
        distanceFromPlayer = vectorFromPlayer.length();

        // Skip if behind player
        if (Settings.proximityDistance < distanceFromPlayer &&
            vectorFromPlayer.dot(GameManager.player.getCamera().forward) < Settings.dotProductCutoff)
        {
            return false;
        }

        // Skip if outside draw distance
        if (Settings.drawDistance < distanceFromPlayer)
        {
            return false;
        }

        // Construct the render mask
        BlockUtils.constructRenderMask(mask, renderMask, position, GameManager.player.position);

        return !renderMask.isInvisible();
    }

    protected void checkTargetedBlock()
    {
        if (distanceFromPlayer <= Settings.reach)
        {
            boolean collides = CollisionsUtil.lineIntersectsWithBlock(GameManager.player.position, GameManager.player.getCamera().forward, this);
            Block block = GameManager.player.getTargetBlock();

            if (collides)
            {
                if (block == null)
                {
                    GameManager.player.setTargetBlock(this);
                }
                else if (distanceFromPlayer < block.distanceFromPlayer)
                {
                    GameManager.player.setTargetBlock(this);
                }
            }
        }
    }

    public Vector3D getVectorFromPlayer()
    {
        return vectorFromPlayer;
    }

    public float getDistanceFromPlayer()
    {
        return distanceFromPlayer;
    }

    public Point3D getLeftBottom()
    {
        return leftBottom;
    }

    public Point3D getRightTop()
    {
        return rightTop;
    }

    public int getChunkX()
    {
        return chunkX;
    }

    public int getChunkY()
    {
        return chunkY;
    }

    public TargetFace getTargetFace()
    {
        return targetFace;
    }

    public void setTargetFace(TargetFace targetFace)
    {
        this.targetFace = targetFace;
    }

    public BlockType getBlockType()
    {
        return blockType;
    }
}
