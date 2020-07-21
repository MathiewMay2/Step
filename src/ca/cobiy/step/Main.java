package ca.cobiy.step;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import ca.cobiy.step.Commands.NPCCommand;
import ca.cobiy.step.Libs.NPC;
import ca.cobiy.step.Libs.v1_10.NPC_1_10_R1;
import ca.cobiy.step.Libs.v1_11.NPC_1_11_R1;
import ca.cobiy.step.Libs.v1_12.NPC_1_12_R1;
import ca.cobiy.step.Libs.v1_13.NPC_1_13_R1;
import ca.cobiy.step.Libs.v1_13.NPC_1_13_R2;
import ca.cobiy.step.Libs.v1_14.NPC_1_14_R1;
import ca.cobiy.step.Libs.v1_15.NPC_1_15_R1;
import ca.cobiy.step.Libs.v1_16.NPC_1_16_R1;
import ca.cobiy.step.Libs.v1_8.NPC_1_8_R2;
import ca.cobiy.step.Libs.v1_8.NPC_1_8_R3;
import ca.cobiy.step.Libs.v1_9.NPC_1_9_R1;
import ca.cobiy.step.Libs.v1_9.NPC_1_9_R2;

public class Main extends JavaPlugin {

	public String PluginName = "Step";
	public String PluginPrefix = "&6[&aStep&6]&r ";
	
	public NPC npc;
	
	@Override
	public void onEnable() {
		Log("&eEnabling "+PluginName+"...");
		npcAbstraction();
		this.saveDefaultConfig();
		this.getCommand("NPC").setExecutor(new NPCCommand(this));
		Log("&aEnabled "+PluginName+" successfully");
	}
	
	@Override
	public void onDisable() {
		Log("&eDisabling "+PluginName+"...");
		Log("&aDisabled "+PluginName+" successfully");
	}
	
	public void npcAbstraction() {
		String bukkitClassName = Bukkit.getServer().getClass().getPackage().getName();
		String versionID = bukkitClassName.split("\\.")[3];
		
		if(versionID.equals("v1_8_R2")) {
			npc = new NPC_1_8_R2(this);
		}else if(versionID.equals("v1_8_R3")){
			npc = new NPC_1_8_R3(this);
		}else if(versionID.equals("v1_9_R1")){
			npc = new NPC_1_9_R1(this);
		}else if(versionID.equals("v1_9_R2")){
			npc = new NPC_1_9_R2(this);
		}else if(versionID.equals("v1_10_R1")){
			npc = new NPC_1_10_R1(this);
		}else if(versionID.equals("v1_11_R1")){
			npc = new NPC_1_11_R1(this);
		}else if(versionID.equals("v1_12_R1")){
			npc = new NPC_1_12_R1(this);
		}else if(versionID.equals("v1_13_R1")){
			npc = new NPC_1_13_R1(this);
		}else if(versionID.equals("v1_13_R2")){
			npc = new NPC_1_13_R2(this);
		}else if(versionID.equals("v1_14_R1")){
			npc = new NPC_1_14_R1(this);
		}else if(versionID.equals("v1_15_R1")){
			npc = new NPC_1_15_R1(this);
		}else if(versionID.equals("v1_16_R1")){
			npc = new NPC_1_16_R1(this);
		}else {
			Log("&4Your server version is not supported by step Step");
		}
	}
	
	public void Log(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PluginPrefix+message));
	}
}
