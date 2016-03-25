package com.example.root.sober.tools;

import android.graphics.Bitmap;

/**
 * Created by wangjiangchuan on 16-3-17.
 */
public class ImageTools {

    private static int[] sobelX = {-1, 0, 1,
            -2, 0, 2,
            -1, 0, 1};
    private static int[] sobelY = {-1, -2, -1,
            0, 0, 0,
            1, 2, 1};

    /**
     * 得到平均值
     *
     * @param pixels 灰度值矩阵
     * @return
     */
    private static double getAverage(double[][] pixels) {
        int width = pixels[0].length;
        int height = pixels.length;
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                count += pixels[i][j];
            }
        }
        return count / (width * height);
    }

    private static double getAverage(int[] pixels) {
        long average = 0;
        for (int i = 0; i < pixels.length; i++) {
            average += pixels[i];
        }
        return average / (pixels.length);
    }

    /**
     * 计算灰度值
     *
     * @param pixel
     * @return
     */
    public static double computeGrayValue(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = (pixel) & 255;
        return 0.3 * red + 0.59 * green + 0.11 * blue;
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertGreyImg(Bitmap img) {
        float max_light = 0;
        float min_light = 0;

        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组
        //double average = getAverage(pixels);
        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //灰度图像转化的必要一个步骤
                //原理是 RGB中三个维度的值一样的时候呈现出来的就是灰度图像
                //grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;

                if(min_light == 0 && max_light == 0) {
                    min_light = grey;
                    max_light = grey;
                }

                if(min_light > grey)
                    min_light = grey;
                if(max_light < grey)
                    max_light = grey;
            }
        }
        int count = 0;
        int[] pixels2 = sobelBitmap(pixels,(int)(max_light + min_light)/2,width,height, count);

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels2, 0, width, 0, 0, width, height);
        return result;
    }



    public static int[] sobelBitmap(int[] pixels, int VALUE, int width, int height, int count) {

        count = 0;

        int[] result = new int[width * height];
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                int a1 = pixels[width * (i - 1) + j - 1];
                int a2 = pixels[width * (i - 1) + j];
                int a3 = pixels[width * (i - 1) + j + 1];

                int b1 = pixels[width * i + j - 1];
                int b2 = pixels[width * i + j];
                int b3 = pixels[width * i + j + 1];

                int c1 = pixels[width * (i + 1) + j - 1];
                int c2 = pixels[width * (i + 1) + j];
                int c3 = pixels[width * (i + 1) + j + 1];

                int sobelx = getSobelX(a1, a2, a3, b1, b2, b3, c1, c2, c3);
                int sobely = getSobelY(a1, a2, a3, b1, b2, b3, c1, c2, c3);
                int sobel = Math.abs(sobelx) + Math.abs(sobely);
                if(sobel > VALUE){
                    result[width * i + j] = -1;
                    count++;
                }else {
                    result[width * i + j] = 0;
                }
            }
        }


        return result;
    }

    public static int getSobelX(int a1, int a2, int a3, int b1, int b2, int b3, int c1, int c2, int c3){
        return  a1*sobelX[0] + a2*sobelX[1] + a3*sobelX[2] +
                b1*sobelX[3] + b2*sobelX[4] + b3*sobelX[5] +
                c1*sobelX[6] + c2*sobelX[7] + c3*sobelX[8];
    }

    public static int getSobelY(int a1, int a2, int a3, int b1, int b2, int b3, int c1, int c2, int c3){
        return  a1*sobelY[0] + a2*sobelY[1] + a3*sobelY[2] +
                b1*sobelY[3] + b2*sobelY[4] + b3*sobelY[5] +
                c1*sobelY[6] + c2*sobelY[7] + c3*sobelY[8];
    }






    //获取二值图像
    public static Bitmap convert(Bitmap bitmap) {

        float max_light = 0;
        float min_light = 0;


        int width = bitmap.getWidth();         //获取位图的宽
        int height = bitmap.getHeight();       //获取位图的高

        int []pixels = new int[width * height]; //通过位图的大小创建像素点数组
        //double average = getAverage(pixels);
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for(int i = 0; i < height; i++)  {
            for(int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey  & 0x00FF0000 ) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
                pixels[width * i + j] = grey;

                if(min_light == 0 && max_light == 0) {
                    min_light = grey;
                    max_light = grey;
                }

                if(min_light > grey)
                    min_light = grey;
                if(max_light < grey)
                    max_light = grey;
            }
        }

        double average = getAverage(pixels);

        for(int i = 0; i < pixels.length; i++)  {
            if(average < pixels[i])
                pixels[i] = -1;
            else {
                pixels[i] = 0;
            }
        }

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }



    //以下是优化算法的方法
    //原理是手动获取缩略图的

}
