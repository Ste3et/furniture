package de.Ste3et_C0st.Furniture.Model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.Ste3et_C0st.Furniture.Main.main;

public class ModelTimer {
	Integer index;
	BukkitTask timer;
	Modele m;
	Player p;
	ModelListener modelL;
	public ModelTimer(Player p, Integer index, Modele m, ModelListener modelListener) {
		this.index = index;
		this.p = p;
		this.m = m;
		this.modelL = modelListener;
	}
	
	public void StartTimer(){
		this.timer = Bukkit.getScheduler().runTaskLater(main.getInstance(), new Runnable() {
			@Override
			public void run() {
				m.removeArmorStand(index);
				cancleTimer();
			}
		}, 10*20);
	}
	
	public void restartTimer(){
		if(this.timer != null){
			this.timer.cancel();
		}
		
		StartTimer();
	}
	
	public void cancleTimer(){
		modelL.remove(this, this.p);
		this.index = null;
		this.m = null;
		this.p = null;
		this.timer.cancel();
		this.timer = null;
	}

}
