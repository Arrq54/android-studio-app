package com.example.arkadiuszapp001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NotesActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<Note> notes;
    protected void refresh(){
        DatabaseManager db = new DatabaseManager (
                NotesActivity.this, // activity z galerią zdjęć
                "NotatkiWojdylaArkadiusz.db", // nazwa bazy
                null,
                1 //wersja bazy, po zmianie schematu bazy należy ją zwiększyć
        );
        notes = db.getAll();
        NoteAdapter adapter = new NoteAdapter(
                NotesActivity.this,
                R.layout.note_layout,
                notes

        );
        lv = findViewById(R.id.lvnotes);
        lv.setAdapter(adapter);
        db.close();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        refresh();
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(NotesActivity.this);
                alert.setTitle("Wybierz opcje dla notatki!");
                String[] opcje = {"edytuj","usun","sortuj wg tytyulu"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // wyswietl opcje[which]);
                        if(opcje[which]=="edytuj"){
                            AlertDialog.Builder editAlert = new AlertDialog.Builder(NotesActivity.this);
                            editAlert.setTitle("Edytuj notatke! ");
                            LinearLayout ll = new LinearLayout(NotesActivity.this);
                            EditText title = new EditText(NotesActivity.this);
                            EditText content = new EditText(NotesActivity.this);
                            title.setText(notes.get(i).getTitle());
                            int[] selectedColor = new int[]{Color.parseColor("#"+notes.get(i).getColor())};
                            title.setTextColor(selectedColor[0]);
                            content.setText(notes.get(i).getNoteContent());
                            int[] arr = new int[]{0xff0099ff, 0xffff5100, 0xffeb2f58, 0xffa841d1, 0xfff765d0, 0xff7fe3bd, 0xff2fa134, 0xff074f0a};
                            LinearLayout colors = new LinearLayout(NotesActivity.this);
                            colors.setOrientation(LinearLayout.HORIZONTAL);
                            for(int i :arr){
                                ImageView x = new ImageView(NotesActivity.this);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
                                x.setLayoutParams(layoutParams);
                                x.setBackgroundColor(i);
                                x.setBackgroundResource(R.drawable.circle);

                                ((GradientDrawable)x.getBackground()).setColor(i);
                                colors.addView(x);
                                x.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        selectedColor[0] = i;
                                        title.setTextColor(selectedColor[0]);
                                    }
                                });
                            }
                            ll.addView(colors);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            ll.addView(title);
                            ll.addView(content);
                            editAlert.setView(ll);
                            editAlert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseManager db = new DatabaseManager (
                                            NotesActivity.this, // activity z galerią zdjęć
                                            "NotatkiWojdylaArkadiusz.db", // nazwa bazy
                                            null,
                                            1 //wersja bazy, po zmianie schematu bazy należy ją zwiększyć
                                    );
                                    db.editNote(notes.get(i).getId(), Integer.toHexString(selectedColor[0]), title.getText().toString(), content.getText().toString(), notes.get(i).getPhotopath());
                                    refresh();
                                    db.close();
                                }
                            });
                            editAlert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //wyświetl which
                                }
                            });
                            editAlert.show();
                        }
                        else if(opcje[which]=="usun"){
                            DatabaseManager db = new DatabaseManager (
                                    NotesActivity.this,
                                    "NotatkiWojdylaArkadiusz.db",
                                    null,
                                    1
                            );
                            db.deleteNote(notes.get(i).getId());
                            db.close();
                            refresh();
                        }
                        else if(opcje[which]=="sortuj wg tytyulu"){
                            Collections.sort(notes, new Comparator<Note>() {
                                @Override
                                public int compare(Note a, Note b) {
                                    return a.getTitle().compareTo(b.getTitle());
                                }
                            });
                            NoteAdapter adapter = new NoteAdapter(
                                    NotesActivity.this,
                                    R.layout.note_layout,
                                    notes

                            );
                            lv = findViewById(R.id.lvnotes);
                            lv.setAdapter(adapter);

                        }


                    }
                });
                alert.show();
            }
        });
    }
}