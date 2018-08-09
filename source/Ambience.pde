class Ambience{
  
  PImage back;
  PImage foreground;
  float x = -5.0;
  float y = 0.0;
  float speedB = .2;
  
  Ambience(){
    back = loadImage("backG.png");
    back.resize(back.width * 2, back.height * 2);
  }
  
  public void draw(){
    image(back, x, y);
    x -= speedB;
    if(x + back.width < 507 || x > -5.0){
      speedB *= -1;
    } 
  }
  
  
  
}
