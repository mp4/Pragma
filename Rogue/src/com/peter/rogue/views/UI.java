package com.peter.rogue.views;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.peter.entities.Entity;
import com.peter.entities.MPPlayer;
import com.peter.entities.NPC;
import com.peter.entities.Player;
import com.peter.entities.Shopkeep;
import com.peter.inventory.Chest;
import com.peter.rogue.Global;
import com.peter.rogue.network.Chat;
import com.peter.rogue.screens.Play;

public class UI{
    private Texture texture1 = new Texture(Gdx.files.internal("img/guiLeftTest.png"));
    private Texture texture2 = new Texture(Gdx.files.internal("img/guiRightTest.png"));
    public static HashMap<Vector3, Entity> screenMarks;
    public static Chat chat;
    private Vector2 screenCoord;
    
	public UI(Player player){
		screenMarks = new HashMap<Vector3, Entity>();
		chat = new Chat(player);
	}
	
	public void draw(Player player){
		// Draws the messages and statuses on top of everything
		for(NPC npc : Play.map.npcs.values()){
			if(npc.canDraw){
				if(npc.messageFlag){
					Global.mapShapes.begin(ShapeType.Filled);
					Global.mapShapes.setColor(0, 0, 0, 1f);
					Global.mapShapes.rect(npc.getX(), npc.getY() - 17, Global.font.getBounds(npc.getMessage()).width, Global.font.getLineHeight());
					Global.mapShapes.end();
				}
				if(npc.statusFlag){
					Global.mapShapes.begin(ShapeType.Filled);
					if(npc.getStatus() == 0)
						Global.mapShapes.setColor(0f, 0f, .6f, 1f);
					else if(npc.getStatus() < 0)
						Global.mapShapes.setColor(.4f, 0f, 0f, 1f);
					else if(npc.getStatus() > 0)
						Global.mapShapes.setColor(0f, .4f, 0f, 1f);
					Global.mapShapes.circle(npc.getX(), npc.getY() + 20, 13);
					Global.mapShapes.end();
					Global.mapShapes.begin(ShapeType.Line);
					Global.mapShapes.setColor(0f, 0f, 0f, 1f);
					Global.mapShapes.circle(npc.getX(), npc.getY() + 20, 13);
					Global.mapShapes.end();
				}
				Play.map.getSpriteBatch().begin();
				Global.font.draw(Play.map.getSpriteBatch(), npc.getMessage(), npc.getX(), npc.getY());
				if(npc.getStatus() != null)
					if(npc.getStatus() < 10)
						Global.font.draw(Play.map.getSpriteBatch(), ((Integer)(Math.abs(npc.getStatus()))).toString(), npc.getX() - 4, npc.getY() + 26);
					else
						Global.font.draw(Play.map.getSpriteBatch(), ((Integer)(Math.abs(npc.getStatus()))).toString(), npc.getX() - 7, npc.getY() + 26);
				Play.map.getSpriteBatch().end();
			}
		}
		
		for(MPPlayer mpPlayer : Play.map.players.get(Play.map.getFloor()).values()){
			if(mpPlayer.canDraw){
				if(mpPlayer.statusFlag){
					Global.mapShapes.begin(ShapeType.Filled);
					if(mpPlayer.getStatus() == 0)
						Global.mapShapes.setColor(0f, 0f, .6f, 1f);
					else if(mpPlayer.getStatus() < 0)
						Global.mapShapes.setColor(.4f, 0f, 0f, 1f);
					else if(mpPlayer.getStatus() > 0)
						Global.mapShapes.setColor(0f, .4f, 0f, 1f);
					Global.mapShapes.circle(mpPlayer.getX(), mpPlayer.getY() + 20, 13);
					Global.mapShapes.end();
					Global.mapShapes.begin(ShapeType.Line);
					Global.mapShapes.setColor(0f, 0f, 0f, 1f);
					Global.mapShapes.circle(mpPlayer.getX(), mpPlayer.getY() + 20, 13);
					Global.mapShapes.end();
				}
				Play.map.getSpriteBatch().begin();
				Global.font.draw(Play.map.getSpriteBatch(), mpPlayer.getMessage(), mpPlayer.getX(), mpPlayer.getY());
				if(mpPlayer.getStatus() != null)
					if(mpPlayer.getStatus() < 10)
						Global.font.draw(Play.map.getSpriteBatch(), ((Integer)(Math.abs(mpPlayer.getStatus()))).toString(), mpPlayer.getX() - 4, mpPlayer.getY() + 26);
					else
						Global.font.draw(Play.map.getSpriteBatch(), ((Integer)(Math.abs(mpPlayer.getStatus()))).toString(), mpPlayer.getX() - 7, mpPlayer.getY() + 26);
				Play.map.getSpriteBatch().end();
			}
		}
		
		if(player.messageFlag){
			Global.mapShapes.begin(ShapeType.Filled);
			Global.mapShapes.setColor(0, 0, 0, 1f);
			Global.mapShapes.rect(player.getX(), player.getY() - 17, Global.font.getBounds(player.getMessage()).width, Global.font.getLineHeight());
			Global.mapShapes.end();
		}
		if(player.statusFlag){
			Global.mapShapes.begin(ShapeType.Filled);
			if(player.getStatus() == 0)
				Global.mapShapes.setColor(0f, 0f, .6f, 1f);
			else if(player.getStatus() < 0)
				Global.mapShapes.setColor(.4f, 0f, 0f, 1f);
			else if(player.getStatus() > 0)
				Global.mapShapes.setColor(0f, .4f, 0f, 1f);
			Global.mapShapes.circle(player.getX(), player.getY() + 20, 13);
			Global.mapShapes.end();
		}

		Play.map.getSpriteBatch().begin();
		Global.font.draw(Play.map.getSpriteBatch(), player.getMessage(), player.getX(), player.getY());
		if(player.getStatus() != null)
			if(player.getStatus() < 10)
				Global.font.draw(Play.map.getSpriteBatch(), ((Integer)(Math.abs(player.getStatus()))).toString(), player.getX() - 4, player.getY() + 26);
			else
				Global.font.draw(Play.map.getSpriteBatch(), ((Integer)(Math.abs(player.getStatus()))).toString(), player.getX() - 7, player.getY() + 26);
		
		Play.map.getSpriteBatch().end();

		Global.screenShapes.begin(ShapeType.Filled);
		Global.screenShapes.setColor(0, 0, 0, 1f);
		Global.screenShapes.rect(0, 0, Global.SCREEN_WIDTH, 100f);
		Global.screenShapes.end();
		Global.screenShapes.begin(ShapeType.Line);
		Global.screenShapes.setColor(Color.DARK_GRAY);
		Global.screenShapes.line(0, 100f, Global.SCREEN_WIDTH, 100f);
		Global.screenShapes.end();
		
		display(Global.screen, player);
		
		if(!player.getMenu().equals("")){
			screenCoord = new Vector2(Gdx.input.getX(), (Gdx.input.getY() * -1 + Global.SCREEN_HEIGHT));
			if(player.getMenu().equals("Inventory")){
				player.getInventory().display(Global.screen, Global.font, screenCoord);
			}
			
			else if(player.getMenu().equals("Chest")){
				player.getInventory().display(Global.screen, Global.font, screenCoord);
				((Chest) player.getInventory().getTrade()).display(Global.screen, Global.font, screenCoord);
			}
			
			else if(player.getMenu().equals("Barter")){
				player.getInventory().display(Global.screen, Global.font, screenCoord);
				((Shopkeep) player.getInventory().getTrade()).display(Global.screen, Global.font, screenCoord);
			}
			
			else if(player.getMenu().equals("Throw")){
				Gdx.gl.glEnable(GL10.GL_BLEND);
			    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				Global.screenShapes.begin(ShapeType.Filled);
				Global.screenShapes.setColor(new Color(.1f, .1f, .1f, .7f));
				Global.screenShapes.rect(Global.SCREEN_WIDTH-240, Global.SCREEN_HEIGHT/2 - 40, 190, 140);
				Global.screenShapes.end();
				Gdx.gl.glDisable(GL10.GL_BLEND);
				
				Global.screen.begin();
				Global.font.draw(Global.screen, "In what direction?", Global.SCREEN_WIDTH-200, Global.SCREEN_HEIGHT/2 + 80);
				Global.font.draw(Global.screen, "7   8   9", Global.SCREEN_WIDTH-169, Global.SCREEN_HEIGHT/2 + 45);
				Global.font.draw(Global.screen, "4        6", Global.SCREEN_WIDTH-169, Global.SCREEN_HEIGHT/2 + 25);
				Global.font.draw(Global.screen, "1   2   3", Global.SCREEN_WIDTH-169, Global.SCREEN_HEIGHT/2 + 5);
				Global.screen.end();
			}
			else if(player.getMenu().equals("Help")){
				Gdx.gl.glEnable(GL10.GL_BLEND);
			    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				Global.screenShapes.begin(ShapeType.Filled);
				Global.screenShapes.setColor(.1f, .1f, .1f, .9f);
				Global.screenShapes.rect(500, 150, Global.SCREEN_WIDTH-1000, Global.SCREEN_HEIGHT-300);
				Global.screenShapes.end();
				Global.screenShapes.begin(ShapeType.Line);
				Global.screenShapes.setColor(0.4f, 0.4f, 0.4f, 1);
				Global.screenShapes.rect(500, 150, Global.SCREEN_WIDTH-1000, Global.SCREEN_HEIGHT-300);
				Global.screenShapes.end();
				Gdx.gl.glDisable(GL10.GL_BLEND);
				
				Global.screen.begin();
				Global.gothicFont.draw(Global.screen, "Keys", 
						Global.SCREEN_WIDTH/2 - Global.gothicFont.getBounds("Keys").width/2, Global.SCREEN_HEIGHT/2 + 170);
				Global.font.draw(Global.screen, "ESC - Close current window", 300, 320);
				Global.font.draw(Global.screen, "d - Change demeanor", 300, 300);
				Global.font.draw(Global.screen, "i - Open inventory", 300, 280);
				Global.screen.end();
			}
		}
		
		if(player.showPlayers){
			Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			Global.screenShapes.begin(ShapeType.Filled);
			Global.screenShapes.setColor(.1f, .1f, .1f, .9f);
			Global.screenShapes.rect(Global.SCREEN_WIDTH-300, Global.SCREEN_HEIGHT-300, 250, 250);
			Global.screenShapes.end();
			Global.screenShapes.begin(ShapeType.Line);
			Global.screenShapes.setColor(0.4f, 0.4f, 0.4f, 1);
			Global.screenShapes.rect(Global.SCREEN_WIDTH-300, Global.SCREEN_HEIGHT-300, 250, 250);
			Global.screenShapes.setColor(0.7f, 0.7f, 0.7f, 1);
			Global.screenShapes.line(Global.SCREEN_WIDTH-290, Global.SCREEN_HEIGHT-100, Global.SCREEN_WIDTH-60, Global.SCREEN_HEIGHT-100);
			Global.screenShapes.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);

			Global.screen.begin();
			Global.gothicFont.draw(Global.screen, "Players           Floor", Global.SCREEN_WIDTH-285, Global.SCREEN_HEIGHT-60);
			Global.gothicFont.setScale(.6f);
			
			int i=0;
			for(int floor=0; floor<Play.map.players.size(); floor++)
				for(MPPlayer mpPlayer : Play.map.players.get(floor).values()){
					if(mpPlayer.getName() != null && mpPlayer.getPictureURL() != null){
						Global.gothicFont.draw(Global.screen, mpPlayer.getName(), Global.SCREEN_WIDTH-285, Global.SCREEN_HEIGHT-100 - i*30);
						Global.gothicFont.draw(Global.screen, floor + "", Global.SCREEN_WIDTH-125, Global.SCREEN_HEIGHT-100 - i*30);
						//Global.screen.draw(new Texture(Gdx.files.internal(mpPlayer.getPictureURL())), Global.SCREEN_WIDTH-100, Global.SCREEN_HEIGHT-100 - i*30);
						i++;
					}
				}
			
			Global.gothicFont.setScale(1f);
			Global.screen.end();
		}
		
