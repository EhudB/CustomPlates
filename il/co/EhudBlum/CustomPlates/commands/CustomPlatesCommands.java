package il.co.EhudBlum.CustomPlates.commands;

import java.util.ArrayList;

import il.co.EhudBlum.CustomPlates.CustomPlates;
import il.co.EhudBlum.CustomPlates.Plate.Plate;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;



public class CustomPlatesCommands implements CommandExecutor {
	private CustomPlates plugin;
	ArrayList<Plate> plates = new ArrayList<Plate>();
	 
	public CustomPlatesCommands(CustomPlates plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		if(cmd.getName().equalsIgnoreCase("allowedplayers") || cmd.getName().equalsIgnoreCase("ap")){
			if(!(sender instanceof Player)){
				sender.sendMessage("Your not a player you can't use this command");
			}else{
				Player player = (Player)sender;
				if(args[0].equalsIgnoreCase("add")){
					if(args.length > 1){
						Location loc = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(),player.getLocation().getBlockZ());
						if(loc.getBlock().getTypeId() == 70 || loc.getBlock().getTypeId() == 72){
							plates = plugin.getPlates();
							for(int i = 0; i< plates.size(); i++){
								if(plates.get(i).getLocation().equals(loc)){
									if(plates.get(i).getOwner().equalsIgnoreCase(player.getDisplayName())){
										if(plates.get(i).getMod().equalsIgnoreCase("AllowedPlayers")){
											String str = "";
											String wontadd = "";
											for(int t=1;t<args.length; t++){
												if(plates.get(i).getAllowedPlayers().contains(args[t])){
													if(t == args.length-1){
														wontadd += args[t];
													}else{
														wontadd += args[t]+",";
													}
													continue;
												}else{
													plates.get(i).addAllowedPlayer(args[t]);
													if(t == args.length-1){
														str += args[t];
													}else{
														str += args[t]+",";
													}
												}
											}
											if(!str.isEmpty()){
												player.sendMessage(ChatColor.GREEN+"You added the players:");
												player.sendMessage(ChatColor.DARK_GREEN+str);
											}
											if(!wontadd.isEmpty()){
												player.sendMessage(ChatColor.RED+"You didnt added the players, they are already in:");
												player.sendMessage(ChatColor.DARK_GREEN+wontadd);
											}
											plugin.setPlates(plates);
											plugin.RemoveLine();
										return true;
										}else{
											player.sendMessage(ChatColor.RED+"this pressure plate is not on the right mod");
											return false;
										}
									}else{
										player.sendMessage(ChatColor.RED+"You dont own this pressure plate");
										return false;
									}
								}
							}
						}else{
							player.sendMessage(ChatColor.RED+"You are not standing on a pressure plate");
						}
					}else{
						sender.sendMessage(ChatColor.RED+"You are not trying to add anyone");
						return false;
					}
				}else if(args[0].equalsIgnoreCase("remove")){
					if(args.length > 1){
						Location loc = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(),player.getLocation().getBlockZ());
						if(loc.getBlock().getTypeId() == 70 || loc.getBlock().getTypeId() == 72){
							plates = plugin.getPlates();
							for(int i = 0; i< plates.size(); i++){
								if(plates.get(i).getLocation().equals(loc)){
									if(plates.get(i).getOwner().equalsIgnoreCase(player.getDisplayName())){
										if(plates.get(i).getMod().equalsIgnoreCase("AllowedPlayers")){
											String str = "";
											String wontadd = "";
											for(int t=1;t<args.length; t++){
												if(plates.get(i).getAllowedPlayers().contains(args[t])){
													plates.get(i).removeAllowedPlayer(args[t]);
													if(t == args.length-1){
														str += args[t];
													}else{
														str += args[t]+",";
													}
												}else{
													if(t == args.length-1){
														wontadd += args[t];
													}else{
														wontadd += args[t]+",";
													}
												}
											}
											if(!str.isEmpty()){
												player.sendMessage(ChatColor.GREEN+"You removed the players:");
												player.sendMessage(ChatColor.DARK_GREEN+str);
											}
											if(!wontadd.isEmpty()){
												player.sendMessage(ChatColor.RED+"You didnt removed the players, they are already out:");
												player.sendMessage(ChatColor.DARK_GREEN+wontadd);
											}
											plugin.setPlates(plates);
											plugin.RemoveLine();
										return true;
										}else{
											player.sendMessage(ChatColor.RED+"this pressure plate is not on the right mod");
											return false;
										}
									}else{
										player.sendMessage(ChatColor.RED+"You dont own this pressure plate");
										return false;
									}
								}
							}
						}else{
							player.sendMessage(ChatColor.RED+"You are not standing on a pressure plate");
						}
					}else{
						sender.sendMessage(ChatColor.RED+"You are not trying to remove anyone");
						return false;
					}
				}else if(args[0].equalsIgnoreCase("list")){
					if(args.length == 1){
						Location loc = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(),player.getLocation().getBlockZ());
						if(loc.getBlock().getTypeId() == 70 || loc.getBlock().getTypeId() == 72){
							plates = plugin.getPlates();
							for(int i = 0; i< plates.size(); i++){
								if(plates.get(i).getLocation().equals(loc)){
									if(plates.get(i).getOwner().equalsIgnoreCase(player.getDisplayName())){
										if(plates.get(i).getMod().equalsIgnoreCase("AllowedPlayers")){
											player.sendMessage(ChatColor.GOLD+"Allowed Players: "+ plugin.getUtil().CleanString(plates.get(i).getAllowedPlayers().toString(), new String[]{"[","]"}));
											return true;
										}
									}
								}
							}
						}else{
							player.sendMessage(ChatColor.RED+"You are not standing on a pressure plate");
							return false;
						}
					}
				}else{
					return false;
				}
			}
		}else if(cmd.getName().equalsIgnoreCase("pp")){
			if(args.length == 0){
				if(!(sender instanceof Player)){
					sender.sendMessage("Your not a player you can't use this command");
				}else{
					Player player = (Player)sender;
					plates = plugin.getPlates();
					int t = 1;
					player.sendMessage("Plates owned by you:");
					for(int i = 0;i<plates.size();i++){
						if(plates.get(i).getOwner().equalsIgnoreCase(player.getDisplayName())){
							player.sendMessage(t+"."+plates.get(i).toString());
							t++;
						}
					}
					if(t == 1){
						player.sendMessage("None");
					}
					return true;
				}
			}
		}else if(cmd.getName().equalsIgnoreCase("cp") || cmd.getName().equalsIgnoreCase("CustomPlates")){
			if(args.length == 0){
				if(!(sender instanceof Player)){
					sender.sendMessage("Your not a player you can't use this command");
				}else{
					Player player = (Player)sender;
					player.sendMessage(ChatColor.AQUA+""+ChatColor.UNDERLINE+"Items");
					player.sendMessage(ChatColor.BLUE+"Lock item: "+new ItemStack(plugin.GetLockItem()).getType().toString());
					player.sendMessage(ChatColor.BLUE+"Remove lock item: "+new ItemStack(plugin.getRemoveLockItem()).getType().toString());
					player.sendMessage(ChatColor.BLUE+"Mod change item: "+new ItemStack(plugin.getModeSwitchItem()).getType().toString());
					player.sendMessage(ChatColor.AQUA+""+ChatColor.UNDERLINE+"Commands");
					player.sendMessage(ChatColor.BLUE+"/pp");
					player.sendMessage(ChatColor.GREEN+"Give you a list of pressure plates owned by you.");
					player.sendMessage(ChatColor.BLUE+"/ap|allowedplayers [list]");
					player.sendMessage(ChatColor.GREEN+"Give you a list of allowed players that can use the pressure plate");
					player.sendMessage(ChatColor.RED+"You mush stand on the pressure plate, and be the owner of it");
					player.sendMessage(ChatColor.BLUE+"/ap|allowedplayers [add] [player,player...]");
					player.sendMessage(ChatColor.GREEN+"Add players to your allowed players list");
					player.sendMessage(ChatColor.RED+"You mush stand on the pressure plate, and be the owner of it");
					player.sendMessage(ChatColor.BLUE+"/ap|allowedplayers [remove] [player,player...]");
					player.sendMessage(ChatColor.GREEN+"Remove players from your allowed players list");
					player.sendMessage(ChatColor.RED+"You mush stand on the pressure plate, and be the owner of it");
					return true;
				}
			}
		}
		return false;
	}


}
