package com.throwgame.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.throwgame.main.Main;
import controller.Controller;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.fullscreen = false;
                config.height = 900;
                config.width = 1600;
                config.title = "Orbital Throw";
		new LwjglApplication(new Controller(), config);
	}
}
