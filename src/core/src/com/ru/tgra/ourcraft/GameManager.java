package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.*;
import com.ru.tgra.ourcraft.objects.*;
import com.ru.tgra.ourcraft.utilities.*;

import java.util.ArrayList;

public class GameManager
{
    public static ArrayList<GameObject> gameObjects;
    public static ArrayList<GameObject> allBlocks;

    public static boolean[][][] worldBlocks;
    public static Chunk[][] chunks;

    public static boolean mainMenu;

    public static int drawCount;

    public static Light headLight;
    public static Camera minimapCamera;
    public static Player player;
    public static boolean noclip;
    public static boolean won;

    private static WorldGenerator worldGenerator;

    public static void init()
    {
        gameObjects = new ArrayList<GameObject>();
        allBlocks = new ArrayList<GameObject>();
        worldGenerator = new WorldGenerator();
        noclip = false;
        won = false;
    }

    public static void createWorld()
    {
        gameObjects.clear();

        worldGenerator.generateWorld();
        createPlayer();
        createHeadLight();

        worldBlocks = worldGenerator.getWorldBlocks();
        chunks = worldGenerator.getChunks();

        System.out.println("GameObjects: " + gameObjects.size());
        System.out.println("All blocks: " + allBlocks.size());
    }

    public static void drawWorld(int viewportID)
    {
        int chunkX = (int) player.getPosition().x / Settings.chunkWidth;
        int chunkY = (int) player.getPosition().z / Settings.chunkHeight;

        int startX = chunkX - Settings.chunkDrawRadius;
        startX = (startX < 0 ? 0 : startX);

        int endX = chunkX + Settings.chunkDrawRadius;
        endX = (chunks.length-1 < endX ? chunks.length-1 : endX);

        int startY = chunkY - Settings.chunkDrawRadius;
        startY = (startY < 0 ? 0 : startY);

        int endY = chunkY + Settings.chunkDrawRadius;
        endY = (chunks[0].length-1 < endY ? chunks[0].length-1 : endY);

        System.out.format(" | chunkX: %d | chunkY: %d", chunkX, chunkY);
        System.out.format(" | startX: %d | endX: %d | startY: %d | endY: %d", startX, endX, startY, endY);

        int chunkDraw = 0;

        for (int x = startX; x <= endX; x++)
        {
            for (int y = startY; y <= endY; y++)
            {
                chunkDraw++;
                chunks[x][y].drawBlocks(viewportID);
            }
        }

        System.out.format(" | Chunks drawn: %d", chunkDraw);
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
        player = new Player(1, new Point3D(0, Settings.worldScale, 0), new Vector3D(0.25f, 0.25f, 0.25f), Settings.playerSpeed, Settings.playerMinimapMaterial);
        gameObjects.add(player);
    }
}
