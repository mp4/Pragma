package com.peter.rogue.inventory;

public class Food extends Item{

	public static Food BREAD = new Food("Bread", 1, "f.png");
	public static Food MEAT = new Food("Meat", 1, "f.png");
	
	private Food(String name, int weight, String filename) {
		super(name, weight, filename);
	}

}