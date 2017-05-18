
//Camera class
//cam xpos and ypos -->  based on left corner
public class Camera {
    public int x;
	public int y;
    public int width;
    public int height;
    AABBbox box;
    
    public Camera(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.box = new AABBbox(x,y);
    }
    
    public void setX(int x){
    	this.x = x;
    	this.box.updateX(this.x);
    }
    public void setY(int y){
    	this.y = y;
    	this.box.updateY(this.y);
    }
    public void setWidth(int width){
    	this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public int getWidth(){
    	return width;
    }
    public int getHeight(){
    	return height;
    }
    
	public AABBbox getAABBIntersection(){
		return box;
	}
    
}