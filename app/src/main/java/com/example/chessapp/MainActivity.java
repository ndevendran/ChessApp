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
import android.util.Log;
import com.example.chessapp.ChessPiece.PieceColor;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    ChessBoard chessBoard;
    int screenWidth;
    int screenHeight;
    int squareLength;
    ChessView chessView;
    Boolean playingChess = true;
    ChessPiece activePiece;
    Point activePieceOldCoords = new Point();
    ChessMaster chessMaster;
    ChessGraphics chessGraphics;
    PieceColor currentTurn = PieceColor.WHITE;


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
                chessGraphics.drawBoard(chessBoard, ourHolder, paint);
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
                    activePiece = chessBoard.getPiece(activePieceOldCoords.x,activePieceOldCoords.y);
                    if(activePiece != null && activePiece.getColor() != currentTurn) {
                        activePiece = null;
                    }
                } else {
                    activePiece = null;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if(activePiece != null) {
                    posX = (int)(motionEvent.getX()-(squareLength/2));
                    posY = (int)(motionEvent.getY()-(squareLength/2));

                    if(posX < boardLength*squareLength && posY < boardLength*squareLength) {
                        chessBoard.getPiece(activePieceOldCoords.x,activePieceOldCoords.y).setPos(posX, posY);
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
                        if(indexX != activePieceOldCoords.x || indexY != activePieceOldCoords.y){
                            posX = indexX*squareLength;
                            posY = indexY*squareLength;
                            activePiece.setPos(posX, posY);
                            chessBoard.setPiece(indexX,indexY,activePiece.clone());
                            chessBoard.setPiece(activePieceOldCoords.x,activePieceOldCoords.y,null);
                            activePiece = null;

                            if(currentTurn == PieceColor.WHITE) {
                                if(!chessMaster.isCheckmate(PieceColor.WHITE)){
                                    currentTurn = PieceColor.BLACK;
                                } else {
                                    //Reset Game here
                                    Log.i("Checkmate! WHITE WINS", "Checkmate! WHITE WINS");
                                }
                            } else {
                                if(!chessMaster.isCheckmate(PieceColor.WHITE)){
                                    currentTurn = PieceColor.WHITE;
                                } else {
                                    //Reset Game here
                                    Log.i("Checkmate! BLACK WINS", "Checkmate! BLACK WINS");
                                }

                            }
                        }
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
