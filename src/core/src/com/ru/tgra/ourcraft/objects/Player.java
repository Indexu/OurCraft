package com.ru.tgra.ourcraft.objects;

import com.ru.tgra.ourcraft.Camera;
import com.ru.tgra.ourcraft.GameManager;
import com.ru.tgra.ourcraft.GraphicsEnvironment;
import com.ru.tgra.ourcraft.Settings;
import com.ru.tgra.ourcraft.models.Material;
import com.ru.tgra.ourcraft.models.ModelMatrix;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Vector3D;
import com.ru.tgra.ourcraft.shapes.SphereGraphic;
import com.ru.tgra.ourcraft.utilities.BlockUtils;

import java.util.ArrayList;

public class Player extends GameObject
{
    private Camera camera;

    private Block targetBlock;

    private float radius;

    private Vector3D movementVector;
    private float yaw;
    private float pitch;
    private float accumulatedGravity;
    private boolean sprinting;
    private boolean holdingTorch;
    private Block.BlockType selectedBlock;
    private int selectedBlockNum;

    public Player(int ID, Point3D position, Vector3D scale, float speed)
    {
        super();

        this.ID = ID;
        this.position = position;
        this.scale = scale;
        this.speed = speed;

        radius = 0.25f;

        Point3D center = new Point3D(position);
        center.z += 1;

        camera = new Camera();
        camera.look(position, center, new Vector3D(0,1,0));
        camera.setPerspectiveProjection(Settings.playerFOV, GraphicsEnvironment.viewport.width / GraphicsEnvironment.viewport.height, Settings.nearPlane, Settings.farPlane);

        movementVector = new Vector3D();
        yaw = 0;
        pitch = 0f;
        accumulatedGravity = 0f;
        sprinting = false;

        targetBlock = null;
        holdingTorch = false;
        selectedBlock = Block.BlockType.DIRT;
    }

    public void update(float deltaTime)
    {
        movementVector.scale(deltaTime);
        yaw *= deltaTime;
        pitch *= deltaTime;

        camera.yaw(yaw);
        camera.pitch(pitch);

        if (sprinting)
        {
            movementVector.scale(Settings.playerSprintMultiplier);
        }

        if (GameManager.noclip)
        {
            camera.allSlide(movementVector.x, movementVector.y, movementVector.z);
        }
        else
        {
            accumulatedGravity += Settings.gravity;

            position.y -= accumulatedGravity * deltaTime;

            camera.slide(movementVector.x, movementVector.z);
        }

        movementVector.set(0, 0, 0);
        yaw = 0;
        pitch = 0;
        targetBlock = null;
        sprinting = false;

    }

    public void draw()
    {
        if (targetBlock != null)
        {
            targetBlock.drawOutline();
        }
    }

    public void moveLeft()
    {
        movementVector.x = -Settings.playerSpeed;
    }

    public void moveRight()
    {
        movementVector.x = Settings.playerSpeed;
    }

    public void moveForward()
    {
        movementVector.z = -Settings.playerSpeed;
    }

    public void moveBack()
    {
        movementVector.z = Settings.playerSpeed;
    }

    public void jump()
    {
        if (accumulatedGravity == 0f)
        {
            accumulatedGravity = -Settings.jumpStrength;
        }
    }

    public void sprint()
    {
        sprinting = true;
    }

    public void lookLeft()
    {
        yaw = Settings.playerButtonLookSensitivity;
    }

    public void lookRight()
    {
        yaw = -Settings.playerButtonLookSensitivity;
    }

    public void lookUp()
    {
        pitch = Settings.playerButtonLookSensitivity;
    }

    public void lookDown()
    {
        pitch = -Settings.playerButtonLookSensitivity;
    }

    public void mouseLookYaw(float amount)
    {
        yaw = amount * Settings.playerMouseLookSensitivity;
    }

    public void mouseLookPitch(float amount)
    {
        pitch = amount * Settings.playerMouseLookSensitivity;
    }

    public Camera getCamera()
    {
        return camera;
    }

    public int getWorldX()
    {
        return (int) (position.x + 0.5f);
    }

    public int getWorldY()
    {
        return (int) (position.y + 0.5f);
    }

    public int getWorldZ()
    {
        return (int) (position.z + 0.5f);
    }

    public float getRadius()
    {
        return radius;
    }

    public void resetGravity()
    {
        accumulatedGravity = 0f;
    }

    public Block getTargetBlock()
    {
        return targetBlock;
    }

    public void setTargetBlock(Block targetBlock)
    {
        this.targetBlock = targetBlock;
    }

    public void placeBlock()
    {
        if (targetBlock == null)
        {
            return;
        }

        Point3D pos = BlockUtils.getTargetArea(targetBlock);

        GameManager.addBlock(pos, selectedBlock);
    }

    public void toggleTorch()
    {
        holdingTorch = !holdingTorch;
    }

    public boolean isHoldingTorch()
    {
        return holdingTorch;
    }

    public void selectBlock(int num)
    {
        switch (num)
        {
            case 1:
                selectedBlock = Block.BlockType.DIRT;
                break;

            case 2:
                selectedBlock = Block.BlockType.STONE;
                break;

            case 3:
                selectedBlock = Block.BlockType.COBBLESTONE;
                break;

            case 4:
                selectedBlock = Block.BlockType.STONE_BRICK;
                break;

            case 5:
                selectedBlock = Block.BlockType.BRICK;
                break;

            case 6:
                selectedBlock = Block.BlockType.OAK_LOG;
                break;

            case 7:
                selectedBlock = Block.BlockType.OAK_PLANK;
                break;

            case 8:
                selectedBlock = Block.BlockType.GRAVEL;
                break;

            case 9:
                selectedBlock = Block.BlockType.SAND;
                break;

            case 0:
                selectedBlock = Block.BlockType.DIAMOND;
                break;

            default:
                return;
        }

        selectedBlockNum = num;
    }

    public Block.BlockType getSelectedBlock()
    {
        return selectedBlock;
    }

    public void scrollUpSelectedBlock()
    {
        selectedBlockNum++;
        selectedBlockNum = selectedBlockNum % 10;

        selectBlock(selectedBlockNum);
    }

    public void scrollDownSelectedBlock()
    {
        selectedBlockNum--;
        selectedBlockNum = (selectedBlockNum < 0 ? 9 : selectedBlockNum);

        selectBlock(selectedBlockNum);
    }
}
