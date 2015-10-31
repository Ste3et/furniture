package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.ColorType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class tent_3 extends Furniture implements Listener{
	public tent_3(Plugin plugin, ObjectID id){
		super(plugin, id);
		setBlock();
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		Location loc = getLocation();
		if(getBlockFace().equals(BlockFace.WEST)){loc=getLutil().getRelativ(loc, getBlockFace(), 1D, 0D);}
		if(getBlockFace().equals(BlockFace.NORTH)){loc=getLutil().getRelativ(loc, getBlockFace(), 1D, 1D);}
		if(getBlockFace().equals(BlockFace.EAST)){loc=getLutil().getRelativ(loc, getBlockFace(), 0D, 1D);}
		
		spawn(loc);
	}
	
	Block bed;
	
	public void spawn(Location loc){
		List<fArmorStand> aspL = new ArrayList<fArmorStand>();
		ItemStack banner = new ItemStack(Material.BANNER);
		BannerMeta meta = (BannerMeta) banner.getItemMeta();
		meta.setBaseColor(DyeColor.WHITE);
		banner.setItemMeta(meta);
		
		Location locstart = getLutil().getRelativ(loc, getBlockFace(), .2D, -.17D);
		locstart.add(0,.65,0);
		
		Location locstart2 = getLutil().getRelativ(loc, getBlockFace(), .2D, -.8D);
		locstart2.add(0,.65,0);
		
		for(int i = 0; i<=2;i++){
			Location location = getLutil().getRelativ(locstart, getBlockFace(), .74*i, 0D);
			location.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
			fArmorStand as = getManager().createArmorStand(getObjID(), location);
			as.getInventory().setHelmet(banner);
			as.setPose(new EulerAngle(3.5, 0, 0), BodyPart.HEAD);
			if(i==2){as.setMarker(false);}
			aspL.add(as);

			location = getLutil().getRelativ(locstart, getBlockFace(), .74*i, -.3D);
			location.add(0,.9,0);
			location.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
			
			as = getManager().createArmorStand(getObjID(), location);
			as.getInventory().setHelmet(banner);
			as.setPose(new EulerAngle(3.5, 0, 0), BodyPart.HEAD);
			if(i==2){as.setMarker(false);}
			aspL.add(as);
		}
		
		for(int i = 0; i<=2;i++){
			Location location = getLutil().getRelativ(locstart2, getBlockFace(), .74*i, 0D);
			location.setYaw(getLutil().FaceToYaw(getBlockFace())-90);
			
			fArmorStand as = getManager().createArmorStand(getObjID(), location);
			as.getInventory().setHelmet(banner);
			as.setPose(new EulerAngle(3.5, 0, 0), BodyPart.HEAD);
			if(i==2){as.setMarker(false);}
			aspL.add(as);
			
			location = getLutil().getRelativ(locstart2, getBlockFace(), .74*i, .32D);
			location.add(0,.9,0);
			location.setYaw(getLutil().FaceToYaw(getBlockFace())-90);
			as = getManager().createArmorStand(getObjID(), location);
			as.getInventory().setHelmet(banner);
			as.setPose(new EulerAngle(3.5, 0, 0), BodyPart.HEAD);
			if(i==2){as.setMarker(false);}
			aspL.add(as);
		}
		
		banner = new ItemStack(Material.BANNER);
		meta = (BannerMeta) banner.getItemMeta();
		meta.setBaseColor(DyeColor.WHITE);
		meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_SMALL));
		banner.setItemMeta(meta);
		
		Location banner1 = getLutil().getRelativ(loc, getBlockFace(), 1.7D, -.1D);
		banner1.add(0,-1.2,0);
		Location banner2 = getLutil().getRelativ(banner1, getBlockFace(), 0D, -.75);
		banner1.setYaw(getLutil().FaceToYaw(getBlockFace()));
		banner2.setYaw(getLutil().FaceToYaw(getBlockFace()));
		
		fArmorStand as = getManager().createArmorStand(getObjID(), banner1);
		as.getInventory().setHelmet(banner);
		as.setPose(new EulerAngle(-1.568, 0, 0), BodyPart.HEAD);
		as.setMarker(false);
		aspL.add(as);
		
		as = getManager().createArmorStand(getObjID(), banner2);
		as.getInventory().setHelmet(banner);
		as.setPose(new EulerAngle(-1.568, 0, 0), BodyPart.HEAD);
		as.setMarker(false);
		aspL.add(as);
		
		Location sit = getLutil().getCenter(loc);
		
		if(getBlockFace().equals(BlockFace.WEST)){sit=getLutil().getRelativ(sit, getBlockFace(), -1D, 0D);}
		if(getBlockFace().equals(BlockFace.NORTH)){sit=getLutil().getRelativ(sit, getBlockFace(), -1D, -1D);}
		if(getBlockFace().equals(BlockFace.EAST)){sit=getLutil().getRelativ(sit, getBlockFace(), 0D, -1D);}
		
		sit.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		Location locationsit = getLutil().getRelativ(sit, getBlockFace(), 1D, 0D);
		locationsit.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		
		as = getManager().createArmorStand(getObjID(), locationsit.add(0,-2,0));
		as.setName("#SITZ#");
		aspL.add(as);
		
		for(fArmorStand packet : aspL){
			packet.setInvisible(true);
			packet.setGravity(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	private void setBlock(){
		Location sit = getLutil().getCenter(getLocation());
		sit.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
		bed = getLutil().setHalfBed(getBlockFace(), getLutil().getRelativ(sit.add(0,-2,0).getBlock().getLocation().add(0,2,0), getBlockFace(), 2D, 0D));
		getObjID().addBlock(Arrays.asList(bed));
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		bed.setType(Material.AIR);
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		e.setCancelled(true);
		if(!e.canBuild()){return;}
		Player p = e.getPlayer();
		if(p.getItemInHand().getType().equals(Material.INK_SACK)){
			getLib().getColorManager().color(p, e.canBuild(), Material.BANNER, getObjID(), ColorType.BANNER, 1);
		}else{
			for(fArmorStand packet : getManager().getfArmorStandByObjectID(getObjID())){
				if(packet.getName().equalsIgnoreCase("#SITZ#")){
					packet.setPassanger(p);
					packet.update();
				}
			}
		}
	}
}
