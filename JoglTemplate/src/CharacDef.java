//for sprite creating and animation

public class CharacDef {
	private int x;
	private int y;
	private int height;
	private int width;
	private boolean isWalking;
	private boolean isStanding;
	private boolean isJumping;
	private int velocity;
	private int speed;
	private int position;
	
	public CharacDef (int x, int y, int height, int width){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
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
	public boolean setState(){
		return false;
	}
}
