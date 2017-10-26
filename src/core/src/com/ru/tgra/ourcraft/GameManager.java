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

    public static MazeGenerator mazeGenerator;
    public static boolean[][] mazeWalls;
    public static boolean[][] mazeSpears;

    public static boolean mainMenu;

    public static Light headLight;
    public static Light endPointLight;
    public static Camera minimapCamera;
    public static Player player;
    public static boolean dead;
    public static boolean won;

    private static int currentLevel;
    private static float distanceToEnd;
    private static float maxDistanceToEnd;

    public static void init()
    {
        gameObjects = new ArrayList<GameObject>();
        mazeGenerator = new MazeGenerator();
        currentLevel = 0;
        distanceToEnd = 0f;
        maxDistanceToEnd = 0f;
        dead = false;
    }

    public static void createMaze()
    {
        won = false;
        currentLevel++;

        gameObjects.clear();

        int sideLength = Settings.startSideLength + (Settings.sideLengthIncrement * (currentLevel - 1));
        maxDistanceToEnd = (float) Math.sqrt(sideLength * sideLength * 2);

        generateMaze(sideLength);
        createFloor(new Point3D(sideLength / 2, -0.5f, sideLength / 2), sideLength);
        createWalls();
        createPlayer();
        createMinimap();
        createHeadLight();
    }

    public static boolean isDead()
    {
        return dead;
    }

    public static boolean hasWon()
    {
        return won;
    }

    public static void revive()
    {
        player.getPosition().set(GameManager.mazeGenerator.getStart());
        AudioManager.playHeartbeat();
        GraphicsEnvironment.shader.setBrightness(1.0f);
        dead = false;
    }

    public static void death()
    {
        AudioManager.stopHeartbeat();
        AudioManager.playDeath();
        dead = true;
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
        //player = new Player(new Point3D(mazeGenerator.getEnd()), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, Settings.playerMinimapMaterial);
        player = new Player(new Point3D(mazeGenerator.getStart()), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, Settings.playerMinimapMaterial);
        gameObjects.add(player);
    }

    private static void createWalls()
    {
        Vector3D scale = new Vector3D(1f, 2f, 1f);
        int sideLength = mazeWalls.length;

        for (int i = 0; i < sideLength; i++)
        {
            for (int j = 0; j < sideLength; j++)
            {
                // Skip corners
                if ((i == 0 && j == 0) || (i == 0 && j == sideLength-1) ||
                    (i == sideLength-1 && j == 0) || (i == sideLength-1 && j == sideLength-1))
                {
                    continue;
                }

                if (mazeWalls[i][j])
                {
                    Point3D position = new Point3D(i, 0.5f, j);

                    /* === Cube mask === */
                    CubeMask mask = new CubeMask();
                    mask.setBottom(false);
                    mask.setTop(false);

                    // North
                    if (i != sideLength-1)
                    {
                        mask.setNorth(!mazeWalls[i+1][j]);
                    }
                    else
                    {
                        mask.setNorth(false);
                    }

                    // South
                    if (i != 0)
                    {
                        mask.setSouth(!mazeWalls[i-1][j]);
                    }
                    else
                    {
                        mask.setSouth(false);
                    }

                    // East
                    if (j != sideLength-1)
                    {
                        mask.setEast(!mazeWalls[i][j+1]);
                    }
                    else
                    {
                        mask.setEast(false);
                    }

                    // West
                    if (j != 0)
                    {
                        mask.setWest(!mazeWalls[i][j-1]);
                    }
                    else
                    {
                        mask.setWest(false);
                    }

                    Block block = new Block(position, new Vector3D(scale), Settings.wallMaterial, Settings.wallMinimapMaterial, mask);

                    gameObjects.add(block);
                }
            }
        }
    }

    private static void createFloor(Point3D pos, float sideLength)
    {
        Block floor = new Block(pos, new Vector3D(sideLength, 0.01f, sideLength), Settings.floorMaterial, Settings.floorMinimapMaterial, new CubeMask(false, false, false, false, true, false));
        gameObjects.add(floor);
    }

    private static void generateMaze(int sideLength)
    {
        mazeGenerator.generateMaze(sideLength);
        mazeWalls = mazeGenerator.getWalls();
        mazeSpears = mazeGenerator.getSpears();
    }
}
