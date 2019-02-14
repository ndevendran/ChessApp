package com.example.chessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Paint;
import android.content.Context;
import android.view.Display;
import android.graphics.Point;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.MotionEvent;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    ChessBoard chessBoard;
    int screenWidth;
    int screenHeight;
    int squareLength;
    ChessView chessView;
    Canvas canvas;
    Boolean playingChess = true;
    ChessPiece activePiece;
    Point activePieceOldCoords = new Point();
    ChessMaster chessMaster;


    class ChessView extends SurfaceView implements Runnable {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        Paint paint;

        public ChessView(Context context, int squareLength) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
            chessBoard = new ChessBoard(getApplicationContext(), squareLength);
            chessMaster = new ChessMaster(chessBoard);
        }

        public void drawBoard() {
            if(ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();

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

                drawAllPieces();
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void drawPiece(ChessPiece piece, Paint paint) {
            int posLeft = piece.getPos().x;
            int posTop = piece.getPos().y;
            Rect rectToBeDrawn = new Rect(25,25,squareLength,squareLength);
            Rect destRect = new Rect(posLeft,posTop, posLeft+squareLength, posTop+squareLength);
            canvas.drawBitmap(piece.getSprite(), rectToBeDrawn, destRect, paint);
        }

        public void drawAllPieces() {
            for(int i = 0; i<chessBoard.getBoard().length; i++) {
                for(int j = 0; j<chessBoard.getBoard()[0].length; j++) {
                    if(chessBoard.getBoard()[i][j] != null) {
                        drawPiece(chessBoard.getBoard()[i][j], paint);
                    }
                }
            }

        }

        public void update() {

        }

        public void controlFPS() {

        }

        public void pause() {
            playingChess = false;
            try {
                ourThread.join();
            } catch (InterruptedException e) {

            }
        }


        public void resume() {
            playingChess = true;
            ourThread = new Thread(this);
            ourThread.start();
        }


        @Override
        public void run() {
            while(playingChess) {
                drawBoard();
                update();
                controlFPS();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int boardLength = chessBoard.getBoard().length-1;
        int posX;
        int posY;
        int indexX;
        int indexY;

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                float floatX = motionEvent.getX();
                float floatY = motionEvent.getY();

                activePieceOldCoords.x = (int)(floatX/squareLength);
                activePieceOldCoords.y = (int)(floatY/squareLength);
                if(activePieceOldCoords.x < chessBoard.getBoard().length && activePieceOldCoords.y < chessBoard.getBoard()[0].length){
                    activePiece = chessBoard.getBoard()[activePieceOldCoords.x][activePieceOldCoords.y];
                } else {
                    activePiece = null;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if(activePiece != null) {
                    posX = (int)(motionEvent.getX()-(squareLength/2));
                    posY = (int)(motionEvent.getY()-(squareLength/2));
                    //chessBoard.getBoard()[activePieceOldCoords.x][activePieceOldCoords.y].setPos(posX, posY);

                    if(posX < boardLength*squareLength && posY < boardLength*squareLength) {
                        chessBoard.getBoard()[activePieceOldCoords.x][activePieceOldCoords.y].setPos(posX, posY);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if(activePiece != null) {
                    indexX = (int)(motionEvent.getX()/squareLength);
                    indexY = (int)(motionEvent.getY()/squareLength);
                    boolean isValidMove = chessMaster.isValidMove(activePieceOldCoords.x, activePieceOldCoords.y, indexX, indexY);
                    Log.i("Index X", Integer.toString(indexX));
                    Log.i("Index Y", Integer.toString(indexY));
                    if(indexX < chessBoard.getBoard().length && indexY < chessBoard.getBoard()[0].length && isValidMove) {
                        posX = indexX*squareLength;
                        posY = indexY*squareLength;
                        activePiece.setPos(posX, posY);
                        chessBoard.getBoard()[indexX][indexY] = activePiece.clone();
                        chessBoard.getBoard()[activePieceOldCoords.x][activePieceOldCoords.y] = null;
                        activePiece = null;
                    } else {
                        activePiece.setPos(activePieceOldCoords.x*squareLength, activePieceOldCoords.y*squareLength);
                        activePiece = null;
                    }
                }
                break;
        }

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        if(screenWidth < screenHeight) {
            squareLength = screenWidth/8;
        } else {
            squareLength = screenHeight/8;
        }

        chessView = new ChessView(this, squareLength);
        chessView.setOnTouchListener(this);
        setContentView(chessView);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        while (true) {
            chessView.pause();
            break;
        }

        finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        chessView.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        chessView.resume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            chessView.pause();
            finish();
            return true;
        }

        return false;
    }


}
