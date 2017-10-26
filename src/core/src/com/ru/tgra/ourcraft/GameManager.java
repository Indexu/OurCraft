package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.models.Light;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.models.Vector3D;
import com.ru.tgra.ourcraft.objects.*;
import com.ru.tgra.ourcraft.utilities.*;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<GameObject> allBlocks;

    public static boolean[][][] worldBlocks;

    public static boolean mainMenu;

    public static Light headLight;
    public static Camera minimapCamera;
    public static Player player;
    public static boolean dead;
    public static boolean won;

    private static OpenSimplexNoise noise;
    private static int[][] heightMap;

    public static void init()
    {
        gameObjects = new ArrayList<GameObject>();
        allBlocks = new ArrayList<GameObject>();
        noise = new OpenSimplexNoise();
        //noise = new OpenSimplexNoise(new Date().getTime());
        worldBlocks = new boolean[Settings.worldWidth][Settings.worldScale * 2][Settings.worldHeight];
        dead = false;
        won = false;
    }

    public static void createWorld()
    {
        gameObjects.clear();

        generateHeightMap();
        createWorldBlockArray();
        createBlocks();
        createPlayer();
        createHeadLight();
//        createWalls();
//        createMinimap();
//        createHeadLight();

        System.out.println(gameObjects.size());
    }

    private static void createHeadLight()
    {
        headLight = new Light();
        headLight.setID(4);
        headLight.setColor(Settings.helmetLightColor);
        headLight.setSpotFactor(Settings.helmetLightSpotFactor);
        headLight.setConstantAttenuation(Settings.helmetConstantAttenuation);
        headLight.setLinearAttenuation(Settings.helmetLightLinearAttenuation);
        headLight.setQuadraticAttenuation(Settings.helmetLightQuadraticAttenuation);
    }

    private static void createMinimap()
    {
        minimapCamera = new Camera();
        minimapCamera.setOrthographicProjection(-5, 5, -5, 5, 3f, 100);
    }

    private static void createPlayer()
    {
        player = new Player(new Point3D(0, Settings.worldScale, 0), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, Settings.playerMinimapMaterial);
        gameObjects.add(player);
    }

    private static void createBlocks()
    {
        Vector3D scale = new Vector3D(1f, 1f, 1f);

//        for (int x = 0; x < Settings.worldWidth; x++)
//        {
//            for (int y = 0; y < Settings.worldHeight; y++)
//            {
//                for (int i = -Settings.worldScale; i < heightMap[x][y]; i++)
//                {
//                    Point3D position = new Point3D(x, i, y);
//
//                    CubeMask mask = new CubeMask();
//
//                    Block block = new Block(position, new Vector3D(scale), Settings.wallMaterial, Settings.wallMinimapMaterial, mask, Block.BlockType.GRASS);
//
//                    gameObjects.add(block);
//                }
//            }
//        }

        int maxX = Settings.worldWidth;
        int maxY = Settings.worldScale * 2;
        int maxZ = Settings.worldHeight;

        for (int x = 0; x < maxX; x++)
        {
            for (int y = 0; y < maxY; y++)
            {
                for (int z = 0; z < maxZ; z++)
                {
                    if (worldBlocks[x][y][z])
                    {
                        CubeMask mask = new CubeMask();

                        // Up
                        if (y+1 != maxY && worldBlocks[x][y+1][z])
                        {
                            mask.setTop(false);
                        }

                        // Down
                        if (y == 0 || worldBlocks[x][y-1][z])
                        {
                            mask.setBottom(false);
                        }

                        // North
                        if (x+1 == maxX || worldBlocks[x+1][y][z])
                        {
                            mask.setNorth(false);
                        }

                        // South
                        if (x == 0 || worldBlocks[x-1][y][z])
                        {
                            mask.setSouth(false);
                        }

                        // East
                        if (z+1 == maxZ || worldBlocks[x][y][z+1])
                        {
                            mask.setEast(false);
                        }

                        // West
                        if (z == 0 || worldBlocks[x][y][z-1])
                        {
                            mask.setWest(false);
                        }

                        Point3D position = new Point3D(x, y, z);

                        Block block = new Block
                        (
                            position,
                            new Vector3D(scale),
                            Settings.grassMaterial,
                            Settings.wallMinimapMaterial,
                            mask,
                            Block.BlockType.GRASS
                        );

                        allBlocks.add(block);
                    }
                }
            }
        }

        for (GameObject gameObject : allBlocks)
        {
            Block block = (Block) gameObject;

            if (!block.getMask().isInvisible())
            {
                gameObjects.add(gameObject);
            }
        }
    }

//    private static void createFloor(Point3D pos, float sideLength)
//    {
//        Block floor = new Block(pos, new Vector3D(sideLength, 0.01f, sideLength), Settings.floorMaterial, Settings.floorMinimapMaterial, new CubeMask(false, false, false, false, true, false));
//        gameObjects.add(floor);
//    }

    private static void generateHeightMap()
    {
        heightMap = new int[Settings.worldHeight][Settings.worldWidth];

        for (int x = 0; x < Settings.worldWidth; x++)
        {
            for (int y = 0; y < Settings.worldHeight; y++)
            {
                double value = noise.eval(x / Settings.worldFeatureSize, y / Settings.worldFeatureSize, 0.0);
                value += 1.0f;
                value *= Settings.worldScale;

                heightMap[x][y] = (int) value;

                //System.out.format("Value: %f | HeightMap: %d\n", value, heightMap[y][x]);
            }
        }
    }

    private static void createWorldBlockArray()
    {
        for (int x = 0; x < Settings.worldWidth; x++)
        {
            for (int y = 0; y < Settings.worldHeight; y++)
            {
                for (int i = 0; i < heightMap[x][y]; i++)
                {
                    worldBlocks[x][i][y] = true;
                }
            }
        }
    }
}
