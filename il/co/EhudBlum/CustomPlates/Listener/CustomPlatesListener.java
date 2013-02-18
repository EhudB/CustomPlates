package il.co.EhudBlum.CustomPlates.Listener;

import il.co.EhudBlum.CustomPlates.CustomPlates;
import il.co.EhudBlum.CustomPlates.Plate.Plate;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CustomPlatesListener implements Listener {
	CustomPlates plugin;
	private ArrayList<Plate> plates = new ArrayList<Plate>();
	ArrayList<String> Mods= new ArrayList<String>();
	
	public CustomPlatesListener(CustomPlates cp){
		this.plugin = cp;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		Mods.add("OnlyMe");
		Mods.add("AllowedPlayers");
		if(!Mods.contains(plugin.GetDefaultMod())){
			plugin.setDefaultMod("OnlyMe");
		}
	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event){
		if(event.getBlock().getTypeId() != 0){
			plates = plugin.getPlates();
			for(int i = 0; i< plates.size(); i++){
				Location loc = new Location(event.getBlock().getWorld(),event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ());
				Location loc2 = new Location(plates.get(i).getLocation().getWorld(), plates.get(i).getLocation().getBlockX(),plates.get(i).getLocation().getBlockY()-1,plates.get(i).getLocation().getBlockZ());
				if(loc2.equals(loc)){
					event.setCancelled(true);
					break;
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockDestroy(BlockBreakEvent event){
		if(event.getBlock().getTypeId() != 0){
			plates = plugin.getPlates();
			for(int i = 0; i< plates.size(); i++){
				Location loc = new Location(event.getPlayer().getWorld(),event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ());
				Location loc2 = new Location(plates.get(i).getLocation().getWorld(), plates.get(i).getLocation().getBlockX(),plates.get(i).getLocation().getBlockY()-1,plates.get(i).getLocation().getBlockZ());
				if(plates.get(i).getLocation().equals(event.getBlock().getLocation())){
					if(plates.get(i).getOwner().equalsIgnoreCase(event.getPlayer().getDisplayName())){
						event.getPlayer().sendMessage(ChatColor.GREEN+"You removed the pressure plate.");
						plates.remove(i);
						plugin.setPlates(this.plates);
						plugin.RemoveLine();
						event.getPlayer().getInventory().addItem(new ItemStack(264,1));
						event.getPlayer().updateInventory();
						event.setCancelled(false);
						break;
					}else{
						event.getPlayer().sendMessage(ChatColor.RED+"This pressure plate is not yours.");
						event.getPlayer().sendMessage(ChatColor.RED+"don't try to remove it.");
						event.setCancelled(true);
					}
				}else if(loc2.equals(loc)){
					if(plates.get(i).getOwner().equalsIgnoreCase(event.getPlayer().getDisplayName())){
						event.getPlayer().sendMessage(ChatColor.GREEN+"You removed the pressure plate.");
						plates.remove(i);
						plugin.setPlates(this.plates);
						plugin.RemoveLine();
						event.getPlayer().getInventory().addItem(new ItemStack(264,1));
						event.getPlayer().updateInventory();
						event.setCancelled(false);
						break;
					}else{
						event.getPlayer().sendMessage(ChatColor.RED+"This pressure plate is not yours.");
						event.getPlayer().sendMessage(ChatColor.RED+"don't try to remove it.");
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		boolean Allow = true;
		ArrayList<String> players = new ArrayList<String>();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getPlayer().getItemInHand().getTypeId() == plugin.GetLockItem()){
				if(event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() ==72){
					players.add(event.getPlayer().getDisplayName());
					plates = plugin.getPlates();
					for(int i = 0; i< plates.size(); i++){
						if(plates.get(i).getLocation().equals(event.getClickedBlock().getLocation())){
							if(plates.get(i).getOwner().equalsIgnoreCase(event.getPlayer().getDisplayName())){
								event.getPlayer().sendMessage(ChatColor.RED+"You already marked this pressure plate");
							}else{
								event.getPlayer().sendMessage(ChatColor.RED+"This pressure plate was marked by another player");
							}
							Allow= false;
							break;
						}
					}
					if(Allow){
						boolean Write = plugin.AddUser(event.getPlayer().getDisplayName(),event.getClickedBlock().getLocation() ,players, plugin.GetDefaultMod());
						if(Write == false){
							event.getPlayer().sendMessage(ChatColor.RED+"Cant Bound This Pressure Plate");
						}else{
							event.getPlayer().sendMessage(ChatColor.GREEN+"This Pressure Plate is now yours");
							plates.add(new Plate(event.getPlayer().getDisplayName(),event.getClickedBlock().getLocation() ,players, plugin.GetDefaultMod()));
							plugin.setPlates(plates);
							ItemStack m = new ItemStack(plugin.GetLockItem(), 1);
							event.getPlayer().getInventory().removeItem(m);
							event.getPlayer().updateInventory();
						}
					}
				}
			}else if(event.getPlayer().getItemInHand().getTypeId() == plugin.getRemoveLockItem()){
				if(event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() == 72){
					plates = plugin.getPlates();
					for(int i = 0; i< plates.size(); i++){
						if(plates.get(i).getLocation().equals(event.getClickedBlock().getLocation())){
							if(plates.get(i).getOwner().equalsIgnoreCase(event.getPlayer().getDisplayName())){
								event.getPlayer().sendMessage(ChatColor.GREEN+"You removed the pressure plate.");
								plates.remove(i);
								plugin.setPlates(this.plates);
								plugin.RemoveLine();
								event.getPlayer().getInventory().addItem(new ItemStack(264,1));
								event.getPlayer().updateInventory();
								event.setCancelled(false);
							}else{
								event.getPlayer().sendMessage(ChatColor.RED+"This pressure plate is not yours.");
								event.getPlayer().sendMessage(ChatColor.RED+"don't try to remove it.");
								event.setCancelled(true);
							}
							break;
						}
					}
				}
			}else if(event.getPlayer().getItemInHand().getTypeId() == plugin.getModeSwitchItem()){
				if(event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() == 72){
					String mod = "OnlyMe";
					plates = plugin.getPlates();
					for(int i = 0; i< plates.size(); i++){
						if(plates.get(i).getLocation().equals(event.getClickedBlock().getLocation())){
							if(plates.get(i).getOwner().equalsIgnoreCase(event.getPlayer().getDisplayName())){
								for(int t=0;t< Mods.size();t++){
									if(Mods.get(t).equals(plates.get(i).getMod())){
										if(t+1>=Mods.size()){
											mod = Mods.get(0);
										}else{
											mod = Mods.get(t+1);
										}
										break;
									}
								}
								event.getPlayer().sendMessage(ChatColor.GREEN+"You have changed the mod of this plate to "+mod+".");
								plates.get(i).setMod(mod);
								plugin.setPlates(this.plates);
								plugin.RemoveLine();
							}else{
								event.getPlayer().sendMessage(ChatColor.RED+"This pressure plate is not yours.");
								event.getPlayer().sendMessage(ChatColor.RED+"don't try to change it mod.");
							}
							break;
						}
					}
				}
			}
		}else if(event.getAction() == Action.PHYSICAL){
			if(event.getClickedBlock().getTypeId() == 70 || event.getClickedBlock().getTypeId() ==72)
			{
				plates = plugin.getPlates();
				for(int i = 0; i< plates.size(); i++){
					if(plates.get(i).getLocation().equals(event.getClickedBlock().getLocation())){
						if(plates.get(i).getOwner().equalsIgnoreCase(event.getPlayer().getDisplayName())){
							event.setCancelled(false);
						}else if(plates.get(i).getMod().equals("AllowedPlayers")){
							if(plates.get(i).getAllowedPlayers().contains(event.getPlayer().getDisplayName())){
								event.setCancelled(false);
							}else{
								event.setCancelled(true);
							}
						}else{
							event.setCancelled(true);
						}
						break;
					}
				}
			}
					
		}
	}
}
