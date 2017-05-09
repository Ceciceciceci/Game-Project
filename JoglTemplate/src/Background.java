
//Background array like FrameAnimation.java
public class Background{
    int width;
    int height;
    private Tile[] tiles;
	private Tile[][] bg;
	
	public Background(Tile[] tiles, int width, int height){
		this.tiles = tiles;
	}

	public Background(Tile[][] bg){
		this.bg = bg;
		this.height = bg.length;
	}
	 
//    public Background(int image, boolean collision, int width, int height){
//    	this.width = width;
//    	this.height = height;
//		bg = new Tile[height][width];
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++){
//				bg[y][x] = new Tile(image, collision); 
//			}
//		}	
//    }

    public int getWidth(){
    	return width;
    }
    public int getHeight(){
    	return height;
    }
    
    public Tile getTile(int x, int y) {
	    return tiles[(y*width)+x];
    }
    public Tile setTile(int posY, int posX, Tile t) {
	    return bg[posY][posX] = t;
    }
}