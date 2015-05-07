package de.Ste3et_C0st.Furniture.Model;

import java.util.HashMap;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;
import de.Ste3et_C0st.Furniture.Main.Utils;

public class ModelListener implements Listener {

	private HashMap<Player, Integer> degressLevel = new HashMap<Player, Integer>();
	private HashMap<Player, Double> scaleList = new HashMap<Player, Double>();
	private HashMap<Player, ModelTimer> modelTimerList = new HashMap<Player, ModelTimer>();
	
	@EventHandler
	public void onMouseWeel(PlayerItemHeldEvent e){
		Vector v1 = new Vector(0,0,0);
		if(ModelManager.getModelCreator().isInManager(e.getPlayer())){
			Integer index = ModelManager.getModelCreator().getModelCreator(e.getPlayer()).getActive();
			ModelCreator mc = ModelManager.getModelCreator().getModelCreator(e.getPlayer());
			Modele model = mc.getModel();
			ArmorStand as = mc.getArmorStand(index);
			if(as==null){return;}
			Player p = e.getPlayer();
			Double scale = 1D;
			if(scaleList.containsKey(p)){scale = scaleList.get(p);}
			if(!p.isSneaking()){
				BlockFace face1 = Utils.yawToFace(p.getLocation().getYaw(), p.getLocation().getPitch());
				BlockFace face2 = Utils.yawToFaceRadial(as.getLocation().getYaw());
				HeadArmType type = mc.getType(index);
				EulerAngle angle = mc.getEuler(index, type);
				ItemStack is = mc.getItemStack(index);
				
				if(((e.getPreviousSlot()-e.getNewSlot()>=0) || (e.getPreviousSlot()==0&&e.getNewSlot()==8)) && !(e.getPreviousSlot()==8&&e.getNewSlot()==0)){
					v1 = Utils.getRelativ(as.getLocation().toVector(), -1D * scale, face1);
					mc.editArmorStand(v1, type, angle, is, as.isVisible(), as.isSmall(), index, face1, face2);
				}else{
					v1 = Utils.getRelativ(as.getLocation().toVector(), 1D * scale, face1);
					mc.editArmorStand(v1, type, angle, is, as.isVisible(), as.isSmall(), index, face1, face2);
				}
			}else{
				HeadArmType type = mc.getType(index);
				EulerAngle angle = mc.getEuler(index, type);
				ItemStack is = mc.getItemStack(index);
				BlockFace face1 = Utils.yawToFace(p.getLocation().getYaw());
				BlockFace face3 = Utils.yawToFace(as.getLocation().getYaw());
				EulerAngle oldEular = mc.getEuler(index, type);
				if(!degressLevel.containsKey(p)){degressLevel.put(p, 0);}
				if(Utils.yawToFace(p.getLocation().getYaw(), p.getLocation().getPitch()).equals(BlockFace.UP)){return;}
				if(Utils.yawToFace(p.getLocation().getYaw(), p.getLocation().getPitch()).equals(BlockFace.DOWN)){return;}
				v1=as.getLocation().toVector();
				if(degressLevel.get(p).equals(0)){
					if(((e.getPreviousSlot()-e.getNewSlot()>=0) || (e.getPreviousSlot()==0&&e.getNewSlot()==8)) && !(e.getPreviousSlot()==8&&e.getNewSlot()==0)){
						BlockFace face2 = Utils.yawToFaceRadial(as.getLocation().getYaw() + 45);
						mc.editArmorStand(v1, type, angle, is, as.isVisible(), as.isSmall(), index, face1, face2);
					}else{
						BlockFace face2 = Utils.yawToFaceRadial(as.getLocation().getYaw() - 45);
						mc.editArmorStand(v1, type, angle, is, as.isVisible(), as.isSmall(), index, face1, face2);
					}
				}else if(degressLevel.get(p).equals(1)){
					if(((e.getPreviousSlot()-e.getNewSlot()>=0) || (e.getPreviousSlot()==0&&e.getNewSlot()==8)) && !(e.getPreviousSlot()==8&&e.getNewSlot()==0)){
						EulerAngle newEular = oldEular.add(.2*scale, 0, 0);
						mc.editArmorStand(v1, type, newEular, is, as.isVisible(), as.isSmall(), index, face1, face3);
					}else{
						EulerAngle newEular = oldEular.add(-.2*scale, 0, 0);
						mc.editArmorStand(v1, type, newEular, is, as.isVisible(), as.isSmall(), index, face1, face3);
					}
				}else if(degressLevel.get(p).equals(2)){
					if(((e.getPreviousSlot()-e.getNewSlot()>=0) || (e.getPreviousSlot()==0&&e.getNewSlot()==8)) && !(e.getPreviousSlot()==8&&e.getNewSlot()==0)){
						EulerAngle newEular = oldEular.add(0, .2*scale, 0);
						mc.editArmorStand(v1, type, newEular, is, as.isVisible(), as.isSmall(), index, face1, face3);
					}else{
						EulerAngle newEular = oldEular.add(0, -.2*scale, 0);
						mc.editArmorStand(v1, type, newEular, is, as.isVisible(), as.isSmall(), index, face1, face3);
					}
				}else if(degressLevel.get(p).equals(3)){
					if(((e.getPreviousSlot()-e.getNewSlot()>=0) || (e.getPreviousSlot()==0&&e.getNewSlot()==8)) && !(e.getPreviousSlot()==8&&e.getNewSlot()==0)){
						EulerAngle newEular = oldEular.add(0, 0, .2*scale);
						mc.editArmorStand(v1, type, newEular, is, as.isVisible(), as.isSmall(), index, face1, face3);
					}else{
						EulerAngle newEular = oldEular.add(0, 0, -.2*scale);
						mc.editArmorStand(v1, type, newEular, is, as.isVisible(), as.isSmall(), index, face1, face3);
					}
				}
			}
			check(p, index, model);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent e){
		if(e.isCancelled()){return;}
		if(e.getDamager() instanceof Player == false){return;}
		if(e.getEntity() instanceof ArmorStand == false){return;}
		if(e.getEntity() == null){return;}
		if(e.getEntity().getName() == null){return;}
		Player p = (Player) e.getDamager();
		String name = e.getEntity().getName();
		if(ModelManager.getModelCreator().isInManager(p)){
			e.setCancelled(true);
			Integer index = ModelManager.getModelCreator().getModelCreator(p).getActive();
			ModelCreator mc = ModelManager.getModelCreator().getModelCreator(p);
			ArmorStand as = mc.getArmorStand(index);
			if(e.getEntity().equals(as)){
				if(modelTimerList.containsKey(p)){
					e.setDamage(0);
					e.setCancelled(true);
					p.sendMessage("ArmorStand saved #" + index);
					mc.setActiveArmorStandIndex(null);
					modelTimerList.get(p).cancleTimer();
				}
			}
			
			if(name != null && name.length()>=13){
				String[] split = name.split("-");
				if(split != null && split.length>=1){
					e.setDamage(0);
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onClickEvent(PlayerInteractEvent e){
		if(ModelManager.getModelCreator().isInManager(e.getPlayer())){
			if(e.getItem()==null){return;}
			if(e.getAction()==null){return;}
			e.setCancelled(true);
			if(!degressLevel.containsKey(e.getPlayer())){degressLevel.put(e.getPlayer(), 0);}
			ModelInventory mInv = ModelManager.getModelCreator().getModelCreator(e.getPlayer()).getModelInv();
			ItemStack is1 = e.getItem();
			ItemStack is2 = mInv.getItem();
			Integer lastMode = degressLevel.get(e.getPlayer());
			if(is1.equals(is2)){
				if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
					lastMode++;
					if(lastMode>3){lastMode=0;}
					mInv.setItem(lastMode);
				}else if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
					lastMode--;
					if(lastMode<0){lastMode=3;}
					mInv.setItem(lastMode);
				}
			}
			degressLevel.put(e.getPlayer(), lastMode);
		}
	}
	
	public void check(Player p, Integer index, Modele m){
		ModelTimer model = null;
		if(modelTimerList.containsKey(p)){
			model = modelTimerList.get(p);
			model.restartTimer();
			modelTimerList.put(p, model);
		}else{
			model = new ModelTimer(p,index, m, this);
			model.StartTimer();
		}
		modelTimerList.put(p, model);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		if(ModelManager.getModelCreator().isInManager(e.getPlayer())){
			e.setCancelled(true);
			Double scale = 1D;
			if(scaleList.containsKey(e.getPlayer())){scale = scaleList.get(e.getPlayer());}
			if(e.getPlayer().isSneaking()){
				if(scale>0.03125){
					scale = scale/2;
				}else{
					scale = 0.03125;
				}
			}else{
				if(scale<1){
					scale = scale*2;
				}else{
					scale = 1.0;
				}
			}
			
			if(scale<=0D){scale=0.03125;}
			if(scale>=1D){scale=1D;}
			
			e.getPlayer().sendMessage("Scalamode:" + scale);
			scaleList.put(e.getPlayer(), scale);
		}
	}

	public void remove(ModelTimer modelTimer, Player p) {
		ModelManager.getModelCreator().getModelCreator(p).activeArmorStandID = null;
		modelTimerList.remove(modelTimer);
	}
}
