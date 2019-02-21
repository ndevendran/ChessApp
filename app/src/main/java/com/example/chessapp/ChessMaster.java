package com.example.chessapp;

import android.graphics.Point;
import com.example.chessapp.ChessPiece.PieceColor;


public class ChessMaster {
    ChessBoard chessBoard;
    Point blackKingPos;
    Point whiteKingPos;

    public boolean isValidMove(int oldX, int oldY, int newX, int newY) {
        ChessPiece piece = chessBoard.getPiece(oldX,oldY);

        if(newX > chessBoard.getBoard().length) {
            return false;
        }

        if(newY > chessBoard.getBoard()[0].length) {
            return false;
        }

        ChessPiece targetPiece = chessBoard.getPiece(newX,newY);

        if(targetPiece != null && targetPiece.getColor() == piece.getColor()) {
            return false;
        }

        if(piece.getColor() == PieceColor.WHITE){
            if(isKingInCheck(whiteKingPos.x, whiteKingPos.y)) {
                return false;
            }
        }

        //TODO: PAWN CAPTURES/FORMAL CAPTURING
        //TODO: CHECK FOR CHECKS AND CHECKMATE

        switch(piece.getPiece()){
            case KNIGHT:
                return validateKnightMove(newX, newY, oldX, oldY);
            case KING:
                return validateKingMove(newX, newY, oldX, oldY);
            case PAWN:
                return validatePawnMove(newX, newY, oldX, oldY);
            case ROOK:
                return validateRookMove(newX, newY, oldX, oldY);
            case BISHOP:
                return validateBishopMove(newX, newY, oldX, oldY);
            case QUEEN:
                return validateQueenMove(newX, newY, oldX, oldY);
            default:
                return false;
        }

    }

    public ChessMaster(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        blackKingPos.x = 4;
        blackKingPos.y = 0;

        whiteKingPos.x = 4;
        whiteKingPos.y = 7;
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

    private boolean validateKingMove(int newX, int newY, int oldX, int oldY) {
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


        if(movingPiece.getColor() == PieceColor.WHITE) {
            whiteKingPos.x = newX;
            whiteKingPos.y = newY;
        } else {
            blackKingPos.x = newX;
            blackKingPos.y = newY;
        }
        return true;
    }

    private boolean validatePawnMove(int newX, int newY, int oldX, int oldY) {
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

    private boolean validateRookMove(int newX, int newY, int oldX, int oldY) {
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

    private boolean validateBishopMove(int newX, int newY, int oldX, int oldY) {
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

    private boolean validateQueenMove(int newX, int newY, int oldX, int oldY) {
        return (validateBishopMove(newX, newY, oldX, oldY) || validateRookMove(newX, newY, oldX, oldY));
    }

    private boolean isKingInCheck(int kingX, int kingY){
        //TODO CHECK IF IN LINE OF SIGHT
        //TODO CHECK IF PIECE BLOCKING
        for(int i = 0; i < chessBoard.getBoard().length; i++) {
            for(int j = 0; j < chessBoard.getBoard()[0].length; j++){
                ChessPiece attackingPiece = chessBoard.getPiece(i,j);
                int attX = attackingPiece.getPos().x;
                int attY = attackingPiece.getPos().y;
                if(isValidMove(attX, attY, kingX, kingY)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean doesMovePutKingInCheck(int oldX, int oldY, int newX, int newY, Point kingPos) {
        ChessPiece tempHolder = chessBoard.getPiece(newX, newY);
        chessBoard
    }
}
