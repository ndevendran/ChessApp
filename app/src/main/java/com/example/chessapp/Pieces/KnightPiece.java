package com.example.chessapp.Pieces;

import android.content.Context;

import com.example.chessapp.ChessBoard;
import com.example.chessapp.ChessPiece;

public class KnightPiece extends ChessPiece {
    public KnightPiece (int resourceId, int squareLength, PieceColor color, Context context, int i, int j){
        super(resourceId, ChessPiece.Piece.KNIGHT, color, context, i*squareLength, j*squareLength);
    }

    @Override
    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        return validateKnightMove(newX, newY, oldX, oldY);
    }

    @Override
    public boolean isValidAttack(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        ChessPiece movingPiece = chessBoard.getPiece(oldX, oldY);
        ChessPiece targetPiece = chessBoard.getPiece(newX, newY);

        if(movingPiece.getColor() != targetPiece.getColor() && isValidMove(oldX, oldY, newX, newY, chessBoard)) {
            return true;
        }

        return false;
    }

    private boolean validateKnightMove(int newX, int newY, int oldX, int oldY) {
        int changeX = newX-oldX;
        int changeY = newY-oldY;

        if(changeX < 0) {
            changeX = changeX * -1;
        }

        if(changeY < 0) {
            changeY = changeY * -1;
        }


        if(changeX == 1 && changeY == 2) {
            return true;
        } else if(changeY == 1 && changeX == 2) {
            return true;
        } else {
            return false;
        }
    }
}
