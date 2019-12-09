package com.example.mitchrv.model;

import com.example.mitchrv.model.Files;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Threads {

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("num")
    @Expose
    private int num;

    @SerializedName("files")
    @Expose
    private ArrayList<Files> files;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ArrayList<Files> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Files> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Threads{" +
                "subject='" + subject + '\'' +
                ", files=" + files +
                '}';
    }
}
