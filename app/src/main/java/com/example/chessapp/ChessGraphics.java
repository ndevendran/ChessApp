package com.example.chessapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.content.res.Resources;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.chessapp.View.ChessPieceView;
import com.example.chessapp.View.ChessBoardView;

import java.util.Map;
import java.util.HashMap;

public class ChessGraphics {
    private int squareLength;
    private Map<String, Map<String, Integer>> chessGraphicsLookup = new HashMap<>();

    public ChessGraphics(int squareLength)
    {
        Map<String, Integer> whitePiecesLookup = new HashMap<>();
        Map<String, Integer> blackPiecesLookup = new HashMap<>();

        whitePiecesLookup.put("BISHOP", R.drawable.white_bishop);
        whitePiecesLookup.put("QUEEN", R.drawable.white_queen);
        whitePiecesLookup.put("KING", R.drawable.white_king);
        whitePiecesLookup.put("PAWN", R.drawable.white_pawn);
        whitePiecesLookup.put("KNIGHT", R.drawable.white_knight);
        whitePiecesLookup.put("ROOK", R.drawable.white_rook);

        blackPiecesLookup.put("BISHOP", R.drawable.black_bishop);
        blackPiecesLookup.put("QUEEN", R.drawable.black_queen);
        blackPiecesLookup.put("KING", R.drawable.black_king);
        blackPiecesLookup.put("PAWN", R.drawable.black_pawn);
        blackPiecesLookup.put("KNIGHT", R.drawable.black_knight);
        blackPiecesLookup.put("ROOK", R.drawable.black_rook);

        chessGraphicsLookup.put("WHITE",whitePiecesLookup);
        chessGraphicsLookup.put("BLACK", blackPiecesLookup);

        this.squareLength = squareLength;
    }

    private void drawPiece(ChessPieceView piece, Paint paint, Canvas canvas, Context context) {
        if(piece == null) {
            return;
        }

        int posLeft = piece.getPos().x;
        int posTop = piece.getPos().y;

        Rect rectToBeDrawn = new Rect(25,25,squareLength,squareLength);
        Rect destRect = new Rect(posLeft,posTop, posLeft+squareLength, posTop+squareLength);
        Resources resources = context.getResources();
        int resId = chessGraphicsLookup.get(piece.getPieceColor().toString()).get(piece.getPiece().toString());
        Bitmap sprite = BitmapFactory.decodeResource(resources, resId);
        canvas.drawBitmap(sprite, rectToBeDrawn, destRect, paint);
    }

    private void drawAllPieces(ChessBoardView chessBoardView, Paint paint, Canvas canvas, Context context) {
        for(int i = 0; i<chessBoardView.getChessBoard().length; i++) {
            for(int j = 0; j<chessBoardView.getChessBoard()[0].length; j++) {
                ChessPieceView chessPiece = chessBoardView.getPiece(i, j);
                drawPiece(chessPiece, paint, canvas, context);
            }
        }
    }

    public void drawBoard(ChessBoardView chessBoardView, SurfaceHolder ourHolder, Paint paint, Context context) {
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

            drawAllPieces(chessBoardView, paint, canvas, context);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
