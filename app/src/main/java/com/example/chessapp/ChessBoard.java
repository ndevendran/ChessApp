package com.example.chessapp;

import android.content.Context;

public class ChessBoard {
    private ChessPiece [][] chessBoard;


    public ChessBoard(Context context, int squareLength) {
        this.chessBoard = new ChessPiece[8][8];

        chessBoard[0][7] = new ChessPiece(R.drawable.white_rook, ChessPiece.Piece.ROOK, ChessPiece.PieceColor.WHITE, context, 0*squareLength, 7*squareLength);
        chessBoard[7][7] = new ChessPiece(R.drawable.white_rook, ChessPiece.Piece.ROOK, ChessPiece.PieceColor.WHITE, context, 7*squareLength, 7*squareLength);
        chessBoard[2][7] = new ChessPiece(R.drawable.white_bishop, ChessPiece.Piece.BISHOP, ChessPiece.PieceColor.WHITE, context, 2*squareLength, 7*squareLength);
        chessBoard[5][7] = new ChessPiece(R.drawable.white_bishop, ChessPiece.Piece.BISHOP, ChessPiece.PieceColor.WHITE, context, 5*squareLength, 7*squareLength);
        chessBoard[1][7] = new ChessPiece(R.drawable.white_knight, ChessPiece.Piece.KNIGHT, ChessPiece.PieceColor.WHITE, context, 1*squareLength, 7*squareLength);
        chessBoard[6][7] = new ChessPiece(R.drawable.white_knight, ChessPiece.Piece.KNIGHT, ChessPiece.PieceColor.WHITE, context, 6*squareLength, 7*squareLength);
        chessBoard[3][7] = new ChessPiece(R.drawable.white_queen, ChessPiece.Piece.QUEEN, ChessPiece.PieceColor.WHITE, context, 3*squareLength, 7*squareLength);
        chessBoard[4][7] = new ChessPiece(R.drawable.white_king, ChessPiece.Piece.KING, ChessPiece.PieceColor.WHITE, context, 4*squareLength, 7*squareLength);

        chessBoard[0][0] = new ChessPiece(R.drawable.black_rook, ChessPiece.Piece.ROOK, ChessPiece.PieceColor.BLACK, context, 0*squareLength, 0*squareLength);
        chessBoard[7][0] = new ChessPiece(R.drawable.black_rook, ChessPiece.Piece.ROOK, ChessPiece.PieceColor.BLACK, context, 7*squareLength, 0*squareLength);
        chessBoard[2][0] = new ChessPiece(R.drawable.black_bishop, ChessPiece.Piece.BISHOP, ChessPiece.PieceColor.BLACK, context, 2*squareLength, 0*squareLength);
        chessBoard[5][0] = new ChessPiece(R.drawable.black_bishop, ChessPiece.Piece.BISHOP, ChessPiece.PieceColor.BLACK, context, 5*squareLength, 0*squareLength);
        chessBoard[1][0] = new ChessPiece(R.drawable.black_knight, ChessPiece.Piece.KNIGHT, ChessPiece.PieceColor.BLACK, context, 1*squareLength, 0*squareLength);
        chessBoard[6][0] = new ChessPiece(R.drawable.black_knight, ChessPiece.Piece.KNIGHT, ChessPiece.PieceColor.BLACK, context, 6*squareLength, 0*squareLength);
        chessBoard[3][0] = new ChessPiece(R.drawable.black_queen, ChessPiece.Piece.QUEEN, ChessPiece.PieceColor.BLACK, context, 3*squareLength, 0*squareLength);
        chessBoard[4][0] = new ChessPiece(R.drawable.black_king, ChessPiece.Piece.KING, ChessPiece.PieceColor.BLACK, context, 4*squareLength, 0*squareLength);

        for(int i=0; i<8; i++) {
            chessBoard[i][6] = new ChessPiece(R.drawable.white_pawn, ChessPiece.Piece.PAWN, ChessPiece.PieceColor.WHITE, context, i*squareLength, 6*squareLength);
            chessBoard[i][1] = new ChessPiece(R.drawable.black_pawn, ChessPiece.Piece.PAWN, ChessPiece.PieceColor.BLACK, context, i*squareLength, 1*squareLength);
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
