package com.example.chessapp;

import android.content.Context;
import com.example.chessapp.Pieces.*;

public class ChessBoard {
    private ChessPiece [][] chessBoard;


    public ChessBoard(Context context, int squareLength) {
        this.chessBoard = new ChessPiece[8][8];

        chessBoard[0][7] = new RookPiece(R.drawable.white_rook, squareLength, ChessPiece.PieceColor.WHITE, context, 0, 7);
        chessBoard[7][7] = new RookPiece(R.drawable.white_rook, squareLength, ChessPiece.PieceColor.WHITE, context, 7, 7);
        chessBoard[2][7] = new BishopPiece(R.drawable.white_bishop, squareLength, ChessPiece.PieceColor.WHITE, context, 2, 7);
        chessBoard[5][7] = new BishopPiece(R.drawable.white_bishop, squareLength, ChessPiece.PieceColor.WHITE, context, 5, 7);
        chessBoard[1][7] = new KnightPiece(R.drawable.white_knight, squareLength, ChessPiece.PieceColor.WHITE, context, 1, 7);
        chessBoard[6][7] = new KnightPiece(R.drawable.white_knight, squareLength, ChessPiece.PieceColor.WHITE, context, 6, 7);
        chessBoard[3][7] = new QueenPiece(R.drawable.white_queen, squareLength, ChessPiece.PieceColor.WHITE, context, 3, 7);
        chessBoard[4][7] = new KingPiece(R.drawable.white_king, squareLength, ChessPiece.PieceColor.WHITE, context, 4, 7);

        chessBoard[0][0] = new RookPiece(R.drawable.black_rook, squareLength, ChessPiece.PieceColor.BLACK, context, 0, 0);
        chessBoard[7][0] = new RookPiece(R.drawable.black_rook, squareLength, ChessPiece.PieceColor.BLACK, context, 7, 0);
        chessBoard[2][0] = new BishopPiece(R.drawable.black_bishop, squareLength, ChessPiece.PieceColor.BLACK, context, 2, 0);
        chessBoard[5][0] = new BishopPiece(R.drawable.black_bishop, squareLength, ChessPiece.PieceColor.BLACK, context, 5, 0);
        chessBoard[1][0] = new KnightPiece(R.drawable.black_knight, squareLength, ChessPiece.PieceColor.BLACK, context, 1, 0);
        chessBoard[6][0] = new KnightPiece(R.drawable.black_knight, squareLength, ChessPiece.PieceColor.BLACK, context, 6, 0);
        chessBoard[3][0] = new QueenPiece(R.drawable.black_queen, squareLength, ChessPiece.PieceColor.BLACK, context, 3, 0);
        chessBoard[4][0] = new KingPiece(R.drawable.black_king, squareLength, ChessPiece.PieceColor.BLACK, context, 4, 0);

        for(int i=0; i<8; i++) {
            chessBoard[i][6] = new PawnPiece(R.drawable.white_pawn, squareLength, ChessPiece.PieceColor.WHITE, context, i, 6);
            chessBoard[i][1] = new PawnPiece(R.drawable.black_pawn, squareLength, ChessPiece.PieceColor.BLACK, context, i, 1);
        }
    }

    public ChessPiece[][] getBoard() {
        return this.chessBoard;
    }

    public ChessPiece getPiece(int x, int y) {
        return this.chessBoard[x][y];
    }

    public void setPiece(int x, int y, ChessPiece piece) {
        this.chessBoard[x][y] = piece;
    }
}
