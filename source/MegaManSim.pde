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
