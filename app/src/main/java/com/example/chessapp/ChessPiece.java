package com.example.chessapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.Resources;
import android.content.Context;
import android.graphics.Point;

public class ChessPiece {

    public enum Piece {
        ROOK,PAWN,QUEEN,KING,BISHOP,KNIGHT;
    }

    public enum PieceColor {
        BLACK,WHITE;
    }

    private Piece piece;
    private PieceColor color;
    private Bitmap sprite;
    private Point pos;

    public ChessPiece(Bitmap sprite, Piece piece, PieceColor color, int x, int y) {
        this.sprite = sprite;
        this.piece = piece;
        this.color = color;
        this.pos = new Point();
        this.pos.x = x;
        this.pos.y = y;
    }

    public ChessPiece(int resourceId, Piece piece, PieceColor color, Context context, int x, int y) {
        Resources res = context.getResources();
        this.sprite = BitmapFactory.decodeResource(res, resourceId);
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

    public Bitmap getSprite() {
        return this.sprite;
    }

    public Point getPos() {
        return this.pos;
    }

    public void setPos(int x, int y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public ChessPiece clone() {
        return new ChessPiece(this.sprite, this.piece, this.color, this.pos.x, this.pos.y);
    }

    public boolean isValidMove(int newX, int newY, ChessBoard chessBoard) {
        //Need to overwrite in extending classes
        return false;
    }

}
