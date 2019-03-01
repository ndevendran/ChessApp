package com.example.chessapp;

import android.graphics.Point;
import android.util.Log;

public class ChessPiece {
    private Piece piece;
    private PieceColor color;
    private Point pos;

    public enum Piece {
        ROOK,PAWN,QUEEN,KING,BISHOP,KNIGHT
    }

    public enum PieceColor {
        BLACK,WHITE
    }

    public ChessPiece(Piece piece, PieceColor color, int x, int y) {
        this.piece = piece;
        this.color = color;
        this.pos = new Point();
        this.pos.x = x;
        this.pos.y = y;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public Point getPos() {
        return this.pos;
    }

    public void setPos(int x, int y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public ChessPiece clone() {
        return new ChessPiece(this.piece, this.color, this.pos.x, this.pos.y);
    }

    public boolean isValidMove(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard) {
        //Need to overwrite in extending classes
        Log.i("Strange behavior", "real strange");
        return false;
    }

    public boolean isValidAttack(int oldX, int oldY, int newX, int newY, ChessBoard chessBoard) {
        //Need to overwrite in extending classes
        return false;
    }

    public boolean canAttackBeBlocked(int attX, int attY, int targetX, int targetY, ChessBoard chessBoard) {
        //Need to overwrite in extending classes
        return false;
    }

}
