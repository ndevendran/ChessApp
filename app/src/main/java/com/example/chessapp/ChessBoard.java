package com.example.chessapp;

import com.example.chessapp.Pieces.*;
import com.example.chessapp.ChessPiece.*;
import android.graphics.Point;

public class ChessBoard {
    private ChessPiece [][] chessBoard;


    public ChessBoard() {
        this.chessBoard = new ChessPiece[8][8];

        chessBoard[0][7] = new RookPiece(ChessPiece.PieceColor.WHITE, 0, 7);
        chessBoard[7][7] = new RookPiece(ChessPiece.PieceColor.WHITE, 7, 7);
        chessBoard[2][7] = new BishopPiece(ChessPiece.PieceColor.WHITE,2, 7);
        chessBoard[5][7] = new BishopPiece(ChessPiece.PieceColor.WHITE,5, 7);
        chessBoard[1][7] = new KnightPiece(ChessPiece.PieceColor.WHITE,1, 7);
        chessBoard[6][7] = new KnightPiece(ChessPiece.PieceColor.WHITE, 6, 7);
        chessBoard[3][7] = new QueenPiece(ChessPiece.PieceColor.WHITE, 3, 7);
        chessBoard[4][7] = new KingPiece(ChessPiece.PieceColor.WHITE,4, 7);

        chessBoard[0][0] = new RookPiece( ChessPiece.PieceColor.BLACK, 0, 0);
        chessBoard[7][0] = new RookPiece(ChessPiece.PieceColor.BLACK, 7, 0);
        chessBoard[2][0] = new BishopPiece(ChessPiece.PieceColor.BLACK, 2, 0);
        chessBoard[5][0] = new BishopPiece(ChessPiece.PieceColor.BLACK, 5, 0);
        chessBoard[1][0] = new KnightPiece(ChessPiece.PieceColor.BLACK, 1, 0);
        chessBoard[6][0] = new KnightPiece(ChessPiece.PieceColor.BLACK, 6, 0);
        chessBoard[3][0] = new QueenPiece(ChessPiece.PieceColor.BLACK, 3, 0);
        chessBoard[4][0] = new KingPiece(ChessPiece.PieceColor.BLACK,4, 0);

        for(int i=0; i<8; i++) {
            chessBoard[i][6] = new PawnPiece( ChessPiece.PieceColor.WHITE, i, 6);
            chessBoard[i][1] = new PawnPiece(ChessPiece.PieceColor.BLACK, i, 1);
        }
    }

    public ChessPiece[][] getBoard() {
        return this.chessBoard;
    }

    public ChessPiece getPiece(int x, int y) {
        return this.chessBoard[x][y];
    }

    public Point getPieceCoords(Piece piece, PieceColor color) {
        for(int i = 0; i<chessBoard.length; i++) {
            for(int j = 0; j<chessBoard[0].length; j++) {
                ChessPiece chessPiece = chessBoard[i][j];
                if(chessPiece != null){
                    if(chessPiece.getPiece() == piece){
                        if(chessPiece.getColor() == color){
                            return new Point(i,j);
                        }
                    }
                }
            }
        }

        return null;
    }

    public void setPiece(int x, int y, ChessPiece piece) {
        this.chessBoard[x][y] = piece;
    }
}
