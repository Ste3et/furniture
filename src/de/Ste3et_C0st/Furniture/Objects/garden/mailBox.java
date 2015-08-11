package de.Ste3et_C0st.Furniture.Objects.garden;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureBreakEvent;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureLateSpawnEvent;
import de.Ste3et_C0st.FurnitureLib.Utilitis.LocationUtil;
import de.Ste3et_C0st.FurnitureLib.main.ArmorStandPacket;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;

public class mailBox implements Listener {

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Plugin plugin;
	List<Block> blockList = new ArrayList<Block>();
	
	private String id;
	public String getID(){return this.id;}
	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	
	public mailBox(Location location, FurnitureLib lib, String name, Plugin plugin, ObjectID id, Player player){
		this.lutil = main.getLocationUtil();
		this.b = lutil.yawToFace(location.getYaw());
		this.loc = location.getBlock().getLocation();
		this.loc.setYaw(location.getYaw());
		this.w = location.getWorld();
		this.manager = lib.getFurnitureManager();
		this.lib = lib;
		this.plugin = plugin;
		if(id!=null){
			this.obj = id;
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}else{
			this.obj = new ObjectID(name, plugin.getName(), location);
			if(player!=null){
				FurnitureLateSpawnEvent lateSpawn = new FurnitureLateSpawnEvent(player, obj, obj.getProjectOBJ(), location);
				Bukkit.getServer().getPluginManager().callEvent(lateSpawn);
			}
		}
		spawn(location);
	}
	
	public void spawn(Location location){
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		Location middle = lutil.getCenter(loc);
		middle.add(0, -1.4, 0);
		
		switch (b) {
		case NORTH:middle = lutil.getRelativ(middle, b, 0D, 0.03D);break;
		case EAST:middle = lutil.getRelativ(middle, b, 0D, 0.03D);break;
		default:break;
		}
		
		ArmorStandPacket as = manager.createArmorStand(obj, middle);
		as.getInventory().setHelmet(new ItemStack(Material.STONE));
		as.setSmall(true);
		aspList.add(as);

		for(int i = 0; i<=1;i++){
			Location loc = lutil.getRelativ(middle.clone(), b, .47, .38).add(0, .88*i, 0);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
			packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
			aspList.add(packet);
		}
		
		as = manager.createArmorStand(obj, lutil.getRelativ(middle.clone().add(0, 1.2, 0), b, -.21, 0D));
		as.getInventory().setHelmet(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0));
		as.setSmall(true);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, lutil.getRelativ(middle.clone().add(0, 1.2, 0), b, .21, 0D));
		as.getInventory().setHelmet(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0));
		as.setSmall(true);
		aspList.add(as);
		
		for(int i = 0; i<=4;i++){
			Location loc = lutil.getRelativ(middle.clone().add(0, 1.898, 0), b, -.44+.165*i, .43D);
			ArmorStandPacket packet = manager.createArmorStand(obj, loc);
			packet.setSmall(true);
			//x z y
			//-.7 .73 -.3
			packet.setPose(new EulerAngle(-.716D, .71D, -.32D), BodyPart.RIGHT_ARM);
			packet.getInventory().setItemInHand(new ItemStack(Material.SMOOTH_STAIRS));
			aspList.add(packet);
			
			loc = lutil.getRelativ(middle.clone().add(0, 1.898, 0), b.getOppositeFace(), -.44+.165*i, .462D);
			packet = manager.createArmorStand(obj, loc);
			packet.setSmall(true);
			//x z y
			//-.7 .73 -.3
			packet.setPose(new EulerAngle(-.716D, .71D, -.32D), BodyPart.RIGHT_ARM);
			packet.getInventory().setItemInHand(new ItemStack(Material.SMOOTH_STAIRS));
			aspList.add(packet);
			
			
			loc = lutil.getRelativ(middle.clone().add(0, 1.898, 0), b.getOppositeFace(), -.44+.165*i, .362D);
			packet = manager.createArmorStand(obj, loc);
			packet.setSmall(true);
			//x z y
			//-.7 .73 -.3
			packet.setPose(new EulerAngle(-.716D, .71D, -.32D), BodyPart.RIGHT_ARM);
			packet.getInventory().setItemInHand(new ItemStack(Material.SMOOTH_BRICK, 1 ,(short) 0));
			aspList.add(packet);
		}
		
		BlockFace face = b;
		face = lutil.yawToFace(lutil.FaceToYaw(b) + 90);
		as = manager.createArmorStand(obj, lutil.getRelativ(middle.clone().add(0, 1.5, 0), face, -.21, -.32D));
		as.getInventory().setHelmet(new ItemStack(Material.REDSTONE_TORCH_ON, 1, (short) 0));
		as.setPose(lutil.degresstoRad(new EulerAngle(0, 0, 90)), BodyPart.HEAD);
		as.setSmall(true);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, lutil.getRelativ(middle.clone().add(0, 1.10, 0), face.getOppositeFace(), -.0, .31D));
		as.getInventory().setItemInHand(new ItemStack(Material.PAPER, 1, (short) 0));
		as.setPose(lutil.degresstoRad(new EulerAngle(0, -120, -90)), BodyPart.RIGHT_ARM);
		as.setSmall(true);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, lutil.getRelativ(middle.clone().add(0, 1.40, 0), face.getOppositeFace(), -.1, .34D));
		as.getInventory().setItemInHand(new ItemStack(Material.WOOD_BUTTON, 1, (short) 0));
		as.setPose(lutil.degresstoRad(new EulerAngle(-15,-67, -90)), BodyPart.RIGHT_ARM);
		as.setSmall(true);
		aspList.add(as);
		
		as = manager.createArmorStand(obj, lutil.getRelativ(middle.clone().add(0, 1.2, 0), b, .2, .07D));
		as.getInventory().setItemInHand(new ItemStack(Material.EMPTY_MAP, 1, (short) 0));
		as.setPose(lutil.degresstoRad(new EulerAngle(0, -120, -90)), BodyPart.RIGHT_ARM);
		as.setSmall(true);
		aspList.add(as);
		
		
		for(ArmorStandPacket asp : aspList){
			asp.setGravity(false);
			asp.setInvisible(true);
			asp.setBasePlate(false);
		}
		
		for(int i = 0; i<=1;i++){
			Block b = location.clone().add(0, i, 0).getBlock();
			b.setType(Material.BARRIER);
			blockList.add(b);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	/*public void addMailbox(Player p) throws MailboxException{
		if(Bukkit.getPluginManager().isPluginEnabled("PostalService")){
			PostalService.getMailboxManager().addMailboxAtLoc(blockList.get(1).getLocation(), p);
		}
	}*/
	
	@EventHandler
	private void onBreak(FurnitureBreakEvent e){
		if(e.isCancelled()) return;
		if(!e.canBuild()) return;
		if(!e.getID().equals(obj)) return;
		if(obj==null){return;}
		for(Block b : blockList){
			b.setType(Material.AIR);
		}
		blockList.clear();
		e.remove();
		obj=null;
	}
	
	@EventHandler
	private void onInteract(PlayerInteractEvent e){
		if(obj==null){return;}
		if(e.isCancelled()){return;}
		if(e.getAction()==null){return;}
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
			if(blockList.contains(e.getClickedBlock())){
				e.setCancelled(true);
				for(Block b : blockList){
					b.setType(Material.AIR);
				}
				blockList.clear();
				this.obj.remove(e.getPlayer());
				obj=null;
			}
		}
	}
}
