package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class RenderClass extends MapRenderer {
	private boolean hasRendered = false;
	private HashMap<Integer, HashMap<Integer, Byte>> byteList = new HashMap<Integer, HashMap<Integer, Byte>>();
	
	Integer height = 55;
	Integer width = 55;
	public RenderClass(Location loc){
        GetBlocks blocks = new GetBlocks();
        hasRendered = true;
        this.byteList = blocks.returnBlocks(loc, height, width);
    }
	
	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		if(!hasRendered){return;}
		if(this.byteList.isEmpty()){return;}
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
			

			for(int layer = byteList.size()-1; layer>=0;layer--){
				HashMap<Integer, Byte> colorList = this.byteList.get(layer);
				Integer index = colorList.size()-1;
				for(int y = 0; y<=width;y++){
					for(int z = 0; z<=height;z++){
							Integer Y = startX+y;
							Integer Z = startY+z;
							if(colorList.get(index)!=null&&colorList.get(index)!=0){
								mapCanvas.setPixel(Y,Z,colorList.get(index));
								mapCanvas.setPixel(Y,Z,colorList.get(index));
							}
							
							index--;
					}
				}
			}
			
			//mapCanvas.drawText(10, 10, font, "§18;TESTPHASE");
			
			hasRendered = false;
		}catch(Exception e){e.printStackTrace();}
	}

	
}
