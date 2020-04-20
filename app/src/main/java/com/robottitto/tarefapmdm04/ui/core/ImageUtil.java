package com.robottitto.tarefapmdm04.ui.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    public static byte[] imageViewToArray(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        return imageInByte;
    }

    public static Bitmap arrayToImageView(byte[] imageInByte) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        return bitmap;
    }

}
