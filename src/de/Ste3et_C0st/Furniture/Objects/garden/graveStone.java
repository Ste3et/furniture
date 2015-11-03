package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;

public class graveStone extends Furniture implements Listener{
	public graveStone(Plugin plugin, ObjectID id){
		super(plugin, id);
		if(isFinish()){
			setBlock();
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(id.getStartLocation());
		setBlock();
	}
	
	Location signLoc;
	Block sign;
	String[] lines = new String[4];
	
	private void setBlock(){
		Location location = getLocation().clone();
		if(getBlockFace().equals(BlockFace.WEST)){location = getLutil().getRelativ(location, getBlockFace(), .0, -1.02);}
		if(getBlockFace().equals(BlockFace.SOUTH)){location = getLutil().getRelativ(location, getBlockFace(), -1.0, -1.02);}
		if(getBlockFace().equals(BlockFace.EAST)){location = getLutil().getRelativ(location, getBlockFace(), -1.0, .0);}
		Location center = getLutil().getRelativ(location, getBlockFace(), .18D, .955D);
		center.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()) + 90);
		Location kreutz2 = getLutil().getRelativ(center, getBlockFace(), -.23, -1.27);
		Location sign = getLutil().getRelativ(kreutz2.getBlock().getLocation(), getBlockFace(), 0D, 1D);
		this.signLoc = sign;
		
		if(!sign.getBlock().getType().equals(Material.WALL_SIGN)){
			this.sign = getLutil().setSign(getBlockFace(), sign);
		}else{
			this.sign = sign.getBlock();
		}
		this.lines = getText();
		getObjID().addBlock(Arrays.asList(this.sign));
	}
	
	public void spawn(Location location){
		List<fArmorStand> aspList = new ArrayList<fArmorStand>();
		if(getBlockFace().equals(BlockFace.WEST)){location = getLutil().getRelativ(location, getBlockFace(), .0, -1.02);}
		if(getBlockFace().equals(BlockFace.SOUTH)){location = getLutil().getRelativ(location, getBlockFace(), -1.0, -1.02);}
		if(getBlockFace().equals(BlockFace.EAST)){location = getLutil().getRelativ(location, getBlockFace(), -1.0, .0);}
		Location center = getLutil().getRelativ(location, getBlockFace(), .18D, .955D);
		center.add(0,-.8,0);
		center.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()) + 90);
		Location kreutz1 = getLutil().getRelativ(center, getBlockFace(), -.55, .0);
		kreutz1.add(0, 1.5, 0);
		kreutz1.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()) + 90);
		
		Location kreutz2 = getLutil().getRelativ(center, getBlockFace(), -.23, -1.27);
		kreutz2.add(0, 1.6, 0);
		kreutz2.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()));

		Location loc = center;
		
		for(int i = 0; i<=2;i++){
			Location l = getLutil().getRelativ(loc, getBlockFace(), .0, -.44*i);
			
			l.setYaw(getLutil().FaceToYaw(getBlockFace().getOppositeFace()) + 90);
			fArmorStand as = getManager().createArmorStand(getObjID(), l);
			as.getInventory().setHelmet(new ItemStack(Material.COBBLESTONE));
			as.setSmall(true);
			as.setPose(getLutil().degresstoRad(new EulerAngle(0, 0, 90)), BodyPart.HEAD);
			aspList.add(as);
		}
		
		Double l = .3;
		for(int i = 0;i<=2;i++){
			setRow(center, .38, l, -.35,2,getLutil().degresstoRad(new EulerAngle(90, 0, 0)), aspList);
			if(i!=1){
				l+=.4;
			}else{
				l+=.35;
			}
			
		}
		
		fArmorStand as = getManager().createArmorStand(getObjID(), kreutz1);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.38,.0,.0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		as = getManager().createArmorStand(getObjID(), kreutz2);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.38,1.57,1.57), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		for(fArmorStand asp : aspList){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(getObjID()==null){return;}
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		e.remove();
		sign.setType(Material.AIR);
		sign = null;
		delete();
		removeSign();
	}
	
	@EventHandler
	private void onPhysiks(BlockPhysicsEvent e){
		 if(getObjID()==null){return;}
		  if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		  if (sign==null) return;
		  if (e.getBlock() == null) return;
		  if (!e.getBlock().equals(sign)) return;
		  e.setCancelled(true);
	}
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(getObjID()==null){return;} 
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)){return;}
		Player p = e.getPlayer();
		if(e.isCancelled()) return;
		if(!e.getID().equals(getObjID())) return;
		if(!e.canBuild()){return;}
		ItemStack is = p.getItemInHand();
		if (is == null) return;
		if (!is.getType().equals(Material.WRITTEN_BOOK)) return;
		readFromBook(is);
	}

	public void resetSign(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
			@Override
			public void run() {
				sign = getLutil().setSign(getBlockFace(), signLoc);
				placetext();
			}
		});
	}
	
	public void removeSign(){
		if(sign!=null){
			sign.setType(Material.AIR);
			sign = null;
			getManager().remove(getObjID());
			delete();
		}
	}
	
	public void readFromBook(ItemStack is){
		BookMeta bm = (BookMeta) is.getItemMeta();
		if(bm == null){return;}
		String side = bm.getPage(1);
		if(side==null){return;}
		String lines[] = side.split("\\r?\\n");
		
		Integer line = 0;
		for(String s : lines){
			if(s!=null && line<=3){
				Integer i = 15;
				if(s.length()>=15){i=15;}else{i=s.length();}
				String a = s.substring(0, i);
				if(a!=null){
					a = ChatColor.translateAlternateColorCodes('&', a);
					setText(line, a);
				}
				line++;
			}
		}
		
		if(line!=3){
			for(int i = line; i<=3; i++){
				setText(i, "");
			}
		}
		return;
	}
	
	public void placetext(){
		if ((this.sign.getState() instanceof Sign) && lines != null){
			Sign sign = (Sign) this.sign.getState();
			Integer i = 0;
			for(String s : lines){
				if(i>3){break;}
				sign.setLine(i, s);
				i++;
			}
			sign.update(true, false);
		}
	}
	
	public String[] getText(){
		if(sign==null || !sign.getType().equals(Material.WALL_SIGN)){return null;}
		Sign sign = (Sign) this.sign.getState();
		return sign.getLines();
	}
	
	public void setText(Integer line, String text){
		if(line==null || text == null){return;}
		if(sign==null || !sign.getType().equals(Material.WALL_SIGN)){return;}
		Sign sign = (Sign) this.sign.getState();
		sign.setLine(line, text);
		sign.update(true, false);
		lines[line] = text;
	}
	
	public void setRow(Location loc, double x,double y, double z, int replay, EulerAngle angle, List<fArmorStand> list){
		Double d = .0;
		for(int i = 0; i<=replay;i++){
			Location loc1= getLutil().getRelativ(loc, getBlockFace(), z, d-.825);
			loc1.setYaw(getLutil().FaceToYaw(getBlockFace()));
			loc1.add(0, y,0);
			fArmorStand packet = getManager().createArmorStand(getObjID(), loc1);
			packet.getInventory().setHelmet(new ItemStack(Material.STONE_PLATE));
			packet.setSmall(true);
			packet.setPose(angle, BodyPart.HEAD);
			list.add(packet);
			d+=x;
		}
	}
	
}
