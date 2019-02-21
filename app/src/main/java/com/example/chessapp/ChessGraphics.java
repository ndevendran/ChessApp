package com.example.chessapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ChessGraphics {
    private int squareLength;

    public ChessGraphics(int squareLength) {
        this.squareLength = squareLength;
    }

    private void drawPiece(ChessPiece piece, Paint paint, Canvas canvas) {
        int posLeft = piece.getPos().x;
        int posTop = piece.getPos().y;
        Rect rectToBeDrawn = new Rect(25,25,squareLength,squareLength);
        Rect destRect = new Rect(posLeft,posTop, posLeft+squareLength, posTop+squareLength);
        canvas.drawBitmap(piece.getSprite(), rectToBeDrawn, destRect, paint);
    }

    private void drawAllPieces(ChessBoard chessBoard, Paint paint, Canvas canvas) {
        for(int i = 0; i<chessBoard.getBoard().length; i++) {
            for(int j = 0; j<chessBoard.getBoard()[0].length; j++) {
                if(chessBoard.getBoard()[i][j] != null) {
                    drawPiece(chessBoard.getBoard()[i][j], paint, canvas);
                }
            }
        }

    }

    public void drawBoard(ChessBoard chessBoard, SurfaceHolder ourHolder, Paint paint) {
        if(ourHolder.getSurface().isValid()) {
            Canvas canvas = ourHolder.lockCanvas();

            for(int i = 0; i<8; i++){
                int posLeft = squareLength*i;

                for(int j = 0; j<8; j++){
                    int posTop = squareLength*j;

                    if(i%2 == 0) {
                        if(j%2 == 0) {
                            paint.setColor(Color.rgb(255,255,255));
                        } else {
                            paint.setColor(Color.rgb(75,75,75));
                        }
                    } else {
                        if(j%2 == 0) {
                            paint.setColor(Color.rgb(75,75,75));
                        } else {
                            paint.setColor(Color.rgb(255,255,255));
                        }
                    }


                    canvas.drawRect(posLeft,posTop,posLeft+squareLength,posTop+squareLength,paint);

                }
            }

            drawAllPieces(chessBoard, paint, canvas);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
