package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import de.Ste3et_C0st.Furniture.Main.main;

public class GetBlocks {	
	public HashMap<Integer, HashMap<Integer, Byte>> returnBlocks(Location location, int höhe, int links){
		HashMap<Integer, HashMap<Integer, Byte>> blockList = new HashMap<Integer, HashMap<Integer, Byte>>();
		BlockFace b = main.getLocationUtil().yawToFace(location.getYaw());
		höhe = höhe/2;
		//links = links/2;
		location = main.getLocationUtil().getRelativ(location, b, 0D,- (double) (links/2));
		Integer tiefe = 24;
		int layer = 0;
		for(int x = 0;x<=tiefe;x++){
			HashMap<Integer, Byte> layerlist = new HashMap<Integer, Byte>();
			Integer blockindex = 0;
			for(int y = 0;y<=links;y++){
				for(int z = 0;z<=höhe;z++){
					Block b1 = main.getLocationUtil().getRelativ(location, b,(double) x, (double) y).add(0,z,0).getBlock();
					byte byte1 = getByteFromBlock(b1);
					layerlist.put(blockindex, byte1);
					blockindex++;
					layerlist.put(blockindex, byte1);
					blockindex++;
				}
			}
			blockList.put(layer, layerlist);
			layer++;
		}
		return blockList;
	}
	
