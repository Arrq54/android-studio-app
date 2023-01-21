package com.example.arkadiuszapp001;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item listItem = list.get(position);


        Log.d("xxx", String.valueOf(holder));

        holder.infoTxt.setText(listItem.getCreationTime());
        holder.sizeTxt.setText(listItem.getSize());

        String ip = "192.168.0.1";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        if (preferences.getString("ip", null) != null){
            ip = preferences.getString("ip", null)+":3000";
        }
        Log.d("xxx","http://"+ip+"/upload/"+listItem.getName());
        Picasso.get().load("http://"+ip+"/upload/"+listItem.getName()).into(holder.photo);
    }

    @Override
    public int getItemCount()  {
        return list.size();
    }

    private List<Item> list;
    private Context _context;

    public RecAdapter(List<Item> list, Context ctx) {
        this.list = list;
        this._context = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView infoTxt;
        private TextView sizeTxt;
        private ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infoTxt = itemView.findViewById(R.id.info);
            sizeTxt = itemView.findViewById(R.id.size);
            photo = itemView.findViewById(R.id.photo);
        }
    }
}
