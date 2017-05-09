import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.*;


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import static java.lang.Math.atan2;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


//background stopped painting so need to fix that for HWK 5
//need to return in hwk4
//invisible collidable 

public class JavaTemplate {
    // Set this to true to make the game loop exit.
    private static boolean shouldExit;

    // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];

    // Position of the sprite.
    private static int[] spritePos = new int[] {200, 50};
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
    				   projectile;
//					   tableTile1,
//					   tableTile2,
//					   tableTile3,
//					   tableTile4;
    
    private static int projTile;
    
    //initialize background
    private static Tile[][] floorTiles;
    private static Tile[][] tableTiles;
    private static Tile[][] backgroundLevelTiles;
    
    private static Background bgMain, 
    						  bgObjects,
    						  bgLevel;
    
//    private static BackgroundTiles bgTiles;  
    private static ArrayList<Tile> tiles = new ArrayList<Tile>();
    	
    // Size of the sprite/tiles/projectiles
    private static int[] spriteSize = new int[2];
    private static int[] enemySize = new int[2];
    private static int[] tileSize = new int[2];
    private static int[] projectileSize = new int[2];
    
    //Size of window size
    private static int windowWidth = 400;
    private static int windowHeight = 400;
   
    //How big the grid is in the background in tiles.
//    private static int gridHeight = 15;
//    private static int gridWidth = 35;
    
    //size of the game play
    private static int worldHeight = 15; 
    private static int worldWidth = 50;
    
    //initialize camera
    private static Camera cam = new Camera(0,0);
   
    
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
	    /************** CHARACTERS*****************/
		//Initialize characters 
		CharacDef chara = new CharacDef(spritePos[0], spritePos[1], spriteSize[0], spriteSize[1], spriteTex);
		chara.setHealth(3);
		
		//for the main character
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
        FrameDef[] jumping= { 
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),				
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 )
        };
        
        
        //enemy animation
        
		FrameAnimation walkAni = new FrameAnimation(walking);
		FrameAnimation walkAniLeft = new FrameAnimation(walkingLeft);
		FrameAnimation jumpingAni = new FrameAnimation(jumping);
		FrameAnimation walkAniEnemy = new FrameAnimation(enemyWalking);
		
		
		/*************BACKGROUND TEXTURES*************/
		bgTile = glTexImageTGAFile(gl, "10.tga",tileSize); //wall tile
//		bgTile2 = glTexImageTGAFile(gl, "11.tga", tileSize); //floor tile
//		//invisible tile
//		tableTile1 = glTexImageTGAFile(gl, "12.tga", tileSize);
//		tableTile2 = glTexImageTGAFile(gl, "13.tga", tileSize);
//		tableTile3 = glTexImageTGAFile(gl, "14.tga", tileSize);
//		tableTile4 = glTexImageTGAFile(gl, "15.tga", tileSize);
		
		//the walls
			String filename = "10.tga";
			tiles.add(new Tile(bgTile, filename, tileSize, false));
			System.out.println(filename);
			System.out.println(tiles + " \n");
	
//		//floor tile!!
//		tiles.add(new Tile(glTexImageTGAFile(gl,"background\\bgmain\\11.tga", tileSize), "11",tileSize, false));
//        Tile[][] floorBG = new Tile[80][80];
//        for (int y = 0; y < 80; y++) {
//            for (int x = 0; x < 80; x++) {
//                floorBG[y][x] = tiles.get(10);
//            }
//        }
//		
//	

		
//		bgMain1 = new Tile(bgTile, true); //main purple floor tile
//		bgMain = new Background(bgTile, true, gridWidth, gridHeight);
//		bgObjects = new Background(tableTile1, true, 5,5)
	    loadBG();
	        
	   
		
		/***************** SOUNDS *****************/
		
		
		/************** PROJECTILE*****************/
		projectile = glTexImageTGAFile(gl, "projectile.tga", projectileSize);
		
	    //size of the world 
//	    worldHeight = gridHeight * tileSize[0];
//	    worldWidth = gridWidth * tileSize[1];
	     cam.width = windowWidth; 
	     cam.height = windowHeight;
	     
		/*************AABB bounding box****************/
	    cam.getAABBIntersection().setHeight(windowHeight);
	    cam.getAABBIntersection().setWidth(windowHeight);
	    AABBbox  tileBox;
		AABBbox spriteBox;