		if(player.hasAlert()){
			Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			Global.screenShapes.begin(ShapeType.Filled);
			Global.screenShapes.setColor(0, 0, 0, .9f);
			Global.screenShapes.rect((Global.SCREEN_WIDTH/2) - Global.gothicFont.getBounds(player.getAlertMessage()).width, 165, 
					Global.gothicFont.getBounds(player.getAlertMessage()).width * 2, 45);
			Global.screenShapes.end();
			Global.screenShapes.begin(ShapeType.Line);
			Global.screenShapes.setColor(0.25f, 0.25f, 0.25f, 1);
			Global.screenShapes.rect((Global.SCREEN_WIDTH/2) - Global.gothicFont.getBounds(player.getAlertMessage()).width, 165, 
					Global.gothicFont.getBounds(player.getAlertMessage()).width * 2, 45);
			Global.screenShapes.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			
			Global.screen.begin();
			Global.gothicFont.setScale(1.1f);
			if(player.isError())
				Global.gothicFont.setColor(Color.RED);
			else
				Global.gothicFont.setColor(Color.GREEN);
			Global.gothicFont.draw(Global.screen, player.getAlertMessage(), 
					(Global.SCREEN_WIDTH/2) - Global.gothicFont.getBounds(player.getAlertMessage()).width/2, 215);
			Global.gothicFont.setScale(1f);
			Global.gothicFont.setColor(Color.WHITE);
			Global.screen.end();
		}
		
