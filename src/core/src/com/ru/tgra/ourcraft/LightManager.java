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
    public static float sunAngle;

    private static float radius;

    public static void init()
    {
        globalAmbiance = Settings.globalAmbience;
        sunAngle = 0f;
        radius = Settings.worldWidth * 2f;

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

//        sun.setOn(Settings.worldScale < sun.getPosition().y);
//        moon.setOn(Settings.worldScale < moon.getPosition().y);

        setSunMoonColors(s, c);

        System.out.print(" | Sun pos: " + sun.getPosition());
        System.out.print(" | Moon pos: " + moon.getPosition());
        System.out.print(" | Sun on: " + sun.isOn());
        System.out.print(" | Mon on: " + moon.isOn());
    }

    public static void createSun()
    {
        sun = new Light();
        sun.setID(0);
        sun.setColor(Settings.sunLightColor);
        sun.setSpotFactor(0);
        sun.setPosition(new Point3D(), false);
    }

    public static void createMoon()
    {
        moon = new Light();
        moon.setID(1);
        moon.setColor(Settings.moonLightColor);
        moon.setSpotFactor(0);
        moon.setPosition(new Point3D(), false);
    }

    public static Light createTorchLight(Point3D position)
    {
        Light torchLight = new Light();
        torchLight.setID(2);
        torchLight.setPosition(position, false);
        torchLight.setDirection(Settings.torchLightDirection);
        torchLight.setColor(Settings.torchLightColor);
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

            sunColor.scale(t);
            moonColor.scale(1-t);
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

            moonColor.scale(t);
            sunColor.scale(1-t);
        }

        sunColor.r = 0;
        sunColor.g = 0;
        sunColor.b = 0;
        sunColor.a = 0;

        moonColor.r = 0;
        moonColor.g = 0;
        moonColor.b = 0;
        moonColor.a = 0;
    }
}
