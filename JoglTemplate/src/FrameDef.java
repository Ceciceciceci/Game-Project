
public class FrameDef {
	public int sprite;
	public float frameTime; //in secs
	
	public FrameDef(int sprite, float frameTime){
		this.sprite = sprite;
		this.frameTime = frameTime;
	}
	
	//get the next sprite in the sequence
	public int getSprite() {
		return sprite;
	}

	public void setSprite(int sprite) {
		this.sprite = sprite;
	}

	//get the time of last frame
	public float getTimeAtPoint() {
		return frameTime;
	}

	//set to new time
	public void setSpriteActiveTime(float frameTime) {
		this.frameTime = frameTime;
	}
	

}
