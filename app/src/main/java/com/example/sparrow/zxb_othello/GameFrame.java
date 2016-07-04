package com.example.sparrow.zxb_othello;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameFrame extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = GameFrame.class.getSimpleName();

    static final int EMPTY_CHESS = 0;
    static final int BLACK_CHESS = 1;
    static final int WHITE_CHESS = 2;
    static final Integer IMG_BLACK = R.drawable.black_chess;
    static final Integer IMG_WHITE = R.drawable.white_chess;
    static final Integer IMG_EMPTY = R.drawable.transparent;
    static final Integer IMG_BLACK_T = R.drawable.black_chess_t;
    static final Integer IMG_WHITE_T = R.drawable.white_chess_t;
    static final int BORDER = 8;
    static final int CHESSBOARD = 64;

    Button btn_newgame;
//    Button btn_retract;
    Button btn_hintson;
    TextView black_counter;
    TextView white_counter;
    TextView Turn;
    int blackcounter=2;
    int whitecounter=2;

    ArrayList<Integer> turn = new ArrayList<>();
    ImageView img_turn;
    boolean started;
    boolean hintson;
    boolean mark = true;

    int whoseTurn = BLACK_CHESS;
    int currentChess;
    int nextChess;

    private MyAdapter chessAdapter = new MyAdapter(this);
    private final int[] WHITE_INITIAL = {27, 36};
    private final int[] BLACK_INITIAL = {28, 35};

    int[] Records = new int[CHESSBOARD];
    int[] Hints = new  int[CHESSBOARD];
    Integer[] imgs={
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.white_chess,R.drawable.black_chess,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.black_chess,R.drawable.white_chess,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,
            R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,R.drawable.transparent,

    };

    public GameFrame(){
        started = false;
        hintson = false;
    }

    public class MyAdapter extends BaseAdapter{

        Context context;

        MyAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public Object getItem(int item) {
            return item;
        }

        @Override
        public long getItemId(int id) {
            return id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int size = parent.getWidth() / BORDER;

            if (convertView == null){
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(size,size));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(5,5,5,5);
            }
            else
                imageView = (ImageView) convertView;

            imageView.setImageResource(imgs[position]);
            imageView.setBackgroundResource(R.drawable.chessboard);
            return imageView;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_frame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_newgame = (Button)findViewById(R.id.btn_newgame);
//        btn_retract = (Button)findViewById(R.id.btn_retract);
        btn_hintson = (Button)findViewById(R.id.btn_hintson);
        black_counter = (TextView)findViewById(R.id.black_counter);
        white_counter = (TextView)findViewById(R.id.white_counter);
        Turn = (TextView)findViewById(R.id.Turn);

        img_turn = (ImageView)findViewById(R.id.turn);
        btn_newgame.setOnClickListener(this);
//        btn_retract.setOnClickListener(this);
        btn_hintson.setOnClickListener(this);

        started = false;
        hintson = false;

        Records[27] = 2;
        Records[36] = 2;
        Records[28] = 1;
        Records[35] = 1;
        currentChess = BLACK_CHESS;
        nextChess = WHITE_CHESS;

       // chessboard setting starts
        GridView chessboard = (GridView)findViewById(R.id.chessboard);
        chessboard.setAdapter(chessAdapter);
        chessboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(GameFrame.this, "" + position, Toast.LENGTH_SHORT).show();

                boolean current_mark = false;

                Move(Records[position], position);

                for (int i = 0; i < CHESSBOARD; i++) {
                    MoveAvailable(i);
                    current_mark = mark;
                    if (current_mark == true) {
                        break;
                    }
//                    Log.v(LOG_TAG,""+i+":"+Hints[i]);
                }

//                if (current_mark == true) {
//                    Log.v(LOG_TAG, "current_mark = true, next turn will continue as usual");
//                } else {
//                    Log.v(LOG_TAG, "current_mark = false, next turn will be skipped");
//                }

                if (current_mark == true) {
                    Cleanhints(hintson);
                    DisplayHints(hintson);
                } else if (current_mark == false) {
                    Toast.makeText(GameFrame.this, "There is no more move currently, skipped to next!", Toast.LENGTH_SHORT).show();

                    whoseTurn = currentChess;
                    currentChess = nextChess;
                    nextChess = whoseTurn;


                    for (int i = 0; i < CHESSBOARD; i++) {
                        MoveAvailable(i);
                        current_mark = mark;
                        if (current_mark == true)
                            break;
//                        Log.v(LOG_TAG, "" + i + ":" + Hints[i]);
                    }
                    if (current_mark == true) {
                        if (currentChess == WHITE_CHESS)
                            img_turn.setImageResource(R.drawable.white_chess);
                        else
                            img_turn.setImageResource(R.drawable.black_chess);
                        Cleanhints(hintson);
                        DisplayHints(hintson);
                    } else if (current_mark == false) {
                        Cleanhints(hintson);
                        if (blackcounter > whitecounter) {
                            Turn.setText("winner:");
                            img_turn.setImageResource(R.drawable.black_chess);
                            Toast.makeText(GameFrame.this, "The game is over, blackchess is the winner", Toast.LENGTH_SHORT).show();
                        } else if (blackcounter < whitecounter) {
                            Turn.setText("winner:");
                            img_turn.setImageResource(R.drawable.white_chess);
                            Toast.makeText(GameFrame.this, "The game is over, whitechess is the winner", Toast.LENGTH_SHORT).show();
                        } else {
                            Turn.setText("The game ends in a draw");
                            img_turn.setImageResource(R.drawable.transparent);
                            Toast.makeText(GameFrame.this, "The game ends in a draw", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }
        });
        //chessboard setting ends

    }



    public void NewGame(){

        started = true;
        mark = true;
        hintson = false;
        btn_hintson.setText(String.valueOf("Hints On"));

        for (int i=0; i<Records.length; i++)
            Records[i] = 0;
        for (int i=0; i < imgs.length; i++){
            imgs[i] = R.drawable.transparent;
            Records[i] = EMPTY_CHESS;
        }
        for (int i : WHITE_INITIAL){
            imgs[i] = R.drawable.white_chess;
            Records[i] = WHITE_CHESS;
        }
        for (int i : BLACK_INITIAL){
            imgs[i] = R.drawable.black_chess;
            Records[i] = BLACK_CHESS;
        }
        blackcounter = 2;
        whitecounter = 2;
        black_counter.setText("" + blackcounter);
        white_counter.setText("" + whitecounter);
        Turn.setText("Turn:");
        img_turn.setImageResource(R.drawable.black_chess);
        currentChess = BLACK_CHESS;
        nextChess = WHITE_CHESS;
        Records[27] = 2;
        Records[36] = 2;
        Records[28] = 1;
        Records[35] = 1;

         for (int i = 0; i < CHESSBOARD; i ++) {
             Hints[i] = EMPTY_CHESS;
         }

        chessAdapter.notifyDataSetChanged();
    }

    public void MoveAvailable(int position){

        int pos;
        int turnpos;

        turn.clear();
        mark = false;

        boolean[] flag = new boolean[8];
        for (int i = 0; i < 8; i++){
            flag[i] = false;
        }

        if (Records[position] == EMPTY_CHESS) {

            //check top direction
            pos = position;
            pos -= BORDER;
            if ((pos >= 0) && (Records[pos] == nextChess)) {

                for (pos -= BORDER; pos >= 0; pos -= BORDER) {
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[0] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[0] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[0] = true;

//                        Log.v(LOG_TAG, "hintCheckTOP, Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos -= BORDER; turnpos > pos; turnpos -= BORDER){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            } else flag[0] = false;

            //check bottom direction
            pos = position;
            pos += BORDER;
            if ((pos < CHESSBOARD) && (Records[pos] == nextChess)) {

                for (pos += BORDER; pos < CHESSBOARD; pos += BORDER) {
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[1] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[1] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[1] = true;

//                        Log.v(LOG_TAG, "hintCheckBottom, Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos += BORDER; turnpos < pos; turnpos += BORDER){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            } else flag[1] = false;

            //check left direction
            pos = position;
            pos -= 1;
            if ((pos >= 0) && ((pos % BORDER) < (position % BORDER)) && (Records[pos] == nextChess)){

                for (pos -=1; (pos>= 0)&&((pos % BORDER) < (position % BORDER)) ; pos -= 1){
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[2] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[2] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[2] = true;

//                        Log.v(LOG_TAG, "hintCheckleft, Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos -= 1; turnpos > pos; turnpos -= 1){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            }else flag[2] = false;

            //check right direction
            pos = position;
            pos += 1;
            if ((pos < CHESSBOARD) && ((pos % BORDER) > (position % BORDER)) && (Records[pos] == nextChess)){

                for (pos +=1; ((pos % BORDER) > (position % BORDER)) && (pos<CHESSBOARD); pos +=1){
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[3] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[3] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[3] = true;

//                        Log.v(LOG_TAG, "hintCheckRight, Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos += 1; turnpos < pos; turnpos += 1){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            }else flag[3] = false;

            //check topleft direction
            pos = position;
            pos -= (BORDER+1);
            if ((pos>=0) && ((pos % BORDER) < (position % BORDER)) && (Records[pos] == nextChess)){

                for (pos -= (BORDER+1); (pos>= 0)&&((pos % BORDER) < (position % BORDER)) ; pos -= (BORDER+1)){
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[4] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[4] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[4] = true;

//                        Log.v(LOG_TAG, "hintCheckTopleft, Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos -= (BORDER+1); turnpos > pos; turnpos -= (BORDER+1)){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            }else flag[4] = false;

            //check topright direction
            pos = position;
            pos -= (BORDER-1);
            if ((pos>=0) && ((pos % BORDER) > (position % BORDER)) && (Records[pos] == nextChess)){

                for (pos -= (BORDER-1); (pos>= 0)&&((pos % BORDER) > (position % BORDER)) ; pos -= (BORDER-1)){
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[5] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[5] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[5] = true;

//                        Log.v(LOG_TAG, "hintCheckTopRight(), Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos -= (BORDER-1); turnpos > pos; turnpos -= (BORDER-1)){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            }else flag[5] = false;

            //check bottomleft direction
            pos = position;
            pos += (BORDER-1);
            if ((pos < CHESSBOARD) && ((pos % BORDER) < (position % BORDER)) && (Records[pos] == nextChess)){

                for (pos += (BORDER-1); ((pos % BORDER) < (position % BORDER)) && (pos<CHESSBOARD); pos += (BORDER-1)){
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[6] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[6] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[6] = true;

//                        Log.v(LOG_TAG, "hintCheckBottomLeft, Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos += (BORDER-1); turnpos < pos; turnpos += (BORDER-1)){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            }else flag[6] = false;

            //check bottomright direction
            pos = position;
            pos += (BORDER+1);
            if ((pos < CHESSBOARD) && ((pos % BORDER) > (position % BORDER)) && (Records[pos] == nextChess)){

                for (pos += (BORDER+1); ((pos % BORDER) > (position % BORDER)) && (pos<CHESSBOARD); pos += (BORDER+1)){
                    if (Records[pos] == EMPTY_CHESS) {
                        flag[7] = false;
                        break;
                    } else if (Records[pos] == nextChess) {
                        flag[7] = false;
                    } else if (Records[pos] == currentChess) {
                        flag[7] = true;

//                        Log.v(LOG_TAG, "hintCheckBottomRight, Start Position: " + position + ", Added Potential Position: " + pos);

                        turnpos = position;
                        for (turnpos += (BORDER+1); turnpos < pos; turnpos += (BORDER+1)){
                            if (!turn.contains(turnpos)) {
                                turn.add(turnpos);
                            }
                        }

                        break;
                    }
                }
            }else flag[7] = false;


            for (int i = 0; i < 8; i++) {
                if (flag[i]) {
                    Hints[position] = currentChess;

//                    Log.v(LOG_TAG,""+i+":"+position);

                    mark = true;
                    flag[i] = false;
                    break;
                }
            }

        }


        chessAdapter.notifyDataSetChanged();
    }


    public void Move(int record, int position){

        started = true;

        MoveAvailable(position);

        if (Hints[position] == BLACK_CHESS && mark == true) {
            Records[position] = BLACK_CHESS;
            imgs[position] = IMG_BLACK;
            currentChess = WHITE_CHESS;
            nextChess = BLACK_CHESS;

           for (Integer e : turn){
                Records[e] = BLACK_CHESS;
                imgs[e] = IMG_BLACK;
            }

            blackcounter = blackcounter + turn.size() + 1;
            whitecounter = whitecounter - turn.size();
            black_counter.setText("" + blackcounter);
            white_counter.setText("" + whitecounter);
            img_turn.setImageResource(R.drawable.white_chess);

        }
        else if (Hints[position] == WHITE_CHESS && mark == true) {
            Records[position] = WHITE_CHESS;
            imgs[position] = IMG_WHITE;
            currentChess = BLACK_CHESS;
            nextChess = WHITE_CHESS;

           for (Integer e : turn){
                Records[e] = WHITE_CHESS;
                imgs[e] = IMG_WHITE;
            }

            whitecounter = whitecounter + turn.size() + 1;
            blackcounter = blackcounter - turn.size();
            black_counter.setText("" + blackcounter);
            white_counter.setText("" + whitecounter);
            img_turn.setImageResource(R.drawable.black_chess);
        }

        for (int i = 0; i < CHESSBOARD; i ++) {
            Hints[i] = EMPTY_CHESS;
        }

        turn.clear();
        chessAdapter.notifyDataSetChanged();
    }

    protected void HintsOn() {
        boolean flag;
        if (!hintson) {
            flag = true;
        } else {
            flag = false;
        }
        hintson = flag;
        if (hintson) {
            btn_hintson.setText(String.valueOf("Hints Off"));
        } else {
            btn_hintson.setText(String.valueOf("Hints On"));
        }

            DisplayHints(hintson);

    }

    protected void DisplayHints(boolean flag){

        if (flag){
            for (int i=0; i<CHESSBOARD; i ++){

                MoveAvailable(i);

                if (Hints[i] == BLACK_CHESS && mark == true)
                    imgs[i] = IMG_BLACK_T;
                else if (Hints[i] == WHITE_CHESS && mark == true)
                    imgs[i] = IMG_WHITE_T;

                // Log.v(LOG_TAG,""+i+":"+Hints[i]);

                Hints[i] = EMPTY_CHESS;
                mark = false;

                chessAdapter.notifyDataSetChanged();

            }
        }
        else {
            for (int i=0; i<CHESSBOARD; i ++){
                if (Records[i] == EMPTY_CHESS){
                    MoveAvailable(i);
                    imgs[i] = IMG_EMPTY;
                    Hints[i] = EMPTY_CHESS;
                    mark = false;

                    chessAdapter.notifyDataSetChanged();
                }
            }
        }



    }

    public void Cleanhints(boolean flag){

        for (int i=0; i<CHESSBOARD; i ++){
            if (Records[i] == EMPTY_CHESS){
                MoveAvailable(i);
                imgs[i] = IMG_EMPTY;
                Hints[i] = EMPTY_CHESS;
                mark =false;

            }
        }

        for (int i = 0; i < CHESSBOARD; i ++){
            Hints[i] = EMPTY_CHESS;
        }
        chessAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_hintson:
                HintsOn();return;
            case R.id.btn_newgame:
                NewGame();break;
//            case R.id.btn_retract:
//                Toast.makeText(GameFrame.this, "Retract clicked", Toast.LENGTH_SHORT).show();
        }

    }

}
