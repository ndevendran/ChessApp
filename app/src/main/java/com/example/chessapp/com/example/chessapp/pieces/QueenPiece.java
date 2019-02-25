package com.example.chessapp.com.example.chessapp.pieces;

import android.content.Context;

import com.example.chessapp.ChessPiece;
import com.example.chessapp.ChessBoard;

public class QueenPiece extends ChessPiece {
    public QueenPiece (int resourceId, int squareLength, PieceColor color, Context context, int i, int j){
        super(resourceId, ChessPiece.Piece.QUEEN, color, context, i*squareLength, j*squareLength);
    }

    @Override
    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        return (validateBishopMove(newX, newY, oldX, oldY, chessBoard) || validateRookMove(newX, newY, oldX, oldY, chessBoard));
    }

    private boolean validateRookMove(int newX, int newY, int oldX, int oldY, ChessBoard chessBoard) {
        int changeX = newX - oldX;
        int changeY = newY - oldY;

        if(changeX != 0 && changeY != 0) {
            return false;
        }


        //Check all squares in between for pieces in the way
        if(changeY == 0){
            for(int i= oldX+1; i< newX; i++) {
                if(chessBoard.getPiece(i,oldY) != null) {
                    return false;
                }
            }
        } else {
            for(int i=oldY+1; i< newY; i++) {
                if(chessBoard.getPiece(oldX,i) != null) {
                    return false;
                }
            }

        }

        ChessPiece targetPiece = chessBoard.getPiece(newX,newY);
        ChessPiece movingPiece = chessBoard.getPiece(oldX,oldY);
        if(targetPiece != null) {
            if(targetPiece.getColor() == movingPiece.getColor()) {
                return false;
            }
        }

        return true;

    }

    private boolean validateBishopMove(int newX, int newY, int oldX, int oldY, ChessBoard chessBoard) {
        int changeX = newX - oldX;
        int changeY = newY - oldY;

        if(changeX < 0) {
            changeX = changeX * -1;
        }

        if(changeY < 0) {
            changeY = changeY * -1;
        }

        if(changeX != changeY) {
            return false;
        }

        int i = oldX;
        int j = oldY;

        if(oldX > newX && oldY > newY) {
            i--;
            j--;
            while(i > newX && j > newY) {
                if(chessBoard.getPiece(i,j) != null) {
                    return false;
                }

                i--;
                j--;
            }
        }

        if(oldX > newX && oldY < newY) {
            i--;
            j++;
            while(i > newX && j < newY) {
                if(chessBoard.getPiece(i,j) != null) {
                    return false;
                }

                i--;
                j++;
            }


        }

        if(oldX < newX && oldY < newY) {
            i++;
            j++;
            while(i < newX && j< newY) {
                if(chessBoard.getPiece(i,j) != null) {
                    return false;
                }

                i++;
                j++;
            }
        }

        if(oldX < newX && oldY > newY ) {
            i++;
            j--;
            while(i < newX && j > newY) {
                if(chessBoard.getPiece(i,j) != null) {
                    return false;
                }

                i++;
                j--;
            }
        }



        ChessPiece targetPiece = chessBoard.getPiece(newX,newY);
        ChessPiece movingPiece = chessBoard.getPiece(oldX,oldY);

        if(targetPiece != null) {
            if(targetPiece.getColor() == movingPiece.getColor()) {
                return false;
            }
        }


        return true;
    }
}
