//for sprite creating and animation
import java.util.ArrayList;
import java.util.List;

public class CharacDef {
	private double x, y; 
	private int height, width, tex;	
	//character x pos, y pos, height, width of texture, texture
	private double yVelocity, acceleration, speed;
	private ArrayList<Projectile> projectiles;
	private int health = 0;
	AABBbox charaHitBox; 
	private int boxOffset;
	
	//reverse chara texture
	private boolean reverse = false;
	private boolean visible = true;
	int timer;
	private boolean isShooting, isShot, isDead, isHit = false;
	private boolean isWalking, isStanding, isJumping, isGrounded= false;
 
	public CharacDef (int x, int y, int height, int width, int spriteTex){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		tex = spriteTex;
        yVelocity = 0.0;
        isGrounded = false;
		projectiles = new ArrayList<Projectile>();
		charaHitBox = new AABBbox(x, y, width, height);
		health = 5;
		isShooting = false;
		isShot = false;
		isDead = false;
		boxOffset = 0;
		timer = 5;
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
	
	public void setX(double x){
		this.x = x;
		charaHitBox.setX((int) (x + boxOffset));
	}
	public double getX(){
		return x;
	}
	
	public void setY(double y){
		this.y = y;
		charaHitBox.setY((int) y);
	}
	public double getY() {
		return y;
	}
	 
	 public int getCurrentTexture() {
		 return tex;
	 }

	 public void setCurrentTexture(int tex) {
		 this.tex = tex;
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
	 
	 public double updateyvelocity() {
	     return yVelocity;
	 }
	 
	 public void setShooting(boolean isShooting) {
	 	 this.isShooting = isShooting;
	 }
	 public boolean isShootin(){
	 	 return isShooting;
	 }
	 
	 public boolean isHit() {
		 return isHit;
	 }

	 public void setHit(boolean isHit) {
		 this.isHit = isHit;
	 }

	 
	 public void setVisible(boolean bool){
		 visible = bool;
	 }
		
	 public boolean getVisible() {
		 return visible;
	 }
	  
	 public void setTimer(int timer){
		 this.timer = timer;
	 }
	 
	 public int getTimer(){
		 return timer;
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
