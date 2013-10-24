package com.peter.rogue.data;

import java.util.HashMap;

import com.peter.rogue.entities.Animate;
import com.peter.rogue.entities.Entity;

public class LevelData {
	private int citizens;
	private int shopkeeps;
	private int monsters;
    private int npcTotal;
    private HashMap<String, Entry1> animateDB;
    private HashMap<String, Entry2> inanimateDB;
    
    class Entry1{
    	private Animate entity;
    	public Entry1(Animate entity){
    		this.entity = entity;
    	}
    	public Animate getEntity(){
    		return entity;
    	}
    }
    class Entry2{
    	private Entity entity;
    	public Entry2(Entity entity){
    		this.entity = entity;
    	}
    	public Entity getEntity(){
    		return entity;
    	}
    }
	public LevelData(){
		animateDB = new HashMap<String, Entry1>();
		inanimateDB = new HashMap<String, Entry2>();
		citizens = 2;
		shopkeeps = 3;
		monsters = 1;
		npcTotal = citizens + shopkeeps + monsters;
	}
	
	public int getNPCTotal() {
		return npcTotal;
	}
	public int getCitizens() {
		return citizens;
	}
	public int getShopkeeps() {
		return shopkeeps;
	}
	public int getMonsters() {
		return monsters;
	}
	public void setCitizens(int citizens) {
		this.citizens = citizens;
	}
	public void setShopkeeps(int shopkeeps) {
		this.shopkeeps = shopkeeps;
	}
	public void setMonsters(int monsters) {
		this.monsters = monsters;
	}
	public void add(String ID, Entity entity){
		if(entity.isAnimate())
			animateDB.put(ID, new Entry1((Animate)entity));
		else
			inanimateDB.put(ID, new Entry2(entity));
	}
	public String get(String ID){
		if(ID != "null")
			if(animateDB.get(ID) != null)
				if(animateDB.get(ID).getEntity().getName().equals("null"))
					return animateDB.get(ID).getEntity().getType() + ", level " + animateDB.get(ID).getEntity().getStats().getLevel();
				else
					return animateDB.get(ID).getEntity().getName() + ", level " + animateDB.get(ID).getEntity().getStats().getLevel();
			else
				return inanimateDB.get(ID).getEntity().getName();
		return "null";
	}
}