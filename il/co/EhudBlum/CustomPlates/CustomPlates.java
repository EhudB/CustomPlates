package il.co.EhudBlum.CustomPlates;

import il.co.EhudBlum.CustomPlates.Listener.CustomPlatesListener;
import il.co.EhudBlum.CustomPlates.Plate.Plate;
import il.co.EhudBlum.CustomPlates.commands.CustomPlatesCommands;
import il.co.EhudBlum.CustomPlates.util.CustomPlatesUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomPlates extends JavaPlugin {
	
	private CustomPlatesUtil cpu;
	private ArrayList<Plate> plates = new ArrayList<Plate>();
	
	//Config Values
	
	private int LockItem;
	private int ModeSwitchItem;
	private int RemoveLockItem;
	
	private String DefaultMod;
	
	//End Config Values
	
	@Override
	public void onEnable(){
		loadConfig();
		new CustomPlatesListener(this);
		getCommand("allowedplayers").setExecutor(new CustomPlatesCommands(this));
		getCommand("ap").setExecutor(new CustomPlatesCommands(this));
		getCommand("pp").setExecutor(new CustomPlatesCommands(this));
		getCommand("cp").setExecutor(new CustomPlatesCommands(this));
		getCommand("CustomPlates").setExecutor(new CustomPlatesCommands(this));
		this.saveDefaultConfig();
		loadPlates();
		cpu = new CustomPlatesUtil(this);
	    getDataFolder().mkdirs();
	    File myCustomSettings = new File(getDataFolder(), "Plates.prop");
	    try {
			myCustomSettings.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().info("Error Creating CustomPlates File: 'Plates.Prop'.");
			getLogger().info("Disableing plugin.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	@Override
	public void onDisable(){
	}
	
	public int GetLockItem(){
		return LockItem;
	}
	
	public String GetDefaultMod(){
		return DefaultMod;
	}
	
	public int getModeSwitchItem() {
		return ModeSwitchItem;
	}
	
	public int getRemoveLockItem() {
		return RemoveLockItem;
	}
	
	public CustomPlatesUtil getUtil(){
		return cpu;
	}
	
	public ArrayList<Plate> getPlates(){
		plates.clear();
		loadPlates();
		return plates;
	}
	
	public void setPlates(ArrayList<Plate> plates){
		this.plates = plates;
	}
	
	public void setDefaultMod(String Mod){
		this.DefaultMod = Mod;
	}
	
	public void loadConfig(){
		ConfigurationSection itemSection = getConfig().getConfigurationSection("Items");
		LockItem = itemSection.getInt("LockItem");
		ModeSwitchItem = itemSection.getInt("ModSwitchItem");
		RemoveLockItem = itemSection.getInt("RemoveLockItem");
		ConfigurationSection modSection = getConfig().getConfigurationSection("Mod");
		DefaultMod = modSection.getString("DefaultMod");
	}
	
	public void loadPlates(){
		File file = new File(getDataFolder(),"Plates.prop");
		if(!file.exists()){
			getLogger().info("Error Loading Plates, Shuting Down");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		try{
	        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            Plate plate = cpu.StringToPlate(line);
	            if(plate != null){
	            	plates.add(plate);
	            }
	        }
	        bufferedReader.close();
		}catch (Exception e){
		}
	}
	
	public boolean AddUser(String PlayerName, Location loc, ArrayList<String> players, String Mod){
		File file = new File(getDataFolder(),"Plates.prop");
		if(PlayerName == null || loc == null || players.isEmpty() || Mod == null){
			return false;
		}
		String string = PlayerName +":"+loc.getWorld().getName()+","+ loc.getBlockX()+"," + loc.getBlockY()+","+loc.getBlockZ()+"="+Mod+"="+players.toString()+"";
        if (!file.exists()) {
            try {
            	file.createNewFile();
            } catch (IOException e) {
            }
        }
        boolean DidWrite = logMessage(file,string);
        return DidWrite;
	}
	public boolean logMessage(File file, String string) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.append(string);
			bw.newLine();
			bw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void RemoveLine(){
		try{
			File file = new File(getDataFolder(),"Plates.prop");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
	        for(int i= 0;i<plates.size(); i++){
				String world = plates.get(i).getLocation().getWorld().getName();
				int x = plates.get(i).getLocation().getBlockX();
				int y = plates.get(i).getLocation().getBlockY();
				int z = plates.get(i).getLocation().getBlockZ();
				String str = plates.get(i).getOwner() + ":"+world+","+x+","+y+","+z+"="+plates.get(i).getMod()+"="+plates.get(i).getAllowedPlayers().toString();
				bw.append(str);
				bw.newLine();
	        }
	        bw.close();
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}catch (IOException ex) {
		    ex.printStackTrace();
		}
	}

}
