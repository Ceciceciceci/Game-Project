import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.*;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static java.lang.Math.atan2;


//HWK 3
//Add projectiles and a way to shoot them to your existing project. 
//If you have yet to add enemies, add some enemies to the game (they do not need to move).
//When a projectile hits the enemy, it should damage the enemy and then go away. 
//There must be some enemies with health that need to be hit multiple times.

//Extra credit:
//Enemies shoot projectiles at the player and he or she can get hit
//Raycast style instant shots.

public class JavaTemplate {
    // Set this to true to make the game loop exit.
    private static boolean shouldExit;

    // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];

    // Position of the sprite.
    private static int[] spritePos = new int[] { 200, 200 };
    private static int[] enemyPos = new int[] {spritePos[0]-50, spritePos[1]-50};
    
    //follow variables
	static int enemySpeed = 3; //set enemy speed
	
    //atan2()
	static double target_direction = atan2(spritePos[1] - enemyPos[1], spritePos[0] - spritePos[0]);

	// Initialize texture variables for sprite, bg, projectile.
    private static int spriteTex,
				       enemyTex;
    
    private static int bgTile, 
    				   bgTile2,
    				   bgTile3,
    				   projectile,
					   tableTile1,
					   tableTile2,
					   tableTile3,
					   tableTile4;
    
    private static int projTile;
    
    //initialize background
    private static Background bgMain, 
    						  bgItems;
    
    // Size of the sprite/tiles/projectiles
    private static int[] spriteSize = new int[2];
    private static int[] enemySize = new int[2];
    private static int[] tileSize = new int[2];
    private static int[] projectileSize = new int[2];
    
    //checks for parts of the background tiles.
    //private static int backgroundCheck = 0;
  
    
    //Size of window size
    private static int windowWidth = 400;
    private static int windowHeight = 400;
   
    //How big the grid is in the background in tiles.
    private static int gridHeight = 100;
    private static int gridWidth = 100;
    
    //size of the game play
    private static int worldHeight, worldWidth;
    
    //initialize camera
    private static Camera cam = new Camera(0,0, windowWidth, windowHeight);
    
//***************************************************************
    public static void main(String[] args) {
        GLProfile gl2Profile;

        try {
            // Make sure we have a recent version of OpenGL
            gl2Profile = GLProfile.get(GLProfile.GL2);
        }
        catch (GLException ex) {
            System.out.println("OpenGL max supported version is too low.");
            System.exit(1);
            return;
        }

        // Create the window and OpenGL context.
        GLWindow window = GLWindow.create(new GLCapabilities(gl2Profile));
        window.setSize(windowWidth, windowHeight);
        window.setTitle("Java Template");
        window.setVisible(true);
        window.setDefaultCloseOperation(
                WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.isAutoRepeat()) {
                    return;
                }
                kbState[keyEvent.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.isAutoRepeat()) {
                    return;
                }
                kbState[keyEvent.getKeyCode()] = false;
            }
        });

        // Setup OpenGL state.
        window.getContext().makeCurrent();
        GL2 gl = window.getGL().getGL2();
        gl.glViewport(0, 0, windowWidth, windowHeight);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glOrtho(0, windowWidth, windowHeight, 0, 0, 100);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

