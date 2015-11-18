package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.ColorType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class tent_2 extends Furniture implements Listener{
	Integer id;
	List<Block> block = new ArrayList<Block>();
	Location bedLoc;
	
	public tent_2(ObjectID id){
		super(id);
		Location loc = getLocation();
		if(getBlockFace().equals(BlockFace.WEST)){loc=getLutil().getRelativ(loc, getBlockFace(), 1D, 0D);}
		if(getBlockFace().equals(BlockFace.NORTH)){loc=getLutil().getRelativ(loc, getBlockFace(), 1D, 1D);}
		if(getBlockFace().equals(BlockFace.EAST)){loc=getLutil().getRelativ(loc, getBlockFace(), 0D, 1D);}

		Location loca = loc.clone();
		
		switch (getBlockFace()) {
		case NORTH: loca=getLutil().getRelativ(loca, getBlockFace(), -1D, -1D); break;
		case EAST: loca=getLutil().getRelativ(loca, getBlockFace(), 0D, -1D); break;
		case WEST: loca=getLutil().getRelativ(loca, getBlockFace(), -1D, 0D); break;
		default:break;
		}
		
		setBlock(loca);
		
		if(id.isFinish()){
			Bukkit.getPluginManager().registerEvents(this, main.getInstance());
			return;
		}
		spawn(loc);
	}
	
	
	public void spawn(Location loc){
		List<fArmorStand> aspl = new ArrayList<fArmorStand>();
		loc=getLutil().getRelativ(loc, getBlockFace(), -.91D, -0.75D);
		Location LeftLocation = loc;
		LeftLocation.add(0,-.75,0);
		Location RightLocation = getLutil().getRelativ(LeftLocation, getBlockFace(), 0D, -4.55D);
		Location MiddleLocation = getLutil().getRelativ(LeftLocation, getBlockFace(), 0D, -2.27D).add(0,2.4,0);
		Location LeftSart = getLutil().getRelativ(LeftLocation, getBlockFace(), 3.87D, -.2D);
		Location RightSart = getLutil().getRelativ(RightLocation, getBlockFace(), 3.87D, .2D);
		Double d = .0;
		
		for(int i = 0; i<=8;i++){
			Location loc1= getLutil().getRelativ(LeftLocation, getBlockFace(), d, 0D);
			loc1.setYaw(getLutil().FaceToYaw(getBlockFace()));
			fArmorStand as = getManager().createArmorStand(getObjID(), loc1);
			as.getInventory().setHelmet(new ItemStack(Material.LOG));
			as.setSmall(true);
			as.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			aspl.add(as);
			
			Location loc2= getLutil().getRelativ(RightLocation, getBlockFace(), d, 0D);
			loc2.setYaw(getLutil().FaceToYaw(getBlockFace()));
			as = getManager().createArmorStand(getObjID(), loc2);
			as.getInventory().setHelmet(new ItemStack(Material.LOG));
			as.setSmall(true);
			as.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			aspl.add(as);
			
			Location loc3= getLutil().getRelativ(MiddleLocation, getBlockFace(), d, 0D);
			loc3.setYaw(getLutil().FaceToYaw(getBlockFace()));
			
			as = getManager().createArmorStand(getObjID(), loc3);
			as.getInventory().setHelmet(new ItemStack(Material.LOG));
			as.setSmall(true);
			as.setPose(new EulerAngle(1.568, 0, 0), BodyPart.HEAD);
			aspl.add(as);

			d+=.43;
		}

		d = .0;
		Double l = -.25;
		for(int i = 0;i<=4;i++){
			setRow(LeftSart, .62, l, d, 5,new EulerAngle(0, 0, .79), aspl);
			d-=.435;
			l+=.44;
		}
		
		d = .0;
		l = -.25;
		for(int i = 0;i<=4;i++){
			setRow(RightSart, .62, l, d, 5,new EulerAngle(0, 0, -.79), aspl);
			d+=.435;
			l+=.44;
		}

		int i = 0;
		for(fArmorStand packet : aspl){
			packet.setInvisible(true);
			packet.setGravity(false);
			if(i>44&&i<57){
				packet.setMarker(false);
			}
			i++;
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@SuppressWarnings("deprecation")
	public void setBlock(Location loc){
		Location b1 = getLutil().getRelativ(loc, getBlockFace(), 1D, -2D).add(0, 0, 0);
		Location b2 = getLutil().getRelativ(loc, getBlockFace(), 2D, -3D).add(0, 0, 0);
		if(!b2.getBlock().getType().equals(Material.CHEST)){
			b2.setYaw(getLutil().FaceToYaw(getBlockFace()));
			b2.getBlock().setType(Material.CHEST);
			BlockState chest = b2.getBlock().getState(); 
			 switch (getBlockFace()){
			 
			 case SOUTH:
			 chest.setRawData((byte) 0x3);break;
			  
			 case NORTH:
			 chest.setRawData((byte) 0x2);break;
			  
			 case EAST:
			 chest.setRawData((byte) 0x5);break;
			  
			 case WEST:
			 chest.setRawData((byte) 0x4);break;
			 default: chest.setRawData((byte) 0x3);break;
			  
			 }
			chest.update(true, true);
		}

		
		
		bedLoc = getLutil().setBed(this.getBlockFace(), b1);
		getObjID().addBlock(Arrays.asList(b1.getWorld().getBlockAt(b1)));
		block.add(b1.getWorld().getBlockAt(b1));
		block.add(b2.getWorld().getBlockAt(b2));
	}
	
	public void setRow(Location loc, double x,double y, double z, int replay, EulerAngle angle, List<fArmorStand> list){
		Double d = .0;
		for(int i = 0; i<=replay;i++){
			Location loc1= getLutil().getRelativ(loc, getBlockFace(), -3.55+d, z);
			loc1.setYaw(getLutil().FaceToYaw(getBlockFace()));
			loc1.add(0, y,0);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc1);
			packet.getInventory().setHelmet(new ItemStack(Material.CARPET));
			packet.setPose(angle, BodyPart.HEAD);
			list.add(packet);
			d+=x;
		}
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.setCancelled(true);
		for(Block bl : block){
			bl.setType(Material.AIR);
		}
		e.remove();
		delete();
	}
	
	@EventHandler
	public void onFurnitureClick(final FurnitureClickEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(getObjID())){return;}
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(!e.canBuild()){return;}
		if(!p.getItemInHand().getType().equals(Material.INK_SACK)){
			
			for(Block b : block){
				if(b.getType().equals(Material.CHEST)){
					Chest c = (Chest) b.getState();
					e.getPlayer().openInventory(c.getBlockInventory());
				}
			}
		}else{
			getLib().getColorManager().color(p, e.canBuild(), Material.CARPET, getObjID(), ColorType.BLOCK, 1);
		}
	}
}
