package com.peter.rogue.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector3;
import com.peter.rogue.Global;

public class NPC extends Animate {

	protected int move;
	protected boolean hostile;
	
	private MapProperties keys = new MapProperties();
	
	public NPC(String filename, String type) {
		super(filename, type);
	}

	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		update(Gdx.graphics.getDeltaTime());
		if(!messageFlag){
			setMessage(Global.inbox.checkMail(getID()));
		}
		else{
			pos = new Vector3(getX(), getY(), 0);
			Global.camera.project(pos);
			spriteBatch.end();
			Global.shapeRenderer.begin(ShapeType.Filled);
			Global.shapeRenderer.setColor(0, 0, 0, 1f);
			Global.shapeRenderer.rect(pos.x, pos.y - 17, font.getBounds(getMessage()).width, font.getLineHeight());
			Global.shapeRenderer.end();
			spriteBatch.begin();
			Entity.font.draw(spriteBatch, getMessage(), getX(), getY());
		}
	}
	
	public void update(float delta){
		wait += Gdx.graphics.getDeltaTime();
		
		move = Global.randomGenerator(5, 0);
		if(wait >= .6f + delay){
			switch(move){
			case 0:
				setY(getY() + 32);
				wait = 0;
				break;
			case 1:
				setY(getY() - 32);
				wait = 0;
				break;
			case 2:
				setX(getX() - 32);
				wait = 0;
				break;
			case 3:
				setX(getX() + 32);
				wait = 0;
				break;
			case 4:
				wait = 0;
			}
			try{
				checkCollision();
			} catch(Exception e){
				System.out.println(e);
			}
		}

		if(messageDelay > 2.0){
			resetMessage();
			messageDelay = 0;
			messageFlag = false;
		}
		
		if(getMessage() != ""){
			messageFlag = true;
			messageDelay += Gdx.graphics.getDeltaTime();
		}
		
	}
	
	public void checkCollision(){
		keys = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties();
		
		if("1".equals(keys.get("blocked"))){
			setX(oldX);
			setY(oldY);
		}
		
		if(getMapID(getY(), getX()) != "null" && getMapID(getY(), getX()) != getID()){
			Global.inbox.sendMail(getMapID(getY(), getX()), getType());
			setX(oldX);
			setY(oldY);
		}
		
		setMap((int)oldY, (int)oldX, nullEntry);
		setMap((int)getY(), (int)getX(), this.entry);
		
		oldX = getX();
		oldY = getY();
	}
	
	
}