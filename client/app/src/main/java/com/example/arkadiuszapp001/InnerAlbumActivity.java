package com.example.arkadiuszapp001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class InnerAlbumActivity extends AppCompatActivity {
    public String albumName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_album);

        Bundle bundle = getIntent().getExtras();
        albumName = bundle.getString("albumName").toString();





        refreshPhotos() ;
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void refreshPhotos(){
        File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla" + File.separator + albumName);
        LinearLayout ll = findViewById(R.id.mainbox);
        for (File file : folder.listFiles()){

            ImageView iv = new ImageView(InnerAlbumActivity.this);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT, 500);
            iv.setLayoutParams(lp2);
            iv.setAdjustViewBounds(true);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
            p.setMargins(0, 20, 0, 20);
            iv.requestLayout();
            String path = file.getPath();
            Bitmap bmp = betterImageDecode(path);
            iv.setImageBitmap(bmp);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InnerAlbumActivity.this,ImageActivity.class);
                    intent.putExtra("albumName", albumName);
                    intent.putExtra("path", path);
                    startActivity(intent);
                    finish();
                }
            });
            ll.addView(iv);
        }
    }
    private Bitmap betterImageDecode(String filePath) {
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}