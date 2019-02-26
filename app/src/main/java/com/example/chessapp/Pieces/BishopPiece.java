package com.example.chessapp.Pieces;

import com.example.chessapp.ChessPiece;
import android.content.Context;
import com.example.chessapp.ChessBoard;

public class BishopPiece extends ChessPiece {
    public BishopPiece (int resourceId, int squareLength, PieceColor color, Context context, int i, int j){
        super(resourceId, ChessPiece.Piece.BISHOP, color, context, i*squareLength, j*squareLength);
    }

    @Override
    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard){
        return validateBishopMove(newX, newY, oldX, oldY, chessBoard);
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

    @Override
    public boolean canAttackBeBlocked(int attX, int attY, int targetX, int targetY, ChessBoard chessBoard) {
        return canBishopBeBlocked(attX, attY, targetX, targetY, chessBoard);
    }

    private boolean canBishopBeBlocked(int attX, int attY, int kingX, int kingY, ChessBoard chessBoard) {
        int xTrack = attX;
        int yTrack = attY;
        ChessPiece kingPiece = chessBoard.getPiece(kingX, kingY);

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
                    ChessPiece movingPiece = chessBoard.getPiece(i, j);
                    if(movingPiece.isValidMove(i, j, xTrack, yTrack, chessBoard) && (kingPiece.getColor() == movingPiece.getColor())) {
                        return true;
                    }
                }
            }
        }

        return false;
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
