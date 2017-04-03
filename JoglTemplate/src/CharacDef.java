//for sprite creating and animation
import java.util.ArrayList;
import java.util.List;

public class CharacDef {
	private int x;
	private int y;
	private int height;
	private int width;
	private int tex;	
	AABBbox charaHitBox;
	
	private double yVelocity;
	private double acceleration;
	private int speed;
	private ArrayList<Projectile> projectiles;
	private int health = 0;
	
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
	}
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	public int getY() {
		return y;
	}
	
	//collision box?? AABB
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
	 
	 public void setAcceleration(double accel){
		 acceleration = accel;
	 }
	 public double getAcceleration(){
		 return acceleration;
	 }	 
	//jumping state
	//walking state
	//standing state
	public boolean setState(){
		return false;
	}
}
