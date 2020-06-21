package com.throwgame.main;

import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.throwgame.main.Main;

import java.io.File;

import controller.Controller;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Controller controller = new Controller();
		File filesDir = getContext().getFilesDir();
		controller.initContext(filesDir);
		initialize(controller, config);
	}
}
