package de.Ste3et_C0st.Furniture.Objects.RPG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.NBT.CraftItemStack;
import de.Ste3et_C0st.FurnitureLib.NBT.NBTTagCompound;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class Catapult extends FurnitureHelper implements Listener{

	private fArmorStand stand1;
	private HashMap<Entity, Player> fallingSandList = new HashMap<Entity, Player>();
	private List<EntityType> entityList = Arrays.asList(EntityType.PIG, EntityType.CREEPER, EntityType.COW);
	
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
	 	public void onFallingSand(EntityChangeBlockEvent e){
	 		if(e.getEntity()==null) return;
	 		if(e.getEntity() instanceof FallingBlock){
	 			if(fallingSandList.containsKey(e.getEntity())){
	 			Entity entity = e.getEntity();
	 				Player p = fallingSandList.get(e.getEntity());
	 				fallingSandList.remove(e.getEntity());
	 				Block b = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
	 				if(!FurnitureLib.getInstance().getPermManager().canBuild(p, b.getLocation())){
	 					e.setCancelled(true);
	 					e.getEntity().remove();
	 				}
	 			}
	 		}
		}
	
	@EventHandler
	public void FurnitureClickEvent(ProjectClickEvent e){
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
			setRange(e.getPlayer());
			return;
		}else{
//			if(stack.getType().isBlock()){
//				@SuppressWarnings("deprecation")
//				FallingBlock block = getWorld().spawnFallingBlock(loc, stack.getType().getId(), (byte) stack.getDurability());
//				if(block == null) return;
//				Vector v= getLaunchVector(getBlockFace());
//				if(v == null) return;
//				block.setDropItem(false);
//				block.setVelocity(v.multiply(1));
//				fallingSandList.put(block, e.getPlayer());
//				e.getPlayer().playSound(getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, (float) getPitch());
//			}else if(stack.getType().equals(Material.MONSTER_EGG)){
//				try{
//					NBTTagCompound nbtTag = new CraftItemStack().getNBTTag(stack);
//					if(nbtTag.hasKey("tag")){
//						NBTTagCompound tag = nbtTag.getCompound("tag");
//						NBTTagCompound id = tag.getCompound("EntityTag");
//						String str = id.getString("id").replace("minecraft:", "");
//						@SuppressWarnings("deprecation")
//						EntityType type = EntityType.fromName(str);
//						if(!entityList.contains(type)){setRange(e.getPlayer());return;} 
//						Vector v= getLaunchVector(getBlockFace());
//						Entity entity = getWorld().spawnEntity(loc, type);
//						entity.setVelocity(v);
//						LivingEntity entiLivingEntity = (LivingEntity) entity;
//						entiLivingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 4), false);
//						if(type.equals(EntityType.CREEPER)){
//							if(randInt(0, 25)==7){
//								Creeper creeper = (Creeper) entity;
//								creeper.setPowered(true);
//							}
//						}else if(type.equals(EntityType.SHEEP)){
//							Sheep mob = (Sheep) entity;
//							int i = randInt(0, DyeColor.values().length);
//							mob.setColor(DyeColor.values()[i]);
//							if(randInt(0, 25)==7){
//								mob.setCustomName("jeb_");
//								mob.setCustomNameVisible(false);
//							}
//						}
//					}
//					e.getPlayer().playSound(getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, (float) getPitch());
//					return;
//				}catch(Exception ex){
//					ex.printStackTrace();
//				}
//			}else{
				setRange(e.getPlayer());
				return;
//			}
		}
		consumeItem(e.getPlayer());
	}
	
	public void setRange(Player p){
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
			p.playSound(getLocation(), Sound.UI_BUTTON_CLICK, 1, (float) getPitch());
		}
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public double getPitch(){
		String str = "#range:1";
		if(this.stand1!=null){str = stand1.getName().toLowerCase();}
		str = str.replace("#range:", "");
		if(str.equalsIgnoreCase("1")){
			return .1;
		}else if(str.equalsIgnoreCase("2")){
			return .4;
		}else if(str.equalsIgnoreCase("3")){
			return .6;
		}else if(str.equalsIgnoreCase("4")){
			return .7;
		}else if(str.equalsIgnoreCase("5")){
			return .8;
		}
		return .1;
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
