package de.Ste3et_C0st.Furniture.Objects.garden;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectBreakEvent;
import de.Ste3et_C0st.FurnitureLib.ShematicLoader.Events.ProjectClickEvent;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureHelper;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class sunshade extends FurnitureHelper implements Listener{

	public boolean isRunning = false;
	Integer timer;
	
	public sunshade(ObjectID id) {
		super(id);
		Bukkit.getPluginManager().registerEvents(this, main.instance);
	}
	
	private boolean isOpen(fArmorStand packet){
		if(packet.getPose(BodyPart.HEAD).getX()< -1.85){
			return false;
		}return true;
	}
	
	private boolean isClose(fArmorStand packet){
		if(packet.getPose(BodyPart.HEAD).getX()> -3.054){
			return false;
		}return true;
	}
	
	@EventHandler
	public void onClick(ProjectClickEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getItemInMainHand();
		if(is.getType().name().contains("BANNER")){
			for(fEntity packet : getfAsList()){
				if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().name().contains("BANNER")){
					packet.getInventory().setHelmet(is.clone());
				}else if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().name().contains("CARPET")){
					ItemStack item = new ItemStack(Material.WHITE_CARPET);
					item.setDurability(getLutil().getFromDey((short) is.getDurability()));
					packet.getInventory().setHelmet(item);
				}
			}
			update();
			consumeItem(p);
		}else{
			if(!isOpen()){
				open();
			}else{
				close();
			}
		}
		
	}
	
	@EventHandler
	public void onBreak(ProjectBreakEvent e){
		if(e.getID() == null || getObjID() == null) return;
		if(!e.getID().equals(getObjID())){return;}
		if(!e.canBuild()){return;}
		if(isRunning){
			stopTimer();
		}
	}
	
	private void open() {
		if(isRunning) return;
		this.isRunning = true;
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
			@Override
			public void run() {
				for(fEntity packet : getfAsList()){
					if(packet.getName().startsWith("#ELEMENT#")){
						if(!isOpen((fArmorStand) packet)){
							Double x = ((fArmorStand) packet).getPose(BodyPart.HEAD).getX();
							((fArmorStand) packet).setPose(new EulerAngle(x+.02, 0, 0), BodyPart.HEAD);
						}else{
							stopTimer();
							return;
						}
					}
				}
				update();
			}
		}, 0, 1);
	}
	
	private void close() {
		if(isRunning) return;
		this.isRunning = true;
		timer = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {
			@Override
			public void run() {
				for(fEntity packet : getfAsList()){
					if(packet.getName().startsWith("#ELEMENT#")){
						if(!isClose((fArmorStand) packet)){
							Double x = ((fArmorStand) packet).getPose(BodyPart.HEAD).getX();
							((fArmorStand) packet).setPose(new EulerAngle(x-.02, 0, 0), BodyPart.HEAD);
						}else{
							stopTimer();
							return;
						}
					}
				}
				update();
			}
		}, 0, 1);
	}

	private boolean isOpen(){
		for(fEntity packet : getfAsList()){
			if(packet.getName().startsWith("#ELEMENT#")){
				if(((fArmorStand) packet).getPose(BodyPart.HEAD).getX()< -1.85){
					return false;
				}
			}
		}
		return true;
	}
	
	private void stopTimer(){
		if(timer!=null){
			Bukkit.getScheduler().cancelTask(timer);
			timer=null;
			isRunning = false;
		}
	}
}