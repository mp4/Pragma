package com.peter.packets;

import com.esotericsoftware.kryonet.Connection;

public class PlayerPacket{

	private boolean hostile;
	public Connection connection;
	public int x, y;
	public int oldX, oldY;
	public Integer ID;
	
	public PlayerPacket(){
		hostile = false;
	}
	
	public void sync(PlayerPacket packet){
		x = packet.x;
		y = packet.y;
	}

	/*public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		update(Gdx.graphics.getDeltaTime());
		canDraw = true;
	}*/
	
	public boolean isHostile() {
		return hostile;
	}

	public void setHostility(boolean hostile) {
		this.hostile = hostile;
	}
	public void setID(Integer ID){
		this.ID = ID;
	}
}