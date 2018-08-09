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
