package ca.cobiy.step.Libs.v1_8;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;

import ca.cobiy.step.Main;
import ca.cobiy.step.Libs.NPC;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.MathHelper;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import net.minecraft.server.v1_8_R2.PlayerInteractManager;
import net.minecraft.server.v1_8_R2.WorldServer;

public class NPC_1_8_R2 implements NPC {
	
	Map<String, EntityPlayer> npcList = new HashMap<String, EntityPlayer>();
	
	MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
	WorldServer world;
	World bukkitWorld;
	Location location;
	
	Main plugin;
	public NPC_1_8_R2(Main main) {
		this.plugin = main;
		InitConfig();
	}
	
	@Override
	public void createNPC(String id) {
		if(!npcList.containsKey(id)) {
			GameProfile profile = new GameProfile(UUID.randomUUID(), id);
			EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
			npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
			npcList.put(id, npc);
		}else {
			plugin.Log("&4Could not execute createNPC on '"+id+"' as it already exists.");
		}
	}

	@Override
	public void spawnNPC(String id) {
		if(npcList.containsKey(id)) {
			EntityPlayer npc = npcList.get(id);
			for(Player player : Bukkit.getOnlinePlayers()) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
				connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
				connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
			}
		}else {
			plugin.Log("&4Could not execute spawnNPC on '"+id+"' as it was not found.");
		}
	}

	@Override
	public void updateNPC(String id) {
		if(npcList.containsKey(id)) {
			EntityPlayer npc = npcList.get(id);
			for(Player player : Bukkit.getOnlinePlayers()) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
			}
		}else {
			plugin.Log("&4Could not execute spawnNPC on '"+id+"' as it was not found.");
		}
	}

	@Override
	public void jumpNPC(String id) {
		if(npcList.containsKey(id)) {
			EntityPlayer npc = npcList.get(id);
			Player player = npc.getBukkitEntity();
			Location loc = player.getLocation().add(0,0.7,0);
			teleportNPC(id, loc, false);
			new BukkitRunnable(){
				public void run() {
					Location loc = player.getLocation().add(0,0,0);
					teleportNPC(id, loc, false);
				}
			}.runTaskLaterAsynchronously(plugin, 5L);
		}else {
			plugin.Log("&4Could not execute crouchNPC on '"+id+"' as it was not found.");
		}
	}
	@Override
	public void crouchNPC(String id) {
		if(npcList.containsKey(id)) {
			EntityPlayer npc = npcList.get(id);
			npc.setSneaking(true);
			updateNPC(id);
		}else {
			plugin.Log("&4Could not execute crouchNPC on '"+id+"' as it was not found.");
		}
	}

	@Override
	public void uncrouchNPC(String id) {
		if(npcList.containsKey(id)) {
			EntityPlayer npc = npcList.get(id);
			npc.setSneaking(false);
			updateNPC(id);
		}else {
			plugin.Log("&4Could not execute crouchNPC on '"+id+"' as it was not found.");
		}
	}

	@Override
	public void attackNPC(String id) {
		if(npcList.containsKey(id)) {
			EntityPlayer npc = npcList.get(id);
			for(Player player : Bukkit.getOnlinePlayers()) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutAnimation(npc, (byte)0));
			}
		}else {
			plugin.Log("&4Could not execute spawnNPC on '"+id+"' as it was not found.");
		}
	}

	@Override
	public void teleportNPC(String id, Location loc, Boolean onGround) {
		if(npcList.containsKey(id)) {
			EntityPlayer npc = npcList.get(id);
			int x = MathHelper.floor(loc.getX() * 32.0D);
			int y = MathHelper.floor(loc.getY() * 32.0D);
			int z = MathHelper.floor(loc.getZ() * 32.0D);
			for(Player player : Bukkit.getOnlinePlayers()) {
				PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutEntityTeleport(npc.getId(), x, y, z, (byte) 0, (byte) 0, onGround));
			}
		}else {
			plugin.Log("&4Could not execute spawnNPC on '"+id+"' as it was not found.");
		}
	}
	
	@Override
	public boolean existsNPC(String id) {
		if(npcList.containsKey(id)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void InitConfig() {
		String worldName = plugin.getConfig().getString("NPC.Default.world");
		if(Bukkit.getWorld(worldName) != null) {
			world = ((CraftWorld) Bukkit.getWorld(worldName)).getHandle();
			bukkitWorld = Bukkit.getWorld(worldName);
		}else {
			world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
			bukkitWorld = Bukkit.getWorlds().get(0);
			plugin.Log("&4The world '"+worldName+"' was not found, defaulting to "+Bukkit.getWorlds().get(0).getName());
		}
		
		
		String xyz = plugin.getConfig().getString("NPC.Default.xyz");
		Double cX = Double.parseDouble(xyz.split(",")[0]);
		Double cY = Double.parseDouble(xyz.split(",")[1]);
		Double cZ = Double.parseDouble(xyz.split(",")[2]);
		
		location = new Location(bukkitWorld, cX+0.5,cY,cZ+0.5,0,0);
	}
	
}
