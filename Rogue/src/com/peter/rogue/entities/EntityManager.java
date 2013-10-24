package com.peter.rogue.entities;

import java.io.IOException;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.peter.rogue.Global;
import com.peter.rogue.data.*;
import com.peter.rogue.inventory.Food;
import com.peter.rogue.inventory.Head;
import com.peter.rogue.inventory.Item;
import com.peter.rogue.views.UI;

public class EntityManager {

    private LinkedList<NPC> npcs;
    private LinkedList<Item> items = new LinkedList<Item>();
    
    private int randX, randY;
    private LevelData data;
    private Player player;
    private UI ui;
    
    public EntityManager() throws IOException {
    	data = new LevelData();
    	npcs = new LinkedList<NPC>();
    }
    
	public void draw(OrthogonalTiledMapRenderer renderer){
		for(int i=0; i<items.size(); i++){
			if(items.get(i).isPickedUp())
				items.remove(i);
			else
				items.get(i).draw(renderer.getSpriteBatch());
		}
		for(int i=0; i<npcs.size(); i++){
			if(npcs.get(i).getX() > (player.getX() + player.getWidth()/2) - player.getViewDistance() && npcs.get(i).getX() < (player.getX() + player.getWidth()/2) + player.getViewDistance() &&
					npcs.get(i).getY() > (player.getY() + player.getHeight()/2) - player.getViewDistance() && npcs.get(i).getY() < (player.getY() + player.getHeight()/2) + player.getViewDistance())
				npcs.get(i).draw(renderer.getSpriteBatch());
			else
				npcs.get(i).update(Gdx.graphics.getDeltaTime());
		}

		player.draw(renderer.getSpriteBatch());
		
		renderer.setView(Global.camera);
		ui.draw(renderer, player);
		
		Animate.pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Global.camera.unproject(Animate.pos);
		
		if(Animate.pos.y + 32 > (Global.camera.position.y - Global.SCREEN_HEIGHT/3 + 12))
			player.setInformation(data.get(player.getMapID(Animate.pos.y, Animate.pos.x)));
		else
			player.setInformation("null");
		
		Global.camera.project(Animate.pos);
		Global.shapeRenderer.begin(ShapeType.Filled);
		Global.shapeRenderer.setColor(0, 0, 0, 1f);
		Global.shapeRenderer.rect(Animate.pos.x, Animate.pos.y, Entity.font.getBounds(player.getInformation()).width, Entity.font.getLineHeight());
		Global.shapeRenderer.end();
		Animate.pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Global.camera.unproject(Animate.pos);
		renderer.getSpriteBatch().begin();
		Entity.font.draw(renderer.getSpriteBatch(), player.getInformation(), Animate.pos.x, Animate.pos.y + Entity.font.getLineHeight());
    }
    
    public void init(){
    	Entity.initMap();
    	
    	for(int i=0; i<data.getCitizens(); i++){
			randX = Global.randomGenerator(13, 3);
			randY = Global.randomGenerator(7, 3);
			npcs.add(new NPC("c_.png", "Citizen"));
			npcs.getLast().setPosition(randX, randY);
			npcs.getLast().setMap((int)npcs.getLast().getY(), (int)npcs.getLast().getX(), npcs.getLast().getEntry());
		}
    	for(int i=data.getCitizens(); i<data.getCitizens() + data.getShopkeeps(); i++){
			randX = Global.randomGenerator(13, 3);
			randY = Global.randomGenerator(7, 3);
			npcs.add(new Shopkeep("s_.png", "Shopkeep"));
			npcs.getLast().setPosition(randX, randY);
			npcs.getLast().setMap((int)npcs.getLast().getY(), (int)npcs.getLast().getX(), npcs.getLast().getEntry());
		}
    	for(int i=data.getCitizens() + data.getShopkeeps(); i<data.getNPCTotal(); i++){
			randX = Global.randomGenerator(13, 3);
			randY = Global.randomGenerator(7, 3);
			npcs.add(new Monster("w.png", "Worm"));
			npcs.getLast().setPosition(randX, randY);
			npcs.getLast().setMap((int)npcs.getLast().getY(), (int)npcs.getLast().getX(), npcs.getLast().getEntry());
    	}
    	
    	for(int i=0; i<npcs.size(); i++){
    		Global.inbox.setupMail(npcs.get(i).getID());
    		data.add(npcs.get(i).getID(), npcs.get(i));
    	}
    	
		player = new Player("at.png", "Player");
		player.setPosition(18, 7);

		items.add(Food.BREAD);
		items.getLast().setPosition(8, 18);
		
		data.add(items.getLast().getID(), items.getLast());
		
		items.add(Head.HAT);
		items.getLast().setPosition(9, 18);
		
		data.add(items.getLast().getID(), items.getLast());

		Global.inbox.setupMail(player.getID());
		data.add(player.getID(), player);
		
		Gdx.input.setInputProcessor(player);
		ui = new UI();
	}
    
    public void dispose(){
		for(int i=0; i<data.getNPCTotal(); i++)
			npcs.get(i).getTexture().dispose();
		player.getTexture().dispose();
		ui.dispose();
		Global.shapeRenderer.dispose();
    }
}