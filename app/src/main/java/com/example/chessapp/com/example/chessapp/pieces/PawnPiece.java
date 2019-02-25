package com.example.chessapp.com.example.chessapp.pieces;

import android.content.Context;

import com.example.chessapp.ChessBoard;
import com.example.chessapp.ChessPiece;

public class PawnPiece extends ChessPiece {
    public PawnPiece (int resourceId, int squareLength, PieceColor color, Context context, int i, int j){
        super(resourceId, ChessPiece.Piece.PAWN, color, context, i*squareLength, j*squareLength);
    }

    @Override
    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        return validatePawnMove(newX, newY, oldX, oldY, chessBoard);
    }

    private boolean validatePawnMove(int newX, int newY, int oldX, int oldY, ChessBoard chessBoard) {
        int changeX = newX-oldX;
        int changeY = newY-oldY;

        if(changeX != 0){
            return false;
        }

        ChessPiece piece = chessBoard.getPiece(oldX,oldY);

        if(changeY < 0 && piece.getColor() != ChessPiece.PieceColor.WHITE) {
            return false;
        }

        if(changeY > 0 && piece.getColor() != ChessPiece.PieceColor.BLACK) {

            return false;
        }

        if(changeY < 0) {
            changeY = changeY * -1;
        }

        boolean isFirstMove = ((oldY == 6 && piece.getColor() == ChessPiece.PieceColor.WHITE) || (oldY == 1 && piece.getColor() == ChessPiece.PieceColor.BLACK));

        if(changeY > 1 && !isFirstMove) {
            return false;
        }

        return true;
    }
}
