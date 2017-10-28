package com.ru.tgra.ourcraft.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.ourcraft.OurCraftGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "OurCraft";
		config.width = 1152;
		config.height = 648;
		config.x = 100;
		config.y = 50;
//		config.vSyncEnabled = false; // Setting to false disables vertical sync
//		config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
//		config.backgroundFPS = 0; // Setting to 0 disables background fps throttling

		new LwjglApplication(new OurCraftGame(), config);
	}
}
