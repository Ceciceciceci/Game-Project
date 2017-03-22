
//sets the tile texture
public class Tile {
	int image;
	boolean collision;
	
	public Tile(int image, boolean collision) {
		this.image = image;
		this.collision = collision;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}
	
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	public boolean isCollision(){
		return collision;
	}
}
