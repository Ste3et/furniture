package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;

public class graveStone extends Furniture implements Listener{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Plugin plugin;

	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public graveStone(FurnitureLib lib, Plugin plugin, ObjectID id){
		super(lib, plugin, id);
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(id.getStartLocation().getYaw());
		this.loc = id.getStartLocation().getBlock().getLocation();
		this.loc.setYaw(id.getStartLocation().getYaw());
		this.w = id.getStartLocation().getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		this.obj = id;
		if(id.isFinish()){
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
		Location location = loc.clone();
		if(b.equals(BlockFace.WEST)){location = lutil.getRelativ(location, b, .0, -1.02);}
		if(b.equals(BlockFace.SOUTH)){location = lutil.getRelativ(location, b, -1.0, -1.02);}
		if(b.equals(BlockFace.EAST)){location = lutil.getRelativ(location, b, -1.0, .0);}
		Location center = lutil.getRelativ(location, getBlockFace(), .18D, .955D);
		center.setYaw(lutil.FaceToYaw(b.getOppositeFace()) + 90);
		Location kreutz2 = lutil.getRelativ(center, getBlockFace(), -.23, -1.27);
		Location sign = lutil.getRelativ(kreutz2.getBlock().getLocation(), b, 0D, 1D);
		this.signLoc = sign;
		
		if(!sign.getBlock().getType().equals(Material.WALL_SIGN)){
			this.sign = lutil.setSign(b, sign);
		}else{
			this.sign = sign.getBlock();
		}
		
		this.lines = getText();
	}
	
	public void spawn(Location location){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		if(b.equals(BlockFace.WEST)){location = lutil.getRelativ(location, b, .0, -1.02);}
		if(b.equals(BlockFace.SOUTH)){location = lutil.getRelativ(location, b, -1.0, -1.02);}
		if(b.equals(BlockFace.EAST)){location = lutil.getRelativ(location, b, -1.0, .0);}
		Location center = lutil.getRelativ(location, getBlockFace(), .18D, .955D);
		center.add(0,-.8,0);
		center.setYaw(lutil.FaceToYaw(b.getOppositeFace()) + 90);
		Location kreutz1 = lutil.getRelativ(center, getBlockFace(), -.55, .0);
		kreutz1.add(0, 1.5, 0);
		kreutz1.setYaw(lutil.FaceToYaw(b.getOppositeFace()) + 90);
		
		Location kreutz2 = lutil.getRelativ(center, getBlockFace(), -.23, -1.27);
		kreutz2.add(0, 1.6, 0);
		kreutz2.setYaw(lutil.FaceToYaw(b.getOppositeFace()));

		Location loc = center;
		
		for(int i = 0; i<=2;i++){
			Location l = lutil.getRelativ(loc, b, .0, -.44*i);
			
			l.setYaw(lutil.FaceToYaw(b.getOppositeFace()) + 90);
			ArmorStandPacket as = manager.createArmorStand(obj, l);
			as.getInventory().setHelmet(new ItemStack(Material.COBBLESTONE));
			as.setSmall(true);
			as.setPose(lutil.degresstoRad(new EulerAngle(0, 0, 90)), BodyPart.HEAD);
			aspList.add(as);
		}
		
		Double l = .3;
		for(int i = 0;i<=2;i++){
			setRow(center, .38, l, -.35,2,lutil.degresstoRad(new EulerAngle(90, 0, 0)), aspList);
			if(i!=1){
				l+=.4;
			}else{
				l+=.35;
			}
			
		}
		
		ArmorStandPacket as = manager.createArmorStand(obj, kreutz1);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.38,.0,.0), BodyPart.RIGHT_ARM);
		aspList.add(as);
		as = manager.createArmorStand(obj, kreutz2);
		as.getInventory().setItemInHand(new ItemStack(Material.STICK));
		as.setPose(new EulerAngle(1.38,1.57,1.57), BodyPart.RIGHT_ARM);
		aspList.add(as);
		
		for(ArmorStandPacket asp : aspList){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e){
		if(obj==null){return;}
		if(e.isCancelled()) return;
		if(!e.canBuild()){return;}
		e.remove();
		sign.setType(Material.AIR);
		sign = null;
		obj=null;
	}
	
	@EventHandler
	private void onBlockRemove(BlockBreakEvent e)
	{
	  if(obj==null){return;}
	  if (sign==null) return;
	  if (e.getBlock() == null) return;
	  if (e.getBlock().getLocation() == null) return;
	  if (e.getBlock().getLocation().toVector().distance(signLoc.toVector())==0) return;
	  resetSign();
	}
	
	@EventHandler
	private void onBlockPlaceEvent(BlockPlaceEvent e){
		  if(obj==null){return;}
		  if (sign==null) return;
		  if (e.getBlock() == null) return;
		  if (e.getBlock().getLocation() == null) return;
		  if (e.getBlock().getLocation().toVector().distance(signLoc.toVector())!=1) return;
		  resetSign();
	}
	
	  @EventHandler
	  private void onDrop(ItemSpawnEvent e){
		  if(obj==null){return;}
		  if (sign==null) return;
		  if (e.getLocation() == null) return;
		  ItemStack is = e.getEntity().getItemStack();
		  if (is == null) return;
		  if (!is.getType().equals(Material.SIGN)) return;
		  if (e.getLocation().toVector().distance(signLoc.toVector())>=2) return;
		  e.getEntity().remove();
	  }
	  
	
	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e){
		if(obj==null){return;} 
		Player p = e.getPlayer();
		if(e.isCancelled()) return;
		if(!e.getID().equals(obj)) return;
		if(!e.canBuild()){return;}
		ItemStack is = p.getItemInHand();
		if (is == null) return;
		if (!is.getType().equals(Material.WRITTEN_BOOK)) return;
		readFromBook(is);
	}

	public void resetSign(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				sign = lutil.setSign(b, signLoc);
				placetext();
			}
		});
	}
	
	public void removeSign(){
		if(sign!=null){
			sign.setType(Material.AIR);
			sign = null;
			manager.remove(obj);
			obj=null;
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
		Sign sign = (Sign) this.sign.getState();
		Integer i = 0;
		for(String s : lines){
			if(i>3){break;}
			sign.setLine(i, s);
			i++;
		}
		sign.update(true, false);
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
	
	public void setRow(Location loc, double x,double y, double z, int replay, EulerAngle angle, List<ArmorStandPacket> list){
		Double d = .0;
		for(int i = 0; i<=replay;i++){
			Location loc1= lutil.getRelativ(loc, b, z, d-.825);
			loc1.setYaw(lutil.FaceToYaw(b));
			loc1.add(0, y,0);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc1);
			packet.getInventory().setHelmet(new ItemStack(Material.STONE_PLATE));
			packet.setSmall(true);
			packet.setPose(angle, BodyPart.HEAD);
			list.add(packet);
			d+=x;
		}
	}
	
}
