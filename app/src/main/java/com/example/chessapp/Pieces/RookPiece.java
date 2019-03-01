package com.example.chessapp.Pieces;

import com.example.chessapp.ChessBoard;
import com.example.chessapp.ChessPiece;

public class RookPiece extends ChessPiece {
    public RookPiece (PieceColor color, int i, int j){
        super(ChessPiece.Piece.ROOK, color, i, j);
    }

    @Override
    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        return validateRookMove(newX, newY, oldX, oldY, chessBoard);
    }

    @Override
    public boolean isValidAttack(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        ChessPiece movingPiece = chessBoard.getPiece(oldX, oldY);
        ChessPiece targetPiece = chessBoard.getPiece(newX, newY);

        if(targetPiece != null) {
            if(movingPiece.getColor() != targetPiece.getColor() && isValidMove(oldX, oldY, newX, newY, chessBoard)) {
                return true;
            }
        } else {
            return isValidMove(oldX, oldY, newX, newY, chessBoard);
        }

        return false;
    }

    @Override
    public boolean canAttackBeBlocked(int attX, int attY, int targetX, int targetY, ChessBoard chessBoard) {
        return canRookBeBlocked(attX, attY, targetX, targetY, chessBoard);
    }

    private boolean canRookBeBlocked(int attX, int attY, int kingX, int kingY, ChessBoard chessBoard) {
        boolean x = false;
        boolean y = true;

        if(attY == kingY) {
            y = false;
            x = true;
        }

        while((x && attX != kingX) || (y && attY != kingY)) {
            if(x) {
                if(attX < kingX) {
                    attX++;
                } else {
                    attX--;
                }
            }

            if(y) {
                if(attY < kingY) {
                    attY++;
                } else {
                    attY--;
                }
            }

            for(int i = 0; i < chessBoard.getBoard().length; i++){
                for(int j = 0; j < chessBoard.getBoard().length; j++){
                    ChessPiece blockingPiece = chessBoard.getPiece(i,j);
                    if(blockingPiece.isValidMove(i, j, attX, attY, chessBoard)) {
                        return true;
                    }
                }
            }

        }

        return false;

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

}
