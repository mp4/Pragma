package com.peter.rogue.inventory;

public class Head extends Item{

	public static Head HELMET = new Head("Helmet", 5, "at.png");
	public static Head HAT = new Head("Hat", 2, "at.png");
	
	public Head(String name, int weight, String filename) {
		super(name, weight, filename);
	}

}