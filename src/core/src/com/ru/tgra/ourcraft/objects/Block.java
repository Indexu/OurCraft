package com.ru.tgra.ourcraft.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.GraphicsEnvironment;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.TextureManager;
import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.shapes.BoxGraphic;
import com.ru.tgra.ourcraft.utilities.CollisionsUtil;

public class Block extends GameObject
{
    public enum BlockType
    {
        EMPTY,
        BEDROCK,
        GRASS,
        STONE,
        DIRT
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

    private CubeMask mask;
    private CubeMask minimapMask;
    private CubeMask renderMask;
    private BlockType blockType;
    private Vector3D vectorFromPlayer;
    private float distanceFromPlayer;
    private int chunkX;
    private int chunkY;
    private TargetFace targetFace;

    private Point3D leftBottom;
    private Point3D rightTop;

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

    public void draw(int viewportID)
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

    public void update(float deltaTime)
    {
        // Do nothing
    }

    public void destroy()
    {
        GameManager.blocksToRemove.add(this);
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
            renderMask.setWest(false);
        }
    }

    private void checkTargetedBlock()
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
}