	@SuppressWarnings("deprecation")
	public Byte getByteFromBlock(Block b){
		Material m = b.getType();
		Integer subID = (int) b.getData();
		switch (m){
		//green
		case GRASS:return (byte) 4;
		case MYCEL:return (byte) 4;
		//yellow
		case SAND: return (byte) 8;
		case GRAVEL: return (byte) 8;
		case SOUL_SAND: return (byte) 8;
		case SANDSTONE: return (byte) 8;
		//Light Gray
		case STAINED_CLAY: return (byte) 12;
		case SPONGE : return (byte) 12;
		case BED_BLOCK: return (byte) 12;
		case WEB: return (byte) 12;
		//--
		case GOLD_BLOCK: return (byte) 13;
		case IRON_BLOCK: return (byte) 13;
		case DIAMOND_BLOCK: return (byte) 13;
		case IRON_DOOR_BLOCK: return (byte) 13;
		case IRON_BARDING: return (byte) 13;
		case BREWING_STAND: return (byte) 13;
		case CAULDRON: return (byte) 13;
		//RED
		case TNT: return (byte) 16;
		case LAVA: return (byte) 16;
		//BLUE
		case ICE: return (byte) 21;
		case PACKED_ICE: return (byte) 20;
		case LAPIS_BLOCK: return (byte) 100;
		//GREEN DARK
		case SAPLING: return (byte) 28;
		case LEAVES: return (byte) 28;
		case LEAVES_2: return (byte) 28;
		case LONG_GRASS: return (byte) 28;
		case DEAD_BUSH: return (byte) 28;
		case RED_ROSE: return (byte) 28;
		case YELLOW_FLOWER: return (byte) 28;
		case DOUBLE_PLANT: return (byte) 28;
		case RED_MUSHROOM: return (byte) 28;
		case BROWN_MUSHROOM: return (byte) 28;
		case WHEAT: return (byte) 28;
		case CACTUS: return (byte) 28;
		case SUGAR_CANE_BLOCK: return (byte) 28;
		case PUMPKIN: return (byte) 28;
		case JACK_O_LANTERN: return (byte) 28;
		case MELON_BLOCK: return (byte) 28;
		case PUMPKIN_STEM: return (byte) 28;
		case MELON_STEM: return (byte) 28;
		case VINE: return (byte) 28;
		case WATER_LILY: return (byte) 28;
		case NETHER_WARTS: return (byte) 28;
		case DRAGON_EGG: return (byte) 28;
		//WHITE
		case WOOL: switch (subID) {
			case 0: return (byte) 34;
			case 1: return (byte) 62;
			case 2: return (byte) 66;
			case 3: return (byte) 70;
			case 4: return (byte) 122;
			case 5: return (byte) 78;
			case 6: return (byte) 82;
			case 7: return (byte) 15;
			case 8: return (byte) 14;
			case 9: return (byte) 92;
			case 10: return (byte) 99;
			case 11: return (byte) 51;
			case 12: return (byte) 107;
			case 13: return (byte) 31;
			case 14: return (byte) 19;
			case 15: return (byte) 119;
			default: return (byte) 34;
			}
		case SNOW: return (byte) 34;
		case SNOW_BLOCK: return (byte) 34;
		//GRAY
		case CLAY: return (byte) 36;
		case MONSTER_EGG: return (byte) 36;
		//Borwn
		case DIRT: return (byte) 53;
		//Farmlandcase F: return (byte) 9;
		//GRAY
		case STONE: return (byte) 56;
		case COBBLESTONE: return (byte) 56;
		case BEDROCK: return (byte) 56;
		case GOLD_ORE: return (byte) 56;
		case IRON_ORE: return (byte) 56;
		case COAL_BLOCK: return (byte) 56;
		case LAPIS_ORE: return (byte) 56;
		case DISPENSER: return (byte) 56;
		case DROPPER: return (byte) 56;
		case PISTON_STICKY_BASE: return (byte) 56;
		case PISTON_BASE: return (byte) 56;
		case OBSIDIAN: return (byte) 56;
		case MOB_SPAWNER: return (byte) 56;
		case DIAMOND_ORE: return (byte) 56;
		case FURNACE: return (byte) 56;
		case STONE_PLATE: return (byte) 56;
		case REDSTONE_ORE: return (byte) 56;
		case NETHERRACK: return (byte) 56;
		case STONE_SLAB2: return (byte) 56;
		case BRICK: return (byte) 56;
		case BRICK_STAIRS: return (byte) 56;
		case NETHER_BRICK: return (byte) 56;
		case NETHER_BRICK_STAIRS: return (byte) 56;
		case NETHER_FENCE: return (byte) 56;
		case ENCHANTMENT_TABLE: return (byte) 56;
		case ENDER_STONE: return (byte) 56;
		case SMOOTH_BRICK: return (byte) 56;
		case SMOOTH_STAIRS: return (byte) 56;
		//Blue
		case WATER: return (byte) 68;
		//Brown
		case LOG: switch (subID) {
		case 0: return (byte) 43;
		case 1: return (byte) 52;
		case 2: return (byte) 57;
		case 3: return (byte) 60;
		default: return (byte) 43;
		}
		case LOG_2: switch (subID) {
		case 0:return (byte) 44;
		case 1:return (byte) 55;
		default:return (byte) 44;
		}
		//Brown
		case WOOD_PLATE: return (byte) 40;
		case WOOD: switch (subID) {
		case 0:return (byte) 42;
		case 1:return (byte) 40;
		case 2:return (byte) 8;
		case 3:return (byte) 41;
		case 4:return (byte) 62;
		case 5:return (byte) 104;
		default:return (byte) 42;
		}
		case NOTE_BLOCK: return (byte) 40;
		case BOOKSHELF: return (byte) 40;
		case WOOD_STAIRS: switch (subID) {
		case 0:return (byte) 42;
		case 1:return (byte) 40;
		case 2:return (byte) 8;
		case 3:return (byte) 41;
		default:return (byte) 42;
		}
		case CHEST: return (byte) 40;
		case WOOD_DOUBLE_STEP: return (byte) 40;
		case WOOD_STEP: return (byte) 42;
		case ACACIA_STAIRS:switch (subID) {
		case 0:return (byte) 62;
		case 1:return (byte) 104;
		default:return (byte) 104;
		}
		case JUKEBOX: return (byte) 40;
		case FENCE: switch (subID) {
		case 0:return (byte) 42;
		case 1:return (byte) 40;
		case 2:return (byte) 8;
		case 3:return (byte) 41;
		default:return (byte) 42;
		}
		case ACACIA_FENCE:switch (subID) {
		case 0:return (byte) 62;
		case 1:return (byte) 104;
		default:return (byte) 104;
		}
		case WOOD_DOOR: return (byte) 42;
		case SPRUCE_DOOR: return (byte) 40;
		case BIRCH_DOOR: return (byte) 8;
		case JUNGLE_DOOR: return (byte) 41;
		case ACACIA_DOOR:return (byte) 62;
		case DARK_OAK_DOOR: return (byte) 104;
		case TRAP_DOOR: return (byte) 40;
		default: return (byte) 0;
		}
	}
}
