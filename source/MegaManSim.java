import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MegaManSim extends PApplet {

MegaMan m;
Ambience a;
Platform[] platforms;

public void setup() {
  m = new MegaMan();
  a = new Ambience();
  platforms = new Platform[4];
  platforms[0] = new Platform(30, 200);
  platforms[1] = new Platform(150, 200);
  platforms[2] = new Platform(270, 200);
  platforms[3] = new Platform(400, 200);
}



public void settings() {
  size(512, 384);
}



public void draw() {
  a.draw();
  for (int i = 0; i < platforms.length; i++){
    platforms[i].draw();
  }
  m.draw(platforms);
}



public void keyPressed(){
  if(key == 'w'){
    m.jump();
    
  } else if (key == 'a') {
    m.left();
    
  } else if (key == 'd') {
    m.right();
    
  }
}



public void keyReleased(){
  if(key == 'w'){
    
  } else if (key == 'a') {
    m.leftStop();
    
  } else if (key == 'd') {
    m.rightStop();
    
  }
}
class Ambience{
  
  PImage back;
  PImage foreground;
  float x = -5.0f;
  float y = 0.0f;
  float speedB = .2f;
  
  Ambience(){
    back = loadImage("backG.png");
    back.resize(back.width * 2, back.height * 2);
  }
  
  public void draw(){
    image(back, x, y);
    x -= speedB;
    if(x + back.width < 507 || x > -5.0f){
      speedB *= -1;
    } 
  }
  
  
  
}
class Boundary {

  int x1;
  int x2;
  int y1;
  int y2;
  boolean horizontal;
  
  Boundary(int x1, int x2, int y1, int y2, boolean horizontal) {
    this.x1 = Math.max(x1, x2);
    this.x2 = Math.min(x1, x2);
    this.y1 = Math.max(y1, y2);
    this.y2 = Math.min(y1, y2);
    this.horizontal = horizontal;
  }
  
  public boolean checkCollision(int posX, int posY){
    if (horizontal){
      if (Math.abs(posY - y1) < 15 && posX >= x2 && posX <= x1){
        return true;
      } else {
        return false;
      }
      
    } else {
      if (Math.abs(posX - x1) < 15 && posY >= y2 && posY <= y1){
        return true;
      } else {
        return false;
      }
  
    }
  }
  
  public int getY(){
    return y1;
  }
  
  public void update(int x1, int x2, int y1, int y2){
    this.x1 = Math.max(x1, x2);
    this.x2 = Math.min(x1, x2);
    this.y1 = Math.max(y1, y2);
    this.y2 = Math.min(y1, y2);
  }
}
class MegaMan {
  
  PImage[] idleLArr;
  PImage[] idleRArr;
  PImage[] walkLArr;
  PImage[] walkRArr;
  PImage[] jumpLArr;
  PImage[] jumpRArr;
  PImage[] fallLArr;
  PImage[] fallRArr;
  
  Boundary[] boundaries;
  int bArrayLen = 3;
  
  int x;
  int y;
  
  int startT;
  int rate = 90;
  int jumpHeight = 0;
  
  boolean jumping = true;
  boolean facingLeft = false;
  boolean facingRight = true;
  boolean left = false;
  boolean right = false;
  
  
  
  MegaMan () {
    this.x = 250;
    this.y = 100;
    this.startT = millis();
    
    idleLArr = loadAni("grey/MMIdleL_", 9);
    idleRArr = loadAni("grey/MMIdleR_", 9);
    walkLArr = loadAni("grey/MMWalkL_", 11);
    walkRArr = loadAni("grey/MMWalkR_", 11);
    jumpLArr = loadAni("grey/MMJumpL_", 4);
    jumpRArr = loadAni("grey/MMJumpR_", 4);
    fallLArr = loadAni("grey/MMFallL_", 5);
    fallRArr = loadAni("grey/MMFallR_", 5);
    
    boundaries = new Boundary[bArrayLen];
    boundaries[0] = new Boundary(65, 385, 360, 360, true);
    boundaries[1] = new Boundary(25, 175, 210, 210, true);
    boundaries[2] = new Boundary(275, 475, 110, 110, true);
  }
  
  
  
  public PImage[] loadAni(String name, int len) {
    PImage[] arr = new PImage[len];
    for (int i = 0; i < len; i++){
      String fileName = name + i + ".png";
      arr[i] = loadImage(fileName);
      System.out.println(fileName + " has been loaded");

    }
    return arr;
    
  }
  
  
  
  public void draw(Platform[] p) {
    move(p);
    if (!(left || right || jumping)){
      if (facingLeft){
        animate(idleLArr);
        
      } else if (facingRight) {
        animate(idleRArr);
        
      }
      
    } else if (jumping){
      if (jumpHeight >= 0){
        if (facingLeft) {
          animate(jumpLArr);
          
        } else if (facingRight) {
          animate(jumpRArr);
          
        }
      } else {
        if (facingLeft) {
          animate(fallLArr);
          
        } else if (facingRight) {
          animate(fallRArr);
          
        }
      }
      
      
    } else {
      
      if (left) {
        animate(walkLArr);
        
      } else if (right) {
        animate(walkRArr);
        
      }
    }
  }
  
  
  
  private void animate(PImage[] arr){
    image(arr[(millis() - startT)/rate % arr.length], x, y);
  }
  
  
  
  private void move(Platform[] p){
    boolean touching = false;
    Boundary temp = null;
    
    for (int i = 0; i < p.length && !touching; i++){
      if (p[i].checkCollision(x, y)){
        touching = true;
        temp = p[i].getBound();
      }
    }
    
    if (!touching){
      y -= jumpHeight;
      jumpHeight--;
      jumping = true;
      
    } else if (jumpHeight > 0) {
      y -= jumpHeight;
      jumpHeight--;
      jumping = true;
      
    } else {
      y = temp.getY();
      jumpHeight = 0;
      jumping = false;
      
    }
    
    if (left) {
      x -= 5;
    } else if (right){
      x += 5;
      
    }
    
    if (y > 384){
      y = 0;
    }
    if (x > 512){
      x = 0;
    }
    if (x < 0){
      x = 512;
    }
  }
  
  
  
  public void jump() {
    if(!jumping){
      jumpHeight = 16;
      y -= 16;
      
    }
  }
  
  
  
  public void left(){
    left = true;
    right = false;
    faceLeft();
  }
  
  
  
  public void leftStop(){
    left = false;
    faceLeft();
  }
  
  
  
  public void right(){
    right = true;
    left = false;
    faceRight();
  }
  
  
  
  public void rightStop(){
    right = false;
    faceRight();
  }
  
  
  
  private void faceLeft(){
    facingLeft = true;
    facingRight = false;
  }
  
  
  
  private void faceRight(){
    facingLeft = false;
    facingRight = true;
  }
}
class Platform {
 
  int x;
  float y;
  PImage[] stages;
  Boundary b;
  int startT;
  float speed;
  
  Platform(int x, int y) {
    this.x = x;
    this.y = y;
    startT = millis();
    stages = new PImage[4];
    b = new Boundary(this.x - 10, this.x + 60, (int)this.y - 30, (int)this.y - 30, true);
    
    for (int i = 0; i < 4; i++){
      String fileName = "platform/platform_" + i + ".png";
      stages[i] = loadImage(fileName);
      System.out.println(fileName + " has been loaded");
    }
    
    speed = random(-1, 1);
    
    
  }
  
  public void draw(){
    image(stages[(millis() - startT)/200 % stages.length], x, y);
    y += speed;
    
    if ((y < 150 && speed < 0.0f) || (y > 250 && speed > 0.0f)){
      speed *= -1.0f;
    }
    b.update(this.x - 10, this.x + 60, (int)this.y - 30, (int)this.y - 30);
  }
  
  public boolean checkCollision(int x, int y){
    return b.checkCollision(x, y);
  }
  
  public Boundary getBound(){
    return b; 
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MegaManSim" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
