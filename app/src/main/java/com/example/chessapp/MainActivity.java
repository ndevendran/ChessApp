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
import android.view.View;
import android.view.MotionEvent;
import com.example.chessapp.ChessPiece.PieceColor;
import com.example.chessapp.View.ChessBoardView;
import com.example.chessapp.View.ChessPieceView;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    ChessBoard chessBoard;
    int screenWidth;
    int screenHeight;
    int squareLength;
    ChessView chessView;
    Boolean playingChess = true;
    Point activePieceViewCoords = new Point();
    ChessMaster chessMaster;
    ChessGraphics chessGraphics;
    PieceColor currentTurn = PieceColor.WHITE;
    StateOfTheGame stateOfGame = new StateOfTheGame();
    ChessBoardView chessBoardView;
    ChessPieceView holdingPiece;


    class ChessView extends SurfaceView implements Runnable {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        Paint paint;

        public ChessView(Context context, int squareLength) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
            chessBoard = new ChessBoard();
            chessMaster = new ChessMaster(chessBoard);
            chessBoardView = stateOfGame.createChessBoardView(chessBoard);
            chessBoardView.scaleAllPositions(squareLength);
            chessGraphics = new ChessGraphics(squareLength);
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
                chessGraphics.drawBoard(chessBoardView, ourHolder, paint, getContext());
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

                activePieceViewCoords.x = (int)((floatX)/squareLength);
                activePieceViewCoords.y = (int)((floatY)/squareLength);

                holdingPiece = chessBoardView.getPiece(activePieceViewCoords.x, activePieceViewCoords.y);

                if(holdingPiece == null) {
                }

                if(holdingPiece != null && holdingPiece.getPieceColor() != currentTurn) {
                    holdingPiece = null;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if(holdingPiece != null) {
                    posX = (int)(motionEvent.getX()-(squareLength/2));
                    posY = (int)(motionEvent.getY()-(squareLength/2));

                    int borderXRight = (int)((motionEvent.getX()+squareLength)/squareLength);
                    int borderYBottom = (int)((motionEvent.getY()+squareLength)/squareLength);
                    int borderXLeft = (int)(motionEvent.getX()/squareLength);
                    int borderYTop = (int)(motionEvent.getY()/squareLength);

                    if(borderXRight > chessBoardView.getChessBoard().length || borderYBottom > chessBoardView.getChessBoard()[0].length) {
                        //do nothing
                    } else if(borderXLeft < 0 || borderYTop < 0) {
                        //do nothing again
                    } else {
                        holdingPiece.setPos(posX, posY);
                    }

                }

                break;

            case MotionEvent.ACTION_UP:
                if(holdingPiece != null) {
                    indexX = (int)(motionEvent.getX()/squareLength);
                    indexY = (int)(motionEvent.getY()/squareLength);
                    boolean isValidMove = chessMaster.isValidMove(activePieceViewCoords.x, activePieceViewCoords.y, indexX, indexY);

                    if(isValidMove) {
                        stateOfGame = chessMaster.makeMove(activePieceViewCoords.x, activePieceViewCoords.y, indexX, indexY);
                        currentTurn = stateOfGame.getCurrentTurn();
                        chessBoardView = stateOfGame.getChessBoardView();
                        chessBoardView.getPiece(indexX, indexY).setPos(indexX*squareLength, indexY*squareLength);
                        holdingPiece.setPos(indexX*squareLength, indexY*squareLength);
                    } else {
                        holdingPiece.setPos(activePieceViewCoords.x*squareLength, activePieceViewCoords.y*squareLength);
                        holdingPiece = null;
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
