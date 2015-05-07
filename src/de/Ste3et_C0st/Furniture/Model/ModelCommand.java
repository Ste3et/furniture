package de.Ste3et_C0st.Furniture.Model;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.Ste3et_C0st.Furniture.Main.Type.HeadArmType;
import de.Ste3et_C0st.Furniture.Main.Utils;

public class ModelCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str1,String[] args) {
		if(sender instanceof Player == false)return true;
		if(!cmd.getName().equalsIgnoreCase("Model")) return false;
		Player p = (Player) sender;
		String help = "§3/model create <§6ID§3> §7| §9Create a new Model Object\n";
		help+="§3/model add <§6ARM/HEAD§3> <§6MaterialID:subID/hand§3> <§6visible§3> <§6mini§3>\n";
		help+="§3/model edit <§6INDEX§3> §7| §9go back to this ArmorStand edit mode\n";
		help+="§3/model remove <§6INDEX§3> §7| §9you delete this armorstand\n";
		help+="§3/model delete <§6ID§3> §7| §9delete the model with all armorstands\n";
		help+="§3/model setvisible <§6INDEX§3> <§6true§3/§6false> | §9change the visible\n";
		help+="§3/model setitem <§6INDEX§3> <§6MaterialID:subID/hand§3> | §9change Item\n";
		help+="§3/model setype <§6INDEX§3> <§6ARM/HEAD§3> | §9change ArmorStand Type\n";
		help+="§3/model save";
		help+="§3/model killall <§6radius,ModelID§3>";
		if(!p.hasPermission("furniture.admin") && !p.isOp()){return true;}
		if(args.length==0){
			p.sendMessage(help);
			return true;
		}else if(args.length==2){
			if(args[0].equalsIgnoreCase("create")){
				if(ModelManager.getModelCreator().isInManager(p)){p.sendMessage("You already in the ModelCreatorMode");return true;}
				if(ModelManager.getModelCreator().getModelByName(args[1])!=null){p.sendMessage("Model Already exist");return true;}
				ModelManager.getModelCreator().enterModelCreator(p, args[1]);
				p.sendMessage("You enterd the Model Creator for model: " + args[1]);
				return true;
			}if(args[0].equalsIgnoreCase("edit")){
				if(!ModelManager.getModelCreator().isInManager(p)){p.sendMessage("You are not in the edit Mode");return true;}
				if(!Utils.isInt(args[1])){p.sendMessage(help);return true;}
				ModelCreator modelC = ModelManager.getModelCreator().getModelCreator(p);
				if(modelC.activeArmorStandID!=null){p.sendMessage("You have an unsaved armorstand");return true;}
				Integer index = Integer.parseInt(args[1]);
				if(modelC.getArmorStand(index)==null){p.sendMessage("ArmorStand does not exist"); return true;}
				modelC.setActiveArmorStandIndex(index);
				p.sendMessage("You can edit the ArmorStand #" + index);return true;
			}else if(args[0].equalsIgnoreCase("remove")){
				if(!ModelManager.getModelCreator().isInManager(p)){p.sendMessage("You are not in the edit Mode");return true;}
				if(!Utils.isInt(args[1])){p.sendMessage(help);return true;}
				ModelCreator modelC = ModelManager.getModelCreator().getModelCreator(p);
				if(modelC.activeArmorStandID!=null){p.sendMessage("You have an unsaved armorstand");return true;}
				Integer index = Integer.parseInt(args[1]);
				if(modelC.getArmorStand(index)==null){p.sendMessage("ArmorStand does not exist"); return true;}
				modelC.removeArmorStand(index);
				p.sendMessage("You have remove the ArmorStand #" + index);return true;
				
			}else{
				p.sendMessage(help);return true;
			}
		}else if(args.length==5){
			Vector v = new Vector(0, 0, 0);
			if(args[0].equalsIgnoreCase("add")){
				if(!ModelManager.getModelCreator().isInManager(p)){p.sendMessage("You are not in the edit Mode");return true;}
				ModelCreator modelC = ModelManager.getModelCreator().getModelCreator(p);
				if(modelC.activeArmorStandID!=null){p.sendMessage("You have an unsaved armorstand");return true;}
				if(!(args[1].equalsIgnoreCase("ARM") || args[1].equalsIgnoreCase("HEAD"))){p.sendMessage(help);return true;}
				HeadArmType ha = null;
				ItemStack is = null;
				Boolean visible;
				Boolean mini;
				if(args[1].equalsIgnoreCase("ARM")){ha = HeadArmType.ARM;}
				if(args[1].equalsIgnoreCase("HEAD")){ha = HeadArmType.HEAD;}
				if(args[2].equalsIgnoreCase("hand")){is=p.getItemInHand();}else{
					if(getItemStackFromString(args[2]) == null){p.sendMessage("Not Valid Item format");return true;}
					is = getItemStackFromString(args[2]);
				}
				
				if(!Utils.isBoolean(args[3])){p.sendMessage(help);return true;}
				if(!Utils.isBoolean(args[4])){p.sendMessage(help);return true;}
				
				visible = Boolean.parseBoolean(args[3]);
				mini = Boolean.parseBoolean(args[4]);
				v = Utils.getCenter(p.getLocation().getBlock().getLocation()).toVector();
				Integer i = modelC.addArmorStand(v, ha, null, is, visible, mini, Utils.yawToFace(p.getLocation().getYaw()), Utils.yawToFace(p.getLocation().getYaw()));
				modelC.setActiveArmorStandIndex(i);
			}else{
				p.sendMessage(help);
			}
		}else if(args.length==3){
			
			if(!ModelManager.getModelCreator().isInManager(p)){p.sendMessage("You are not in the edit Mode");return true;}
			if(!Utils.isInt(args[1])){p.sendMessage(help);return true;}
			ModelCreator mc = ModelManager.getModelCreator().getModelCreator(p);
			Integer index = Integer.parseInt(args[1]);
			if(mc.getArmorStand(index)==null){p.sendMessage("ArmorStand does not exist"); return true;}
			HeadArmType type = mc.getType(index);
			EulerAngle angle = mc.getEuler(index, type);
			ItemStack is = mc.getItemStack(index);
			ArmorStand as = mc.getArmorStand(index);
			Vector v = as.getLocation().toVector();
			BlockFace face1 = Utils.yawToFaceRadial(as.getLocation().getYaw());
			if(args[0].equalsIgnoreCase("setvisible")){
				if(!Utils.isBoolean(args[2])){p.sendMessage(help);return true;}
				mc.editArmorStand(v, type, angle, is, Boolean.parseBoolean(args[2]), as.isSmall(), index, face1, face1);
				p.sendMessage("Change Armorstand Visible to:" + Boolean.parseBoolean(args[2]));
				return true;
			}else if(args[0].equalsIgnoreCase("setitem")){
				if(args[2].equalsIgnoreCase("hand")){is=p.getItemInHand();}else{
					if(getItemStackFromString(args[2]) == null){p.sendMessage("Not Valid Item format");return true;}
					is = getItemStackFromString(args[2]);
				}
				mc.editArmorStand(v, type, angle, is, as.isVisible(), as.isSmall(), index, face1, face1);
				p.sendMessage("Change Armorstand Item");
				return true;
			}else if(args[0].equalsIgnoreCase("setype")){
				if(!(args[2].equalsIgnoreCase("ARM") || args[2].equalsIgnoreCase("HEAD"))){p.sendMessage(help);return true;}
				if(args[2].equalsIgnoreCase("ARM")){type = HeadArmType.ARM;as.setHelmet(null);}
				if(args[2].equalsIgnoreCase("HEAD")){type = HeadArmType.HEAD;as.setItemInHand(null);}
				mc.editArmorStand(v, type, angle, is, as.isVisible(), as.isSmall(), index, face1, face1);
				p.sendMessage("Change Armorstand Type to:" + type.name());
				return true;
			}else{
				p.sendMessage(help);return true;
			}
		}else if(args.length == 1){
			if(args[0].equalsIgnoreCase("save")){
				if(!ModelManager.getModelCreator().isInManager(p)){p.sendMessage("You are not in the edit Mode");return true;}
				ModelManager mc = ModelManager.getModelCreator();
				ModelSave.save(mc.getModelCreator(p).ID, mc.getModelCreator(p).getModel());
				mc.removeall(mc.getModelCreator(p).ID);
				p.sendMessage("Model Saved");
			}else if(args[0].equalsIgnoreCase("test")){
				Vector pV = p.getLocation().getBlock().getLocation().toVector();
				Vector pVNew = Utils.getRelativ(pV, 1D, Utils.yawToFace(p.getLocation().getYaw()));
				Location l = pVNew.toLocation(p.getWorld());
				l.getBlock().setType(Material.DIAMOND_BLOCK);
				return true;
			}
		}else{
			p.sendMessage(help);return true;
		}
		return false;
	}

	
	public ItemStack getItemStackFromString(String s){
		String mat = s;
		Material m = null;
		short subID = 0;
		if(mat.contains(":")){
			String[] list = mat.split(":");
			if(getMaterialFromString(list[0]) == null){return null;}
			m = getMaterialFromString(list[0]);
			if(Utils.isInt(list[1])) subID = (short) Integer.parseInt(list[1]);
		}else{
			if(getMaterialFromString(s) == null){return null;}
			m = getMaterialFromString(s);
		}
		
		ItemStack is = new ItemStack(m);
		is.setDurability(subID);
		return is;
	}
	
	@SuppressWarnings("deprecation")
	public Material getMaterialFromString(String s){
		if(Utils.isInt(s)){
			Integer i = Integer.parseInt(s);
			if(Material.getMaterial(i)==null){return null;}
			return Material.getMaterial(i);
		}else if(Material.getMaterial(s.toUpperCase()) != null){
			return Material.getMaterial(s.toUpperCase());
		}
		return null;
	}
}
