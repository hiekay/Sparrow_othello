package com.example.sparrow.zxb_othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_START_GAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_START_GAME = (Button) findViewById(R.id.btn_START_GAME);
        btn_START_GAME.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_START_GAME) {
            Intent myIntent = new Intent(v.getContext(),GameFrame.class);
            startActivityForResult(myIntent,0);
        }
    }
}
