package de.Ste3et_C0st.Furniture.Objects.christmas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import de.Ste3et_C0st.Furniture.Objects.garden.config;
import de.Ste3et_C0st.FurnitureLib.main.Furniture;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.SQLAction;
import de.Ste3et_C0st.FurnitureLib.main.entity.fArmorStand;
import de.Ste3et_C0st.FurnitureLib.main.entity.fEntity;

public class AdventCalender extends Furniture implements Listener{
	double sub = .9;
	
	HashMap<Integer, ItemStack[]> isList = new HashMap<Integer, ItemStack[]>();
	HashMap<UUID, Integer> uuidList = new HashMap<UUID, Integer>();
	Player p;
	int i;
	int currentDay = 0;
	Inventory inv;
	
	String ac_1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0=";
	String ac_2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNkOWVlZWU4ODM0Njg4ODFkODM4NDhhNDZiZjMwMTI0ODVjMjNmNzU3NTNiOGZiZTg0ODczNDE0MTk4NDcifX19";
	String ac_3 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19";
	String ac_4 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJlNzhmYjIyNDI0MjMyZGMyN2I4MWZiY2I0N2ZkMjRjMWFjZjc2MDk4NzUzZjJkOWMyODU5ODI4N2RiNSJ9fX0=";
	String ac_5 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ1N2UzYmM4OGE2NTczMGUzMWExNGUzZjQxZTAzOGE1ZWNmMDg5MWE2YzI0MzY0M2I4ZTU0NzZhZTIifX19";
	String ac_6 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM0YjM2ZGU3ZDY3OWI4YmJjNzI1NDk5YWRhZWYyNGRjNTE4ZjVhZTIzZTcxNjk4MWUxZGNjNmIyNzIwYWIifX19";
	String ac_7 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRiNmViMjVkMWZhYWJlMzBjZjQ0NGRjNjMzYjU4MzI0NzVlMzgwOTZiN2UyNDAyYTNlYzQ3NmRkN2I5In19fQ==";
	String ac_8 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTkxOTQ5NzNhM2YxN2JkYTk5NzhlZDYyNzMzODM5OTcyMjI3NzRiNDU0Mzg2YzgzMTljMDRmMWY0Zjc0YzJiNSJ9fX0=";
	String ac_9 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3Y2FmNzU5MWIzOGUxMjVhODAxN2Q1OGNmYzY0MzNiZmFmODRjZDQ5OWQ3OTRmNDFkMTBiZmYyZTViODQwIn19fQ==";
	String ac_0 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGViZTdlNTIxNTE2OWE2OTlhY2M2Y2VmYTdiNzNmZGIxMDhkYjg3YmI2ZGFlMjg0OWZiZTI0NzE0YjI3In19fQ==";
	String ac_NULL = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBlOWQyYmViODRiMzJlM2YxNWUzODBjYzJjNTUxMDY0MjkxMWE1MTIxMDVmYTJlYzY3OWJjNTQwZmQ4MTg0In19fQ==";
	
	public AdventCalender(ObjectID id){
		super(id);
		if(isFinish()){
			Bukkit.getPluginManager().registerEvents(this, getPlugin());
			load();
			check();
			return;
		}
		spawn(id.getStartLocation());
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
	public void onClick(Player player){
		if(getObjID() == null) return;
		if(getObjID().getSQLAction().equals(SQLAction.REMOVE)) return;
		if(player == null) return;
		ItemStack is = player.getInventory().getItemInMainHand();
		if(is==null){open(player); return;}
		if(is.getType()==null){open(player); return;}
		if(is.getType().equals(Material.ARROW)){
			if(p!=null){open(player);return;}
			if(!getObjID().getUUID().equals(player.getUniqueId())){open(player);return;}
			if(!canBuild(player)){return;}
			i = is.getAmount();
			if(i>31 || i<1) return;
			this.p = player;
			if(isList.containsKey(i)){
				openInventory(isList.get(i));
			}else{
				openInventory(null);
			}
		}else{
			open(player);
		}
	}
	
	private void open(Player p){
		if(isList.containsKey(getDay())){
			if(uuidList.containsKey(p.getUniqueId())){
				if(uuidList.get(p.getUniqueId()) == getDay()){
					return;
				}
			}
			ItemStack[] stack = isList.get(getDay());
			for(ItemStack iS : stack){
				if(iS!=null&&iS.getType()!=null){
					if(iS.getType().equals(Material.FIREWORK_ROCKET)){
						Firework fw = (Firework) getWorld().spawnEntity(getCenter(), EntityType.FIREWORK);
						FireworkMeta meta = (FireworkMeta) iS.getItemMeta();
						fw.setFireworkMeta(meta);
					}else if(iS.getType().equals(Material.NAME_TAG)){
						if(iS.getItemMeta()!=null){
							if(iS.getItemMeta().hasDisplayName()){
								String name = iS.getItemMeta().getDisplayName();
								if(name.startsWith("@PLAYER ")){
									name = name.replace("%player%", p.getName());
									name = name.replace("@PLAYER ", "");
									name = ChatColor.translateAlternateColorCodes('&', name);
									p.sendMessage(name);
								}else if(name.startsWith("@BROADCAST ")){
									name = name.replace("%player%", p.getName());
									name = name.replace("@BROADCAST ", "");
									name = ChatColor.translateAlternateColorCodes('&', name);
									getLib().getServer().broadcastMessage(name);
								}
							}
						}
					}else{
						if(iS.getItemMeta()!=null){
							if(iS.getItemMeta().getDisplayName()!=null){
								if(iS.getItemMeta().getDisplayName().startsWith("@CONSOLE ")){
									if(!isOP()) continue;
									String s = iS.getItemMeta().getDisplayName();
									s = s.replace("@CONSOLE ", "");
									s = s.replace("%player%", p.getName());
									ConsoleCommandSender sender = Bukkit.getConsoleSender();
									Bukkit.dispatchCommand(sender, s);
									continue;
								}else if(iS.getItemMeta().getDisplayName().startsWith("@PLAYER ")){
									if(!isOP()) continue;
									String s = iS.getItemMeta().getDisplayName();
									s = s.replace("@PLAYER ", "");
									s = s.replace("%player%", p.getName());
									p.chat("/"+s);
									continue;
								}
							}
						}
						p.getInventory().addItem(iS);
					}
				}
			}
			savePlayer(p.getUniqueId());
			uuidList.put(p.getUniqueId(), getDay());
		}
	}
	
	public boolean isOP(){
		if(getObjID().getUUID()!=null){
			UUID uuid = getObjID().getUUID();
			OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
			if(player == null) return false;
			return player.isOp();
		}
		return false;
	}
	
    public static String itemStackArrayToBase64(ItemStack[] items){
    	try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.length);
            for (int i = 0; i < items.length; i++) {
            	ItemStack is = items[i];
                dataOutput.writeObject(is);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return "";
    }
    
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; i++) {
            	items[i] = (ItemStack) dataInput.readObject();
            }
            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
    
