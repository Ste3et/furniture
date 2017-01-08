package de.Ste3et_C0st.Furniture.Objects.light;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBlockClickEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class WaxCandle extends Furniture{
	
	Block block;
	fEntity stand;
	
	public WaxCandle(ObjectID id){
		super(id);
		if(isFinish()){
			setBlock();
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			stand = getObjID().getPacketList().get(0);
			return;
		}
		spawn(id.getStartLocation());
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	private void setBlock(){
		Location center = getLutil().getCenter(getLocation());
		block = center.getWorld().getBlockAt(center);
		if(!block.getType().equals(Material.AIR)){getObjID().addBlock(Arrays.asList(block));return;}
		block.setType(Material.TORCH);
		getObjID().addBlock(Arrays.asList(block));
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove(true,false);
		delete();
	}
	
	@EventHandler
	public void onFurnitureBlockBreak(FurnitureBlockBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		e.remove(true,false);
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureBlockClickEvent e) {
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		if(e.getPlayer().getInventory().getItemInMainHand()==null) return;
		if(e.getPlayer().getInventory().getItemInMainHand().getType()==null) return;
		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getItemInMainHand();
		if(is.getType().equals(Material.FLINT_AND_STEEL)){
			setLight(true);
		}else if(is.getType().equals(Material.WATER_BUCKET)){
			setLight(false);
		}
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(e.getID() == null || getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!canBuild(e.getPlayer())){return;}
		if(e.getPlayer().getInventory().getItemInMainHand()==null) return;
		if(e.getPlayer().getInventory().getItemInMainHand().getType()==null) return;
		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getItemInMainHand();
		if(is.getType().equals(Material.FLINT_AND_STEEL)){
			setLight(true);
		}else if(is.getType().equals(Material.WATER_BUCKET)){
			setLight(false);
		}
	}
	
	public void setLight(final boolean bool){
		if(bool){block.setType(Material.TORCH);}
		else{block.setType(Material.REDSTONE_TORCH_OFF);}
	}
	
	@EventHandler
	public void onWaterFlow(BlockFromToEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		Location locTo = e.getToBlock().getLocation();
		if(getLocation()!=null && locTo.equals(getLocation().getBlock().getLocation())){
			e.setCancelled(true);
		}
	}

	public void spawn(Location loc) {
		setBlock();
		stand = spawnArmorStand(getCenter().subtract(0, 2, 0));
		stand.setHelmet(getSkull()).setInvisible(true);
		send();
	}
	
	private String generateSessionKey(int length){
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
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
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzgxMjI2NjU2ZDgyNTI5MWIxZDdlNDU2Yjc0ZWNkY2UyODY3MjE2OTY0MWU2YzM1YjFlMjNiOWI0MDI3NGUifX19"
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
	
}