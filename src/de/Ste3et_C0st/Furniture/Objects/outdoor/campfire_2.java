package de.Ste3et_C0st.Furniture.Objects.outdoor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.FurnitureHook;
import de.Ste3et_C0st.Furniture.Main.main;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureConfig;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.BodyPart;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class campfire_2 extends Furniture {
	private List<Material> items = new ArrayList<Material>(Arrays.asList(
			Material.valueOf(FurnitureHook.isNewVersion() ? "BEEF" : "RAW_BEEF"),
			Material.valueOf(FurnitureHook.isNewVersion() ? "CHICKEN" : "RAW_CHICKEN"),
			Material.valueOf(FurnitureHook.isNewVersion() ? "COD" : "RAW_FISH"), Material.POTATO,
			Material.valueOf(FurnitureHook.isNewVersion() ? "PORKCHOP" : "PORK"), Material.RABBIT, Material.MUTTON));

	private EulerAngle[] angle = { new EulerAngle(-1.5, -.5, 0), new EulerAngle(-1.9, -.3, .7),
			new EulerAngle(-1.5, .3, 1.9), new EulerAngle(-0.7, -.5, -1.2)

	};

	private Location middle;
	private Location grill;
	private Integer timer;
	private fArmorStand armorS;
	private ItemStack is;

	public campfire_2(ObjectID id) {
		super(id);
		this.middle = getLutil().getCenter(getLocation());
		this.middle = getLutil().getRelative(middle, getBlockFace(), .5D, -.5D);
		this.middle.add(0, -1.2, 0);

		this.grill = getLutil().getRelative(middle, getBlockFace(), .0D, .5D);
		this.grill.setYaw(getLutil().FaceToYaw(getBlockFace()) + 90);
		if (id.isFinish()) {
			return;
		}
		spawn(id.getStartLocation());
	}

	public void spawn(Location loc) {
	}

	@Override
	public void onClick(Player player) {
		if (getObjID() == null)
			return;
		if (getObjID().getSQLAction().equals(SQLAction.REMOVE))
			return;
		if (player == null)
			return;
		if (canBuild(player, false)) {
			HashSet<fEntity> aspList = getObjID().getPacketList();
			final ItemStack itemStack = player.getInventory().getItemInMainHand();
			fArmorStand packet = null;
			for (fEntity pack : aspList) {
				if (pack instanceof fArmorStand) {
					fArmorStand stand = (fArmorStand) pack;
					if (stand.isSmall() && pack.isInvisible()) {
						packet = stand;
						if (packet.isFire()) {
							break;
						}
					}
				}
			}
			if (itemStack.getType().equals(Material.WATER_BUCKET) && packet.isFire()) {
				setfire(false);
			} else if (itemStack.getType().equals(Material.FLINT_AND_STEEL) && !packet.isFire()) {
				setfire(true);
			}
		}
		
		if(canInteract(player, false)) {
			final ItemStack itemStack = player.getInventory().getItemInMainHand();
			
			fArmorStand packet = null;
			HashSet<fEntity> aspList = getObjID().getPacketList();
			for (fEntity pack : aspList) {
				if (pack instanceof fArmorStand) {
					fArmorStand stand = (fArmorStand) pack;
					if (stand.isSmall() && pack.isInvisible()) {
						packet = stand;
						if (packet.isFire()) {
							break;
						}
					}
				}
			}
			if(Objects.isNull(packet)) return;
			if (items.contains(itemStack.getType()) && packet.isFire() && armorS == null) {
				is = itemStack.clone();
				is.setAmount(1);

				setGrill();

				if (player.getGameMode().equals(GameMode.CREATIVE) && FurnitureConfig.getFurnitureConfig().useGamemode())
					return;
				Integer i = player.getInventory().getHeldItemSlot();
				ItemStack item = player.getInventory().getItemInMainHand();
				item.setAmount(item.getAmount() - 1);
				player.getInventory().setItem(i, item);
				player.updateInventory();
			}
		}
	}

	private void setfire(boolean b) {
		Location loc = getCenter().clone();
		loc.add(0, 1.3, 0);
		if (b) {
			getLib().getLightManager().addLight(loc, 15);
		} else {
			getLib().getLightManager().removeLight(loc);
		}

		for (fEntity entity : getfAsList()) {
			if (entity instanceof fArmorStand) {
				fArmorStand stand = (fArmorStand) entity;
				if ((stand.getInventory().getHelmet() == null
						|| stand.getInventory().getHelmet().getType().equals(Material.AIR))
						&& (stand.getInventory().getItemInMainHand() == null
								|| stand.getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
					if (stand.isSmall() && entity.isInvisible()) {
						stand.setFire(b);
					}
				}
			}
		}
		update();
	}

	@Override
	public void onBreak(Player player) {
		if (getObjID() == null)
			return;
		if (getObjID().getSQLAction().equals(SQLAction.REMOVE))
			return;
		if (player == null)
			return;
		if (canBuild(player)) {
			if (isRunning()) {
				Bukkit.getScheduler().cancelTask(timer);
				timer = null;
				getWorld().dropItemNaturally(middle.clone().add(0, 1, 0), is).setVelocity(new Vector().zero());
			}
			setfire(false);
			this.destroy(player);
		}
	}

	public void removeGrill() {
		if (isRunning()) {
			Bukkit.getScheduler().cancelTask(timer);
			timer = null;
			if (armorS != null && armorS.getInventory().getItemInMainHand() != null
					&& getItem(armorS.getInventory().getItemInMainHand()) != null) {
				getWorld().dropItemNaturally(middle.clone().add(0, 1, 0), getCooked(is))
						.setVelocity(new Vector().zero());
				getObjID().getPacketList().remove(this.armorS);
				armorS.kill();
				armorS.delete();
				armorS = null;
			}
		}
		if (armorS != null) {
			if (armorS.getInventory().getItemInMainHand() != null) {
				getWorld().dropItemNaturally(middle.clone().add(0, 1, 0), getCooked(is))
						.setVelocity(new Vector().zero());
			}
			getObjID().getPacketList().remove(this.armorS);
			armorS.kill();
			armorS.delete();
			armorS = null;
		}
		update();
	}

	public ItemStack getItem(ItemStack is) {
		if (is == null) {
			return null;
		}
		if (is.getType() == null) {
			return null;
		}
		if (items.contains(is.getType())) {
			return is;
		}
		return null;
	}

	public ItemStack getCooked(ItemStack is) {
		if (is == null) {
			return null;
		}
		if (is.getType() == null) {
			return null;
		}
		if (items.contains(is.getType())) {
			Material mat = null;
			int rnd = (int) (Math.random() * 100);
			if (rnd < 5) {
				mat = Material.COAL;
			} else {
				Iterator<Recipe> recipes = Bukkit.recipeIterator();
				while (recipes.hasNext()) {
					Recipe recipe = recipes.next();
					if(Objects.isNull(recipe)) continue;
					if (recipe instanceof FurnaceRecipe == false) continue;
					FurnaceRecipe frecipe = (FurnaceRecipe) recipe;
					if (frecipe.getInput().getType().equals(is.getType())) {
						return frecipe.getResult();
					}
				}
			}
			return Objects.nonNull(mat) ? new ItemStack(mat) : is;
		}
		return is;
	}

	public boolean isRunning() {
		if (timer == null) {
			return false;
		}
		return true;
	}

	public void setGrill() {
		this.armorS = getManager().createArmorStand(getObjID(), grill);
		this.armorS.setInvisible(true);
		this.armorS.getInventory().setItemInMainHand(is);
		getObjID().getPlayerList().clear();
		send();
		this.timer = main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(main.getInstance(),
				new Runnable() {
					Integer rounds = getLutil().randInt(15, 30);
					Integer labs = 0;
					Integer position = 0;

					@Override
					public void run() {
						if (labs >= rounds) {
							removeGrill();
							return;
						}
						if (position > 3) {
							position = 0;
						}
						if (armorS != null) {
							armorS.setPose(angle[position], BodyPart.RIGHT_ARM);
							update();
						}
						position++;
						labs++;
					}
				}, 0, 4);
	}
}
