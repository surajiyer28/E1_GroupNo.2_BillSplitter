package com.firstapp.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DataConverter {

  public static byte [] convertBitmap2ByteArray(Bitmap bitmap){
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
      return stream.toByteArray() ;
  }

  public static Bitmap convertByteArray2Image(byte [] array){
      return BitmapFactory.decodeByteArray(array, 0, array.length);

  }

}

