package com.ru.tgra.ourcraft;

import com.ru.tgra.ourcraft.models.Color;
import com.ru.tgra.ourcraft.models.Light;
import com.ru.tgra.ourcraft.models.Point3D;
import com.ru.tgra.ourcraft.utilities.MathUtils;

public class LightManager
{
    public static Light sun;
    public static Light moon;
    public static Color globalAmbiance;
    public static Color fogColor;
    public static Color torchLightColor;
    public static float sunAngle;

    private static float radius;

    public static void init()
    {
        globalAmbiance = new Color(Settings.globalAmbianceDay);
        fogColor = new Color(Settings.fogColorDay);
        torchLightColor = new Color(Settings.torchLightColorDay);
        sunAngle = (float) Math.PI/2;
        radius = Settings.worldX * 2f;

        createSun();
        createMoon();
    }

    public static void updateSunAngle(float deltaTime)
    {
        sunAngle += Settings.dayNightCycleSpeed * deltaTime;
        sunAngle = sunAngle % Settings.fullCircle;

        float s = (float)Math.sin((sunAngle));
        float c = (float)Math.cos((sunAngle));

        sun.getPosition().x = GameManager.worldCenter.x + (c * radius);
        sun.getPosition().y = GameManager.worldCenter.y + (s * radius);

        moon.getPosition().x = GameManager.worldCenter.x - (c * radius);
        moon.getPosition().y = GameManager.worldCenter.y - (s * radius);

        setSunMoonColors(s, c);
    }

    public static void createSun()
    {
        sun = new Light();
        sun.setID(0);
        sun.setColor(Settings.sunLightColor, true);
        sun.setSpotFactor(0);
        sun.setPosition(new Point3D(), false);
    }

    public static void createMoon()
    {
        moon = new Light();
        moon.setID(1);
        moon.setColor(Settings.moonLightColor, true);
        moon.setSpotFactor(0);
        moon.setPosition(new Point3D(), false);
    }

    public static Light createTorchLight(Point3D position)
    {
        Light torchLight = new Light();
        torchLight.setID(2);
        torchLight.setPosition(position, false);
        torchLight.setDirection(Settings.torchLightDirection);
        torchLight.setColor(torchLightColor, false);
        torchLight.setSpotFactor(Settings.torchLightSpotFactor);
        torchLight.setConstantAttenuation(Settings.torchLightConstantAttenuation);
        torchLight.setLinearAttenuation(Settings.torchLightLinearAttenuation);
        torchLight.setQuadraticAttenuation(Settings.torchLightQuadraticAttenuation);
        torchLight.setSpotlight(false);

        return torchLight;
    }

    private static void setSunMoonColors(float s, float c)
    {
        Color sunColor = sun.getColor();
        Color moonColor = moon.getColor();

        // To Midday
        if (0 < c)
        {
            float t = (s + 1) * 0.5f;

            sunColor.r = MathUtils.lerp(Settings.moonLightColor.r, Settings.sunLightColor.r, t);
            sunColor.g = MathUtils.lerp(Settings.moonLightColor.g, Settings.sunLightColor.g, t);
            sunColor.b = MathUtils.lerp(Settings.moonLightColor.b, Settings.sunLightColor.b, t);

            moonColor.r = MathUtils.lerp(Settings.moonLightColor.r, Settings.sunLightColor.r, t);
            moonColor.g = MathUtils.lerp(Settings.moonLightColor.g, Settings.sunLightColor.g, t);
            moonColor.b = MathUtils.lerp(Settings.moonLightColor.b, Settings.sunLightColor.b, t);

            globalAmbiance.r = MathUtils.lerp(Settings.globalAmbianceNight.r, Settings.globalAmbianceDay.r, t);
            globalAmbiance.g = MathUtils.lerp(Settings.globalAmbianceNight.g, Settings.globalAmbianceDay.g, t);
            globalAmbiance.b = MathUtils.lerp(Settings.globalAmbianceNight.b, Settings.globalAmbianceDay.b, t);

            fogColor.r = MathUtils.lerp(Settings.fogColorNight.r, Settings.fogColorDay.r, t);
            fogColor.g = MathUtils.lerp(Settings.fogColorNight.g, Settings.fogColorDay.g, t);
            fogColor.b = MathUtils.lerp(Settings.fogColorNight.b, Settings.fogColorDay.b, t);

            torchLightColor.r = MathUtils.lerp(Settings.torchLightColorNight.r, Settings.torchLightColorDay.r, t);
            torchLightColor.g = MathUtils.lerp(Settings.torchLightColorNight.g, Settings.torchLightColorDay.g, t);
            torchLightColor.b = MathUtils.lerp(Settings.torchLightColorNight.b, Settings.torchLightColorDay.b, t);

            sunColor.scale(t);
            moonColor.scale(1-t);

            sunColor.a = t;
            moonColor.a = 1-t;
        }
        // To midnight
        else
        {
            float t = (1 - s) * 0.5f;

            moonColor.r = MathUtils.lerp(Settings.sunLightColor.r, Settings.moonLightColor.r, t);
            moonColor.g = MathUtils.lerp(Settings.sunLightColor.g, Settings.moonLightColor.g, t);
            moonColor.b = MathUtils.lerp(Settings.sunLightColor.b, Settings.moonLightColor.b, t);

            sunColor.r = MathUtils.lerp(Settings.sunLightColor.r, Settings.moonLightColor.r, t);
            sunColor.g = MathUtils.lerp(Settings.sunLightColor.g, Settings.moonLightColor.g, t);
            sunColor.b = MathUtils.lerp(Settings.sunLightColor.b, Settings.moonLightColor.b, t);

            globalAmbiance.r = MathUtils.lerp(Settings.globalAmbianceDay.r, Settings.globalAmbianceNight.r, t);
            globalAmbiance.g = MathUtils.lerp(Settings.globalAmbianceDay.g, Settings.globalAmbianceNight.g, t);
            globalAmbiance.b = MathUtils.lerp(Settings.globalAmbianceDay.b, Settings.globalAmbianceNight.b, t);

            fogColor.r = MathUtils.lerp(Settings.fogColorDay.r, Settings.fogColorNight.r, t);
            fogColor.g = MathUtils.lerp(Settings.fogColorDay.g, Settings.fogColorNight.g, t);
            fogColor.b = MathUtils.lerp(Settings.fogColorDay.b, Settings.fogColorNight.b, t);

            torchLightColor.r = MathUtils.lerp(Settings.torchLightColorDay.r, Settings.torchLightColorNight.r, t);
            torchLightColor.g = MathUtils.lerp(Settings.torchLightColorDay.g, Settings.torchLightColorNight.g, t);
            torchLightColor.b = MathUtils.lerp(Settings.torchLightColorDay.b, Settings.torchLightColorNight.b, t);

            moonColor.scale(t);
            sunColor.scale(1-t);

            moonColor.a = t;
            sunColor.a = 1-t;
        }
    }
}
