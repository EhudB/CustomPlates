package il.co.EhudBlum.CustomPlates.Plate;

import java.util.ArrayList;

import org.bukkit.Location;

public class Plate {
	String Owner;
	Location loc;
	ArrayList<String> AllowedPlayers = new ArrayList<String>();
	String Mod;
	
	public Plate(String owner, Location loc, ArrayList<String> AllowedPlayers, String Mod){
		this.Owner = owner;
		this.loc = loc;
		this.AllowedPlayers = AllowedPlayers;
		this.Mod = Mod;
	}
	
	public String getOwner(){
		return this.Owner;
	}
	
	public Location getLocation(){
		return this.loc;
	}
	
	public ArrayList<String> getAllowedPlayers(){
		return this.AllowedPlayers;
	}
	
	public String getMod(){
		return this.Mod;
	}
	
	public void setMod(String mod){
		this.Mod = mod;
	}
	
	public void addAllowedPlayer(String player){
		this.AllowedPlayers.add(player);
	}
	
	public void removeAllowedPlayer(String string){
		this.AllowedPlayers.remove(string);
	}
	
	public String toString(){
		String str = "Location: "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+" Mod: "+Mod;
		return str;
	}
}
