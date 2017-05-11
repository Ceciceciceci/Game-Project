
//set bounding box
public class AABBbox{

	private double x, y;
	private int height, width;
	private int topOver, bottomOver,leftOver,rightOver;
	public AABBbox(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
//		topOver = bottomOver =leftOver =rightOver = 0;
	}
	
	public AABBbox (int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//x
	public void setX(int x){
		this.x = x;
	}
	public double getX(){
		return x;
	}
	
	//y
	public void setY(int y){
		this.y = y;
	}
	public double getY(){
		return y;
	}
	
	public void updateX(int x){
		this.x =x;
		
	}
	public void updateY(int y){
		this.y = y;
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
	
	public static boolean AABBIntersect(AABBbox box1, AABBbox box2){
		// box1 to the right
		 if (box1.x > box2.x + box2.width) {
		 return false;
		 }
		 // box1 to the left
		 if (box1.x + box1.width < box2.x) {
		 return false;
		 }
		 // box1 below
		 if (box1.y > box2.y + box2.height) {
		 return false;
		 }
		 // box1 above
		 if (box1.y + box1.height < box2.y) {
		 return false;
		 }

		return true;
	}
	
	/*If box 1 is left of box 2*/
	public static boolean AABBisLeftOf(AABBbox box1, AABBbox box2){
        // box1 to the right
        return box1.x < box2.x + box2.width;
	}
    public static boolean AABBIntersectLeftOf(AABBbox box1,AABBbox box2, double prevX, double currX){
        return (prevX<currX && AABBIntersect(box1,box2));
    }
	/*If box 1 is right of box 2*/
	public static boolean AABBisRightOf(AABBbox box1, AABBbox box2){
        // box1 to the right
        return box1.x < box2.x + box2.width;
	}
	/*If box 1 is on top of box 2*/
	public static boolean AABBisTopOf(AABBbox box1, AABBbox box2){
        // box1 to the right
        return AABBIntersect(box1, box2);
	}
	/*If box 1 is on the bottom of box 2*/
	public static boolean AABBisBottomOf(AABBbox box1, AABBbox box2){
        // box1 to the right
        return box1.x > box2.x + box2.height;
	}
}
