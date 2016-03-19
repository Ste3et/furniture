package de.Ste3et_C0st.Furniture.Objects.christmas;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class CandyCane extends Furniture implements Listener {

	public CandyCane(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			return;
		}
		spawn(id.getStartLocation());
	}
	
	private String generateSessionKey(int length){
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
		int n = alphabet.length();
		String result = new String(); 
		Random r = new Random();
		for (int i=0; i<length; i++) result = result + alphabet.charAt(r.nextInt(n));
		return result;
	}
	
	private ItemStack getSkull() {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		ItemMeta headMeta = skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), generateSessionKey(10));
        Property textures = new Property(
            "textures",
            "eyJ0aW1lc3RhbXAiOjE0NDc5NDgyNDcwODQsInByb2ZpbGVJZCI6IjBlNGUzZmIwMTY4ZjRmMWI4NDM3ZjM5ZWYxNTdhZjk0IiwicHJvZmlsZU5hbWUiOiJDb29raWVNYWtlcjIwMDAiLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMDgxYWI4MDdjZDRhZTcxZWYyYjI2MzdiNWQ3MWM1ZDZjYTRjNzdjNWQ1YTQ1Mjc1MjRlNzYzZWM5ZWVlIn19fQ=="
        );
        profile.getProperties().put(textures.getName(), textures);
        
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | SecurityException e) {
           	e.printStackTrace();
        } catch (IllegalArgumentException | IllegalAccessException e) {
        	e.printStackTrace();
        }
        skull.setItemMeta(headMeta);
        return skull;
    }
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove();
		delete();
	}

	public void onFurnitureClick(FurnitureClickEvent e) {
		
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		
		ItemStack is = getSkull();
	
		double d = .0;
		double c = .0;
		for(int i = 1; i<7;i++){
			d = -2.6+ .59*i;
			Location loc = getCenter().clone().add(0,d, 0);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setHelmet(is);
			asList.add(stand);
		}
		d = d + 3.5;
		for(int i = 1; i<4;i++){
			c = -.32+.59*i;
			Location loc = getRelative(getCenter(), c, 0).add(0,-2.9+d, 0);
			fArmorStand stand = spawnArmorStand(loc);
			stand.setHelmet(is);
			asList.add(stand);
		}
		
		Location loc = getRelative(getCenter(), c+.3, 0).add(0,-3.5+d, 0);
		fArmorStand stand = spawnArmorStand(loc);
		stand.setHelmet(is);
		asList.add(stand);
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
}
