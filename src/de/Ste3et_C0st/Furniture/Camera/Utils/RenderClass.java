package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class RenderClass extends MapRenderer {
	private boolean hasRendered = false;
	private List<Layer> layerList = new ArrayList<Layer>();
	private ScaleMode mode;
	
	public static enum ScaleMode{
		NORMAL(56, 56), FAR(72, 72), FAHRTEST(102, 102), COMPLETE(128, 128);
		
		int width, height;
		
		ScaleMode(int width, int height){
			this.width = width;
			this.height = height;
		}
		
		public int getWidth(){return this.width;}
		public int getHeight(){return this.height;}
	}
	
	Integer height = 55;
	Integer width = 55;
	
	
	public RenderClass(Location loc, ScaleMode mode){
        GetBlocks blocks = new GetBlocks();
        hasRendered = true;
        this.mode = mode;
        this.layerList = blocks.returnBlocks(loc, 56, 56, 29, 0);
    }
	
	@SuppressWarnings("deprecation")
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
			
			//draw Background
			BufferedImage image = new BufferedImage(57, 57, BufferedImage.TYPE_INT_RGB);
			for(int x = 0; x<image.getWidth();x++){
				for(int z = 0; z<image.getHeight();z++){
					
					if((z) > (52)){
						image.setRGB(x, z, new Color(101,151,213).getRGB());
					}else{
						image.setRGB(x, z, new Color(43,64,151).getRGB());
					}
				}
			}
			
			//draw Minecraft Blocks to the map
			for(Layer l : this.layerList){
				for(Pixel p : l.getPixelList()){
					Byte b = p.getColor();
					if(b == 0) continue;
					try{
						Color c = MapPalette.getColor(p.getColor());
						image.setRGB(p.getX(), p.getZ(), c.getRGB());
					}catch(Exception ex){
						
					}
				}
			}
			
			//Fix the image
			image = image.getSubimage(1, 1, 56, 56);
			
			//resize the image
			image = createResizedCopy(image, mode.getWidth(), mode.getHeight(), true);
			
			//get the x and y start Location
			int x = (127 - image.getWidth()) / 2;
		    int y = (127 - image.getHeight()) / 2;
			
			mapCanvas.drawImage(x, y, image);

			hasRendered = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public BufferedImage createResizedCopy(Image originalImage,int scaledWidth, int scaledHeight, boolean preserveAlpha)
    {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
        g.dispose();
        return scaledBI;
    }
}