		update(Gdx.graphics.getDeltaTime());
	}
	
	public void update(float delta){
		chat.update(delta);
	}
	
	public void display(SpriteBatch spriteBatch, Player player){
		if(!player.getVisible().isEmpty()){
			Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			Global.screenShapes.begin(ShapeType.Filled);
			Global.screenShapes.setColor(new Color(.1f, .1f, .1f, .7f));
			Global.screenShapes.rect(0, 600 - player.getVisible().size() * 17 - 5, 190, player.getVisible().size() * 17 + 10);
			Global.screenShapes.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}

		chat.display(Global.screen, Global.gothicFont, Global.font);
		
		spriteBatch.begin();
		for(int i=0; i<player.getVisible().size(); i++){
			Global.font.draw(spriteBatch, player.getVisible().get(i).symbol + "", 20 - Global.font.getBounds(player.getVisible().get(i).symbol + "").width/2, 600 - i*17);
			Global.font.draw(spriteBatch, "-  " + player.getVisible().get(i).getName(), 38, 600 - i*17);
		}
		
		spriteBatch.draw(texture1, 0, 0);
		spriteBatch.draw(texture2, Global.SCREEN_WIDTH - 179, 0);
		spriteBatch.draw(player.getPicture(),  200, 15);
		
		Global.gothicFont.setScale(1f);
		Global.gothicFont.draw(spriteBatch, player.getName().split(" ")[0], 280, 90);
		Global.gothicFont.setScale(.7f);
		if(player.getStats().getPoints() > 0){
			Global.gothicFont.setColor(Color.GREEN);
			Global.gothicFont.draw(spriteBatch, "Level: " + player.getStats().getLevel(), 280, 50);
			Global.gothicFont.setColor(Color.WHITE);
		}
		else
			Global.gothicFont.draw(spriteBatch, "Level: " + player.getStats().getLevel(), 280, 50);
		Global.gothicFont.draw(spriteBatch, "Hitpoints: " + player.getStats().getHitpoints() + "/" + player.getStats().getMaxHitpoints(), 430, 50);
		Global.gothicFont.draw(spriteBatch, "Experience:  " + player.getStats().getExperience(), 430, 80);
		Global.gothicFont.draw(spriteBatch, "Demeanor: ", 670, 80);
		if(player.isHostile())
			Global.gothicFont.draw(spriteBatch, "Hostile", 755, 80);
		else
			Global.gothicFont.draw(spriteBatch, "Friendly", 755, 80);
		Global.gothicFont.draw(spriteBatch, "Floor: " + Play.map.getFloor(), 670, 50);
		spriteBatch.end();
	}
	
	public void dispose(){
	}

}