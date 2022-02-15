package com.forgottenartsstudios.complicatedsimple;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.useVsync(false);
		config.setForegroundFPS(0);
		config.setTitle("Complicated Simple");
		config.setWindowedMode(480, 854);
		new Lwjgl3Application(new SimpleGame(), config);
	}
}
