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


        if(doesMovePutOurKingInCheck(oldX, oldY, newX, newY)) {
            return false;
        }

        //TODO: PAWN CAPTURES/FORMAL CAPTURING
        //TODO: CHECK FOR CHECKS AND CHECKMATE

        switch(movingPiece.getPiece()){
            case KNIGHT:
                return validateKnightMove(newX, newY, oldX, oldY);
            case KING:
                return validateKingMove(newX, newY, oldX, oldY);
            case PAWN:
                return validatePawnMove(newX, newY, oldX, oldY);
            case ROOK:
                return validateRookMove(newX, newY, oldX, oldY);
            case BISHOP:
                Log.i("Made it", "Made it to bishop check");
                return validateBishopMove(newX, newY, oldX, oldY);
            case QUEEN:
                return validateQueenMove(newX, newY, oldX, oldY);
            default:
                return false;
        }

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
                if(attackingPiece == null) {
                    continue;
                }
                int attX = attackingPiece.getPos().x;
                int attY = attackingPiece.getPos().y;
                if(isValidMove(attX, attY, kingX, kingY)){
                    Log.i("King in Check", "King is in check");
                    return true;
                }
            }
        }

        return false;
    }

    private boolean doesMovePutOurKingInCheck(int oldX, int oldY, int newX, int newY) {
        ChessPiece movingPiece = chessBoard.getPiece(oldX, oldY);
        ChessPiece pieceAtDestination = chessBoard.getPiece(newX, newY);
        int kingX;
        int kingY;

        chessBoard.setPiece(newX, newY, movingPiece);
        chessBoard.setPiece(oldX, oldY, null);


        if(movingPiece.getColor() == PieceColor.BLACK) {
            kingX = blackKingPos.x;
            kingY = blackKingPos.y;
        }else {
            kingX = whiteKingPos.x;
            kingY = whiteKingPos.y;
        }

        boolean result = isKingInCheck(kingX, kingY);

        chessBoard.setPiece(oldX, oldY, movingPiece);
        chessBoard.setPiece(newX, newY, pieceAtDestination);

        return result;
    }

    private boolean canKingMove(int kingX, int kingY) {
        int [] possibleXs = new int[]{kingX-1, kingX, kingX+1};
        int [] possibleYs = new int[]{kingY+1, kingY, kingY-1};

        for(int i = 0; i < possibleXs.length; i++) {
            for (int j = 0; j < possibleYs.length; j++) {
                if(isValidMove(kingX, kingY, possibleXs[i], possibleYs[j])) {
                    return true;
                }

            }
        }

        return false;
    }

    private boolean canCheckBeBlocked(int kingX, int kingY) {
        //TODO: What pieces are attacking the king
        //TODO: Given that piece trace back attack route
        //TODO: Iterating over all friendly pieces see if a piece can move to the attack route
        for(int i=0; i<chessBoard.getBoard().length; i++) {
            for(int j=0; j<chessBoard.getBoard().length; j++) {
                ChessPiece attackingPiece = chessBoard.getPiece(i, j);
                if(attackingPiece != null) {
                    if(isValidMove(i, j, kingX, kingY)) {
                        if(!canAttackerBeBlocked(attackingPiece, i, j, kingX, kingY)){
                            return false;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean canAttackerBeBlocked(ChessPiece attackingPiece, int attX, int attY, int kingX, int kingY){
        boolean canBeBlocked = false;
        switch(attackingPiece.getPiece()) {
            case BISHOP:
                canBeBlocked = canBishopBeBlocked(attX, attY, kingX, kingY);
                break;
            case ROOK:
                canBeBlocked = canRookBeBlocked(attX, attY, kingX, kingY);
                break;
            case QUEEN:
                canBeBlocked = canQueenBeBlocked(attX, attY, kingX, kingY);
                break;
            default:
                canBeBlocked = false;
        }

        return canBeBlocked;
    }

    private boolean canBishopBeBlocked(int attX, int attY, int kingX, int kingY) {
        int xTrack = attX;
        int yTrack = attY;

        while(xTrack != kingX && yTrack != kingY) {
            if(xTrack < kingX) {
                xTrack++;
            } else {
                xTrack--;
            }

            if(yTrack < kingY) {
                yTrack++;
            } else {
                yTrack--;
            }

            for(int i = 0; i<chessBoard.getBoard().length; i++) {
                for(int j = 0; j<chessBoard.getBoard().length; j++) {
                    if(isValidMove(i, j, xTrack, yTrack)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean canRookBeBlocked(int attX, int attY, int kingX, int kingY) {
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
                    if(isValidMove(i, j, attX, attY)) {
                        return true;
                    }
                }
            }

        }

        return false;

    }

    private boolean canQueenBeBlocked(int attX, int attY, int kingX, int kingY) {
        if(attX == kingX || attY == kingY) {
            return canRookBeBlocked(attX, attY, kingX, kingY);
        } else {
            return canBishopBeBlocked(attX, attY, kingX, kingY);
        }
    }

    private boolean isItCheckmate(PieceColor color){
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

        return !(canKingMove || canCheckBeBlocked);
    }

    private boolean isItStalemate(PieceColor color) {
        //TODO
        //canKingMove();
        //areThereAnyValidMovesForTheOtherPieces
        return false;
    }
}
