package com.peter.server;

import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.peter.packets.AddPlayerPacket;
import com.peter.packets.MessagePacket;
import com.peter.packets.Player;
import com.peter.packets.PlayerPacket;
import com.peter.packets.RemovePlayerPacket;

public class PragmaServer extends Listener {

	//Server object
	static Server server;
	
	//Ports to listen on
	private static int udpPort = 23783, tcpPort = 23783;
	public HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	public static void main(String[] args) throws Exception {
		System.out.println("Creating the server...");
		server = new Server();
		server.getKryo().register(PlayerPacket.class);
		server.getKryo().register(MessagePacket.class);
		server.getKryo().register(AddPlayerPacket.class);
		server.getKryo().register(RemovePlayerPacket.class);
		server.bind(tcpPort, udpPort);
		server.start();
		server.addListener(new PragmaServer());
		
		System.out.println("Server is operational!");
	}
	
	//This is run when a connection is received!
	public void connected(Connection c){
		Player player = new Player();
		player.x = 256;
		player.y = 256;
		player.c = c;
		
		AddPlayerPacket packet = new AddPlayerPacket();
		packet.ID = c.getID();
		server.sendToAllExceptTCP(c.getID(), packet);
		
		for(Player p : players.values()){
			AddPlayerPacket packet2 = new AddPlayerPacket();
			packet2.ID = p.c.getID();
			c.sendTCP(packet2);
		}
		
		players.put(c.getID(), player);
		System.out.println("Connection received.");
	}
	
	//This is run when we receive a packet.
	public void received(Connection c, Object o){
		if(o instanceof PlayerPacket){
			PlayerPacket packet = (PlayerPacket) o;
			players.get(c.getID()).x = packet.x;
			players.get(c.getID()).y = packet.y;
			
			packet.ID = c.getID();
			server.sendToAllExceptUDP(c.getID(), packet);
			System.out.println("received and sent an update player packet");
			
		}
	}
	
	public void disconnected(Connection c){
		players.remove(c.getID());
		RemovePlayerPacket packet = new RemovePlayerPacket();
		packet.ID = c.getID();
		server.sendToAllExceptTCP(c.getID(), packet);
		System.out.println("Connection dropped.");
	}
}
