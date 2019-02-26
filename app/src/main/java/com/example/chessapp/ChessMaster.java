package com.example.chessapp;

import android.graphics.Point;
import com.example.chessapp.ChessPiece.PieceColor;
import android.util.Log;
import com.example.chessapp.ChessPiece.Piece;


public class ChessMaster {
    ChessBoard chessBoard;
    Point blackKingPos;
    Point whiteKingPos;

    public boolean isValidMove(int oldX, int oldY, int newX, int newY) {
        boolean isOurKingCurrentlyInCheck;
        boolean isValidPieceMove;
        boolean putsOurKingInCheck;


        if(oldX < 0 || oldX > chessBoard.getBoard().length) {
            return false;
        }

        if(oldY < 0 || oldY > chessBoard.getBoard().length) {
            return false;
        }

        if(newX < 0 || newX > chessBoard.getBoard().length) {
            return false;
        }

        if(newY < 0 || newY > chessBoard.getBoard()[0].length) {
            return false;
        }

        ChessPiece targetPiece = chessBoard.getPiece(newX,newY);
        ChessPiece movingPiece = chessBoard.getPiece(oldX, oldY);



        if(movingPiece == null) {
            return false;
        }

        if(targetPiece != null) {
            if(targetPiece.getColor() == movingPiece.getColor()) {
                return false;
            }
        }

        if(movingPiece.getColor() == PieceColor.BLACK) {
            isOurKingCurrentlyInCheck = isKingInCheck(blackKingPos.x, blackKingPos.y);
        } else {
            isOurKingCurrentlyInCheck = isKingInCheck(whiteKingPos.x, whiteKingPos.y);
        }

        isValidPieceMove = movingPiece.isValidMove(oldX, oldY, newX, newY, chessBoard);

        putsOurKingInCheck = doesMovePutOurKingInCheck(oldX, oldY, newX, newY);

        return (!isOurKingCurrentlyInCheck && isValidPieceMove && !putsOurKingInCheck);
    }

    public ChessMaster(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        blackKingPos = new Point();
        whiteKingPos = new Point();

        blackKingPos.x = 4;
        blackKingPos.y = 0;

        whiteKingPos.x = 4;
        whiteKingPos.y = 7;
    }

    private boolean isKingInCheck(int kingX, int kingY){
        for(int i = 0; i < chessBoard.getBoard().length; i++) {
            for(int j = 0; j < chessBoard.getBoard()[0].length; j++){
                ChessPiece attackingPiece = chessBoard.getPiece(i,j);
                ChessPiece kingPiece = chessBoard.getPiece(kingX, kingY);
                if(attackingPiece == null) {
                    continue;
                }
                if(attackingPiece.getColor() == kingPiece.getColor()){
                    continue;
                }
                if(attackingPiece.isValidAttack(i, j, kingX, kingY, chessBoard)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canKingMove(int kingX, int kingY) {
        int [] possibleXs = new int[]{kingX-1, kingX, kingX+1};
        int [] possibleYs = new int[]{kingY+1, kingY, kingY-1};
        ChessPiece kingPiece = chessBoard.getPiece(kingX, kingY);

        for(int i = 0; i < possibleXs.length; i++) {
            for (int j = 0; j < possibleYs.length; j++) {
                if(possibleXs[i] < 8 && possibleXs[i] >= 0 && possibleYs[j] < 8 && possibleYs[j] >= 0){
                    if(kingPiece.isValidMove(kingX, kingY, possibleXs[i], possibleYs[j], chessBoard)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean canCheckBeBlocked(int kingX, int kingY) {
        for(int i=0; i<chessBoard.getBoard().length; i++) {
            for(int j=0; j<chessBoard.getBoard().length; j++) {
                ChessPiece attackingPiece = chessBoard.getPiece(i, j);
                if(attackingPiece != null) {
                    if(isValidMove(i, j, kingX, kingY)) {
                        if(attackingPiece.canAttackBeBlocked(i, j, kingX, kingY, chessBoard)){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean doesMovePutOurKingInCheck(int oldX, int oldY, int newX, int newY) {
        ChessPiece movingPiece = chessBoard.getPiece(oldX, oldY);
        ChessPiece targetPiece = chessBoard.getPiece(newX, newY);
        boolean kingInCheck = false;

        chessBoard.setPiece(newX, newY, movingPiece);
        chessBoard.setPiece(oldX, oldY, null);
        if(movingPiece.getColor() == PieceColor.BLACK) {
            if(isKingInCheck(blackKingPos.x, blackKingPos.y)){
                kingInCheck = true;
            }
        } else {
            if(isKingInCheck(whiteKingPos.x, whiteKingPos.y)) {
                kingInCheck = true;
            }
        }

        chessBoard.setPiece(newX, newY, targetPiece);
        chessBoard.setPiece(oldX, oldY, movingPiece);

        return kingInCheck;
    }

    public boolean isCheckmate(PieceColor color){
        //TODO
        int kingX;
        int kingY;

        if(color == PieceColor.WHITE) {
            kingX = whiteKingPos.x;
            kingY = whiteKingPos.y;
        } else {
            kingX = blackKingPos.x;
            kingY = blackKingPos.y;
        }

        boolean canKingMove = canKingMove(kingX, kingY);
        boolean canCheckBeBlocked = canCheckBeBlocked(kingX, kingY);
        boolean isKingInCheck = isKingInCheck(kingX, kingY);

        return (isKingInCheck && !(canKingMove || canCheckBeBlocked));
    }

    private boolean isItStalemate(PieceColor color) {
        //TODO
        //canKingMove();
        //areThereAnyValidMovesForTheOtherPieces
        return false;
    }
}
