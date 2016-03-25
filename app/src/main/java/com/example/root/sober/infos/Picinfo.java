package com.example.root.sober.infos;

import android.graphics.Bitmap;

/**
 * Created by root on 16-3-17.
 */
public class Picinfo {
    Bitmap mBitmap;
    int mCount;

    public Picinfo(Bitmap bitmap, int count) {
        this.mBitmap = bitmap;
        this.mCount = count;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public int getmCount() {
        return mCount;
    }
}
