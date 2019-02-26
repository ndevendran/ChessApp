package com.example.chessapp.Pieces;

import android.content.Context;

import com.example.chessapp.ChessBoard;
import com.example.chessapp.ChessPiece;

public class KingPiece extends ChessPiece {
    public KingPiece (int resourceId, int squareLength, PieceColor color, Context context, int i, int j){
        super(resourceId, ChessPiece.Piece.KING, color, context, i*squareLength, j*squareLength);
    }

    @Override
    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        return validateKingMove(newX, newY, oldX, oldY, chessBoard);
    }

    @Override
    public boolean isValidAttack(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        ChessPiece movingPiece = chessBoard.getPiece(oldX, oldY);
        ChessPiece targetPiece = chessBoard.getPiece(newX, newY);

        if(movingPiece.getColor() != targetPiece.getColor() && validateKingAttack(oldX, oldY, newX, newY, chessBoard)) {
            return true;
        }

        return false;
    }

    private boolean validateKingAttack(int newX, int newY, int oldX, int oldY, ChessBoard chessBoard) {
        int changeX = newX-oldX;
        int changeY = newY-oldY;

        if(changeX < 0) {
            changeX = changeX * -1;
        }

        if(changeY < 0) {
            changeY = changeY * -1;
        }

        if(changeX > 1 || changeY > 1) {
            return false;
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

    private boolean validateKingMove(int newX, int newY, int oldX, int oldY, ChessBoard chessBoard) {
        int changeX = newX-oldX;
        int changeY = newY-oldY;

        if(changeX < 0) {
            changeX = changeX * -1;
        }

        if(changeY < 0) {
            changeY = changeY * -1;
        }

        if(changeX > 1 || changeY > 1) {
            return false;
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