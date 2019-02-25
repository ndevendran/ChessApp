package com.example.chessapp.com.example.chessapp.pieces;

import com.example.chessapp.ChessPiece;
import android.content.Context;
import com.example.chessapp.ChessBoard;

public class BishopPiece extends ChessPiece {
    public BishopPiece (int resourceId, int squareLength, PieceColor color, Context context){
        super(resourceId, ChessPiece.Piece.BISHOP, color, context, 2*squareLength, 7*squareLength);
    }

    @Override
    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        return validateBishopMove(newX, newY, oldX, oldY, chessBoard);
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
