package com.example.arkadiuszapp001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;

public class NewAlbumsActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        listView = findViewById(R.id.listview);




        refreshListView();



        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(NewAlbumsActivity.this);
                alert.setTitle("Nowy folder");
                alert.setMessage("Podaj nazwe nowego folderu: ");
                EditText input = new EditText(NewAlbumsActivity.this);
                input.setText("new_folder");
                alert.setView(input);
                alert.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla");
                        File miejsca = new File(folder, String.valueOf(input.getText()));
                        miejsca.mkdir();
                        refreshListView();
                    }

                });

//no
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyświetl which
                    }
                });
//
                alert.show();
            }
        });










        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public void refreshListView(){
        File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla");
        File[] files = folder.listFiles();
        String[] array = new String[files.length];

        int i=0;
        for (File file : folder.listFiles()){
            array[i] = file.getName();
            i++;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                NewAlbumsActivity.this,       // tzw Context
                R.layout.newfolderlistelement,     // nazwa pliku xml naszego wiersza na liście
                R.id.tv1,                // id pola txt w wierszu
                array );                 // tablica przechowująca testowe dane


        listView.setAdapter(adapter);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder alert = new AlertDialog.Builder(NewAlbumsActivity.this);
                alert.setTitle("USUWANIE FOLDERU!");
                alert.setMessage("CZy na pewno chcesz usunac?");
//ok
                alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyświetl zmienną which
                        File file = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla" + File.separator+ array[i]);
                        file.delete();
                        refreshListView();
                    }

                });

//no
                alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyświetl which
                    }
                });
//
                alert.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(NewAlbumsActivity.this,NewInnerAlbumActivity.class);
                intent.putExtra("albumName", array[i]);
                startActivity(intent);
            }
        });
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}