//		AABBbox cameraBox;
		spriteBox = new AABBbox (chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight());
//		cameraBox = new AABBbox (cam.getX(), cam.getY(), windowHeight, windowWidth);
		
		
		/***Array of enemies***/
		ArrayList<CharacDef> enemies = new ArrayList<CharacDef>();
		enemies.add(new Enemy(enemyPos[0] + 200, enemyPos[1], enemySize[0], enemySize[1], enemyTex));
		enemies.add(new Enemy(enemyPos[0] + 15, enemyPos[1]+100, enemySize[0], enemySize[1], enemyTex));
		enemies.add(new Enemy(enemyPos[0] + 100, enemyPos[1]+300, enemySize[0], enemySize[1], enemyTex));
		
		///////////////////////////////////////////////
		//Frame and Time variables
		
		// Physics Time Frames
		long lastPhysicsFrameMS = 0;
		long curFrameMS = 0;
		int physicsDeltaMS = 10;
		
		//Regular Time Frames
        long lastFrameNS;
        long curFrameNS = System.nanoTime();
        lastPhysicsFrameMS = System.nanoTime() / 1000000;
        
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
            	if (chara.isGrounded()){
            		chara.setyVelocity(0);
            	}
            	for (int i = 0; i < chara.getProjectile().size(); i++) {
					ArrayList<Projectile> projectile1 = (ArrayList<Projectile>) chara.getProjectile();
					Projectile p = projectile1.get(i);
					p.setUpdate();
					//update the projectile
					//collision box projectile
					AABBbox projectile = projectile1.get(i).getCollisionBox();
					for (CharacDef c : enemies) {
						if (AABBIntersect(projectile, c.charaHitbox()) && !(i > projectile1.size() - 1)) {
							projectile1.get(i).setVisible(false);
							projectile1.remove(i);
							c.setHealth(c.getHealth() - 1);
							c.setHit(!c.isShot());
							if (c.getHealth() <= 0) {
								c.setDead(true);
								c.setVisible(false);
							}
						}
					}
            	 }
            	
     		   //collision for bg
//    			int startX = (int) Math.floor(chara.getX() / tileSize[0]);
//    			int endX = (int)Math.ceil((chara.getX() + windowWidth)/ tileSize[0]);
//    			int startY = (int) Math.floor(chara.getY()/ tileSize[1]);
//    			int endY = (int)Math.ceil((chara.getY() + windowHeight)/ tileSize[1]);
//    			for (int x = startX; x < endX; x++){
//    				for(int y = startY; y < endY; y++){
//    					if (floorTiles[y][x].isCollision()){
//                            tileBox = new AABBbox(x * tileSize[0], y * tileSize[1], tileSize[0], tileSize[1]);
//    					}
//    					
//    				}
//    			}
    			
            	
            	//if chara is jumping and shot --> health
                physicsDeltaMS = 1000;
            	lastPhysicsFrameMS += physicsDeltaMS;
            }
          	while (lastPhysicsFrameMS + physicsDeltaMS < curFrameMS );
            
            
		/************************************************************************/        
		/*********************** Game logic goes here.***************************/
		/************************************************************************/
            if (kbState[KeyEvent.VK_ESCAPE]) {
                shouldExit = true;
            }

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
	        if (chara.getX() < 0){
	        	chara.setX(0);}
	        if (chara.getX() >= worldWidth * tileSize[0] - spriteSize[0]){
	        	chara.setX(worldWidth* tileSize[0] - spriteSize[0]);}
	        if (chara.getY() < 0) {
	        	chara.setY(0);}
	        if (chara.getY() >= worldHeight*tileSize[1] - spriteSize[1]){
	        	chara.setY(worldHeight*tileSize[1] - spriteSize[1]);
	        	System.out.println(chara.getY());}
	         
	        
	        if (kbState[KeyEvent.VK_SPACE] == true){
	        	chara.addProjectile(new Projectile(chara.getX() + 50, chara.getY(), projectileSize[0],
						projectileSize[1], false));
	        	chara.setShooting(false);
	        }
	        
