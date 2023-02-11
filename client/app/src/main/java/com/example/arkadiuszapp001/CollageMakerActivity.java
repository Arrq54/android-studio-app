package com.example.arkadiuszapp001;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CollageMakerActivity extends AppCompatActivity {
    protected ImageView clickedIv;
    protected ImageView flip;
    protected ImageView rotate;
    protected ImageView done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_maker);


        flip = findViewById(R.id.flip);
        rotate = findViewById(R.id.rotate);
        done = findViewById(R.id.done);

        ArrayList<ImageData> list = (ArrayList<ImageData>) getIntent().getExtras().getSerializable("list");
        FrameLayout body = findViewById(R.id.body);
        body.setDrawingCacheEnabled(true);
        flip.setOnClickListener(v->{
            if(clickedIv != null){
                Matrix matrix = new Matrix();
                matrix.postScale(-1.0f, 1.0f);
                Bitmap oryginal = ((BitmapDrawable) clickedIv.getDrawable()).getBitmap();


                Bitmap rotated = Bitmap.createBitmap(oryginal, 0, 0, oryginal.getWidth(), oryginal.getHeight(), matrix, true);

                clickedIv.setImageBitmap(rotated);
            }
        });

        rotate.setOnClickListener(v->{
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap oryginal = ((BitmapDrawable) clickedIv.getDrawable()).getBitmap();


            Bitmap rotated = Bitmap.createBitmap(oryginal, 0, 0, oryginal.getWidth(), oryginal.getHeight(), matrix, true);

            clickedIv.setImageBitmap(rotated);
        });
        done.setOnClickListener(v->{
            Bitmap b = body.getDrawingCache(true);


            File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
            File dir = new File(pic, "ArkadiuszWojdyla");
            dir.mkdir();

            File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla");

            File collages = new File(folder, "collages");
            collages.mkdir();




            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, stream); // kompresja, typ pliku jpg, png
            byte[] byteArray = stream.toByteArray();


            FileOutputStream fs = null;
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String d = df.format(new Date());
                fs = new FileOutputStream(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla" + File.separator + "collages"+ File.separator+d+".jpg");
                fs.write(byteArray);
                fs.close();
                Toast.makeText(CollageMakerActivity.this, "SAVED", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });





        for (int i = 0; i < list.size(); i++) {
            ImageView iv = new ImageView(CollageMakerActivity.this);
            iv.setX(list.get(i).getX());
            iv.setY(list.get(i).getY());
            iv.setBackgroundColor(0xff242423);
            iv.setPadding(150, 150, 150, 150);
            iv.setImageResource(R.drawable.addphoto);
            iv.setLayoutParams(new FrameLayout.LayoutParams(list.get(i).getW(), list.get(i).getH()));
            iv.setOnClickListener(v->{
                clickedIv = iv;
            });
            iv.setOnLongClickListener(v -> {
                clickedIv = iv;

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100); // 100 - stała wartość, która później posłuży do identyfikacji tej akcji
                return false;
            });

            body.addView(iv);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == 100){
                Uri imgData = data.getData();
                try {
                    InputStream stream = getContentResolver().openInputStream(imgData);
                    Bitmap b = BitmapFactory.decodeStream(stream);
                    clickedIv.setImageBitmap(b);
                    clickedIv.setPadding(0, 0, 0, 0);
                    clickedIv.setScaleType(ImageView.ScaleType.FIT_XY);
                }catch(Exception x){

                }
            }


        }
    }


}