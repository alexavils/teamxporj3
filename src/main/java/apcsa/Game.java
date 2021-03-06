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
  private String[] userPic = {"images/Baby.png","images/PreTeen.png","images/Teen.png"};
  private String[] empic = {"images/BuildingBlocks.png","images/Broomstick.png","images/Books.png"};
  private String[] gudpic = {"images/BabyFood.png","images/SoccerBall.png","images/Phone.png"};
  private String[] gameBackground = {"images/Crib.jpg","images/PreTeen.jpg","images/TeenRoom.png"};
  private int stage = 0;
  private double health = 3;
  private double speed = 1.0;
  
  public Game() {

    grid = new Grid(6, 10);
    grid.setBackground(new Color(128, 255, 175));
    userRow = 3;
    userCol = 0;
    msElapsed = 0;
    updateTitle();
    grid.fullscreen();
   
  }
  
  public void play() {
String retryMessage = "yes";
while(retryMessage.equals("yes")){

    int count = 0;
    while (!isGameOver()) {
      count++;
      grid.pause((int) (100/speed));
      handleKeyPress();

      if (msElapsed % 300 == 0) {
        handleCollision();
        scrollLeft();
        populateRightEdge();            
        updateBackground();
      }

      updateTitle();
      updateStage();
      msElapsed += 100;
    }

    retryMessage = grid.showInputDialog("Do you want to try again? yes no");
    stage = 0;
    health = 3;
    msElapsed = 0;
    userRow = 3;
    userCol = 0;
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
        if(empic[stage].equals(grid.getImage(loc))){
          health -= 1;
        }
        grid.setImage(loc, userPic[stage]);
        
        Location oldLoc = new Location(userRow+1, userCol);
        grid.setImage(oldLoc, null);

  }
    //if I push down arrow, then plane goes down
    if(key == 83 && userRow != grid.getNumRows()-1){
      //check case where out of bounds

      //change the field for userrow

      userRow++;

      //shift the user picture up in the array
      Location loc = new Location(userRow, userCol);
      if(empic[stage].equals(grid.getImage(loc))){
        health -= 1;
      }
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

  if(msElapsed < 30000 )  {
    stage = 0;
    speed = 0.75;
  } else if (msElapsed < 60000) {
    stage = 1;
    speed = 1.5;
  } else if (msElapsed < 90000){
    stage = 2;
    speed = 2.0;
  } else {
    stage = 3;
  }
}

public void updateBackground(){

  grid.setBackgroundImage(this.gameBackground[stage]);
  // if(msElapsed < 30000 ){
    
  // }
  // else if (msElapsed < 60000){
  //   grid.setBackgroundImage("Images/PreTeen.jpg");
  // }
  // else if (msElapsed < 90000){
  //   grid.setBackgroundImage("Images/TeenRoom.png");
  // }
  


}


  public void populateRightEdge(){
  int lastCol = grid.getNumCols() - 1;
  int lastRow  = grid.getNumRows() - 1;

  for( int r = 0; r <= lastRow; r++){

 Location loc = new Location(r,lastCol);

 double rando = Math.random();
 double thresh = .01 * 3;

 if(rando < thresh){

grid.setImage(loc, this.gudpic[stage]);

}
else if(rando < .05 * 3 ){

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
  
  public void handleCollision() {
    


    //save the location to look at
    Location dog = new Location(userRow, userCol+1);
    
    if(empic[stage].equals(grid.getImage(dog))){
      health -= 1;
    }
    
    if(gudpic[stage].equals(grid.getImage(dog))){
      health += .25;
    }



  }
  
  public double getScore() {
    return health;
  }
  
  public void updateTitle() {
    grid.setTitle("GROWING UP        Health:  " + getScore() + "        Time Left Until Stage Shift : " + ((90 - (msElapsed / 1000)) % 30) + " Seconds " + "         By : Alex & Jorge ");

  }
  
  public boolean isGameOver() {
    if(getScore()<=0){
    grid.showMessageDialog("Congrats, you have failed at Growing Up! Please Try Again");
    return true;  
  }
    if(stage == 3){
    grid.showMessageDialog("Congrats, you are now a full Grown Up! Thanks For Playing");
    return true;
    }
    else return false;
  
}
  public static void main(String[] args) {
    Game game = new Game();
    game.play();   
  }
}