package com.example.demoproject_master;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class Ncnn {
    public native boolean loadModel(AssetManager mgr,
                                    int modelid,
                                    int cpugpu);

    // det + seg from blackbox
    public native boolean homogeneousComputing(ImageView imageView,
                                  Bitmap bitmap,
                                  boolean[] opt);

    // det + seg from phone
    public native boolean heterogeneousComputing(ImageView imageView,
                                    Bitmap bitmap,
                                    String dataDet,
                                    Bitmap dataSeg);

    static {
        System.loadLibrary("ncnntotal");
    }
}
