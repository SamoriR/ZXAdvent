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
    
    if ((y < 150 && speed < 0.0) || (y > 250 && speed > 0.0)){
      speed *= -1.0;
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
