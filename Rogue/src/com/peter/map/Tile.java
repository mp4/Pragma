package com.peter.map;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.peter.inventory.Item;
import com.peter.rogue.Global;

public class Tile{
	/*blocked, stairway, door, direction, canSee, animated, hasDescription
	*/
	private Texture texture;
	private Item item;
	private String name;
	private boolean[] properties;
	private float tick = Global.rand(30, 0);
	private Color tint;

	public static Tile BLANK = new Tile("blank.png", "Blank", new boolean[]{true, false, false, false, false, false, false});
	public static Tile GROUND = new Tile("period.png", "Ground", new boolean[]{false, false, false, false, true, false, false});
	public static Tile WALL = new Tile("hash.png", "Wall", new boolean[]{true, false, false, false, false, false, false});
	public static Tile DOOR = new Tile("plus.png", "Door", new boolean[]{false, false, true, false, false, false, true});
	public static Tile WATER = new Tile("tilda.png", "Water", new boolean[]{true, false, false, false, true, true, false});
	public static Tile DOWN = new Tile("greater.png", "Downstairs", new boolean[]{false, true, false, false, true, false, true});
	public static Tile UP = new Tile("less.png", "Upstairs", new boolean[]{false, true, false, true, true, false, true});
	public static Tile GRASS = new Tile("comma.png", "Grass", new boolean[]{false, false, false, false, true, false, false});
	
	public Tile(String filename, String name, boolean[] properties){
		texture = new Texture(Gdx.files.internal("maps/" + filename));
		tint = new Color();
		item = null;
		this.name = name;
		this.properties = properties;
	}
	
	public Tile(Tile tile){
		this.texture = tile.texture;
		this.name = tile.name;
		this.properties = tile.properties;
		this.tint = Palette.getTint(tile.name);
		if(this.tint == null)
			this.tint = Palette.getRandTint(tile.name, Global.rand(8, 0) > 0);
	}
	
	public void update(float delta){
		if(properties[5]){
			tick += delta*10;
			if(tick > 30){
				tint = Palette.getRandTint(name);
				tick = 0;
			}
		}
	}
	
	public void setTint(String tint){
		if(!properties[5]){
			this.tint = Palette.getTint(tint);
			if(this.tint == null)
				this.tint = Palette.getRandTint(tint, Global.rand(8, 0) > 0);
		}
	}
	public Color getTint(){
		return tint;
	}
	public String getName(){
		return name;
	}

	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public boolean isBlocked(){
		return properties[0];
	}
	public boolean hasStairs() {
		return properties[1];
	}
	public boolean isDoor(){
		return properties[2];
	}
	public boolean direction() {
		return properties[3];
	}
	public boolean canSee() {
		return properties[4];
	}
	public boolean isAnimated() {
		return properties[5];
	}
	public boolean hasDescription() {
		return properties[6];
	}

	public void put(Item item) {
		this.item = item;
	}
	public Item remove(Integer ID){
		if(ID.equals(item.ID)){
			Item temp = item;
			item = null;
			return temp;
		}
		System.out.println("[CLIENT] Failed to remove the item!");
		return null;
	}
	public Item get() {
		return item;
	}
}
