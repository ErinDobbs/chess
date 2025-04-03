

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.Highlighter.Highlight;


//Please read the following class carefully! It represents a single chess board square and is what you'll be using
//to represent the chessboard.
@SuppressWarnings("serial")
public class Square extends JComponent {
	//a reference back to the board that stores this square.
    private Board b;
    
    //true for white, false for black.
    private final boolean color;
    //if there's a piece on the square this stores it. If there isn't this stores null.
    private Piece occupyingPiece;
    
    //if desired you can use this to retain the piece where it is but make it invisible to the user. 
    //True means to display the piece. This property will be switched to false when we are dragging a piece around while choosing our next move.
    private boolean dispPiece;
    
    //the coordinates of the square.
    private int row;
    private int col;
    private Color myColor;
    
    public Square(Board b, boolean isWhite, int row, int col) {
        
        this.b = b;
        this.color = isWhite;
        if(color){
            myColor = new Color(221,192,127);
        }
        else{
            myColor = new Color(101,67,33);
        }
        this.dispPiece = true;
        this.row = row;
        this.col = col;
        
        
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    
    public boolean getColor() {
        return this.color;
    }
    
    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }
    
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
    
    public int getRow() {
        return this.row;
    }
    
    public int getCol() {
        return this.col;
    }
    
    public void setDisplay(boolean v) {
        this.dispPiece = v;
    }
    
    public void put(Piece currPiece) {
        this.occupyingPiece = currPiece;
    }
    
    public Piece removePiece() {
        Piece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }

    public void highlightSquare(){
      myColor= Color.red;
    }

    public void unhilight(){
        if(color){
            myColor = new Color(221,192,127);
        }
        else{
            myColor = new Color(101,67,33);
        }

    }
    







    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        

            g.setColor(myColor);
        
        
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        if(occupyingPiece != null && dispPiece) {
            occupyingPiece.draw(g, this);
        }
    }

   
    
    
}
