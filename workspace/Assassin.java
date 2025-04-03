//
//  Class author:  Erin-Elle Dobbs 
//  Date created:  3/6/2025
//  Piece Name: Assassin
//  General description: make a piece that moves 4 spaces in any direction
//

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

//you will need to implement two functions in this file.
public class Assassin extends Piece {

    
    public Assassin(boolean isWhite, String img_file) {
        super(isWhite, img_file);
        
       
    }
    
    
    
    
 public String toString(){
            return "A" +super.toString() + " Assassin";
 }

    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.

    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
      ArrayList<Square> controlledSquares = new ArrayList<>();
    
                        
      int startX = start.getX();
      int startY = start.getY();
      
      // Only diagonal captures at 2 spaces
      int[][] captureDirections = {
          {2, 2}, {2, -2}, {-2, 2}, {-2, -2} 
      };
  
      for (int[] dir : captureDirections) {
          int captureX = startX + dir[0];
          int captureY = startY + dir[1];
  
          if (isValidSquare(captureX, captureY, board)) {
              controlledSquares.add(board[captureX][captureY]);
          }
      }
  
      return controlledSquares;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.


    public ArrayList<Square> getLegalMoves(Board b, Square start){

         // piece can move in any direction 4 spaces and captures with 2 spaces 

         ArrayList<Square> legalMoves = new ArrayList<>();
         Square[][] board = b.getSquareArray();
         
         int startX = start.getRow();
         int startY = start.getCol();
     
         
         int[][] directions = {
             {1, 0}, {-1, 0}, {0, 1}, {0, -1},  
             {1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; 
     
         // Move up to 4 spaces in any direction
         for (int[] dir : directions) {
             for (int i = 1; i <= 4; i++) {
                 int newX = startX + dir[0] * i;
                 int newY = startY + dir[1] * i;
     
                 if (isValidSquare(newX, newY, board)) {
                     legalMoves.add(board[newX][newY]);
                 

        
                 }
             }
         }
         System.out.println(legalMoves);
         return legalMoves;
       }
       private boolean isValidSquare(int x, int y, Square[][] board) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }
  
    
   
    }
  
