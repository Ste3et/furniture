package de.Ste3et_C0st.Furniture.Objects.indoor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureConfig;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.ColorType;
import de.Ste3et_C0st.FurnitureLib.main.Type.DyeColor;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class largeTable extends Furniture{

	private List<Integer> tellerIDs = new ArrayList<Integer>();
	
	public largeTable(ObjectID id){
		super(id);
		for(fEntity packet : getfAsList()){
			if(packet.getName().startsWith("#TELLER")){
				tellerIDs.add(packet.getEntityID());
			}
		}
	}
	
	
	public void setTeller(HashMap<Integer, ItemStack> itemList){
		int i = 0;
		for(Integer id : tellerIDs){
			fEntity as = getManager().getByArmorStandID(getWorld(), id);
			as.getInventory().setItemInMainHand(itemList.get(i));
			i++;
		}
	}

	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			for(Integer id : tellerIDs){
				fEntity asp = getManager().getByArmorStandID(getWorld(), id);
				if(asp!=null&&asp.getInventory().getItemInMainHand()!=null){
					if(asp.getName().startsWith("#TELLER")){
						fEntity packet = asp;
						getWorld().dropItem(getLocation(), packet.getInventory().getItemInMainHand());
					}
				}
			}
			this.destroy(player);
		}
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(FurnitureHook.isNewVersion()) {
				if(DyeColor.getDyeColor(player.getInventory().getItemInMainHand().getType()) != null){
					getLib().getColorManager().color(player, canBuild(player), "STAINED_GLASS_PANE", getObjID(), ColorType.BLOCK, 3);
					update();
					return;
				}
			}else {
				if(player.getInventory().getItemInMainHand().getType().equals(Material.valueOf("INK_SACK"))){
					getLib().getColorManager().color(player, canBuild(player), "STAINED_GLASS_PANE", getObjID(), ColorType.BLOCK, 3);
					update();
					return;
				}
			}
			setTeller(player, player.getInventory().getItemInMainHand());
		}
	}
	
	public void setTeller(Player player, ItemStack is){
		fEntity as = getfAsList().stream().filter(entity -> entity.getName().startsWith("#TELLER")).sorted(Comparator.comparingDouble(p1 -> p1.getLocation().distance(player.getLocation()))).findFirst().orElse(null);
		if(as != null) {
			if(as.getInventory().getItemInMainHand()!= null && as.getInventory().getItemInMainHand().equals(is)){return;}
			if(as.getInventory().getItemInMainHand()!=null&&!as.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
				fEntity asp = as;
				ItemStack item = asp.getInventory().getItemInMainHand();
				item.setAmount(1);
				asp.getLocation().getWorld().dropItem(asp.getLocation(), item);
			}
			
			ItemStack IS = is.clone();
			if(IS.getAmount()<=0){
				IS.setAmount(0);
			}else{
				IS.setAmount(1);
			}
			as.getInventory().setItemInMainHand(IS);
			
			update();
			
			if(player.getGameMode().equals(GameMode.CREATIVE) && FurnitureConfig.getFurnitureConfig().useGamemode()) return;
			Integer i = player.getInventory().getHeldItemSlot();
			ItemStack itemstack = is.clone();
			itemstack.setAmount(itemstack.getAmount()-1);
			player.getInventory().setItem(i, itemstack);
			player.updateInventory();
		}
	}
	
	public HashMap<Integer, ItemStack> getTeller(){
		HashMap<Integer, ItemStack> teller = new HashMap<Integer, ItemStack>();
		for(Integer id : tellerIDs){
			try{
				fEntity as = getManager().getByArmorStandID(getWorld(), id);
				teller.put(teller.size(), as.getInventory().getItemInMainHand());
			}catch(Exception e){
				teller.put(teller.size(), new ItemStack(Material.AIR));
			}
		}
		return teller;
	}


	@Override
	public void spawn(Location location) {}
}
