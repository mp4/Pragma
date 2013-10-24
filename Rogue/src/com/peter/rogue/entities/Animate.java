package com.peter.rogue.entities;

import com.badlogic.gdx.math.Vector3;
import com.peter.rogue.Global;

public class Animate extends Entity{
	protected Stats stats;
	private Response response;
	protected float oldX, oldY;
	protected float wait = 0, messageDelay = 0, delay = 1;
	protected static Vector3 pos = new Vector3();
	
	public Animate(String filename, String type) {
		super(filename, type);
		animate = true;
		stats = new Stats();
		response = new Response(type);
	}
	
	@Override
	public void setPosition(float x, float y){
		setX(x * tileWidth);
		setY(y * tileHeight);
		oldX = getX();
		oldY = getY();
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
	public void setMessage(String type){
		if(type != "null")
			message = response.call(type);
	}
}

class Response{
	
	private String receiver;
	private int dice;
	
	public Response(String type) {
		this.receiver = type;
	}

	public String call(String caller){
		if(receiver == "Player"){
			dice = Global.randomGenerator(8, 0);
			switch(dice){
			case 0:
				return "Watch it bitch.";
			case 1:
				return "Keep to yourself, bud.";
			case 2:
				return "Touch me again and I'll cut you.";
			case 3:
				return "Get out of my face!";
			case 4:
				return "You're going to regret that.";
			case 5:
				return "I'll gut you like a fish.";
			case 6:
				return "Back off motherfucker!";
			case 7:
				return "Shove it up your ass.";
			}
		}
		if(receiver == "Shopkeep" || receiver == "Citizen"){
			dice = Global.randomGenerator(4, 0);
			if(caller == "worm")
				switch(dice){
				case 0:
					return "Eww, a " + caller + "!";
				case 1:
					return "Back vile beast!";
				case 2:
					return "How revolting!";
				case 3:
					return "Do you want to come home with me?";
				}
			switch(dice){
			case 0:
				return "Hello " + caller + "!";
			case 1:
				return "Greetings " + caller + "!";
			case 2:
				return "How goes it " + caller + "?";
			case 3:
				return "Nice day isn't it?";
			}
		}
		if(receiver == "Worm"){
			dice = Global.randomGenerator(2, 0);
			switch(dice){
			case 0:
				return "*Gurgle*";
			case 1:
				return "*Screech*";
			}
		}
		
		return "I don't have a response programmed";
	}
}