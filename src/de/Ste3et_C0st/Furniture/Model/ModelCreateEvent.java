package de.Ste3et_C0st.Furniture.Model;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class ModelCreateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Modele model;
    private Location l;
    private boolean cancelled;
    private boolean copy;
    
    public ModelCreateEvent(Modele model, Location l) {
    	this.model = model;
    	this.l = l;
    	if(model.getID().contains("#")){
    		copy=true;
    	}
    }
    
	public HandlerList getHandlers() {return handlers;}
	public Location getLocation(){return l;}
	public Modele getModel(){return model;}
	public static HandlerList getHandlerList() {return handlers;}
	public boolean isCopy() {return copy;}
	public boolean isCancelled() {return cancelled;}
	public void setCancelled(boolean arg0) {cancelled = arg0;}
}
