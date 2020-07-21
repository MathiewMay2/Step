package ca.cobiy.step.Libs;

import org.bukkit.Location;

public interface NPC {

	public void createNPC(String id);
	
	public void spawnNPC(String id);
	
	public void updateNPC(String id);
	
	public void jumpNPC(String id);
	
	public void crouchNPC(String id);
	
	public void uncrouchNPC(String id);
	
	public void attackNPC(String id);
	
	public void teleportNPC(String id, Location loc, Boolean onGround);
	
	public boolean existsNPC(String id);
}
