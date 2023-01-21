package com.example.arkadiuszapp001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    private ImageView deleteBtn;
    private String albumName;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        androidx.appcompat.app.ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        albumName = bundle.getString("albumName").toString();
        String photoPath = bundle.getString("path").toString();

        Bitmap bmp2 = BitmapFactory.decodeFile(photoPath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        ArrayList<DrawerItem> list = new ArrayList<>();
        list.add(new DrawerItem("upload"));
        list.add(new DrawerItem("effects"));

        ImageView a = findViewById(R.id.main);
        a.setScaleType(ImageView.ScaleType.CENTER_CROP);


        Bitmap bmp = betterImageDecode(photoPath);
        a.setImageBitmap(bmp);
        final boolean[] x = {false};
        DrawerAdapter adapter = new DrawerAdapter(
                ImageActivity.this,
                R.layout.drawer_adapter_item,
                list,
                byteArray,
                ((BitmapDrawable) a.getDrawable()).getBitmap()
        );
        ListView lv = findViewById(R.id.drawerlv);
        lv.setAdapter(adapter);



        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!x[0]){
                    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    a.setLayoutParams(lp2);
                    x[0] =true;
                }else{
                    RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                    a.setLayoutParams(lp2);
                    x[0] =false;
                }
            }
        });

        ImageView settings = findViewById(R.id.settings);
        settings.setOnClickListener(v->{

            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLayout.openDrawer(GravityCompat.START);
        });



        deleteBtn = findViewById(R.id.deletebtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File pic = new File(photoPath);
                pic.delete();

                finish();
                Intent intent = new Intent(ImageActivity.this,InnerAlbumActivity.class);
                intent.putExtra("albumName", albumName);
                startActivity(intent);

            }
        });
        TextView ip = findViewById(R.id.ip);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ImageActivity.this);

        if (preferences.getString("ip", null) != null){
            ip.setText(preferences.getString("ip", null));
        }
        ip.setOnClickListener(v->{
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Podaj nowe ip!");

//tutaj input
            EditText input = new EditText(this);
            input.setText("192.168.0.1");

            LinearLayout ll = new LinearLayout(this);
            ll.addView(input);
    ll.setOrientation(LinearLayout.VERTICAL);
            Button b1 = new Button(this);


            b1.setOnClickListener(z->{
                SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences2.edit();
                editor.putString("ip", String.valueOf(input.getText()));
                editor.commit();
                ip.setText(String.valueOf(input.getText()));
            });


            b1.setText("Zapisz ip");
            ll.addView(b1);
            alert.setView(ll);
//teraz butony jak poprzednio i
            alert.show();
        });


    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Bitmap betterImageDecode(String filePath) {
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 1; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
}