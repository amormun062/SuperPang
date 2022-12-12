package com.politecnicomalaga.pang;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.politecnicomalaga.pang.GdxPang;
import com.politecnicomalaga.pang.managers.SettingsManager;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Pang Angel Moreno Munoz");
		config.setWindowedMode(SettingsManager.SCREEN_WIDTH,SettingsManager.SCREEN_HEIGHT);
		new Lwjgl3Application(new GdxPang(), config);
	}
}
