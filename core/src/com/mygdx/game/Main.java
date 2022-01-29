package com.mygdx.game;

import com.badlogic.gdx.Game;


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
