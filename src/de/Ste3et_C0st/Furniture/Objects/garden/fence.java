package de.Ste3et_C0st.Furniture.Objects.garden;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class fence extends Furniture {
    List<Material> matList = Arrays.asList(
            Material.SPRUCE_FENCE,
            Material.BIRCH_FENCE,
            Material.JUNGLE_FENCE,
            Material.DARK_OAK_FENCE,
            Material.ACACIA_FENCE,
            Material.valueOf(FurnitureHook.isNewVersion() ? "COBBLESTONE_WALL" : "COBBLE_WALL"),
            Material.valueOf(FurnitureHook.isNewVersion() ? "NETHER_BRICK_FENCE" : "NETHER_FENCE"));

    public fence(ObjectID id) {
        super(id);
    }

    @Override
    public void onClick(Player player) {
        if (getObjID() == null) return;
        if (getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
        if (player == null) return;
        if (canBuild(player)) {
            Block b = getWorld().getBlockAt(getObjID().getBlockList().toArray(new Location[0])[0]);
            ItemStack stack = player.getInventory().getItemInMainHand();
            if (stack == null) return;
            if (stack.getType().equals(Material.AIR)) return;
            if (matList.contains(stack.getType())) {
                b.setType(stack.getType());
                consumeItem(player);
            } else if (main.materialWhiteList.contains(stack.getType())) {
                setTypes(stack);
                consumeItem(player);
                update();
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

    private void setTypes(ItemStack is) {
        for (fEntity packet : getManager().getfArmorStandByObjectID(getObjID())) {
            packet.getInventory().setHelmet(is);
        }
    }

    @Override
    public void spawn(Location location) {
    }
}