package apcsa;

import java.awt.Color;

//import org.graalvm.compiler.phases.common.NodeCounterPhase.Stage;

/* Game Class Starter File
 * Last Edit: 5/25/2020
 */

public class Game {

  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private String[] userPic = {"images/Mario.png","images/Mario.png","images/Mario.png"};
  private int stage = 0;
  private String getPic = "images/get.gif";
  
  public Game() {

    grid = new Grid(10, 5);
    grid.setBackground(new Color(128, 255, 175));
    userRow = 3;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), userPic[stage]);
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
        Location loc = new Location(userRow, 0);
        grid.setImage(loc, userPic[stage]);
        
        Location oldLoc = new Location(userRow+1, 0);
        grid.setImage(oldLoc, null);

  }
    //if I push down arrow, then plane goes down
    if(key == 83 && userRow != 9){
      //check case where out of bounds

      //change the field for userrow

      userRow++;

      //shift the user picture up in the array
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, userPic[stage]);
      
      Location oldLoc = new Location(userRow-1, 0);
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
 double thresh = .3;

 if(rando < thresh){

grid.setImage(loc, this.getPic);

}
}
}
  
  public void scrollLeft(){

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