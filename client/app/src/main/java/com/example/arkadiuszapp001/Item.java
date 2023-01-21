package com.example.arkadiuszapp001;

public class Item {

        private String name, url, creationTime;
        private String size;


    public Item(String name, String url, String creationTime, String size) {
        this.name = name;
        this.url = url;
        this.creationTime = creationTime;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public void setSize(String size) {
        this.size = size;
    }
}