    public void load(){
    	config conf = new config();
    	FileConfiguration file = conf.getConfig(getObjID().getSerial(), "plugin/AdventCalender/Data/");
    	if(file == null) return;
    	for(int i = 1; i<32;i++){
    		if(file.isSet(i+"")){
    			String s = file.getString(i+"");
    			try {
					ItemStack[] is = itemStackArrayFromBase64(s);
					isList.put(i, is);
				} catch (IOException e) {e.printStackTrace();}
    		}
    	}
    	
		conf = new config();
	    file = conf.getConfig(getObjID().getSerial() + "_Players", "plugin/AdventCalender/Data/");
	    if(file == null) return;
	    if(!file.isSet("Players")) return;
	    for(String s : file.getConfigurationSection("Players").getKeys(false)){
	    	UUID uuid = UUID.fromString(s);
	    	int i = file.getInt("Players." + s);
	    	uuidList.put(uuid, i);
	    }
    }
    
    public void save(int i){
    	config conf = new config();
    	FileConfiguration file = conf.getConfig(getObjID().getSerial(), "plugin/AdventCalender/Data/");
    	file.set(i + "", getSerialze(i));
		conf.saveConfig(getObjID().getSerial(), file, "plugin/AdventCalender/Data/");
    }
    
    public void savePlayer(UUID uuid){
		if(uuid != null){
			config conf = new config();
	    	FileConfiguration file = conf.getConfig(getObjID().getSerial() + "_Players", "plugin/AdventCalender/Data/");
	    	file.set("Players." + uuid.toString(), i);
			conf.saveConfig(getObjID().getSerial() + "_Players", file, "plugin/AdventCalender/Data/");
		}
    }
	
	private String getSerialze(int i) {
		if(!isList.containsKey(i)){return "";}
		return itemStackArrayToBase64(isList.get(i));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		check();
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e){
		check();
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(p==null) return;
		if(e.getInventory()==null) return;
		if(!e.getInventory().equals(inv)) return;
		if(!e.getPlayer().equals(p)) return;
		isList.put(i, e.getInventory().getContents());
		save(i);
		p = null;
		inv = null;
	}
	
	private void openInventory(ItemStack[] is){
		inv = Bukkit.createInventory(null, 54, "§8Christmas Reward [§c" + i + "§8]");
		if(is!=null) inv.setContents(is);
		p.openInventory(inv);
	}

