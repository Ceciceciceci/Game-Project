//for sprite creating and animation
import java.util.ArrayList;
import java.util.List;

public class CharacDef {
	private int x, y, height, width, tex;	
	
	private double yVelocity, acceleration, speed;
	private ArrayList<Projectile> projectiles;
	private int health = 0;
	AABBbox charaHitBox;
	private int boxOffset;
	
	private boolean isShooting = true;
	private boolean isShot = true;
	private boolean isWalking = true;
	private boolean isStanding = true; 
	private boolean isJumping = true;
	private boolean isGrounded = true;
 
	
	public CharacDef (int x, int y, int height, int width, int spriteTex){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		spriteTex = tex;
		
		projectiles = new ArrayList<Projectile>();
		charaHitBox = new AABBbox(x, y, width, height);
		this.health= 2;
		isShooting = false;
		isShot = false;
		boxOffset = 0;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	public int getHeight(){
		return height;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	public int getWidth(){
		return width;
	}
	
	public void setX(int x){
		this.x = x;
		charaHitBox.setX(x + boxOffset);
	}
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
		charaHitBox.setY(y);
	}
	public int getY() {
		return y;
	}
	
	public boolean isStand(boolean isStanding){
		return isStanding;
	}
	 
	 public void setAcceleration(double accel){
		 acceleration = accel;
	 }
	 public double getAcceleration(){
		 return acceleration;
	 }	 
	 
	 public int getCurrentTexture() {
		 return tex;
	 }

	 public void setCurrentTexture(int tex) {
		 this.tex = tex;
	 }
	 
	//jumping state
	//walking state
	//standing state
	public boolean setState(){
		return false;
	}
	
	//SHOOT PART
	//collision box AABB
	public AABBbox charaHitbox(){
		return charaHitBox;
	}
	
	 public List<Projectile> getProjectile(){
		 return projectiles;
	 }
	 public void addProjectile(Projectile p){
		 projectiles.add(p);
	 }
	
	 public void setHealth(int health){
		 this.health = health;
	 }
	 public int getHealth(){
		 return health;
	 }
	 
	 public void setShooting(boolean isShooting) {
	 	 this.isShooting = isShooting;
	 }
	 public boolean isShootin(){
	 	 return isShooting;
	 }
	 
	 public boolean isShot() {
		return isShot;
	 }

	 public void setHit(boolean isShot) {
		 this.isShot = isShot;
	 }
	 
	
}
