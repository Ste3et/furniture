package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.ColorType;
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class tent_1 extends Furniture implements Listener{

	Block block;
	public tent_1(ObjectID id){
		super(id);
		Location loc = id.getStartLocation();
		if(getBlockFace().equals(BlockFace.WEST)){loc=getLutil().getRelativ(loc, getBlockFace(), 1D, 0D);}
		if(getBlockFace().equals(BlockFace.NORTH)){loc=getLutil().getRelativ(loc, getBlockFace(), 1D, 1D);}
		if(getBlockFace().equals(BlockFace.EAST)){loc=getLutil().getRelativ(loc, getBlockFace(), 0D, 1D);}
		if(id.isFinish()){
			setblock();
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(loc);
	}
	
	public void setblock(){
		Location blockLocation = new Location(getWorld(), getLocation().getBlockX(), getLocation().getBlockY(), getLocation().getBlockZ());
		if(getBlockFace().equals(BlockFace.SOUTH)){
			this.block = blockLocation.getWorld().getBlockAt(getLutil().getRelativ(blockLocation, getBlockFace(), 2D, -2D));
			this.block.setType(Material.WORKBENCH);
		}else if(getBlockFace().equals(BlockFace.WEST)){
			this.block = blockLocation.getWorld().getBlockAt(getLutil().getRelativ(blockLocation, getBlockFace(), 2D, -2D));
			this.block.setType(Material.WORKBENCH);
		}else if(getBlockFace().equals(BlockFace.NORTH)){
			this.block = blockLocation.getWorld().getBlockAt(getLutil().getRelativ(blockLocation, getBlockFace(), 2D, -2D));
			this.block.setType(Material.WORKBENCH);
		}else if(getBlockFace().equals(BlockFace.EAST)){
			this.block = blockLocation.getWorld().getBlockAt(getLutil().getRelativ(blockLocation, getBlockFace(), 2D, -2D));
			this.block.setType(Material.WORKBENCH);
		}
		getObjID().addBlock(Arrays.asList(block));
	}
	
	
	private Location getNew(Location loc, BlockFace b){
		if(b.equals(BlockFace.WEST)){loc=getLutil().getRelativ(loc, b, -1D, 0D);}
		if(b.equals(BlockFace.NORTH)){loc=getLutil().getRelativ(loc, b, -1D, -1D);}
		if(b.equals(BlockFace.EAST)){loc=getLutil().getRelativ(loc, b, 0D, -1D);}
		return loc;
	}
	
	public void spawn(Location loc){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		Location loc_1 = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		Location loc_2 = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		Location loc_3 = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		
		Location karabine1 = getNew(getLutil().getRelativ(loc, getBlockFace(), 0D, 0D), getBlockFace());
		Location karabine2 = getNew(getLutil().getRelativ(loc, getBlockFace(), 3D, 0D), getBlockFace());
		Location karabine3 = getNew(getLutil().getRelativ(loc, getBlockFace(), 3D, -4D), getBlockFace());
		Location karabine4 = getNew(getLutil().getRelativ(loc, getBlockFace(), 0D, -4D), getBlockFace());
		
		
		karabine1 = getLutil().getCenter(karabine1);
		karabine2 = getLutil().getCenter(karabine2);
		karabine3 = getLutil().getCenter(karabine3);
		karabine4 = getLutil().getCenter(karabine4);
		karabine1.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
		karabine2.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
		karabine3.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
		karabine4.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
		
		Location saveLoc = getLutil().getRelativ(loc_1, getBlockFace(), -.55D, -0.6);
		saveLoc.add(0,-1.25,0);
		saveLoc.setYaw(getLutil().FaceToYaw(getBlockFace()) -90);
		
		Location saveLoc2 = getLutil().getRelativ(loc_2, getBlockFace(), -4.27, -4.4);
		saveLoc2.add(0,-1.25,0);
		saveLoc2.setYaw(getLutil().FaceToYaw(getBlockFace()) -90);
		
		Location saveLoc3 = getLutil().getRelativ(loc_3, getBlockFace(), -8D, -2.5D);
		saveLoc3.add(0,.64,0);
		saveLoc3.setYaw(getLutil().FaceToYaw(getBlockFace()) -90);
		Double d = .0;

		setblock();
		
		for(int i = 0; i<=5;i++){
			Location loc1= getLutil().getRelativ(saveLoc, getBlockFace(), d, 0D);
			Location loc2= getLutil().getRelativ(saveLoc, getBlockFace(), d, -.48).add(0,.3,0);
			Location loc3= getLutil().getRelativ(saveLoc, getBlockFace(), d, -.86).add(0,.81,0);
			Location loc4= getLutil().getRelativ(saveLoc, getBlockFace(), d, -1.08).add(0,1.33,0);
			Location loc5= getLutil().getRelativ(saveLoc, getBlockFace(), d, -1.38).add(0,1.86,0);
			
			loc1.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
			loc2.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
			loc3.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
			loc4.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
			loc5.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
			
			loc.add(loc1);
			loc.add(loc2);
			loc.add(loc3);
			loc.add(loc4);
			loc.add(loc5);
			
			fArmorStand asp = getManager().createArmorStand(getObjID(), loc1);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc2);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc3);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc4);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.7), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc5);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.7), BodyPart.HEAD);
			aspList.add(asp);
			d+=.62;
		}

		for(int i = 0; i<=5;i++){
			Location loc1= getLutil().getRelativ(saveLoc2, getBlockFace(), d, .02D);
			Location loc2= getLutil().getRelativ(saveLoc2, getBlockFace(), d, .48).add(0,.3,0);
			Location loc3= getLutil().getRelativ(saveLoc2, getBlockFace(), d, .86).add(0,.81,0);
			Location loc4= getLutil().getRelativ(saveLoc2, getBlockFace(), d, 1.08).add(0,1.33,0);
			Location loc5= getLutil().getRelativ(saveLoc2, getBlockFace(), d, 1.38).add(0,1.86,0);
			
			loc1.setYaw(getLutil().FaceToYaw(getBlockFace()));
			loc2.setYaw(getLutil().FaceToYaw(getBlockFace()));
			loc3.setYaw(getLutil().FaceToYaw(getBlockFace()));
			loc4.setYaw(getLutil().FaceToYaw(getBlockFace()));
			loc5.setYaw(getLutil().FaceToYaw(getBlockFace()));
			
			loc.add(loc1);
			loc.add(loc2);
			loc.add(loc3);
			loc.add(loc4);
			loc.add(loc5);
			
			fArmorStand asp = getManager().createArmorStand(getObjID(), loc1);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc2);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc3);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.2), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc4);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -7.7), BodyPart.HEAD);
			aspList.add(asp);
			asp = getManager().createArmorStand(getObjID(), loc5);
			asp.getInventory().setHelmet(new ItemStack(Material.CARPET));
			asp.setPose(new EulerAngle(0, 0, -.7), BodyPart.HEAD);
			aspList.add(asp);
			d+=.62;
		}
		
		//middle
		for(int i = 0; i<=5;i++){
			Location loc1= getLutil().getRelativ(saveLoc3, getBlockFace(), d, 0D);
			loc1.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));
			loc.add(loc1);
			
			fArmorStand asp = getManager().createArmorStand(getObjID(), loc1);
			asp.getInventory().setHelmet( new ItemStack(Material.WOOD_STEP));
			aspList.add(asp);

			d+=.62;
		}
		
		fArmorStand asp = getManager().createArmorStand(getObjID(), karabine1.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		asp = getManager().createArmorStand(getObjID(), karabine2.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		asp = getManager().createArmorStand(getObjID(), karabine3.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		asp = getManager().createArmorStand(getObjID(), karabine4.add(0,-1.9,0));
		asp.getInventory().setHelmet(new ItemStack(Material.TRIPWIRE_HOOK));
		aspList.add(asp);
		
		Location crafting = getLutil().getCenter(block.getLocation());
		crafting.setYaw(getLutil().FaceToYaw(getBlockFace())+90);
		
		asp = getManager().createArmorStand(getObjID(), crafting.add(0,-1,0).clone());
		asp.getInventory().setHelmet(new ItemStack(Material.LADDER));
		aspList.add(asp);
		
		asp = getManager().createArmorStand(getObjID(), crafting.add(0,.62,0).clone());
		asp.getInventory().setHelmet(new ItemStack(Material.LADDER));
		aspList.add(asp);
		
		for(fArmorStand packet : aspList){
			packet.setInvisible(true);
			packet.setGravity(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		block.setType(Material.AIR);
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		final Player p = e.getPlayer();
		if(!p.getItemInHand().getType().equals(Material.INK_SACK)){
			p.openWorkbench(this.block.getLocation(), true);
		}else{
			getLib().getColorManager().color(p, e.canBuild(), Material.CARPET, getObjID(), ColorType.BLOCK, 1);
		}
	}
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getBlock()==null){return;}
		if(block==null){return;}
		if(!e.getBlock().equals(block)){return;}
		if(!getLib().canBuild(e.getPlayer(), getObjID(), EventType.BREAK)){return;}
		if(this.block!=null&&e.getBlock().equals(block)){this.block.setType(Material.AIR);this.block=null;}
		this.getObjID().remove(e.getPlayer());
		delete();
	}
}
