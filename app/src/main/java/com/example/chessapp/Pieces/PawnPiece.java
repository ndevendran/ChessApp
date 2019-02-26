package com.example.chessapp.Pieces;

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

    @Override
    public boolean isValidAttack(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        ChessPiece movingPiece = chessBoard.getPiece(oldX, oldY);
        ChessPiece targetPiece = chessBoard.getPiece(newX, newY);

        if(movingPiece.getColor() != targetPiece.getColor() && validatePawnAttack(oldX, oldY, newX, newY, chessBoard)) {
            return true;
        }

        return false;
    }

    private boolean validatePawnAttack(int targetX, int targetY, int attX, int attY, ChessBoard chessBoard) {
        int changeY = targetY-attY;
        int changeX = targetX-attX;
        ChessPiece piece = chessBoard.getPiece(attX, attY);

        if(piece.getColor() == PieceColor.WHITE){
            if(changeY == -1 && (changeX == -1 || changeX == 1)){
                return true;
            }
        } else {
            if(changeY == 1 && (changeX == 1 || changeX == -1)){
                return true;
            }
        }

        return false;
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
