package com.example.arkadiuszapp001;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class EffectsActivity extends AppCompatActivity {

    private ImageView iv;
    private HorizontalScrollView hsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);

        iv = findViewById(R.id.imageViewEffects);


        Bitmap b = Imaging.myBitmap;

        iv.setImageBitmap(b);
        float[] normal_tab = {
                1.0f, 0, 0, 0, 0,
                0, 1.0f, 0, 0, 0,
                0, 0, 1.0f, 0, 0,
                0, 0, 0, 1.0f, 0
        };
        float[] red_tab = {
                2, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0
        };
        float[] neg_tab = {
                -1, 0, 0, 1, 0,
                0, -1, 0, 1, 0,
                0, 0, -1, 1, 0,
                0, 0, 0, 1, 0

        };

        ArrayList x = new ArrayList();
        x.add(normal_tab);
        x.add(red_tab);
        x.add(neg_tab);


        for(int i=0;i<x.size();i++){
            LinearLayout ll = new LinearLayout(EffectsActivity.this);




        }




    }
}