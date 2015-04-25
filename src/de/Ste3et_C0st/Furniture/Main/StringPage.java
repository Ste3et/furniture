package de.Ste3et_C0st.Furniture.Main;

import static org.bukkit.util.NumberConversions.ceil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class StringPage {
	
	public void returnStringPage(CommandSender sender, HashMap<String, ItemStack> map, Integer page, Integer perside){
		if(sender==null){return;}
		if(map==null||map.isEmpty()){return;}
		if(page==null||page==0){page=1;}
		
		List<String> strings = getList(map);
		List<String> stringL = new ArrayList<String>();
		Collections.sort(strings);
		Integer max = page*perside;
		Integer min = page*perside-perside;
		
		Integer l = 0;
		for(String s : strings){
			if(l>=min&&l<max){
				stringL.add(s);
			}
			l++;
		}
		
		if(!stringL.isEmpty()){
			for(String s : stringL){
				sender.sendMessage(s);
			}
		}
	}
	
	public List<String> getList(HashMap<String, ItemStack> map){
		if(map==null||map.isEmpty()){return null;}
		List<String> stringlist = new ArrayList<String>();
		for(String s : map.keySet()){
			stringlist.add(s);
		}
		return stringlist;
	}

	public boolean check(CommandSender sender, HashMap<String, ItemStack> stringList, Integer page, Integer perside){
		if(sender==null){return false;}
		if(stringList==null||stringList.isEmpty()){return false;}
		if(page==null||page==0){page=1;}
		int numPages = ceil((double)stringList.size()/(double)perside);
		if(page > numPages){
            return false;
        }
		return true;
	}
}
