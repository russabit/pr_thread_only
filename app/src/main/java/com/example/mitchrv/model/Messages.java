package com.example.mitchrv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Messages {

    @SerializedName("comment")
    @Expose
    private String comment;

/*    @SerializedName("name")
    @Expose
    private int name;*/

    @SerializedName("files")
    @Expose
    private ArrayList<Files> files;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

/*    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }*/

    public ArrayList<Files> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Files> files) {
        this.files = files;
    }

    public Messages() {
    }

    public Messages(String comment, ArrayList<Files> files) {
        this.comment = comment;
        this.files = files;
    }
}
