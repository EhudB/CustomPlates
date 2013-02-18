package il.co.EhudBlum.CustomPlates.util;

import il.co.EhudBlum.CustomPlates.CustomPlates;
import il.co.EhudBlum.CustomPlates.Plate.Plate;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CustomPlatesUtil {
	CustomPlates plugin;
	
	public CustomPlatesUtil(CustomPlates plugin){
		this.plugin = plugin;
	}
	
	public Plate StringToPlate(String string){
		String Owner;
		Location loc;
		String Mod;
		ArrayList<String> AllowedPlayers = new ArrayList<String>();
		Plate plate;
		String str = string;
		String[] array1 = str.split(":");
		Owner = array1[0];
		String str2 = array1[1];
		String[] array2 = str2.split("=");
		String[] location = array2[0].split(",");
		int[] loca = new int[location.length];
		for(int i = 1; i< loca.length; i++){
			loca[i-1] = Integer.parseInt(location[i]);
		}
		loc = new Location(Bukkit.getWorld(location[0]), loca[0], loca[1], loca[2]);
		Mod = array2[1];
		String[] temp = array2[2].split(",");
		for(int i = 0; i< temp.length; i++){
			AllowedPlayers.add(CleanString(temp[i], new String[]{"[","]"," "}));
		}
		plate = new Plate(Owner, loc, AllowedPlayers, Mod);
		return plate;
	}
	
	public String CleanString(String str, String[] args){
		String str3 = str;
		for(int i = 0; i< args.length; i++){
			str3 = str3.replace(args[i], "");
		}
		return str3;
	}
}
