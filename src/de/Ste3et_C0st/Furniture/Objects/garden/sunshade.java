package de.Ste3et_C0st.Furniture.Objects.garden;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class sunshade extends Furniture{

	public boolean isRunning = false;
	Integer timer;
	
	public sunshade(ObjectID id) {
		super(id);
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
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		ItemStack is = player.getInventory().getItemInMainHand();
		if(is.getType().name().contains("BANNER")){
			if(canBuild(player)) {
			for(fEntity packet : getfAsList()){
				if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().name().contains("BANNER")){
					packet.getInventory().setHelmet(is.clone());
				}else if(packet.getInventory().getHelmet()!=null&&packet.getInventory().getHelmet().getType().name().contains("CARPET")){
					ItemStack item = new ItemStack(Material.valueOf(FurnitureHook.isNewVersion() ? "WHITE_CARPET" : "CARPET"));
					packet.getInventory().setHelmet(item);
				}
			}
			update();
			consumeItem(player);
			}
			return;
		}else {
			if(canInteract(player)){
				if(!isOpen()){
					open();
				}else{
					close();
				}
			}
		}
	}

	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			this.destroy(player);
			if(isRunning) stopTimer();
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

	@Override
	public void spawn(Location location) {}
}