package com.example.root.sober.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.root.sober.R;
import com.example.root.sober.tools.ImageTools;

public class MainActivity extends AppCompatActivity {

    private ImageView exampleView;
    private ImageView displayView1;
    private ImageView displayView2;
    private ImageView displayView3;
    private ImageView displayView4;
    //图像的灰度值
    private double[][] grayValue;
    private Bitmap result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayView1 = (ImageView)findViewById(R.id.image_view1);
        displayView2 = (ImageView)findViewById(R.id.image_view2);
        displayView3 = (ImageView)findViewById(R.id.image_view3);
        displayView4 = (ImageView)findViewById(R.id.image_view4);

        float rate = 80.0f;

        Bitmap examBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.example);
        Bitmap reduce_bitmap1 = creatScaleBitmap(examBitmap1,rate);
        displayView1.setImageBitmap(reduce_bitmap1);

        Bitmap result1 = ImageTools.convertGreyImg(reduce_bitmap1);
        displayView3.setImageBitmap(result1);


        Bitmap examBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.hello);
        Bitmap reduce_bitmap2 = creatScaleBitmap(examBitmap2,rate);
        displayView2.setImageBitmap(reduce_bitmap2);
        Bitmap result2 = ImageTools.convertGreyImg(reduce_bitmap2);
        displayView4.setImageBitmap(result2);

    }

    private Bitmap creatScaleBitmap(Bitmap bitmap, float rate){

        float scale_width = rate / bitmap.getWidth();
        float scale_height = rate / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scale_width, scale_height);
        Bitmap reduce_bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return reduce_bitmap1;
    }


}
