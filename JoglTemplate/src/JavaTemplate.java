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
import java.util.Random;

import javax.sound.sampled.Clip;

import static java.lang.Math.atan2;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaTemplate {
    // Set this to true to make the game loop exit.
    private static boolean shouldExit;

    // The previous frame's keyboard state.
    private static boolean kbPrevState[] = new boolean[256];

    // The current frame's keyboard state.
    private static boolean kbState[] = new boolean[256];

    // Position of the sprite.
    private static int[] spritePos = new int[] {150, 300};
    private static int[] enemyPos = new int[] {spritePos[0]-50, spritePos[1]-50};
   

	// Initialize texture variables for sprite, projectile.
    private static int spriteTex,
				       enemyTex;
    private static int projectile;
    
    //initialize background
    private static Tile[][] floorWallTiles;
    private static Tile[][] tableTiles;
    private static ArrayList<Tile> tiles = new ArrayList<Tile>();
    	
    // Size of the sprite/tiles/projectiles
    private static int[] spriteSize = new int[2];
    private static int[] enemySize = new int[2];
    private static int[] tileSize = new int[2];
    private static int[] projectileSize = new int[2];
    
    //Size of window size
    private static int windowWidth = 400;
    private static int windowHeight = 400;
    
    //size of the game play
    private static int worldHeight = 15; 
    private static int worldWidth = 80;
    
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
        
/******************SPRITE ANIMATION*******************************************************************/
        
        /************ LOAD SPRITE TEX***********/
		spriteTex = glTexImageTGAFile(gl, "1.tga", spriteSize);
		enemyTex = glTexImageTGAFile(gl, "1.tga", enemySize);
		
		/************** CHARACTERS*****************/
		//Initialize characters 
		CharacDef chara = new CharacDef(spritePos[0], spritePos[1], spriteSize[0], spriteSize[1], spriteTex);
		chara.setHealth(3);
		
		//for the main character
        FrameDef[] walking= { 
			new FrameDef(glTexImageTGAFile(gl, "2.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "3.tga", spriteSize), (float) 2 ),				
			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "5.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "6.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "7.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "8.tga", spriteSize), (float) 2 )
        };
        FrameDef[] walkingLeft= { 
			new FrameDef(glTexImageTGAFile(gl, "28.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "29.tga", spriteSize), (float) 2 ),				
			new FrameDef(glTexImageTGAFile(gl, "30.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "31.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "32.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "33.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "34.tga", spriteSize), (float) 2 ),
			new FrameDef(glTexImageTGAFile(gl, "35.tga", spriteSize), (float) 2 )
        };
        //jumping
//        FrameDef[] jumping= { 
//			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
//			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),				
//			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
//			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
//			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
//			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 ),
//			new FrameDef(glTexImageTGAFile(gl, "4.tga", spriteSize), (float) 1 )
//        };
        
        
        //for enemy
        FrameDef[] enemyWalking= { 
			new FrameDef(glTexImageTGAFile(gl, "20.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "21.tga", enemySize), (float) 80 ),				
			new FrameDef(glTexImageTGAFile(gl, "22.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "23.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "22.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "21.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "20.tga", enemySize), (float) 80 )
        };
        FrameDef[] enemyWalkingRight= { 
			new FrameDef(glTexImageTGAFile(gl, "24.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "25.tga", enemySize), (float) 80 ),				
			new FrameDef(glTexImageTGAFile(gl, "26.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "27.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "26.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "25.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "24.tga", enemySize), (float) 80 )
        };
        FrameDef[] enemySpinning= { 
			new FrameDef(glTexImageTGAFile(gl, "24.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "37.tga", enemySize), (float) 80 ),				
			new FrameDef(glTexImageTGAFile(gl, "26.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "38.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "24.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "37.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "38.tga", enemySize), (float) 80 ),
			new FrameDef(glTexImageTGAFile(gl, "26.tga", enemySize), (float) 80 )
        };
        

		FrameAnimation walkAni = new FrameAnimation(walking);
		FrameAnimation walkAniLeft = new FrameAnimation(walkingLeft);
//		FrameAnimation jumpingAni = new FrameAnimation(jumping);
		FrameAnimation walkAniEnemy = new FrameAnimation(enemyWalking);
		FrameAnimation walkAniEnemyRight = new FrameAnimation(enemyWalkingRight);
		FrameAnimation spinningEnemy = new FrameAnimation(enemySpinning);
		
		
		/*************BACKGROUND TEXTURES*************/
		System.out.println(tiles + " \n");
		
		//from file 10.tga to 12.tga
		for (int y = 10; y < 13; y++){
			String filename = y + ".tga";
			if (y == 11){
				tiles.add(new Tile(glTexImageTGAFile(gl, filename, tileSize), filename, tileSize, true));
			}
			else{
				tiles.add(new Tile(glTexImageTGAFile(gl, filename, tileSize), filename, tileSize, false));
			}
		}
		
		//from file 13.tga to 17.tga
		for (int y = 13; y < 20; y++){
			String filename2 = y + ".tga";
			if (y == 13 || y == 14 || y == 15 || y == 16 || y == 18 || y == 19){
				tiles.add(new Tile(glTexImageTGAFile(gl, filename2, tileSize), filename2, tileSize, true));
			}
			else{
				tiles.add(new Tile(glTexImageTGAFile(gl, filename2, tileSize), filename2, tileSize, false));
			}
		}
		
	    loadBG();
	        
		/***************** SOUNDS *****************/
		//Sound
		SoundClip soundBackgroundMain = SoundClip.loadFromFile("bgsound.wav");
        
        //Load
    	Clip bgClip = soundBackgroundMain.playLooping();
        boolean bgPlaying = false;
		
		/************** PROJECTILE*****************/
		projectile = glTexImageTGAFile(gl, "projectile.tga", projectileSize);

		/**************ARRAY OF ENEMIES****************/
		ArrayList<CharacDef> enemies = new ArrayList<CharacDef>();
		enemies.add(new Enemy(enemyPos[0] + 50, enemyPos[1]+200, enemySize[0], enemySize[1], enemyTex));
		enemies.add(new Enemy(enemyPos[0] + 300, enemyPos[1]+400, enemySize[0], enemySize[1], enemyTex));
		enemies.add(new Enemy(enemyPos[0] + 560, enemyPos[1]+300, enemySize[0], enemySize[1], enemyTex));
		
		/*************AABB BOUNDING BOX****************/
	    cam.width = windowWidth; 
	    cam.height = windowHeight;
	    
	    cam.getAABBIntersection().setHeight(windowHeight);
	    cam.getAABBIntersection().setWidth(windowHeight);
	    AABBbox tileBox;
		AABBbox spriteBox;
		AABBbox enemyBox;
		
		///////////////////////////////////////////////
		//Frame and Time variables
		
		// Physics Time Frames
		long lastPhysicsFrameMS = 0;
		long curFrameMS = 0;
		double physicsDeltaMS = 0.1;
		
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
               
               //character projectiles
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
            	
    			int charaTileX = (int) Math.floor(chara.getX()/tileSize[0]);
    			int charaTileY = (int) Math.floor(chara.getY()/tileSize[1]);
    			for (int x = Math.max(charaTileX-1, 0); x <= Math.min((charaTileX+1), 79); x++){
    				for(int y = Math.max(charaTileY-1, 0); y <= Math.min((charaTileY+1), 15); y++){
    					if (floorWallTiles[y][x].isCollision() || tableTiles[y][x].isCollision()){
                            tileBox = new AABBbox(x * tileSize[0], y * tileSize[1], tileSize[0], tileSize[1]);
                            spriteBox = new AABBbox (chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight());
                            
                            while (AABBbox.AABBIntersect(tileBox, spriteBox)){
                            	spriteBox = new AABBbox (chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight());
            					System.out.println("sprite and tile collision : " + AABBbox.AABBIntersect(tileBox, spriteBox));
            					if (x < tileBox.getX() && x < tileBox.getX() + tileBox.getWidth()){
	                            	if(tileBox.getX() > chara.getX()){
	                            		 chara.setX((chara.getX()-1));
	                            	}
	                            	if(tileBox.getX() < chara.getX()){
	                           		 	chara.setX((chara.getX()+1));
	                           		 	System.out.println(" get x 2 " + chara.getX());
	                            	}
	                            	System.out.println(" get x " +chara.getX());
            					}
            					if (y < tileBox.getY() && y < tileBox.getY() + tileBox.getHeight()){
	                            	if(tileBox.getY() > chara.getY()){
	                              		 chara.setY((chara.getY()-1)); 
	                               	}
	                            	if(tileBox.getY() < chara.getY()){
	                             		 chara.setY((chara.getY()+1));
	                             		System.out.println(" get y 2 " + chara.getY());
	                              	}
	                            	System.out.println("get y " + chara.getY());
            					}
                            }
                            spriteBox = new AABBbox (chara.getX(), chara.getY(), chara.getWidth(), chara.getHeight());
                           
    					}
    					
    				}
    			}
    			
    			
    			/*ENEMY PORTION*/
    			
    		    //atan2() //enemy sprite follow
    			double target_direction;
    			for (CharacDef ene : enemies) {
    				//enemy projectiles
    				for (int i = 0; i < ene.getProjectile().size(); i++) {
						ArrayList<Projectile> enemyProjectiles = (ArrayList<Projectile>) ene.getProjectile();
						Projectile p = enemyProjectiles.get(i);
						p.setUpdate();
						//update the projectile
						//collision box projectile
						AABBbox projectile = enemyProjectiles.get(i).getCollisionBox();
						if (AABBIntersect(projectile, chara.charaHitbox())) {
							enemyProjectiles.get(i).setVisible(false);
							enemyProjectiles.remove(i);
							chara.setHealth(chara.getHealth() - 1);
							if(chara.getHealth() < 1){
								chara.setDead(true);
							}
							chara.setHit(!chara.isShot());
						}
						
    				} //end of enemy projectiles
    				
    				int timecounter;
        			Random ran = new Random();
        			float nextRandomFloat = ran.nextFloat();
        			System.out.println(nextRandomFloat);
    				target_direction = atan2(chara.getY() - ene.getY(), chara.getX() - ene.getX());
    				//if chara on right, reverse enemy sprite
    				if (chara.getX() > ene.getX()){
    					walkAniEnemyRight.updateSprite(4);	
    					ene.setCurrentTexture(walkAniEnemyRight.getCurrentFrame());
    				}
    				//if chara on left
    				else{
    					walkAniEnemy.updateSprite(4);
    					ene.setCurrentTexture(walkAniEnemy.getCurrentFrame());
    				}
    				
    				//BDRF = bidirectional radial function
    				//rand on actions
    				//enemy stands still 0 < x < 0.5
        			if (nextRandomFloat < .25 && nextRandomFloat >= 0.0){
    					ene.setX(ene.getX());
        				ene.setY(ene.getY());
        			}
        			else if (nextRandomFloat < .5 && nextRandomFloat >= .25){
        				if (chara.getX() - ene.getX() <= 150 ){
            				ene.setX((ene.getX() + (Math.cos(target_direction))));
            				ene.setY((ene.getY() + (Math.sin(target_direction))));
        				}
        			}
        			//enemy moves towards player if 0.5 < x < 0.75
        			else if (nextRandomFloat < .75 && nextRandomFloat >= .5){
        				if (chara.getX() > ene.getX()){
            				ene.setX((ene.getX() + (Math.cos(target_direction))));
            				ene.setY((ene.getY() + (Math.sin(target_direction))));
        				}
        			}
        			//enemy moves toward player if 0.75 < x < 1.0
        			else{
        				spinningEnemy.updateSprite(1);	
    					ene.setCurrentTexture(spinningEnemy.getCurrentFrame());
        				ene.setX(ene.getX());
        				ene.setY(ene.getY());
        			}

        			int enemyTileX = (int) Math.floor(ene.getX() / tileSize[0]);
        			int enemyTileY = (int) Math.floor(ene.getY()/ tileSize[1]);
    			}
    		
          
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
            	walkAniLeft.updateSprite(deltaTimeMS);
				chara.setCurrentTexture(walkAniLeft.getCurrentFrame());
	        }
	        
	        if (kbState[right] == true) {
	        	chara.setX((int) (chara.getX() + deltaTimeMS * 0.5));
	        	walkAni.updateSprite(deltaTimeMS);
	        	chara.setCurrentTexture(walkAni.getCurrentFrame());
	        }
	        
	        if (kbState[up] == true) {
	        	chara.setY((int) (chara.getY() - deltaTimeMS * 0.5));
	        	walkAniLeft.updateSprite(deltaTimeMS);
	        	chara.setCurrentTexture(walkAniLeft.getCurrentFrame());
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
	        	chara.setX(worldWidth*tileSize[0] - spriteSize[0]);
	        	System.out.println(chara.getX());}
	        if (chara.getY() < 0) {
	        	chara.setY(0);}
	        if (chara.getY() >= worldHeight*tileSize[1] - spriteSize[1]){
	        	chara.setY(worldHeight*tileSize[1] - spriteSize[1]);
	        	System.out.println(chara.getY());}
	         
	        
	        if (kbState[KeyEvent.VK_SPACE] == true){
	        	chara.addProjectile(new Projectile((int)chara.getX() + 40, (int)chara.getY(), projectileSize[0],
						projectileSize[1], false));
	        	chara.setShooting(false);
	        }
	        
	        /****************CAMERA STUFF*****************/	        
	        cam.x = (int)chara.getX() - (cam.width/2);
	        cam.y = (int)chara.getY() - (cam.height/2);
	     
			if (cam.x < 0) {cam.setX(0);}
			if (cam.y < 0) {cam.setY(0);}
			if (cam.x > worldWidth * tileSize[0] - cam.width) {cam.setX(worldWidth * tileSize[0] - cam.width);}
			if (cam.y > worldHeight * tileSize[1] - cam.height) {cam.setY(worldHeight * tileSize[1] - cam.height);}
		    
			//if character gets to end of bg, game ends
			//add boss, need projectiles
			
			
            /*****************************************************************
             *************************DRAW SPRITES****************************
             *****************************************************************/
			gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT);         
            
            //DRAW THE BACKGROUND
            for (int x = 0; x < 80; x++) {
                for (int y = 0; y < 15; y++) {
                   glDrawSprite(gl, floorWallTiles[y][x].image, (x * tileSize[0]) - cam.getX(), (y * tileSize[1]) - cam.getY(), tileSize[0], tileSize[1]);
                
                }
            }
					//tables
            for (int x = 0; x < 80; x++) {
               for (int y = 0; y < 15; y++) {
                   glDrawSprite(gl, tableTiles[y][x].image, (x * tileSize[0]) - cam.getX(), (y * tileSize[1]) - cam.getY(), tileSize[0], tileSize[1]);
               
               }
            }

			//DRAW THE PROJECTILE
			ArrayList<Projectile> charaProjectiles = (ArrayList<Projectile>) chara.getProjectile();
			for (int i = 0; i < charaProjectiles.size(); i++) {
				Projectile p = charaProjectiles.get(i);
				glDrawSprite(gl, projectile, 
								(int)(p.getX() - cam.getX()), 
								(int)(p.getY() - cam.getY()),
								projectileSize[0], 
								projectileSize[1] );
			}
			
			//DRAW ENEMY PROJECTILES
			for (CharacDef e : enemies) {
					ArrayList<Projectile> enemyProjectiles = (ArrayList<Projectile>) e.getProjectile();
					for (int i = 0; i < enemyProjectiles.size(); i++) {
						Projectile p = enemyProjectiles.get(i);
						glDrawSprite(gl, projectile, (int) p.getX() - cam.getX(), (int) p.getY() - cam.getY(),
								projectileSize[0], projectileSize[1]);
					}
			}
			
			//DRAW ENEMY SPRITES
			for (CharacDef e : enemies) {
				if (e.getVisible()) {
					glDrawSprite(gl, e.getCurrentTexture(), 
						(int)(e.getX()) - cam.getX(), 
						(int)(e.getY()) - cam.getY(),
						enemySize[0], enemySize[1]);
				}
			}
			 
			
			//DRAW THE SPRITE
			glDrawSprite(gl, chara.getCurrentTexture(), 
					(int)chara.getX() - cam.getX() + tileSize[0]/4, 
					(int)chara.getY() - cam.getY()-  tileSize[1]/2, 
					spriteSize[0], spriteSize[1]);	
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
    	floorWallTiles = new Tile[][] {
    		{tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1)},
    		{tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1)},
    		{tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(2),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1)},
    		{tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(0),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1)},
    		{tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1),tiles.get(1)}
        };
    	tableTiles = new Tile[][]{
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(9),tiles.get(3),tiles.get(4),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(3),tiles.get(4),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(9),tiles.get(5),tiles.get(6),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(5),tiles.get(6),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(3),tiles.get(4),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(5),tiles.get(6),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(3),tiles.get(4),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(9),tiles.get(5),tiles.get(6),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(3),tiles.get(4),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(9),tiles.get(8),tiles.get(9),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(9),tiles.get(5),tiles.get(6),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(8),tiles.get(9),tiles.get(9),tiles.get(8),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)},
    		{tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7),tiles.get(7)}
};
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
