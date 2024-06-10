package com.example.demoproject;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.view.Surface;
import android.widget.ImageView;

public class NanoDetNcnn {

    public native boolean loadModel(AssetManager mgr, int cpugpu);
    public native String predict(ImageView imageView, Bitmap bitmap);

    static {
        System.loadLibrary("nanodetncnn");
    }
}
