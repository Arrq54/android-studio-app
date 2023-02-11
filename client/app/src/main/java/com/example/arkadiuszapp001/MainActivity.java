package com.example.arkadiuszapp001;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private LinearLayout camera;
    private LinearLayout albums;
    private LinearLayout collage;
    private LinearLayout network;
    private LinearLayout albumsNew;
    private LinearLayout notes;
    private AlertDialog folderDialog;

    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    } ;

    public void checkPermissions(String[] permission, int requestCode){
        boolean allPermisions = true;
        for(String p: permissions){if (ContextCompat.checkSelfPermission(MainActivity.this, p) == PackageManager.PERMISSION_DENIED) {allPermisions = false;}}
        if(allPermisions==false){
            ActivityCompat.requestPermissions(MainActivity.this, permissions, requestCode);
        }else{
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File dir = new File(pic, "ArkadiuszWojdyla");
            dir.mkdir();
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "ArkadiuszWojdyla");
            File miejsca = new File(folder, "miejsca");
            File ludzie = new File(folder, "ludzie");
            File rzeczy = new File(folder, "rzeczy");
            miejsca.mkdir();
            ludzie.mkdir();
            rzeczy.mkdir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                    File dir = new File(pic, "ArkadiuszWojdyla");
                    dir.mkdir();

                    File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla");
                    File miejsca = new File(folder, "miejsca");
                    miejsca.mkdir();

                    File ludzie = new File(folder, "ludzie");
                    ludzie.mkdir();

                    File rzeczy = new File(folder, "rzeczy");
                    rzeczy.mkdir();
                } else {
                    Toast.makeText(MainActivity.this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            case 101 :

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions(permissions,100);
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybierz zrodlo zdjecia!");
//ok
                alert.setPositiveButton("Aparat", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                            Toast.makeText(MainActivity.this, "NO CAMERA PERMISSION!", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, 200); // 200 - stała wartość, która później posłuży do identyfikacji tej akcji
                            }
                        }


                    }

                });

//no
                alert.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyświetl which
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 100); // 100 - stała wartość, która później posłuży do identyfikacji tej akcji
                    }
                });
//
                alert.show();




//                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
//                startActivity(intent);
            }
        });

        albums = findViewById(R.id.albums);
        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlbumsActivity.class);
                startActivity(intent);
            }
        });

        notes = findViewById(R.id.notes);
        notes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
        });

        collage = findViewById(R.id.collage);
        collage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollageActivity.class);
                startActivity(intent);
            }
        });

        network = findViewById(R.id.network);
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NetworkActivity.class);
                startActivity(intent);
            }
        });


        albumsNew = findViewById(R.id.albumsnew);
        albumsNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewAlbumsActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
//        list.add(new Item("a", "d"));
//        list.add(new Item("b", "e"));
//        list.add(new Item("c", "f"));
        layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        String ip = "192.168.0.1";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (preferences.getString("ip", null) != null){
            ip = preferences.getString("ip", null)+":3000";
        }
        Log.d("xxx", "http://"+ip+"/getFiles");
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://"+ip+"/getFiles",
                null,
                response -> {
                    Log.d("xxx", "response: " + response);
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject responseObj = response.getJSONObject(i);
                            Item item = new Item(
                                    responseObj.getString("name"),
                                    responseObj.getString("url"),
                                    "czas zapisu: " + responseObj.getString("creationTime"),
                                    "wielkość zdjęcia: " + responseObj.getInt("size")
                            );
                            list.add(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new RecAdapter(list, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                },
                error -> {

                    Log.d("xxx", "error: " + error.getMessage());
//                    Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                }
        );
        Volley.newRequestQueue(MainActivity.this).add(jsonRequest);


    }

    private ArrayList<Item> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap b = null;
        if (resultCode == RESULT_OK) {
            if(requestCode==200){
                Bundle extras = data.getExtras();
                b = (Bitmap) extras.get("data");
            }else if(requestCode == 100){
                Uri imgData = data.getData();
                InputStream stream = null;
                try {
                    stream = getContentResolver().openInputStream(imgData);
                    b = BitmapFactory.decodeStream(stream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);


                ImageView image = new ImageView(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 400);
                image.setLayoutParams(lp);
                image.setAdjustViewBounds(true);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);

                image.setImageBitmap(b);



                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "ArkadiuszWojdyla");
                File[] files = folder.listFiles();
                for (File file : folder.listFiles()){
                    Button bt = new Button(MainActivity.this);
                    bt.setText(file.getName());
                    Bitmap finalB = b;
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {

                                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                String filename = df.format(new Date()) + ".jpg";
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                finalB.compress(Bitmap.CompressFormat.JPEG, 100, stream); // kompresja, typ pliku jpg, png
                                byte[] byteArray = stream.toByteArray();
                                FileOutputStream fs = null;
                                fs = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)  + File.separator + "ArkadiuszWojdyla" + File.separator+ file.getName() + File.separator + filename);
                                fs.write(byteArray);
                                fs.close();
                                folderDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Zapisano", Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            };

                        }
                    });
                    ll.addView(bt);
                }
                ll.addView(image);


                alert.setTitle("W którym miejscu zapisać to zdjęcie?");
                alert.setView(ll);
                 folderDialog = alert.create();
                 folderDialog.show();
        }
    }
}