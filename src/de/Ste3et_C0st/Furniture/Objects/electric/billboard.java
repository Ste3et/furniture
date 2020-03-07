package de.Ste3et_C0st.Furniture.Objects.electric;

import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class billboard extends Furniture {
    public billboard(ObjectID id) {
        super(id);
        if (isFinish()) {
            return;
        }
        spawn(id.getStartLocation());
    }

    public void spawn(Location location) {
        List<fArmorStand> asList = new ArrayList<>();
        Location center = getLutil().getCenter(location).add(0, -1.2, 0);
        Location center2 = getLutil().getRelative(center, getBlockFace(), 0D, -4D);
        Location center3 = getLutil().getRelative(center, getBlockFace(), 0D, -3.3D);

        for (int i = 0; i <= 3; i++) {
            Location loc = getLutil().getRelative(center.clone(), getBlockFace(), -.1, -.5).add(0, .88 * i, 0);
            loc.setYaw(loc.getYaw() + 90);
            fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
            packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
            packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
            asList.add(packet);
        }

        for (int i = 0; i <= 3; i++) {
            Location loc = getLutil().getRelative(center2.clone(), getBlockFace(), -.1, -.5).add(0, .88 * i, 0);
            loc.setYaw(loc.getYaw() + 90);
            fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
            packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
            packet.setPose(new EulerAngle(1.39, 0, 0), BodyPart.RIGHT_ARM);
            asList.add(packet);
        }

        for (int i = 0; i <= 4; i++) {
            Location loc = getLutil().getRelative(center3.clone(), getBlockFace(), -.1, .88 * i).add(0, .7D, 0);
            loc.setYaw(loc.getYaw() + 90);
            fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
            packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
            packet.setPose(new EulerAngle(-.17, 0, 0), BodyPart.RIGHT_ARM);
            asList.add(packet);
        }

        for (int i = 0; i <= 4; i++) {
            Location loc = getLutil().getRelative(center3.clone(), getBlockFace(), -.1, .88 * i).add(0, 2.9D, 0);
            loc.setYaw(loc.getYaw() + 90);
            fArmorStand packet = getManager().createArmorStand(getObjID(), loc);
            packet.getInventory().setItemInMainHand(new ItemStack(Material.STICK));
            packet.setPose(new EulerAngle(-.17, 0, 0), BodyPart.RIGHT_ARM);
            asList.add(packet);
        }
        List<Block> blockLocation = new ArrayList<>();
        for (int x = 0; x <= 1; x++) {
            for (int y = 0; y <= 2; y++) {
                Location loc = getLutil().getRelative(location, getBlockFace(), 0D, (double) -y - 1).add(0, x + 1, 0);
                Location loc2 = getLutil().getRelative(location, getBlockFace(), -1D, (double) -y - 1).add(0, x + 1, 0);
                loc.getBlock().setType(Material.BARRIER);
                ItemFrame frame = getWorld().spawn(loc2, ItemFrame.class);
                frame.setFacingDirection(getBlockFace());
                blockLocation.add(loc.getBlock());
            }
        }
        getObjID().addBlock(blockLocation);
        for (fArmorStand pack : asList) {
            pack.setInvisible(true);
            pack.setBasePlate(false);
        }
        send();
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
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
    public void onClick(Player p) {
    }
}
