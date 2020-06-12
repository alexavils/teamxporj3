package apcsa;

import java.awt.Color;

//import org.graalvm.compiler.phases.common.NodeCounterPhase.Stage;

/* Game Class Starter File
 * Last Edit: 5/25/2020
 */

public class Game {

  private Grid grid;
  private int userRow;
  private int userCol;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private String[] userPic = {"images/Mario.png","images/Mario.png","images/Mario.png"};
  private String[] empic = {"images/avoid.gif","images/avoid.gif","images/avoid.gif"};
  private String[] gudpic = {"images/get.gif","images/get.gif","images/get.gif"};
  private int stage = 0;
  
  public Game() {

    grid = new Grid(10, 20);
    grid.setBackground(new Color(128, 255, 175));
    userRow = 3;
    userCol = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    //grid.setImage(new Location(userRow, 0), userPic[stage]);
    grid.fullscreen();
  }
  
  public void play() {

    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      updateStage();
      msElapsed += 100;
    }
  }
  
  public void handleKeyPress(){

   //check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);

    //set "w" key to move the plane up
    if(key == 87 && userRow != 0){
        //check case where out of bounds

        //change the field for userrow

        userRow--;

        //shift the user picture up in the array
        Location loc = new Location(userRow, userCol);
        grid.setImage(loc, userPic[stage]);
        
        Location oldLoc = new Location(userRow+1, userCol);
        grid.setImage(oldLoc, null);

  }
    //if I push down arrow, then plane goes down
    if(key == 83 && userRow != 9){
      //check case where out of bounds

      //change the field for userrow

      userRow++;

      //shift the user picture up in the array
      Location loc = new Location(userRow, userCol);
      grid.setImage(loc, userPic[stage]);
      
      Location oldLoc = new Location(userRow-1, userCol);
      grid.setImage(oldLoc, null);

}
if(key == 68 && userCol != grid.getNumCols()/2){
  //check case where out of bounds

  //change the field for userrow
  userCol++;
 
 
  //shift the user picture up in the array

  Location loc = new Location(userRow, userCol);
  grid.setImage(loc, userPic[stage]);
  
   Location oldLoc = new Location(userRow, userCol-1);
  grid.setImage(oldLoc, null);


}
if(key == 65 && userCol != 0){
  //check case where out of bounds

  //change the field for userrow
  userCol--;
 
 
  //shift the user picture up in the array

  Location loc = new Location(userRow, userCol);
  grid.setImage(loc, userPic[stage]);
  
   Location oldLoc = new Location(userRow, userCol+1);
  grid.setImage(oldLoc, null);


}


  }
public void updateStage(){

if(msElapsed < 30000 ) stage = 0;
else if (msElapsed < 60000) stage = 1;
else if (msElapsed < 90000) stage = 2; 
else stage = 3;

}

  public void populateRightEdge(){
  int lastCol = grid.getNumCols() - 1;
  int lastRow  = grid.getNumRows() - 1;

  for( int r = 0; r <= lastRow; r++){

 Location loc = new Location(r,lastCol);

 double rando = Math.random();
 double thresh = .01;

 if(rando < thresh){

grid.setImage(loc, this.gudpic[stage]);

}
else if(rando < .05 ){

  grid.setImage(loc, this.empic[stage]);

}
}
}
  
  public void scrollLeft(){

    //get the last column

    int lastCol = grid.getNumCols()-1;
    int lastRow = grid.getNumRows() - 1;

    //loop through each column
    for(int c = 1; c < lastCol; c++){

    //right and left collumn 
    int rightCol = c+1;
    int leftCol = c;

    //loop through each row
    for ( int r = 0; r <= lastRow; r++ ){
    
    //move items from right to left
    Location rightloc = new Location(r, rightCol);
    Location leftLoc = new Location(r, leftCol);

    //copy picture
   String rightPic = grid.getImage(rightloc);
   if(!userPic[stage].equals(rightPic))
    grid.setImage(leftLoc, rightPic );

    //erase old picture
    grid.setImage(rightloc, null);

    }

  }

  grid.setImage(new Location(userRow, userCol), userPic[stage]);
  }
  
  public void handleCollision(Location loc) {
  
  }
  
  public int getScore() {
    return 0;
  }
  
  public void updateTitle() {
    grid.setTitle("Game:  " + getScore());
  }
  
  public boolean isGameOver() {
    if(stage == 3)
    return true;
    else return false;
  
}
  public static void main(String[] args) {
    Game game = new Game();
    game.play();   
  }
}