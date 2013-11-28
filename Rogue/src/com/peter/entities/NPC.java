package com.peter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.peter.rogue.Global;

public class NPC extends Animate {

	protected int move;
	protected boolean canMove;
	//private Item drop;
	
	public NPC(String filename, String type) {
		super(filename, type);
		name = "Bob"/*firstNames.get(Global.rand(firstNames.size(), 0)) + " " + lastNames.get(Global.rand(lastNames.size(), 0))*/;
		delay -= Global.rand(100, 0) * .01f;
		canMove = true;
		//moves = new Stack<Node>();
		//drop = Global.rand(2, 0) == 1 ? new Item(Item.GOLD) : new Item(Item.GEM);
		delay = 2.6f;
	}

	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
	}
	
	/*public Item getDrop(){
		return drop;
	}*/
}