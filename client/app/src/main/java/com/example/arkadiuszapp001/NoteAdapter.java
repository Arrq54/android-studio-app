package com.example.arkadiuszapp001;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter {
    private ArrayList<Note> _list;
    private Context _context;
    private int _resource;
    public NoteAdapter(@NonNull Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
        this._list = objects;
        this._context = context;
        this._resource = resource;
    }
    public void refresh(){
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.note_layout, null);


        TextView tv1 = (TextView) convertView.findViewById(R.id.notetitle);
        tv1.setText(_list.get(position).getTitle());

        tv1.setTextColor(Color.parseColor("#" +_list.get(position).getColor()));

        TextView tv2 = (TextView) convertView.findViewById(R.id.notecontent);
        tv2.setText(_list.get(position).getNoteContent());

        TextView tv3 = (TextView) convertView.findViewById(R.id.path);
        tv3.setText(_list.get(position).getPhotopath());

        TextView tv4 = (TextView) convertView.findViewById(R.id.noteid);
        tv4.setText(String.valueOf(_list.get(position).getId()));

        LinearLayout ll =  (LinearLayout) convertView.findViewById(R.id.rownote);
        if(position%2==0){
            ll.setBackgroundColor(0xff303030);
        }


        return convertView;


    }
}