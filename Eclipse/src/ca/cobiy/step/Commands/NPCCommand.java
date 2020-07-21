package ca.cobiy.step.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ca.cobiy.step.Main;

public class NPCCommand implements CommandExecutor{

	Main plugin;
	public NPCCommand(Main main) {
		this.plugin = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("step.command.npc")) {
			if(sender instanceof Player) {
				if(!plugin.npc.existsNPC("test")) {
					plugin.npc.createNPC("test");
					plugin.npc.spawnNPC("test");
					new BukkitRunnable(){
						int step = 0;
						public void run() {
							step++;
							if(step == 1) {
								plugin.npc.jumpNPC("test");
							}
							if(step == 2) {
								plugin.npc.jumpNPC("test");
							}
							if(step == 3) {
								plugin.npc.crouchNPC("test");
							}
							if(step == 4) {
								plugin.npc.uncrouchNPC("test");
							}
							if(step == 5) {
								plugin.npc.crouchNPC("test");
							}
							if(step == 6) {
								plugin.npc.attackNPC("test");
							}
							if(step == 7) {
								plugin.npc.uncrouchNPC("test");
							}
							if(step == 8) {
								cancel();
							}
						}
					}.runTaskTimer(plugin, 0L, 20L);
					return true;
				}else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.PluginPrefix+"&2The demo is over :)."));
					return true;
				}
			}else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.PluginPrefix+"&4You can only execute this command from a player."));
				return true;
			}
		}else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.PluginPrefix+"&4You do not have permissions to execute this command."));
			return true;
		}
	}
}
