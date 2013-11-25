package com.peter.server.map;

public class Data {
    private int citizens, shopkeeps, monsters;
    
    /*
		citizens = 2000;
		shopkeeps = 38000;
		monsters = 10;
		past ~35fps*/

	public Data(){
		citizens = 4;
		shopkeeps = 1;
		monsters = 5;
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
	public int getNPCTotal() {
		return monsters + citizens + shopkeeps;
	}
}
