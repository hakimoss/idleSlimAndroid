package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.swing.SwingUtilities;



public class Main extends Game {

	public static Scene scene;




	@Override
	public void create() {
		scene = new Scene();
		setScreen(scene);
	}

	@Override
	public void dispose() {
		scene.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}


}