	@Override
	public void spawn(Location arg0) {
		List<fArmorStand> asList = new ArrayList<fArmorStand>();
		int j = 15;
		double l = 0;
		double o = getDegress(j);
		for(int i = 0; i<=j;i++){
			Location loc = getCenter();
			loc.setYaw((float) l);
			fArmorStand stand = spawnArmorStand(loc.subtract(0, 1.2+sub, 0));
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(210, 190, 305)));
			stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(60, 0, 0)));
			stand.setHelmet(new ItemStack(Material.GOLD_BLOCK));
			stand.setItemInMainHand(new ItemStack(Material.OAK_LEAVES));
			asList.add(stand);
			l+=o;
		}
		
		l = 0;
		for(int i = 0; i<=j;i++){
			Location loc = getCenter();
			loc.setYaw((float) l);
			fArmorStand stand = spawnArmorStand(loc.subtract(0, .3+sub, 0));
			stand.setRightArmPose(getLutil().degresstoRad(new EulerAngle(210, 190, 305)));
			stand.setHeadPose(getLutil().degresstoRad(new EulerAngle(60, 0, 0)));
			stand.setHelmet(new ItemStack(Material.OAK_LEAVES));
			stand.setItemInMainHand(new ItemStack(Material.OAK_LEAVES));
			stand.setSmall(true);
			asList.add(stand);
			l+=o;
		}
		
		Location loc = getCenter();
		loc.setYaw(getYaw()+180);
		fArmorStand stand = spawnArmorStand(loc.add(0,-1.5,0));
		stand.setHelmet(new ItemStack(Material.CHEST));
		asList.add(stand);
		
		ItemStack[] is = getStack();
		
		loc = getRelative(getCenter(), getBlockFace(), 0, .25);
		loc.setYaw(getYaw()+180);
		stand = spawnArmorStand(loc);
		stand.setHelmet(is[0]);
		stand.setName("#Advent1#");
		stand.setSmall(true);
		asList.add(stand);
		
		loc = getRelative(getCenter(), getBlockFace(), 0, -.25);
		loc.setYaw(getYaw()+180);
		stand = spawnArmorStand(loc);
		stand.setHelmet(is[1]);
		stand.setName("#Advent2#");
		stand.setSmall(true);
		asList.add(stand);
		
		for(fArmorStand pack : asList){
			pack.setInvisible(true);
			pack.setBasePlate(false);
		}
		
		send();
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}
	
	@SuppressWarnings("deprecation")
	public int getDay(){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		try {
			Date todayWithZeroTime =formatter.parse(formatter.format(today));
			int i = todayWithZeroTime.getDate();
			return i;
		} catch (ParseException e) {
			return 0;
		}
	}
	
	@SuppressWarnings("deprecation")
	public int getMonth(){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		try {
			Date todayWithZeroTime =formatter.parse(formatter.format(today));
			int i = todayWithZeroTime.getMonth();
			return i;
		} catch (ParseException e) {
			return 0;
		}
		
	}
	
	public void check(){
		if(currentDay==getDay()){return;}
		if(getfAsList()==null) return;
		ItemStack[] is = getStack();
		fEntity stand1= null;
		fEntity stand2 = null;
		for(fEntity stand : getfAsList()){
			if(stand.getName().equalsIgnoreCase("#Advent1#")){
				stand1 = stand;
			}else if(stand.getName().equalsIgnoreCase("#Advent2#")){
				stand2 = stand;
			}
		}
		if(stand1!=null){stand1.setHelmet(is[0]);}
		if(stand2!=null){stand2.setHelmet(is[1]);}
		currentDay=getDay();
		update();
	}
	
	public ItemStack[] getStack(){
		ItemStack[] stack = new ItemStack[2];
			stack[0] = getSkull(ac_NULL);
			stack[1] = getSkull(ac_NULL);
			int i = getDay();
			int y = getMonth();
			if(y==11){
				switch (i) {
				case 1: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_1);break;
				case 2: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_2);break;
				case 3: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_3);break;
				case 4: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_4);break;
				case 5: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_5);break;
				case 6: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_6);break;
				case 7: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_7);break;
				case 8: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_8);break;
				case 9: 
					stack[0] = getSkull(ac_0);
					stack[1] = getSkull(ac_9);break;
				case 10: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_0);break;
				case 11: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_1);break;
				case 12: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_2);break;
				case 13: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_3);break;
				case 14: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_4);break;
				case 15: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_5);break;
				case 16: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_6);break;
				case 17: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_7);break;
				case 18: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_8);break;
				case 19: 
					stack[0] = getSkull(ac_1);
					stack[1] = getSkull(ac_9);break;
				case 20: 
					stack[0] = getSkull(ac_2);
					stack[1] = getSkull(ac_0);break;
				case 21: 
					stack[0] = getSkull(ac_2);
					stack[1] = getSkull(ac_1);break;
				case 22: 
					stack[0] = getSkull(ac_2);
					stack[1] = getSkull(ac_2);break;
				case 23: 
					stack[0] = getSkull(ac_2);
					stack[1] = getSkull(ac_3);break;
				case 24: 
					stack[0] = getSkull(ac_2);
					stack[1] = getSkull(ac_4);break;
				default:
					stack[0] = getSkull(ac_NULL);
					stack[1] = getSkull(ac_NULL);break;
				}
			}

			return stack;
	}
	
	public String generateSessionKey(int length){
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
		int n = alphabet.length();
		String result = new String(); 
		Random r = new Random();
		for (int i=0; i<length; i++) result = result + alphabet.charAt(r.nextInt(n));
		return result;
	}
	
	private int getDegress(int j){
		return 360/j;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getSkull(String s) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        UUID hashAsId = new UUID(s.hashCode(), s.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(skull,
				"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + s + "\"}]}}}");
    }
}
