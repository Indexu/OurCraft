package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.Color;
import com.ru.tgra.ourcraft.models.Material;
import com.ru.tgra.ourcraft.models.Vector3D;

public class Settings
{
    /* === Viewport IDs === */
    public static final int viewportIDPerspective = 0;
    public static final int viewportIDMinimap = 1;

    /* === Aspect ratio === */
    public static final int virtualWidth = 1920;
    public static final int virtualHeight = 1080;
    public static final float aspectRatio = (float)virtualWidth / (float)virtualHeight;

    /* === World settings === */
    public static final int worldWidth = 128;
    public static final int worldHeight = 128;
    public static final int worldFeatureSize = 4;
    public static final int worldScale = 40;
    public static final int worldSmoothness = 4;
    public static final int chunkWidth = 16;
    public static final int chunkHeight = 16;
    public static final int chunkDrawRadius = 2;
    public static final float torchLightMaxDistance = 6f;
    public static final Vector3D blockSize = new Vector3D(1f, 1f, 1f);
    public static final Vector3D torchSize = new Vector3D(0.15f, 0.66f, 0.15f);
    public static final float dotProductCutoff = -0.1f;
    public static final float drawDistance = 30f;
    public static final float proximityDistance = 2f;
    public static final float dayNightCycleSpeed = 0.5f;
    public static final float fullCircle = 2 * (float) Math.PI;

    /* === Player settings === */
    public static final float playerFOV = 80f;
    public static final float playerSpeed = 3f;
    public static final float playerSprintMultiplier = 4f;
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

    // Selected
    public static final Material selectedMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.8f, 0.0f, 0.0f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    // Walls
    public static final Material wallMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
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

    // Spears
    public static final Material spearMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.4f, 0.4f, 0.8f, 0.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            32f,
            1f
    );

    // Floor
    public static final Material floorMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.8f, 0.4f, 0.4f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.3f, 0.0f, 0.0f, 1.0f), // Emission
            Float.MAX_VALUE,
            1f
    );

    public static final Material floorMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.5f, 0.5f, 0.5f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            Float.MAX_VALUE,
            1f
    );

    // End point
    public static final Material endPointMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 0.0f, 0.0f, 1.0f), // Diffuse
            new Color(0.5f, 0.5f, 0.5f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            32f,
            1f
    );
    public static final Material endPointMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 0.0f, 0.0f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(1.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    // Watchtower material
    public static final Material watchtowerMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.4f, 0.4f, 0.4f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    public static final Material watchtowerMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.4f, 0.4f, 0.4f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    // Watchtower orb material
    public static final Material watchtowerOrbMaterial = new Material
    (
            new Color(0.6f, 0.0f, 0.0f, 1.0f), // Ambience
            new Color(1.0f, 0.0f, 0.0f, 1.0f), // Diffuse
            new Color(1.0f, 0.0f, 0.0f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            128f,
            1f
    );

    public static final Material watchtowerOrbMinimapMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Diffuse
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
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
    public static final Color globalAmbience = new Color(0.2f, 0.2f, 0.2f, 1.0f);

    // Helmet light
    public static final Color helmetLightColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final float helmetLightSpotFactor = 0.0f;
    public static final float helmetConstantAttenuation = 0.5f;
    public static final float helmetLightLinearAttenuation = 0.3f;
    public static final float helmetLightQuadraticAttenuation = 0.3f;

    // Sun light
    public static final Color sunLightColor = new Color(0.3f, 0.25f, 0.1f, 1.0f);

    // Moon light
    public static final Color moonLightColor = new Color(0.1f, 0.2f, 0.3f, 1.0f);

    // Torch light
    public static final Color torchLightColor = new Color(0.3f, 0.15f, 0.0f, 1.0f);
    public static final Vector3D torchLightDirection = new Vector3D(0f, 1f, 0f);
    public static final float torchLightSpotFactor = 0.01f;
    public static final float torchLightConstantAttenuation = 0.8f;
    public static final float torchLightLinearAttenuation = 0.02f;
    public static final float torchLightQuadraticAttenuation = 0.04f;
}
