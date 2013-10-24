package com.peter.rogue.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector3;
import com.peter.rogue.Global;
import com.peter.rogue.inventory.Inventory;
import com.peter.rogue.inventory.Item;

public class Player extends Animate implements InputProcessor {
	
	private float zoom = 1f;
	private Texture picture;
	private String info;
	private String menu = "null";
	private boolean menuActive = false;
	private MapProperties keys = new MapProperties();
	private Inventory inventory = new Inventory();
	private int wallet;
	private int viewDistance;
	
	public Player(String filename, String type){
		super(filename, type);
		messageFlag = false;
		name = "Adelaide";
		picture = new Texture(Gdx.files.internal("img/adelaide.png"));
		info = new String();
		viewDistance = 228;
		setWallet(0);
		stats.setLevel(1);
		stats.setHitpoints(10);
		stats.setDexterity(5);
		stats.setStrength(5);
		stats.setExperience(100);
	}

	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		
		spriteBatch.end();
		pos = new Vector3(getX(), getY(), 0);
		Global.camera.project(pos);
		Global.shapeRenderer.begin(ShapeType.Line);
		Global.shapeRenderer.setColor(1f, 0, 0, .1f);
		Global.shapeRenderer.circle(pos.x + getWidth()/2, pos.y + getHeight()/2, viewDistance);
		Global.shapeRenderer.end();
		
		if(!messageFlag){
			setMessage(Global.inbox.checkMail(getID()));
			spriteBatch.begin();
		}
		else{
			Global.shapeRenderer.begin(ShapeType.Filled);
			Global.shapeRenderer.setColor(0, 0, 0, 1f);
			Global.shapeRenderer.rect(pos.x, pos.y - 17, font.getBounds(getMessage()).width, font.getLineHeight());
			Global.shapeRenderer.end();
			spriteBatch.begin();
			Entity.font.draw(spriteBatch, getMessage(), getX(), getY());
		}

		update(Gdx.graphics.getDeltaTime());
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public Texture getPicture(){
		return picture;
	}
	
	public void update(float delta){
		wait += Gdx.graphics.getDeltaTime();
		
		if(wait >= .15){
			if(Gdx.input.isKeyPressed(Keys.LEFT)){
				setX(getX() - 32);
				wait = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT)){
				setX(getX() + 32);
				wait = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.DOWN)){
				setY(getY() - 32);;
				wait = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.UP)){
				setY(getY() + 32);
				wait = 0;
			}
			if(wait == 0f)
				menuActive = false;
			checkCollision();
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
			if("1".equals(keys.get("chest")))
				setMenu("chest");
			setX(oldX);
			setY(oldY);
		}
		
		if("1".equals(keys.get("money"))){
			setWallet(Integer.parseInt(getMapID(getY(), getX())));
		}
		
		if(getMapID(getY(), getX()) != "null" && getMapID(getY(), getX()) != getID()){
			
			if(getMapObject(getY(), getX()).getType().equals("item")){
				inventory.add((Item) getMapObject(getY(), getX()));
			}
			else{
				Global.inbox.sendMail(getMapID(getY(), getX()), getType());
				setX(oldX);
				setY(oldY);
			}
		}
		
		setMap((int)oldY, (int)oldX, nullEntry);
		setMap((int)getY(), (int)getX(), this.entry);
		oldX = getX();
		oldY = getY();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.G:
			setMenu("inventory");
			break;
		case Keys.LEFT:
			break;
		case Keys.RIGHT:
			break;
		case Keys.DOWN:
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.UP:
		case Keys.DOWN:
			break;
		case Keys.LEFT:
		case Keys.RIGHT:
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		zoom += (.1f / amount);
		return true;
	}

	// Mouse scroll zoom (may break)
	public float getZoom() {
		return zoom;
	}
	
	public void setInformation(String info){
		if(info != "null")
			this.info = info;
		else
			this.info = "";
	}
	
	public String getInformation(){
		return info;
	}
	
	public void setMenu(String request){
		menu = request;
		menuActive = !menuActive;
	}
	
	public String getMenu(){
		return menu;
	}
	
	public boolean isMenuActive(){
		return menuActive;
	}
	
	public int getViewDistance() {
		return viewDistance;
	}

	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}
}