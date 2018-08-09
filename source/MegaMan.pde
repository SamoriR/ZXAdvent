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
