package com.example.demoproject_master;

import android.graphics.Bitmap;

public class SegDataHolder {
    private static final SegDataHolder instance = new SegDataHolder();
    private Bitmap segData;

    private SegDataHolder() {
        // private 생성자로 싱글톤 패턴 유지
    }

    public static SegDataHolder getInstance() {
        return instance;
    }

    public void setSegdata(Bitmap segData) {
        this.segData = segData;
    }

    public Bitmap getSegdata() {
        return segData;
    }
}

