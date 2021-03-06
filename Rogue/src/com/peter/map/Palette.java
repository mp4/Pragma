package com.peter.map;

import com.badlogic.gdx.graphics.Color;
import com.peter.rogue.Global;

public class Palette {
	private static float rand;
	
	public static Color getTint(String tile){
		switch(tile){
		case "Blank":
			return new Color(1, 1, 1, 1);
		case "Wall":
			return new Color(.7f, .7f, .7f, 1);
		case "Ground":
			return new Color(.4f, .4f, .4f, 1);
		case "Upstairs":
		case "Downstairs":
			return new Color(.75f, .75f, .75f, 1);
		case "Wood":
		case "Door":
			return new Color(.29f, .22f, .02f, 1);
		}
		return null;
	}
	
	public static Color getRandTint(String tile){
		switch(tile){
		case "Water":
			rand = Global.rand(20, 0);
			return new Color(0, rand/100f, (70 + rand)/100f, 1);
		case "Sand":
			return new Color(Global.rand(23, 77)/100f, Global.rand(23, 77)/100f, Global.rand(12, 0)/100f, 1);
		}
		return null;
	}
	
	public static Color getRandTint(String tile, boolean alive){
		switch(tile){
		case "Grass":
			if(alive)
				return new Color(Global.rand(60, 0)/100f, Global.rand(40, 60)/100f, Global.rand(20, 0)/100f, 1);
			else
				return new Color(Global.rand(13, 22)/100f, Global.rand(11, 13)/100f, 0, 1);
		case "Sand":
			return new Color(Global.rand(23, 77)/100f, Global.rand(23, 77)/100f, Global.rand(12, 0)/100f, 1);
		case "Water":
			rand = Global.rand(20, 0);
			return new Color(0, rand/100f, (70 + rand)/100f, 1);
		}
		return null;
	}
}
