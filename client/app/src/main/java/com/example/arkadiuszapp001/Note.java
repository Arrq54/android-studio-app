package com.example.arkadiuszapp001;

import android.content.res.ColorStateList;

public class Note {
    private String color, title, noteContent, photopath;
    private int id;

    public Note(int id, String color, String title, String noteContent, String photopath) {
        this.id = id;
        this.color = color;
        this.title = title;
        this.noteContent = noteContent;
        this.photopath = photopath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }
}
