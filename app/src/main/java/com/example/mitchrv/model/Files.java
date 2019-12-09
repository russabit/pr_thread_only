package com.example.mitchrv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Files {

    @SerializedName("path")
    @Expose
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Files{" +
                "path='" + path + '\'' +
                '}';
    }
}
