package de.Ste3et_C0st.Furniture.Main;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.Ste3et_C0st.Furniture.Main.Type.FurnitureType;

public final class FurnitureCreateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private FurnitureType furnitureType;
    private Location l;
    private String id;
    private boolean cancelled;
    
    public FurnitureCreateEvent(FurnitureType ft, String s, Location l) {
    	this.id = s;
    	this.l = l;
    	this.furnitureType = ft;
    }
    
	public HandlerList getHandlers() {return handlers;}
	public Location getLocation(){return l;}
	public FurnitureType getType(){return furnitureType;}
	public String getID(){return this.id;}
	public static HandlerList getHandlerList() {return handlers;}
	public boolean isCancelled() {return cancelled;}
	public void setCancelled(boolean arg0) {cancelled = arg0;}
}
