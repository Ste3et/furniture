package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
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
import de.Ste3et_C0st.FurnitureLib.main.Type.EventType;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class hammock extends Furniture{

	Location loc;
	BlockFace b;
	World w;
	ObjectID obj;
	FurnitureManager manager;
	FurnitureLib lib;
	LocationUtil lutil;
	Integer id;
	Block block;
	Plugin plugin;
	
	List<Block> blockList = new ArrayList<Block>();
	
	public ObjectID getObjectID(){return this.obj;}
	public Location getLocation(){return this.loc;}
	public BlockFace getBlockFace(){return this.b;}
	
	public hammock(FurnitureLib lib, Plugin plugin, ObjectID id) {
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
		setBlock();
		if(id.isFinish()){
			Bukkit.getPluginManager().registerEvents(this, plugin);
			return;
		}
		spawn(loc);
		
	}
	
	private void setBlock(){
		BlockFace Right = lutil.yawToFace(lutil.FaceToYaw(b)+90);
		Location center2 = lutil.getRelativ(loc, Right, 6D, 0D);
		Block b1 = this.loc.getBlock();
		Block b2 = this.loc.getBlock().getRelative(BlockFace.UP);
		Block b3 = center2.getBlock();
		Block b4 = center2.getBlock().getRelative(BlockFace.UP);

		if(!isFence(b1.getType())) b1.setType(Material.FENCE);
		if(!isFence(b2.getType())) b2.setType(Material.FENCE);
		if(!isFence(b3.getType())) b3.setType(Material.FENCE);
		if(!isFence(b4.getType())) b4.setType(Material.FENCE);
		
		blockList.add(b1);
		blockList.add(b2);
		blockList.add(b3);
		blockList.add(b4);
	}
	
	private boolean isFence(Material m){
		if(m==null){return false;}
		return m.toString().toLowerCase().endsWith("fence");
	}
	
	private boolean isMaterial(Material m){
		if(m==null){return false;}
		boolean b = false;
		if(m.toString().toLowerCase().startsWith("log")){
			b=true;
		}else if(m.toString().toLowerCase().equalsIgnoreCase("banner")){
			b=true;
		}
		
		return b;
	}

	@EventHandler
	public void onFurnitureBreak(FurnitureBreakEvent e) {
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		if(!e.canBuild()){return;}
		for(Block b : blockList){
			if(b!=null){
				b.setType(Material.AIR);
			}
		}
		e.remove();
		blockList.clear();
	}

	@EventHandler
	public void onFurnitureClick(FurnitureClickEvent e) {
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(!e.getID().equals(obj)){return;}
		List<ArmorStandPacket> aspList = manager.getArmorStandPacketByObjectID(obj);
		Player p = e.getPlayer();
		ItemStack stack = p.getItemInHand();
		if(stack!=null){
			if(isMaterial(stack.getType())){
				if(setColor(p,stack, e.canBuild(), aspList)){return;}
			}
		}
		
		for(ArmorStandPacket packet : aspList){
			if(packet.getName().equalsIgnoreCase("#SITZ#")){
				if(packet.getPessanger()==null){
					packet.setPessanger(e.getPlayer());
					return;
				}
			}
		}
	}
	
	public void removeItem(Player p){
		Boolean useGameMode = FurnitureLib.getInstance().useGamemode();
		if(useGameMode&&p.getGameMode().equals(GameMode.CREATIVE)){return;}
		Integer slot = p.getInventory().getHeldItemSlot();
		ItemStack itemStack = p.getItemInHand().clone();
		itemStack.setAmount(itemStack.getAmount()-1);
		p.getInventory().setItem(slot, itemStack);
		p.updateInventory();
	}
	
	@EventHandler
	private void onClick(PlayerInteractEvent e){
		if(obj==null){return;}
		if(obj.getSQLAction().equals(SQLAction.REMOVE)){return;}
		if(e.isCancelled()){return;}
		if(e.getClickedBlock()==null){return;}
		if(e.getAction() == null){return;}
		if(e.getPlayer()==null){return;}
		if(!blockList.contains(e.getClickedBlock())){return;}
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
			e.setCancelled(true);
			if(!lib.canBuild(e.getPlayer(), obj, EventType.BREAK)){return;}
			for(Block b : blockList){
				if(b!=null){
					b.setType(Material.AIR);
				}
			}
			obj.remove(e.getPlayer());
			blockList.clear();
			return;
		}else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			e.setCancelled(true);
			List<ArmorStandPacket> aspList = manager.getArmorStandPacketByObjectID(obj);
			Player p = e.getPlayer();
			ItemStack stack = p.getItemInHand();

			if(stack!=null){
				if(isMaterial(stack.getType())){
					if(setColor(p,stack, lib.canBuild(e.getPlayer(), obj, EventType.INTERACT), aspList)){return;}
				}
			}
			
			for(ArmorStandPacket packet : aspList){
				if(packet.getName().equalsIgnoreCase("#SITZ#")){
					if(packet.getPessanger()==null){
						packet.setPessanger(e.getPlayer());
						return;
					}
				}
			}
		}
	}
	
	private boolean setColor(Player p,ItemStack stack, Boolean canbuild, List<ArmorStandPacket> aspList){
		if(!canbuild){return true;}
		if(stack!=null){
			switch (stack.getType()) {
			case BANNER:
				for(ArmorStandPacket packet : aspList){
					if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().equals(Material.BANNER)){
						packet.getInventory().setHelmet(stack);
					}
				}
				removeItem(p);
				manager.updateFurniture(obj);
				return true;
			case LOG:
				for(ArmorStandPacket packet : aspList){
					if(packet.getName().equalsIgnoreCase("#PILLAR#")){
						packet.getInventory().setHelmet(stack);
					}
				}
				setPillar(stack.getDurability());
				removeItem(p);
				manager.updateFurniture(obj);
				return true;
			case LOG_2:
				for(ArmorStandPacket packet : aspList){
					if(packet.getName().equalsIgnoreCase("#PILLAR#")){
						packet.getInventory().setHelmet(stack);
					}
				}
				setPillar(4 +  stack.getDurability());
				removeItem(p);
				manager.updateFurniture(obj);
				return true;
			default: break;
			}
		}
		return false;
	}
	
	private void setPillar(int i){
		Material m = null;
		switch (i) {
		case 0: m = Material.FENCE;break;
		case 1: m = Material.SPRUCE_FENCE;break;
		case 2: m = Material.BIRCH_FENCE;break;
		case 3: m = Material.JUNGLE_FENCE;break;
		case 4: m = Material.ACACIA_FENCE;break;
		case 5: m = Material.DARK_OAK_FENCE;break;
		}
		if(m!=null){
			for(Block b : blockList){
				b.setType(m);
			}
		}
	}

	
	public void spawn(Location location) {
		Location center1 = lutil.getCenter(location);
		center1 = center1.add(0, -1.9, 0);
		
		BlockFace Right = lutil.yawToFace(lutil.FaceToYaw(b)+90);
		Location center2 = lutil.getRelativ(center1, Right, 6D, 0D);
		List<ArmorStandPacket> aspList = new ArrayList<ArmorStandPacket>();
		
		ArmorStandPacket packet = manager.createArmorStand(obj, center1);
		packet.getInventory().setHelmet(new ItemStack(Material.LOG));
		packet.setName("#PILLAR#");
		aspList.add(packet);
		
		packet = manager.createArmorStand(obj, center2);
		packet.getInventory().setHelmet(new ItemStack(Material.LOG));
		packet.setName("#PILLAR#");
		aspList.add(packet);
		
		Location middle = lutil.getRelativ(center1, Right, 2.85D, 0D);
		middle = middle.add(0, .75, 0);
		packet = manager.createArmorStand(obj, middle);
		packet.getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 0));
		packet.setPose(lutil.degresstoRad(new EulerAngle(-69f,0f,0f)), BodyPart.HEAD);
		aspList.add(packet);
		
		Location middle2 = lutil.getRelativ(middle, Right, 1.5D, 0D);
		middle2 = middle2.add(0, .23, 0);
		packet = manager.createArmorStand(obj, middle2);
		packet.getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 0));
		packet.setPose(lutil.degresstoRad(new EulerAngle(-90f,0f,0f)), BodyPart.HEAD);
		aspList.add(packet);
		
		Location sitz = lutil.getRelativ(center1, Right, 3D, 0D).add(0, .6, 0);
		sitz.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
		packet = manager.createArmorStand(obj, sitz);
		packet.setName("#SITZ#");
		aspList.add(packet);
		
		middle = lutil.getRelativ(middle, Right, .3D, 0D);
		middle.setYaw(middle.getYaw()+180);
		packet = manager.createArmorStand(obj, middle);
		packet.getInventory().setHelmet(new ItemStack(Material.BANNER, 1, (short) 0));
		packet.setPose(lutil.degresstoRad(new EulerAngle(-69f,0f,0f)), BodyPart.HEAD);
		aspList.add(packet);
		
		Location stick = lutil.getRelativ(center1, Right, 4.6d, .5d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(lutil.FaceToYaw(b));
		packet = manager.createArmorStand(obj, stick);
		packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packet.setPose(lutil.degresstoRad(new EulerAngle(-40f,110f,0f)), BodyPart.RIGHT_ARM);
		aspList.add(packet);
		
		stick = lutil.getRelativ(center1, Right, 4.6d, -.335d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(lutil.FaceToYaw(b));
		packet = manager.createArmorStand(obj, stick);
		packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packet.setPose(lutil.degresstoRad(new EulerAngle(-40f,73f,0f)), BodyPart.RIGHT_ARM);
		packet.setArms(true);
		aspList.add(packet);
		
		stick = lutil.getRelativ(center1, Right.getOppositeFace(), -1.37d, -.335d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
		packet = manager.createArmorStand(obj, stick);
		packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packet.setPose(lutil.degresstoRad(new EulerAngle(-40f,73f,0f)), BodyPart.RIGHT_ARM);
		packet.setArms(true);
		aspList.add(packet);
		
		stick = lutil.getRelativ(center1, Right.getOppositeFace(), -1.37d, .5d);
		stick = stick.add(0, 1.7, 0);
		stick.setYaw(lutil.FaceToYaw(b.getOppositeFace()));
		packet = manager.createArmorStand(obj, stick);
		packet.getInventory().setItemInHand(new ItemStack(Material.STICK));
		packet.setPose(lutil.degresstoRad(new EulerAngle(-40f,110f,0f)), BodyPart.RIGHT_ARM);
		packet.setArms(true);
		aspList.add(packet);
		
		for(ArmorStandPacket pack : aspList){
			pack.setInvisible(true);
			pack.setGravity(false);
			pack.setBasePlate(false);
		}
		
		manager.send(obj);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
}
