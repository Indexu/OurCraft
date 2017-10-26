package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.Color;
import com.ru.tgra.ourcraft.models.Material;
import com.ru.tgra.ourcraft.models.Vector3D;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Color;

public class Settings
{
    /* === Viewport IDs === */
    public static final int viewportIDPerspective = 0;
    public static final int viewportIDMinimap = 1;

    /* === Aspect ratio === */
    public static final int virtualWidth = 1920;
    public static final int virtualHeight = 1080;
    public static final float aspectRatio = (float)virtualWidth / (float)virtualHeight;

    /* === Maze settings === */
    // Side length must be an odd number and increment must be an even number
    public static final int startSideLength = 15;
    public static final int sideLengthIncrement = 4;
    public static final float percentageOfMazeSpears = 0.15f;

    /* === Player settings === */
    public static final float playerFOV = 60f;
    public static final float playerSpeed = 2f;
    public static final float playerButtonLookSensitivity = 100f;
    public static final float playerMouseLookSensitivity = 30f;
    public static final float fadeTime = 3f;

    /* === End point settings === */
    public static final float bobbingSpeed = 4f;
    public static final float bobbingFactor = 0.4f;

    /* === Spear settings === */
    public static final float spearPauseTime = 1.5f;
    public static final float spearRetractSpeed = 0.5f;
    public static final float spearFallSpeed = 10f;
    public static final float spearGroundY = 0.5f;
    public static final float spearUpY = 2f;
    public static final float spearVolumeRadius = 8f;

    /* === Watchtower settings === */
    public static final Vector3D watchtowerScale = new Vector3D(1f, 4f, 1f);
    public static final Vector3D watchtowerOrbScale = new Vector3D(0.5f, 0.5f, 0.5f);

    /* === Materials === */
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
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Diffuse
            new Color(0.4f, 0.4f, 0.4f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
            32f,
            1f
    );

    // Floor
    public static final Material floorMaterial = new Material
    (
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Ambience
            new Color(0.5f, 0.5f, 0.5f, 1.0f), // Diffuse
            new Color(1.0f, 1.0f, 1.0f, 1.0f), // Specular
            new Color(0.0f, 0.0f, 0.0f, 0.0f), // Emission
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
    public static final int numberOfLights = 6; // !!!MUST MATCH IN THE SHADER!!!
    public static final Color globalAmbience = new Color(0.5f, 0.0f, 0.0f, 1.0f);

    // Helmet light
    public static final Color helmetLightColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final float helmetLightSpotFactor = 10.0f;
    public static final float helmetConstantAttenuation = 0.5f;
    public static final float helmetLightLinearAttenuation = 0.3f;
    public static final float helmetLightQuadraticAttenuation = 0.3f;

    // End point light
    public static final Color endPointLightColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Vector3D endPointLightDirection = new Vector3D(0, -1, 0);
    public static final float endPointLightSpotFactor = 5.0f;
    public static final float endPointConstantAttenuation = 0.0f;
    public static final float endPointLightLinearAttenuation = 0f;
    public static final float endPointLightQuadraticAttenuation = 2f;

    // Watchtower light
    public static final Color watchtowerLightColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static final float watchtowerLightSpotFactor = 50.0f;
    public static final float watchtowerLightConstantAttenuation = 0.0f;
    public static final float watchtowerLightLinearAttenuation = 0.0f;
    public static final float watchtowerLightQuadraticAttenuation = 0.04f;
}
