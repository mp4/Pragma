package com.peter.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.esotericsoftware.kryonet.Listener;
import com.peter.rogue.Global;
import com.peter.rogue.Rogue;
import com.peter.rogue.entities.Entity;
import com.peter.rogue.entities.EntityManager;
import com.peter.rogue.network.Network;

public class Play extends Listener implements Screen{

	//private Rogue game;
	
	private FPSLogger fps;
	
    private EntityManager manager;
    
    
	public Play(Rogue game){
		fps = new FPSLogger();
		fps.log();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    //fps.log();
	    
		manager.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		Global.camera.viewportWidth = width;
		Global.camera.viewportHeight = height;
	}
	
	@Override
	public void show() {
		manager = new EntityManager();
		manager.init();

		Global.camera.setToOrtho(false);    
		Global.network = new Network();
		Global.network.connect();
	}
	

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
        Global.mapShapes.dispose();
        Global.screenShapes.dispose();
        Entity.map.getSpriteBatch().dispose();
		manager.dispose();
		Global.network.client.close();
	}

}
