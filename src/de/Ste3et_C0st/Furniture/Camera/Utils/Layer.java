package de.Ste3et_C0st.Furniture.Camera.Utils;

import java.util.ArrayList;
import java.util.List;

public class Layer {

	private int layerID = 0;
	private List<Pixel> pixelList = new ArrayList<Pixel>();
	
	public Layer(int layerID){
		this.layerID = layerID;
	}
	
	public void addPixel(Pixel p){
		this.pixelList.add(p);
	}
	
	public List<Pixel> getPixelList(){
		return this.pixelList;
	}
	
	public int getLayerID(){
		return this.layerID;
	}
}
