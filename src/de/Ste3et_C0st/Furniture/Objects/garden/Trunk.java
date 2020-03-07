package de.Ste3et_C0st.Furniture.Objects.garden;

import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Trunk extends Furniture {

    public Trunk(ObjectID id) {
        super(id);
    }

    @Override
    public void onClick(Player player) {
        if (getObjID() == null) return;
        if (getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
        if (player == null) return;
        ItemStack stack = player.getInventory().getItemInMainHand();
        if (stack == null) return;
        if (main.materialWhiteList.contains(stack.getType())) {
            if (stack.getType().equals(Material.AIR)) return;
            for (fEntity entity : getfAsList()) {
                if (entity.getName().startsWith("#TO")) {
                    entity.setHelmet(stack);
                }
            }
            update();
        } else {
            for (fEntity entity : getfAsList()) {
                if (entity.getName().startsWith("#SITZ:")) {
                    if (entity.getPassenger() == null || entity.getPassenger().isEmpty()) {
                        entity.setPassenger(player);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void onBreak(Player player) {
        if (getObjID() == null) return;
        if (getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
        if (player == null) return;
        if (canBuild(player)) {
            this.destroy(player);
        }
    }

    @Override
    public void spawn(Location location) {
    }
}