//        	if (chara.isGrounded()== true && kbState[KeyEvent.VK_W]){
//    			chara.setAcceleration(5);
	//    	}
	//    	if (chara.isGrounded()) { 
	//    		int yVelocity = 0; int jumpVelocity = 4; int yPos; int gravity = 1;
	//    		if (chara.isGrounded() && kbState[KeyEvent.VK_W])
	//    		yVelocity = jumpVelocity;
	//    		yPos = (int) (chara.getY() + yVelocity * deltaTimeMS);
	//    		yVelocity = (int) (yVelocity + gravity * deltaTimeMS);
	//    	}
	        
//	        double zVelocity = chara.getyVelocity();
//	        double zSpeed= 10;
//	        double zHeight = 0;
//	        double zPosition = chara.getY();
//	        double yPosition = chara.getY();
//	        double yVelocity = chara.getyVelocity();
//	        double gravity = 0.1;
//
//	        
//	        if (zPosition <= 0){
//	        	zSpeed = 0;
//	        	zPosition = 0;
//	        }
//	        if (kbState[KeyEvent.VK_W] == true && !chara.isJumping()){
//	        	
//	        	zSpeed = 10;
//	        }
//	        zPosition += zSpeed;
//	        zSpeed -= gravity;
	        
	        
 

	        /****************CAMERA STUFF*****************/	        
	        //cam.x = spritePos[0] - (cam.width/2);
	        //cam.y = spritePos[1] - (cam.height/2);
	        cam.x = chara.getX() - (cam.width/2);
	        cam.y = chara.getY() - (cam.height/2);
	      
	        
			if (cam.x < 0) {cam.setX(0);}
			if (cam.y < 0) {cam.setY(0);}
			if (cam.x > worldWidth) {cam.setX(worldWidth * tileSize[0] - cam.width);}
			if (cam.y > worldHeight) {cam.setY(worldHeight * tileSize[1] - cam.height);}
		
	        
	        //set up AABB box stuff between the camera and sprite
			//need collision resolution AABBbox
			spriteBox.setX(spritePos[0]);
			spriteBox.setY(spritePos[1]);
			spriteBox.setWidth(spriteSize[0]);
			spriteBox.setHeight(spriteSize[1]);

//			cameraBox.setX(cam.getX());
//			cameraBox.setY(cam.getY());
//			cameraBox.setWidth(windowWidth);
//			cameraBox.setHeight(windowHeight);
		    
	        //enemy sprite follow
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
			
            /*****************************************************************
             *************************DRAW SPRITES****************************
             *****************************************************************/
			gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
 		   //check if bg is in bounds w/in camera
 			int startX = (int) Math.floor(cam.x / tileSize[0]);
 			int endX = (int)Math.ceil(cam.x+ windowWidth  / tileSize[0]);
 			int startY = (int) Math.floor(cam.y / tileSize[1]);
 			int endY = (int)Math.ceil(cam.y+ windowHeight / tileSize[1]);
			            
            
            //DRAW THE BACKGROUND
//			for (int x = startX; x < Math.min(bgMain.getWidth(), endX); x++) {
//				for (int y = startY; y < Math.min(bgMain.getHeight(), endY); y++) {
//						glDrawSprite(gl,bgMain.getTile(x, y).getImage(), 
//								x * tileSize[0] - cam.getX(),
//								y * tileSize[1] - cam.getY(), 
//								tileSize[0], 
//								tileSize[1]);
//				}
//			} 
		
           for (int x = 0; x < 80; x++) {
                for (int y = 0; y < 15; y++) {
                    glDrawSprite(gl, floorTiles[y][x].image, (x * tileSize[0]) - cam.getX(), (y * tileSize[1]) - cam.getY(), tileSize[0], tileSize[1]);
//                	glDrawSprite(gl, floorTiles[y][x].image, x, y, tileSize[0], tileSize[1]);
                
                }
           }
			

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
					if (AABBIntersect(spriteBox, e.charaHitbox())) {
						glDrawSprite(gl, e.getCurrentTexture(), (e.getX()) - cam.x, (e.getY()) - cam.y,
								enemySize[0], enemySize[1]);
					}
				}
			}
			 
			
			//DRAW THE SPRITE
			glDrawSprite(gl, chara.getCurrentTexture(), chara.getX() - cam.getX(), chara.getY() - cam.getY(), spriteSize[0], spriteSize[1]);	
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
    
    public static void loadBG(){
    	System.out.print("Before: " + floorTiles);
    	floorTiles = new Tile[][] {
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)},
    		  {tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0)}
    		};
    	System.out.print("After: " + floorTiles);
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
