
public class Projectile {
	private int x;
	private int y;
	private int height;
	private int width;
	private int speed; 
	private int distance;
	private double gravity;
	private boolean visible;
	private boolean shot;	
	private AABBbox collisionBox;
	
	public Projectile(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		//this.shot = shot;
		this.gravity = 1;
		this.distance = 0;	
		collisionBox = new AABBbox(x, y, width, height);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width){
		this.width = width;
	}
	public int getWidth(){
		return width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	public int getHeight(){
		return height;
	}
	
	public void setDistance(int distance){
		this.distance = distance;
	}
	public int getDistance() {
		return distance;
	}
	
	//collision of projectile
	public AABBbox getCollisionBox() {
		return collisionBox;
	}
	
	//check if projectile should visible or not
	public boolean isVisible(){
		return visible;
	}
	public void setVisible(boolean visi){
		visible = visi;
	}
}
