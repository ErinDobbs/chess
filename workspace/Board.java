
//
//  Class author:  Erin-Elle Dobbs 
//  Date created:  3/6/2025
//  General description: makes the board for chess
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
    private static final String RESOURCES_WROOK_PNG = "wrook.png";
    private static final String RESOURCES_BROOK_PNG = "brook.png";
    private static final String RESOURCES_WKING_PNG = "wking.png";
    private static final String RESOURCES_BKING_PNG = "bking.png";
    private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = "bpawn.png";

    private final Square[][] board;
    private final GameWindow g;

    private boolean whiteTurn;
    private Piece currPiece;
    private Square fromMoveSquare;
    private int currX;
    private int currY;

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // Populate board with alternating color squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isWhite = (row + col) % 2 == 0;
                board[row][col] = new Square(this, isWhite, row, col);
                this.add(board[row][col]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare) {
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                }
                sq.paintComponent(g);
            }
        }
        if (currPiece != null) {
            final Image img = currPiece.getImage();
            g.drawImage(img, currX, currY, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            System.out.println(currPiece.getColor() != whiteTurn);
            if (currPiece.getColor() != whiteTurn) {
                currPiece = null;
                return;
            }

            ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);
            for (Square move : legalMoves) {
                move.setBackground(Color.RED);
            }

            sq.setDisplay(false);
        }
        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (fromMoveSquare != null) {
            fromMoveSquare.setDisplay(true);
        }

        if (currPiece != null && endSquare != null) {
            ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);

            if (legalMoves.contains(endSquare)) {
                Piece originalPiece = endSquare.getOccupyingPiece();
// temp move 
                endSquare.put(currPiece);
                fromMoveSquare.removePiece();


					// Check if king is safe
				 	boolean isInCheck = this.isKingInCheck(currPiece.getColor());
	 // Undo the move
     fromMoveSquare.put(currPiece);
     endSquare.put(originalPiece);
     if (!isInCheck) {
        endSquare.put(currPiece);
        fromMoveSquare.removePiece();
        whiteTurn = !whiteTurn; // Change turn
    }

				 
				 }



            

        }
        if (currPiece != null) {
            for (Square s : currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.unhilight();
                s.setBorder(null);
            }
        }
        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        if (currPiece != null) {
            for (Square s : currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.highlightSquare();

            }
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void initializePieces() {
        // Place rooks
        board[0][0].put(new Rook(true, RESOURCES_WROOK_PNG));
        board[0][7].put(new Rook(true, RESOURCES_WROOK_PNG));
        board[7][0].put(new Rook(false, RESOURCES_BROOK_PNG));
        board[7][7].put(new Rook(false, RESOURCES_BROOK_PNG));

        // Place knights
        board[0][1].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[0][6].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][1].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[7][6].put(new Knight(false, RESOURCES_BKNIGHT_PNG));

        // Place bishops
        board[0][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[0][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[7][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));

        // Place queens
        board[0][3].put(new Queen(true, RESOURCES_WQUEEN_PNG));
        board[7][3].put(new Queen(false, RESOURCES_BQUEEN_PNG));

        // Place kings
        board[0][4].put(new King(true, RESOURCES_WKING_PNG));
        board[7][4].put(new King(false, RESOURCES_BKING_PNG));

        // Place pawns
        for (int col = 0; col < 8; col++) {
            board[1][col].put(new Assassin(true, RESOURCES_WPAWN_PNG));
            board[6][col].put(new Assassin(false, RESOURCES_BPAWN_PNG));
        }
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    public boolean isKingInCheck(boolean kingColor) {

        // Check if any opponent piece attacks king's square
        for (Square[] row : board) {
            for (Square square : row) {
                if (square.isOccupied() && square.getOccupyingPiece().getColor() != kingColor) {
                    ArrayList<Square> controlled = square.getOccupyingPiece().getControlledSquares(board, square);
                    for (Square control : controlled) {
                        if (control.isOccupied() && control.getOccupyingPiece() instanceof King
                                && control.getOccupyingPiece().getColor() == kingColor) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }
}
