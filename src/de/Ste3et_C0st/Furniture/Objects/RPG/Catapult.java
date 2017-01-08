package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.Events.FurnitureClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class Catapult extends FurnitureHelper implements Listener{

	private fArmorStand stand1;
	private HashMap<Entity, Player> fallingSandList = new HashMap<Entity, Player>();
	
	public Catapult(ObjectID id) {
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
		
		for(fEntity stand : id.getPacketList()){
			if(stand.getCustomName().toLowerCase().startsWith("#range")){
				stand1 = (fArmorStand) stand;
				stand1.setNameVasibility(false);
				if(stand1.getCustomName().equalsIgnoreCase("#range")){
					stand1.setName("#range:1");
				}
			}
		}
	}
	
	@EventHandler
	public void onPrimedTnt(EntityExplodeEvent e){
		if(e.getEntity()==null) return;
		if(e.getEntity() instanceof TNTPrimed){
			if(fallingSandList.containsKey(e.getEntity())){
				Entity entity = e.getEntity();
				Player p = fallingSandList.get(e.getEntity());
				fallingSandList.remove(e.getEntity());
				Block b = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
				if(!FurnitureLib.getInstance().getPermManager().canBuild(p, b.getLocation())){
					e.setCancelled(true);
					e.blockList().clear();
					e.getEntity().remove();
				}
			}
		}
	}
	
	@EventHandler
	public void FurnitureClickEvent(FurnitureClickEvent e){
		if(getObjID()==null) return;
		if(e.getID()==null) return;
		if(!e.getID().equals(getObjID())) return;
		if(e.getID().getSQLAction().equals(SQLAction.REMOVE)) return;
		Location loc = getRelative(getCenter(), getBlockFace(), -.8, -1.03).add(0, -.2, 0);
		loc.setYaw(getYaw());
		ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
		if(stack.getType().equals(Material.TNT)){
			TNTPrimed tnt = (TNTPrimed) e.getID().getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
			if(tnt == null) return;
			Vector v= getLaunchVector(getBlockFace());
			if(v == null) return;
			tnt.playEffect(EntityEffect.WITCH_MAGIC);
			tnt.setVelocity(v.multiply(1));
			fallingSandList.put(tnt, e.getPlayer());
		}else if(stack.getType().equals(Material.AIR)){
			if(stand1!=null){
				int x = -59;
				String name = "#range:1";
				switch (stand1.getName().substring(stand1.getName().length()-1, stand1.getName().length())) {
					case "1":
						name = "#range:2";
						x -= 4;
						break;
					case "2":
						name = "#range:3";
						x -= 8;
						break;
					case "3":
						name = "#range:4";
						x -= 12;
						break;
					case "4":
						name = "#range:5";
						x -= 16;
						break;
					default: break;
				}
				stand1.setName(name);
				EulerAngle angle = stand1.getLeftArmPose();
				angle = getLutil().Radtodegress(angle);
				angle = angle.setX(x);
				stand1.setLeftArmPose(getLutil().degresstoRad(angle));
				update();
			}
		}else{
				@SuppressWarnings("deprecation")
				FallingBlock block = getWorld().spawnFallingBlock(loc, stack.getType().getId(), (byte) stack.getDurability());
				if(block == null) return;
				Vector v= getLaunchVector(getBlockFace());
				if(v == null) return;
				block.playEffect(EntityEffect.WITCH_MAGIC);
				block.setDropItem(false);
				block.setVelocity(v.multiply(1));
				fallingSandList.put(block, e.getPlayer());
			
		}
		
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && FurnitureLib.getInstance().useGamemode()) return;
		Integer i = e.getPlayer().getInventory().getHeldItemSlot();
		ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
		is.setAmount(is.getAmount()-1);
		e.getPlayer().getInventory().setItem(i, is);
		e.getPlayer().updateInventory();
	}
	
	public Vector getLaunchVector(BlockFace face){
		String str = "#range:1";
		if(this.stand1!=null){str = stand1.getName().toLowerCase();}
		str = str.replace("#range:", "");
		String level = "Range" + str;
		Vector v = new Vector(0, .5, 1.2);
		if(main.catapultRange.containsKey(level)) v = main.catapultRange.get(level);
		switch (face) {
			case NORTH:v= new Vector(0, v.getY(), v.getZ());break;
			case SOUTH:v= new Vector(0, v.getY(), -v.getZ());break;
			case EAST: v= new Vector(-v.getZ(), v.getY(), 0);break;
			case WEST: v= new Vector(v.getZ(), v.getY(), 0);break;
			default:break;
		}
		return v;
	}
	

}
