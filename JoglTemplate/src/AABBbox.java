
//set bounding box
public class AABBbox extends Camera{

	private int x, y, height, width;
	
	public AABBbox(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	//x
	public void setX(int x){
		this.x = x;
	}
	public int getX(){
		return x;
	}
	
	//y
	public void setY(int y){
		this.y = y;
	}
	public int getY(){
		return y;
	}
	
	//height
	public void setHeight(int height){
		this.height = height;
	}
	public int getHeight(){
		return height;
	}
	
	//width
	public void setWidth(int width){
		this.width = width;
	}
	public int getWidth(){
		return width;
	}
}
