
//gets the time and sets the frame

public class FrameAnimation {
	public FrameDef[] frames;
	int currentFrame;
	private boolean frameDone;
	private float time;
	
	public FrameAnimation(FrameDef[] f){
		frames = f;
		currentFrame = 0;
		frameDone = false;
		time = frames[0].frameTime;
	}
	
	public float getTime (){
		return time;
	}
	
	//set current frame and time for that frame
	public void setFrame(int frame){
		currentFrame = frame;
		//time should equal the that current frame's time
		time = frames[currentFrame].frameTime;
	}
	
	//get sprite
	public int getCurrentFrame(){
			return frames[currentFrame].getSprite();
	}
	
	//get frame number in array
	public int getFrame(){
		return currentFrame;
	}
	
	//return if frame finished or not
	public boolean frameFin(){
		return frameDone;
	}
	
	//update sprite frame based on deltatime
	public void updateSprite(float dTime){
		time -= dTime;
		if (time <= 0){
			currentFrame++;
			if (currentFrame > frames.length - 1){
				frameDone = true;
				currentFrame = 0;
			}
			time = frames[currentFrame].frameTime ++;
		}
	}
	
	//reset animation
	public void resetAnimation(){
		frameDone = false;
		currentFrame = 0;
		time = frames[0].frameTime;
	}
}

