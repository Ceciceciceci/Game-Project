
public class Projectile {
	private double x;
	private double y;
	private int height;
	private int width;
	private int speed; 
	private int distance;
	private double gravity;

	private boolean visible;
	private boolean isShot;	
	private boolean update;
	private boolean reverse;
	private AABBbox collisionBox;
	
	public Projectile(int x, int y, int width, int height, boolean isLeft){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		reverse = isLeft;
		if(!isLeft){
			this.speed = 3;
			this.x = x;
			this.y = y;
		}
		else{
			this.speed = -3;
			this.x = x - width;
			this.y = y;
		}
		this.gravity = 1;
		this.distance = 0;	
		collisionBox = new AABBbox(x, y, width, height);
	}
	
	public double getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
	
	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	
	public boolean isShot() {
		return isShot;
	}

	public void setShot(boolean isShot) {
		this.isShot = isShot;
	}
	
	public void setUpdate(){
		if(this.isShot()){
			y += speed;
			distance += speed;
			collisionBox.setY((int) y);
		}
		else{
			x += speed;
			distance += speed;
			collisionBox.setX((int) x);
		}
	}
}
