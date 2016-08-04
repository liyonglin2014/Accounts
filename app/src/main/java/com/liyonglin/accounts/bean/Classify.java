package com.liyonglin.accounts.bean;

import java.io.Serializable;

/**
 * Created by 永霖 on 2016/7/28.
 */
public class Classify implements Serializable{
    private int id;
    private String name;
    private int mode;
    private int imgId;

    public Classify(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public Classify(int id, String name, int mode, int imgId) {
        this.id = id;
        this.name = name;
        this.mode = mode;
        this.imgId = imgId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classify(String name, int mode, int imgId) {
        this.name = name;
        this.mode = mode;
        this.imgId = imgId;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
