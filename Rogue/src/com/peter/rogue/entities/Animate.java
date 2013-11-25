package com.peter.rogue.entities;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.peter.rogue.Global;

public class Animate extends Entity{
	protected Stats stats;
	protected Responses response;
	protected float oldX, oldY;
	//protected static Scanner in;
	protected static LinkedList<String> firstNames;
	protected static LinkedList<String> lastNames;
	protected float messageDelay = 0, statusDelay = 0, delay, time = 0;
	public boolean messageFlag, statusFlag;
	protected HostilityList list;
	protected String message;
	protected Integer status;
	protected String target;
	public static Vector3 pos = new Vector3();
	protected Animate attacker;
	protected Sound death;
	
	static{
		/*firstNames = new LinkedList<String>();
		lastNames = new LinkedList<String>();
		in = new Scanner(Gdx.files.internal("data/firstName.txt").readString());
		while(in.hasNextLine())
			firstNames.add(new String(in.nextLine()));
		in.close();
		in = new Scanner(Gdx.files.internal("data/lastName.txt").readString());
		while(in.hasNextLine())
			lastNames.add(new String(in.nextLine()));
		in.close();*/
	}
	
	public Animate(String filename, String type) {
		super(filename, type);
		list = new HostilityList();
		stats = new Stats();
		response = new Responses(type);
		message = new String("");
		status = null;
	}
	public void update(float delta){
		time += Gdx.graphics.getDeltaTime();
		
		if(messageDelay > 2.0){
			resetMessage();
			messageDelay = 0;
			messageFlag = false;
		}
		
		if(getMessage() != ""){
			messageFlag = true;
			messageDelay += Gdx.graphics.getDeltaTime();
		}

		if(statusDelay > 1.6f){
			resetStatus();
			statusDelay = 0;
			statusFlag = false;
		}
		
		if(getStatus() != null){
			statusFlag = true;
			statusDelay += Gdx.graphics.getDeltaTime();
		}
	}
	@Override
	public void setPosition(float x, float y){
		setX(x * tileWidth);
		setY(y * tileHeight);

		oldX = getX();
		oldY = getY();
		
		map.put(ID, this);
		map.setMark(ID, getX(), getY());
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	
	public String getMessage() {
		return message;
	}
	
	public void resetMessage() {
		message = "";
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(int amount){
		if(!statusFlag)
			status = amount;
	}
	public void resetStatus() {
		status = null;
	}

	protected void attack(Animate entity){
		int amount = 0;
		if(this.getStats().getStrength() == 0)
			amount = Global.rand(3, 0);
		else
			amount = Global.rand(this.getStats().getStrength(), 0);
		amount -= entity.getStats().getDefense();
		if(amount < 0)
			amount = 0;
		amount *= -1;
		entity.getStats().mutateHitpoints(amount);
		
		if(this instanceof Player)
			entity.attacker = this;
		entity.setStatus(amount);
		bump(entity);
		if(entity.getStats().getHitpoints() <= 0){
			Entity.map.setMark("", entity.getX(), entity.getY());
			stats.addExperience(entity.type);
			if(entity instanceof NPC){
				map.npcs.remove(entity);
				map.remove(entity.getID());
				Entity.map.items.add(((NPC) entity).getDrop());
				Entity.map.items.get(Entity.map.items.size()-1).setPosition(entity.getX()/32, entity.getY()/32);
			}
			else if(entity instanceof Player){
				Global.gameOver = true;
				entity.death.play();
			}
		}
	}

	protected void bump(Animate entity){
		entity.setMessage(this);
		setX(oldX);
		setY(oldY);
	}

	protected void bump(){
		setX(oldX);
		setY(oldY);
	}
	
	public void setMessage(Animate entity){
		if(entity instanceof Player && ((Player)(entity)).isHostile() && !list.check(entity))
			list.addID(entity.getID());
		if(!messageFlag)
			// Uncomment for player responses
			/*if(type.equals("Player"))
				message = response.call(entity, entity.list.check(this));
			else*/
				message = response.call(entity, list.check(entity));
	}
	

	public void setDelays(int delay) {
		this.delay = delay;
		messageDelay = delay;
		statusDelay = delay;
		
	}
}