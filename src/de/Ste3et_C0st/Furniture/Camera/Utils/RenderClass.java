package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class RenderClass extends MapRenderer {
	private boolean hasRendered = false;
	private List<Layer> layerList = new ArrayList<Layer>();
	
	Integer height = 55;
	Integer width = 55;
	
	public RenderClass(Location loc){
        GetBlocks blocks = new GetBlocks();
        hasRendered = true;
        this.layerList = blocks.returnBlocks(loc, 56, 56, 29, 0);
    }
	
	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		if(!hasRendered){return;}
		try {
			mapView.setWorld(player.getWorld());
			mapView.setCenterX(player.getLocation().getChunk().getX());
			mapView.setCenterZ(player.getLocation().getChunk().getZ());
			for(int x = 0; x<=127;x++){
				for(int y = 0; y<=127;y++){
					mapCanvas.setPixel(x, y, (byte) 54);
				}
			}
			
			Integer startX = 35;
			Integer startY = 35;
			
			for(int x = 0; x<=height;x++){
				for(int y = 0; y<=width;y++){
					mapCanvas.setPixel(startX + x, startY +y, (byte) 70);
				}
			}
			
			for(int x = 0; x<=height;x++){
				for(int y = 0; y<=width-5;y++){
					mapCanvas.setPixel(startX + x, startY +y, (byte) 100);
				}
			}
			
			for(Layer l : this.layerList){
				for(Pixel p : l.getPixelList()){
					Byte b = p.getColor();
					if(b == 0) continue;
					mapCanvas.setPixel(- 1 + startX + p.getX(),- 1 + startY + p.getZ(), b);
				}
			}
			hasRendered = false;
		}catch(Exception e){e.printStackTrace();}
	}
}
