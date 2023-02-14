package com.example.app0214;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Button bt_prev = findViewById(R.id.bt_prev);
        Button bt_next = findViewById(R.id.bt_next);
        GalleryView galleryView=findViewById(R.id.galleryView);

        bt_next.setOnClickListener((v)->{
            galleryView.nextImg();
        });

        bt_prev.setOnClickListener((v)->{
            galleryView.prevImg();
        });

    }
}