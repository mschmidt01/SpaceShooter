package com.neworder.spaceshooter.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.neworder.spaceshooter.SpaceShooter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = (int)(config.width * 1.778);

		TexturePacker.process(new TexturePacker.Settings(),"images","packed","packed");
		new LwjglApplication(new SpaceShooter(), config);
	}
}
