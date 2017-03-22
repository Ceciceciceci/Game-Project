
//Background array like FrameAnimation.java
public class Background{
    int width;
    int height;
	private Tile[][] bg;
    int pixelStart;
    int pixelEnd;
	
    public Background(int image, boolean collision, int width, int height){
    	this.width = width;
    	this.height = height;
		bg = new Tile[height][width]; // set width and height of Tile
		
		//from this pixel to end pixel use this tile.
		//similar to 
		// 1 1 1 1 1 1 0 0 0 1 1 1 1 
		// 1 1 1 0 1 0 0 0 0 1 1 1 1 etc;
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++){
				bg[y][x] = new Tile(image, collision); //set bg to this tile in the array 
			}
		}	
    }
    
//    public void setWidth(int width){
//    	this.width = width;
//    }
    public int getWidth(){
    	return width;
    }
    
//    public void setHeight(int height){
//    	this.height = height;
//    }
    public int getHeight(){
    	return height;
    }
    
    public Tile getTile(int x, int y) {
	    return bg[y][x];
    }
}