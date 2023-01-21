package com.example.arkadiuszapp001;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arkadiuszapp001.multipart.Multipart;

import java.io.ByteArrayOutputStream;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class EffectsActivity extends AppCompatActivity {

    private ImageView iv;
    private HorizontalScrollView hsv;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);

        iv = findViewById(R.id.imageViewEffects);

        ImageView settings = findViewById(R.id.settings);
        settings.setOnClickListener(v->{


            Bitmap bmp2 = ((BitmapDrawable)iv.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();





            String ip = "192.168.0.1";
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EffectsActivity.this);
            if (preferences.getString("ip", null) != null){
                ip = preferences.getString("ip", null)+":3000";
            }
            Multipart multipart = new Multipart(EffectsActivity.this);
            String uniqueName =  (ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT ) +"_"+ ThreadLocalRandom.current().nextInt(0, 1000000 + 1)).replace('.','-').replace(':','_');;
            multipart.addFile("image/jpeg", "file", uniqueName+".jpg", byteArray);
            String finalIp = ip;
            multipart.launchRequest("http://"+ip+"/upload",
                    response -> {
                        Toast.makeText(EffectsActivity.this, "UPLOAD", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Toast.makeText(EffectsActivity.this, finalIp, Toast.LENGTH_SHORT).show();
                        Toast.makeText(EffectsActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    });
        });


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

        float[] grayscale = {
                0.5F, 0.5F, 0.5F, 0, 0,
                0.5f, 0.5f, 0.5F, 0, 0,
                0.5f, 0.5f, 0.5f, 0, 0,
                0, 0, 0, 1, 0

        };
        float[] violet_green = {
                0,1,0,0,0,
                1,0,0,0,0,
                0,0,2,0,0,
                0,0,0,1,0

        };

        float[] yel = {
                1,0,0,0,0,
                0,1,-1,0,0,
                0,-1,1,0,0,
                0,0,0,1,0

        };
        float[] green = {
                1,0,0,0,0,
                0,3,2,0,0,
                0,0,0,0,0,
                0,0,0,1,0

        };

        float[] blue = {
                -1,-1,0,0,0,
                1,1,0,0,0,
                0,3,1,0,0,
                0,0,0,1,0

        };
        ArrayList x = new ArrayList();
        x.add(normal_tab);
        x.add(red_tab);
        x.add(neg_tab);
        x.add(violet_green);
        x.add(yel);
        x.add(green);
        x.add(blue);
        x.add(blue);

        hsv = findViewById(R.id.hsv);
        LinearLayout main = new LinearLayout(EffectsActivity.this);
        for(int i=0;i<x.size();i++){
            LinearLayout ll = new LinearLayout(EffectsActivity.this);

            ColorMatrix cMatrix = new ColorMatrix();
//
            cMatrix.set(red_tab);
            switch(i){
                case 0:
                    cMatrix.set(normal_tab);
                    break;
                case 1:
                    cMatrix.set(red_tab);
                    break;
                case 2:
                    cMatrix.set(neg_tab);
                    break;
                case 3:
                    cMatrix.set(grayscale);
                    break;
                case 4:
                    cMatrix.set(violet_green);
                    break;
                case 5:
                    cMatrix.set(yel);
                    break;
                case 6:
                    cMatrix.set(green);
                    break;
                case 7:
                    cMatrix.set(blue);
                    break;
                default:break;
            }
//
            Paint paint = new Paint();

            Bitmap copy = b.copy(b.getConfig(),true);

            paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

            Canvas canvas = new Canvas(copy);

            canvas.drawBitmap(copy, 0, 0, paint);

            ImageView ivFilter = new ImageView(this);

            ivFilter.setImageBitmap(copy);


//            TextView tv = new TextView(this);
//            tv.setText("asdasdads");
            ll.addView(ivFilter);
//            ll.addView(tv);

            ll.setOnClickListener(v->{
                iv.setImageBitmap(copy);
            });

            ll.setOrientation(LinearLayout.VERTICAL);
            main.addView(ll);

        }
        hsv.addView(main);




    }
}