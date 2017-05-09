
//sets the tile texture
public class Tile {
	int image;
	public String name;
	public int[] imageSize;
	public boolean collision;
	public int xOffset, yOffset;
	
	public Tile(int image, String name, int[] textureSize) {
		this.name = name;
		this.image = image;
		this.imageSize = textureSize;
		collision = false;
	}

	public Tile(int image, String name, int[] textureSize, boolean c) {
		this.name = name;
		this.image = image;
		this.imageSize = textureSize;
		collision = c;
	}
	
	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}
	
	public void setXOffset(int height){
		xOffset = height;
	}
	
	public void setYOffset(int width){
		yOffset = width;
	}
	
	public String getName(){
		return name;
	}
	
	public void setCollision(boolean c) {
		this.collision = c;
	}
	public boolean isCollision(){
		return collision;
	}
}