// Game initialization goes here.
        
        /************ LOAD SPRITE TEX***********/
		spriteTex = glTexImageTGAFile(gl, "1.tga", spriteSize);
		enemyTex = glTexImageTGAFile(gl, "1.tga", enemySize);
        
		/*********SPRITE ANIMATION********/
        FrameDef[] walking= { 
			new FrameDef(glTexImageTGAFile(gl, "2.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "3.tga", spriteSize), (float) 1 ),				
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "5.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "6.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "7.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "8.tga", spriteSize), (float) 1 )
        };
        FrameDef[] walkingLeft= { 
			new FrameDef(glTexImageTGAFile(gl, "2.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "3.tga", spriteSize), (float) 1 ),				
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "5.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "6.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "7.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "8.tga", spriteSize), (float) 1 )
        };
        
        FrameDef[] enemyWalking= { 
			new FrameDef(glTexImageTGAFile(gl, "2.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "3.tga", spriteSize), (float) 1 ),				
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "5.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "6.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "7.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "8.tga", spriteSize), (float) 1 )
        };
        
//
//		FrameDef[] jumping = {	
        
//		};
		
		FrameAnimation walkAni = new FrameAnimation(walking);
		FrameAnimation walkAniLeft = new FrameAnimation(walkingLeft);
		FrameAnimation walkAniEnemy = new FrameAnimation(enemyWalking);
//		FrameAnimation walkUpAni = new FrameAnimation(walkingUp);
//		FrameAnimation jumpAni = new FrameAnimation(jumping);
		
		
		
		
		/*************BACKGROUND TEXTURES*************/
		
		bgTile = glTexImageTGAFile(gl, "purpletile.tga",tileSize); //wall tile
		bgTile2 = glTexImageTGAFile(gl, "purpletile2.tga", tileSize); //floor tile
		tableTile1 = glTexImageTGAFile(gl, "tabble1.tga", tileSize);
		tableTile2 = glTexImageTGAFile(gl, "tabble2.tga", tileSize);
		tableTile3 = glTexImageTGAFile(gl, "tabble3.tga", tileSize);
		tableTile4 = glTexImageTGAFile(gl, "tabble4.tga", tileSize);
		
		bgMain = new Background(bgTile2, false, gridWidth, gridHeight); //main purple floor tile
		bgItems = new Background(bgTile, false, 1, 1);
		//bgItems = new Background(bgTile, true, gridWidth-100, gridHeight-100);
		
		bgMain.getTile(0, 0).setImage(bgTile);
		bgMain.getTile(0, 1).setImage(bgTile);
		bgMain.getTile(0, 2).setImage(bgTile);
		bgMain.getTile(1, 0).setImage(bgTile);
		bgMain.getTile(1, 1).setImage(bgTile);
		bgMain.getTile(1, 2).setImage(bgTile);
		bgMain.getTile(2, 0).setImage(bgTile);
		bgMain.getTile(2, 1).setImage(bgTile);
		bgMain.getTile(2, 2).setImage(bgTile);
		bgMain.getTile(3, 0).setImage(bgTile);
		bgMain.getTile(3, 1).setImage(bgTile);
		bgMain.getTile(3, 2).setImage(bgTile);
		bgMain.getTile(4, 0).setImage(bgTile);
		bgMain.getTile(4, 1).setImage(bgTile);
		bgMain.getTile(4, 2).setImage(bgTile);
		bgMain.getTile(5, 0).setImage(bgTile);
		bgMain.getTile(5, 1).setImage(bgTile);
		bgMain.getTile(5, 2).setImage(bgTile);
		bgMain.getTile(6, 0).setImage(bgTile);
		bgMain.getTile(6, 1).setImage(bgTile);
		bgMain.getTile(6, 2).setImage(bgTile);
		bgMain.getTile(7, 0).setImage(bgTile);
		bgMain.getTile(7, 1).setImage(bgTile);
		bgMain.getTile(7, 2).setImage(bgTile);
		bgMain.getTile(8, 0).setImage(bgTile);
		bgMain.getTile(8, 1).setImage(bgTile);
		bgMain.getTile(8, 2).setImage(bgTile);
		bgMain.getTile(9, 0).setImage(bgTile);
		bgMain.getTile(9, 1).setImage(bgTile);
		bgMain.getTile(9, 2).setImage(bgTile);
		bgMain.getTile(10, 0).setImage(bgTile);
		bgMain.getTile(10, 1).setImage(bgTile);
		bgMain.getTile(10, 2).setImage(bgTile);

		//table
		bgMain.getTile(1, 3).setImage(tableTile1);
		bgMain.getTile(1, 4).setImage(tableTile3);
		bgMain.getTile(2, 3).setImage(tableTile2);
		bgMain.getTile(2, 4).setImage(tableTile4);
		
		bgMain.getTile(1, 7).setImage(tableTile1);
		bgMain.getTile(1, 8).setImage(tableTile3);
		bgMain.getTile(2, 7).setImage(tableTile2);
		bgMain.getTile(2, 8).setImage(tableTile4);

		//Initialize characters 
		CharacDef chara = new CharacDef(spritePos[0], spritePos[1], spriteSize[0], spriteSize[1], spriteTex);
		chara.setHealth(3);
		
		//projectile: make an array of projectiles 
		projectile = glTexImageTGAFile(gl, "projectile.tga", projectileSize);
		
	    //size of the world 
	    worldHeight = gridHeight * tileSize[0];
	    worldWidth = gridHeight * tileSize[1];
	     
		/*************AABB bounding box****************/
		AABBbox spriteBox;
		AABBbox cameraBox;
		spriteBox = new AABBbox (chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight());
		cameraBox = new AABBbox (cam.getX(), cam.getY(), windowHeight, windowWidth);
		
		
		/***Array of enemies***/
		ArrayList<CharacDef> enemies = new ArrayList<CharacDef>();
		enemies.add(new CharacDef(enemyPos[0] + 200, enemyPos[1], enemySize[0], enemySize[1], enemyTex));
		enemies.add(new CharacDef(enemyPos[0] + 500, enemyPos[1]+500, enemySize[0], enemySize[1], enemyTex));
		enemies.add(new CharacDef(enemyPos[0] + 100, enemyPos[1]+300, enemySize[0], enemySize[1], enemyTex));
		enemies.add(new CharacDef(enemyPos[0] + 70, enemyPos[1]+100, enemySize[0], enemySize[1], enemyTex));	
		
		///////////////////////////////////////////////
		//Frame and Time variables
		
		// Physics Time Frames
		long lastFrame;
		long currentFrame = System.nanoTime();
		int lastPhysicsFrameMs = 0;
		long curFrameMs = 0;
		int physicsDeltaMs = 10;
		
		//Regular Time Frames
        long lastFrameNS;
        long curFrameNS = System.nanoTime();
        while (!shouldExit) {
        	
            System.arraycopy(kbState, 0, kbPrevState, 0, kbState.length);
            lastFrameNS = curFrameNS; //NS = nano secs
            curFrameNS = System.nanoTime();
            long deltaTimeMS = (curFrameNS - lastFrameNS) / 1000000;
            
            // Actually, this runs the entire OS message pump.
            window.display();
            if (!window.isVisible()) {
                shouldExit = true;
                break;
            }
              
            //key events
            int right = KeyEvent.VK_RIGHT;
            int left = KeyEvent.VK_LEFT;
            int up = KeyEvent.VK_UP;
            int down = KeyEvent.VK_DOWN;

        	/********PHYSICS UPDATE********/
            do{
            	for (int i = 0; i < chara.getProjectile().size(); i++) {
					ArrayList<Projectile> projectile1 = (ArrayList<Projectile>) chara.getProjectile();
					Projectile p = projectile1.get(i);
					p.setUpdate();
					//update the projectile
            	}
            	
            	//if chara is jumping and shot --> health
                physicsDeltaMs = 1000;
            	lastPhysicsFrameMs += physicsDeltaMs;
            }
          	while (lastPhysicsFrameMs + physicsDeltaMs < curFrameMs );
            
            
		/************************************************************************/        
		/*********************** Game logic goes here.***************************/
		/************************************************************************/
            if (kbState[KeyEvent.VK_ESCAPE]) {
                shouldExit = true;
            }
            
            //CharacDef chara = new CharacDef(spritePos[0], spritePos[1], spriteSize[0], spriteSize[1], spriteTex);
            //public CharacDef (int x, int y, int height, int width, int spriteTex)
            /***************KEYBOARD STATES****************/
            if (kbState[left] == true) {
            	chara.setX((int) (chara.getX() - deltaTimeMS * 0.5));
	        	walkAni.updateSprite(deltaTimeMS);
				chara.setCurrentTexture(walkAni.getCurrentFrame());
	        }
	        
	        if (kbState[right] == true) {
	        	chara.setX((int) (chara.getX() + deltaTimeMS * 0.5));
	        	walkAni.updateSprite(deltaTimeMS);
	        	chara.setCurrentTexture(walkAni.getCurrentFrame());
	        }
	        
	        if (kbState[up] == true) {
	        	chara.setY((int) (chara.getY() - deltaTimeMS * 0.5));
	        	walkAni.updateSprite(deltaTimeMS);
	        	chara.setCurrentTexture(walkAni.getCurrentFrame());
				chara.setReverse(true);
	        }
	        
	        if (kbState[down] == true) {
	        	chara.setY((int) (chara.getY() + deltaTimeMS * 0.5));
	        	walkAni.updateSprite(deltaTimeMS);
	        	chara.setCurrentTexture(walkAni.getCurrentFrame());
	         }
	        
	        
	        //for the main sprite, keep in bounds of the main background
	        if (chara.getX() < 0)
	        	chara.setX(0);
	        if (chara.getX() > worldWidth - spriteSize[0])
	        	chara.setX(worldWidth - spriteSize[0]);
	        if (chara.getY() < 0) 
	        	chara.setY(0);
	        if (chara.getY() > worldHeight- spriteSize[1])
	        	chara.setY(worldWidth - spriteSize[1]);
	         
	        
	        if (kbState[KeyEvent.VK_SPACE] == true){
	        	chara.addProjectile(new Projectile(chara.getX() + 50, chara.getY(), projectileSize[0],
						projectileSize[1], false));
	        	chara.setShooting(false);
	        }
	        
	        //JUMPING
	        if (kbState[KeyEvent.VK_W] == true){
	        	if (chara.isGrounded()== true && kbState[KeyEvent.VK_W]){
	        		chara.setAcceleration(5);
	        	}
	        	if (chara.isGrounded()) { 
	        		int yVelocity = 0; int jumpVelocity = 4; int yPos; int gravity = 1;
	        			if (chara.isGrounded() && kbState[KeyEvent.VK_W])
	        			 yVelocity = jumpVelocity;
	        			 yPos = (int) (chara.getY() + yVelocity * deltaTimeMS);
	        			 yVelocity = (int) (yVelocity + gravity * deltaTimeMS);
	        	}
	        }
	      
	        

	        /****************CAMERA STUFF*****************/	        
	        //cam.x = spritePos[0] - (cam.width/2);
	        //cam.y = spritePos[1] - (cam.height/2);
	        cam.x = chara.getX() - (cam.width/2);
	        cam.y = chara.getY() - (cam.height/2);
	        
			if (cam.x < 0) {cam.setX(0);}
			if (cam.y < 0) {cam.setY(0);}
			if (cam.x > worldWidth - cam.width) {cam.setX(worldWidth - cam.width);}
			if (cam.y > worldHeight - cam.height) {cam.setY(worldHeight - cam.height);}
		
	        
	        //set up AABB box stuff between the camera and sprite
			//need collision resolution AABBbox
			spriteBox.setX(spritePos[0]);
			spriteBox.setY(spritePos[1]);
			spriteBox.setWidth(spriteSize[0]);
			spriteBox.setHeight(spriteSize[1]);

			cameraBox.setX(cam.getX());
			cameraBox.setY(cam.getY());
			cameraBox.setWidth(windowWidth);
			cameraBox.setHeight(windowHeight);
		    
	        //enemy sprite stuff
		    if (enemyPos[0] > chara.getX() + 20){
		    	enemyPos[0] -= target_direction * 3;
		    }
		    if (enemyPos[0] < chara.getX() - 20){
		    	enemyPos[0] += target_direction * 3;
		    }
		    if (enemyPos[1] > chara.getY() + 20){
		    	enemyPos[1] -= target_direction * 3;
		    }
		    if (enemyPos[1] < chara.getY() - 20){
		    	enemyPos[1] += target_direction * 3;
		    }

		   //check if bg is in bounds w/in camera
		   // int bgCheck = backgroundCheck(cam.getX(), cam.getY());
			int startX = (int) Math.floor(cam.x / tileSize[0]);
			int endX = (int)Math.ceil(cam.x+ windowWidth  / tileSize[0]);
			int startY = (int) Math.floor(cam.y / tileSize[1]);
			int endY = (int)Math.ceil(cam.y+ windowHeight / tileSize[1]);
			
			
            /*****************************************************************
             *************************DRAW SPRITES****************************
             *****************************************************************/
			gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
            
            
            //DRAW THE BACKGROUND
			for (int x = startX; x < Math.min(bgMain.getWidth(), endX); x++) {
				for (int y = startY; y < Math.min(bgMain.getHeight(), endY); y++) {
						glDrawSprite(gl,bgMain.getTile(x, y).getImage(), 
								x * tileSize[0] - cam.getX(),
								y * tileSize[1] - cam.getY(), 
								tileSize[0], 
								tileSize[1]);
				}
			}
			
			//DRAW THE COLLISIONS
			
			
			//DRAW THE PROJECTILE
			ArrayList<Projectile> charaProjectiles = (ArrayList<Projectile>) chara.getProjectile();
			for (int i = 0; i < charaProjectiles.size(); i++) {
				Projectile p = charaProjectiles.get(i);
				glDrawSprite(gl, projectile, 
								p.getX() - cam.getX(), 
								p.getY() - cam.getY(),
								projectileSize[0], 
								projectileSize[1]);
			}
			
			//DRAW ENEMY SPRITES
			for (CharacDef e : enemies) {
				if (e.getVisible()) {
					if (AABBIntersect(cameraBox, e.charaHitbox())) {
						glDrawSprite(gl, e.getCurrentTexture(), (e.getX()) - cam.x, (e.getY()) - cam.y,
								enemySize[0], enemySize[1]);
					}
				}
			}
			 
			
			//DRAW THE SPRITE
			glDrawSprite(gl, chara.getCurrentTexture(), chara.getX() - cam.x, chara.getY() - cam.y, spriteSize[0], spriteSize[1]);	
        }
    }

    
    //**AABBIntersect: If they don't intersect then one box must be above, below, to the
    //left, or to the right of the other box
    //box 1 = camera; box 2 = sprite
    public static boolean AABBIntersect(AABBbox box1, AABBbox box2){
		// box1 to the right
		if (box1.getX() > box2.getX() + box2.getWidth()) {
			return false;
		}
		// box1 to the left
		if (box1.getX() + box1.getWidth() < box2.getX()) {
			return false;
		}
		// box1 below
		if (box1.getY() > box2.getY() + box2.getHeight()) {
			return false;
		}
		// box1 above
		if (box1.getY() + box1.getHeight() < box2.getY()) {
			return false;
		}
		return true;
	}
    
    //check if the background is in bounds of the camera
	public static int backgroundCheck(int x, int y) {
		int tile_x = (int)(Math.floor(y / tileSize[1]));
		int tile_y = (int)(Math.floor(x / tileSize[0])); 
		//return (x / tileSize[0]) + ((y / tileSize[1]) * worldHeight);
		return tile_y * tile_x;
	}
   
    
    // Load a file into an OpenGL texture and return that texture.
    public static int glTexImageTGAFile(GL2 gl, String filename, int[] out_size) {
        final int BPP = 4;

        DataInputStream file = null;
        try {
            // Open the file.
            file = new DataInputStream(new FileInputStream(filename));
        } catch (FileNotFoundException ex) {
            System.err.format("File: %s -- Could not open for reading.", filename);
            return 0;
        }

        try {
            // Skip first two bytes of data we don't need.
            file.skipBytes(2);

            // Read in the image type.  For our purposes the image type
            // should be either a 2 or a 3.
            int imageTypeCode = file.readByte();
            if (imageTypeCode != 2 && imageTypeCode != 3) {
                file.close();
                System.err.format("File: %s -- Unsupported TGA type: %d", filename, imageTypeCode);
                return 0;
            }

            // Skip 9 bytes of data we don't need.
            file.skipBytes(9);

            int imageWidth = Short.reverseBytes(file.readShort());
            int imageHeight = Short.reverseBytes(file.readShort());
            int bitCount = file.readByte();
            file.skipBytes(1);

            // Allocate space for the image data and read it in.
            byte[] bytes = new byte[imageWidth * imageHeight * BPP];

            // Read in data.
            if (bitCount == 32) {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = file.readByte();
                }
            } else {
                for (int it = 0; it < imageWidth * imageHeight; ++it) {
                    bytes[it * BPP + 0] = file.readByte();
                    bytes[it * BPP + 1] = file.readByte();
                    bytes[it * BPP + 2] = file.readByte();
                    bytes[it * BPP + 3] = -1;
                }
            }
            file.close();

            
            // Load into OpenGL
            int[] texArray = new int[1];
            gl.glGenTextures(1, texArray, 0);
            int tex = texArray[0];
            gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
            gl.glTexImage2D(
                    GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, imageWidth, imageHeight, 0,
                    GL2.GL_BGRA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(bytes));
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

            out_size[0] = imageWidth;
            out_size[1] = imageHeight;
            return tex;
        }
        catch (IOException ex) {
            System.err.format("File: %s -- Unexpected end of file.", filename);
            return 0;
        }
    }

    public static void glDrawSprite(GL2 gl, int tex, int x, int y, int w, int h) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
        gl.glBegin(GL2.GL_QUADS);
        {
            gl.glColor3ub((byte)-1, (byte)-1, (byte)-1);
            gl.glTexCoord2f(0, 1);
            gl.glVertex2i(x, y);
            gl.glTexCoord2f(1, 1);
            gl.glVertex2i(x + w, y);
            gl.glTexCoord2f(1, 0);
            gl.glVertex2i(x + w, y + h);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2i(x, y + h);
        }
        gl.glEnd();
    }
}
