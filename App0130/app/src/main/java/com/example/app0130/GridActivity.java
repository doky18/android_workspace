package com.example.app0130;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //GridLayout도 경험해보기
        GridLayout layout = new GridLayout(this);
        layout.setColumnCount(3);
        layout.setRowCount(5);

        for(int i=1;i<=15;i++){
            Button bt = new Button(this);
            bt.setText("버튼"+i);

            layout.addView(bt);
        }

        setContentView(layout);
    }
}