package com.example.arkadiuszapp001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class NewInnerAlbumActivity extends AppCompatActivity {

    private String albumName;
    ImagesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_inner_album);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        albumName = bundle.getString("albumName").toString();


        File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla" + File.separator + albumName);

        ArrayList<File> list = new ArrayList<>(Arrays.asList(folder.listFiles()));


        adapter = new ImagesAdapter (
                NewInnerAlbumActivity.this,
                R.layout.imagelistviewelement,
                list
        );

        ListView lv = findViewById(R.id.listview);
        lv.invalidateViews();



        lv.setAdapter(adapter);


        LinearLayout ll = findViewById(R.id.mainbox);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}