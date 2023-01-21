package com.example.arkadiuszapp001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkActivity extends AppCompatActivity {
    private ArrayList<Item> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recyclerViewNetwork);
//        list.add(new Item("a", "d"));
//        list.add(new Item("b", "e"));
//        list.add(new Item("c", "f"));
        layoutManager = new LinearLayoutManager(NetworkActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        String ip = "192.168.0.1";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NetworkActivity.this);
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
                    adapter = new RecAdapter(list, NetworkActivity.this);
                    recyclerView.setAdapter(adapter);
                },
                error -> {

                    Log.d("xxx", "error: " + error.getMessage());
                    Toast.makeText(NetworkActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
        );
        Volley.newRequestQueue(NetworkActivity.this).add(jsonRequest);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}