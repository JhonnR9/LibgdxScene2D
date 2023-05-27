package com.jhonn.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jhonn.game.Scene2D;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.useVsync(false);
		config.setTitle("LibgdxScene2D");
		config.setWindowedMode(640,360);
		new Lwjgl3Application(new Scene2D(), config);
	}
}
