package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.Color;
import com.ru.tgra.ourcraft.models.CubeMask;
import com.ru.tgra.ourcraft.models.Material;
import com.ru.tgra.ourcraft.models.Vector3D;

public class Settings
{
    /* === Aspect ratio === */
    public static final int virtualWidth = 1920;
    public static final int virtualHeight = 1080;
    public static final float aspectRatio = (float)virtualWidth / (float)virtualHeight;

    /* === World settings === */
    public static final int worldX = 512;
    public static final int worldZ = 512;
    public static final int worldY = 128;
    public static final int worldFeatureSize = 8;
    public static final int worldSmoothness = 16;
    public static final int cavernMinHeight = 3;
    public static final int cavernMaxHeight = 6;
    public static final int chunkWidth = 16;
    public static final int chunkHeight = 16;
    public static final Vector3D blockSize = new Vector3D(1f, 1f, 1f);
    public static final Vector3D torchSize = new Vector3D(0.1f, 0.5f, 0.1f);
    public static final float dayNightCycleSpeed = 0.01f;
    public static final float fullCircle = 2 * (float) Math.PI;

    /* === Render settings === */
    public static final float nearPlane = 0.1f;
    public static final float farPlane = 40f;
    public static final int chunkDrawRadius = 2;
    public static final float dotProductCutoff = -0.1f;
    public static final float drawDistance = 40f;
    public static final float proximityDistance = 2f;
    public static final float fogStart = 10f;
    public static final float fogEnd = 30f;
    public static final Color fogColorDay = new Color(0.7f, 0.7f, 0.7f, 1.0f);
    public static final Color fogColorNight = new Color(0.1f, 0.1f, 0.1f, 1.0f);
    public static final CubeMask fullMask = new CubeMask();

    /* === Player settings === */
    public static final float playerFOV = 80f;
    public static final float playerSpeed = 3f;
    public static final float playerSprintMultiplier = 32f;
    public static final float playerButtonLookSensitivity = 100f;
    public static final float playerMouseLookSensitivity = 30f;
    public static final float gravity = 0.2f;
    public static final float jumpStrength = 5f;
    public static final float reach = 3f;
    public static final int playerHeight = 2;

    /* === Skybox settings === */
    public static final Vector3D skyboxScale = new Vector3D(10.0f, 10.0f, 10.0f);
    public static final Material skyboxMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            0.1f,
            1f
    );

    /* === UI settings === */
    public static final Color crosshairColor = new Color(1.0f, 1.0f, 1.0f, 0.8f);
    public static final Color textColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);

    /* === Materials === */

    // Grass
    public static final Material grassMaterial = new Material
    (
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Ambience
            new Color(0.408f, 0.624f, 0.22f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    public static final Material targetedBlockMaterial = new Material
    (
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Ambience
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Emission
            128f,
            1f
    );

    public static final Material wallMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 1.0f), // Emission
            128f,
            1f
    );

    // Player
    public static final Material playerMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 1.0f, 0.0f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(1.0f, 1.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    /* === Lights === */
    public static final int numberOfLights = 3; // !!!MUST MATCH IN THE SHADER!!!

    public static final Color globalAmbianceDay = new Color(0.35f, 0.35f, 0.35f, 1.0f);
    public static final Color globalAmbianceNight = new Color(0.05f, 0.05f, 0.05f, 1.0f);

    public static final Color globalAmbianceHUD = new Color(0.6f, 0.6f, 0.6f, 1.0f);

    // Sun light
    public static final Color sunLightColor = new Color(0.45f, 0.45f, 0.35f, 1.0f);

    // Moon light
    public static final Color moonLightColor = new Color(0.025f, 0.10f, 0.2f, 1.0f);

    // Torch light
    public static final Color torchLightColorDay = new Color(0.01f, 0.01f, 0.007f, 1.0f);
    public static final Color torchLightColorNight = new Color(1f, 1f, 0.7f, 1.0f);
    public static final Vector3D torchLightDirection = new Vector3D(0f, 1f, 0f);
    public static final float torchLightSpotFactor = 0.01f;
    public static final float torchLightConstantAttenuation = 1f;
    public static final float torchLightLinearAttenuation = 0.5f;
    public static final float torchLightQuadraticAttenuation = 0.2f;
}
