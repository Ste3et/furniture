package de.Ste3et_C0st.Furniture.Objects.outdoor;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.ColorType;
import de.Ste3et_C0st.FurnitureLib.main.Type.DyeColor;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;

public class tent_1 extends Furniture{

	public tent_1(ObjectID id){
		super(id);
	}
	
	@Override
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			if(FurnitureHook.isNewVersion()) {
				if(DyeColor.getDyeColor(player.getInventory().getItemInMainHand().getType()) != null){
					getLib().getColorManager().color(player, true, "_CARPET", getObjID(), ColorType.BLOCK, 1);
					return;
				}
			}else {
				if(player.getInventory().getItemInMainHand().getType().name().equalsIgnoreCase("INK_SACK")){
					getLib().getColorManager().color(player, true, "CARPET", getObjID(), ColorType.BLOCK, 1);
					return;
				}
			}
		}
		player.openWorkbench(null, true);
	}
	
	@Override
	public void onBreak(Player player) {
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		if(canBuild(player)) {
			this.destroy(player);
		}
	}

	@Override
	public void spawn(Location location) {}
}
