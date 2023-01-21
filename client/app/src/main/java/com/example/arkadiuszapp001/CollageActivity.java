package com.example.arkadiuszapp001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

public class CollageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);


        ImageView c1 = findViewById(R.id.c1);
        c1.setOnClickListener(v ->{
            ArrayList<ImageData> list = new ArrayList<>();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            list.add(new ImageData(0,0,(size.x/2)-1,((5*size.y)/7)-1));
            list.add(new ImageData(0,((5*size.y)/7),(size.x/2)-1,((2*size.y)/7)-1));
            list.add(new ImageData((size.x/2)+1,0,(size.x/2)-1,((2*size.y)/7)-1));
            list.add(new ImageData((size.x/2)+1,((2*size.y)/7)+1,(size.x/2)-1,((5*size.y)/7)-1));
            Intent intent = new Intent(CollageActivity.this, CollageMakerActivity.class);
            intent.putExtra("list", list);
            startActivity(intent);
        });

        ImageView c2 = findViewById(R.id.c2);
        c2.setOnClickListener(v ->{
            ArrayList<ImageData> list = new ArrayList<>();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            list.add(new ImageData(0,0,(size.x/2)-1,(size.y/2)-1));
            list.add(new ImageData((size.x/2),0,(size.x/2)-1,(size.y/2)-1));
            list.add(new ImageData(0,(size.y/2),size.x,(size.y/2)-1));

            Intent intent = new Intent(CollageActivity.this, CollageMakerActivity.class);
            intent.putExtra("list", list);
            startActivity(intent);
        });

        ImageView c3 = findViewById(R.id.c3);
        c3.setOnClickListener(v ->{
            ArrayList<ImageData> list = new ArrayList<>();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            list.add(new ImageData(0,0,(size.x/2)-1,size.y));
            list.add(new ImageData((size.x/2),0,(size.x/2)-1,(size.y/2)-1));
            list.add(new ImageData((size.x/2),(size.y/2),(size.x/2)-1,(size.y/2)-1));

            Intent intent = new Intent(CollageActivity.this, CollageMakerActivity.class);
            intent.putExtra("list", list);
            startActivity(intent);
        });


        ImageView c4 = findViewById(R.id.c4);
        c4.setOnClickListener(v ->{
            ArrayList<ImageData> list = new ArrayList<>();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            list.add(new ImageData(0,0,(size.x/3)-1,size.y/2));
            list.add(new ImageData((size.x/3),0,(size.x/3)-1,size.y/2));
            list.add(new ImageData((2*size.x/3),0,(size.x/3)-1,size.y/2));
            list.add(new ImageData(0,(size.y/2)+1,size.x,size.y/2));
            Intent intent = new Intent(CollageActivity.this, CollageMakerActivity.class);
            intent.putExtra("list", list);
            startActivity(intent);
        });







        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}