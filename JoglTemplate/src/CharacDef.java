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
	
	//reverse chara texture
	private boolean reverse = false;
	private boolean visible = true;
	
	private boolean isShooting = false;
	private boolean isShot = false;
	private boolean isWalking = false;
	private boolean isStanding = false; 
	private boolean isJumping = false;
	private boolean isGrounded = false;
	private boolean isDead = false;
 
	public CharacDef (int x, int y, int height, int width, int spriteTex){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		tex = spriteTex;
		
		projectiles = new ArrayList<Projectile>();
		charaHitBox = new AABBbox(x, y, width, height);
		this.health= 10;
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
	 
	 public void setAcceleration(double accel){
		 acceleration = accel;
	 }
	 public double getAcceleration(){
		 return acceleration;
	 }	 
	 
	 public boolean isJumping() {
		 return isJumping;
	 }

	 public void setJumping(boolean isJumping) {
		 this.isJumping = isJumping;
	 }
	 
	 public double getyVelocity() {
		 return yVelocity;
	 }

	 public void setyVelocity(double yVelocity) {
		 this.yVelocity = yVelocity;
	 }
	 
	 public int getCurrentTexture() {
		 return tex;
	 }

	 public void setCurrentTexture(int tex) {
		 this.tex = tex;
	 }
	 
	 public boolean getReverse() {
	     return reverse;
	 }

	 public void setReverse(boolean bool) {
		 reverse = bool;
	 }
	 
	 public boolean isStand(boolean isStanding){
		 return isStanding;
	 }
	 
	 //Reverse the sprite 
	 public boolean reverse(){

	     x = x * -1;
	     y = x * -1;
	     return true;
	    	
	 }
	 
	 
	//jumping state
	//walking state
	//standing state
	
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
	 
	 public void setVisible(boolean bool){
		 visible = bool;
	 }
		
	 public boolean getVisible() {
		 return visible;
	 }
	 
	 public boolean isGrounded(){
		return isGrounded;
	}
	public void setDead(boolean isDead){
		this.isDead = isDead;
	}
	public boolean isDead(){
		return isDead;
	}
	 
	
}
