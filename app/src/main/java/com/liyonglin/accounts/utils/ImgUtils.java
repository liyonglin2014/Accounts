package com.liyonglin.accounts.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.liyonglin.accounts.activity.AccountAddActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by 永霖 on 2016/8/7.
 */
public class ImgUtils {

    public static Uri saveBitmap(Bitmap bm) {
        File img = getFile(bm);
        return Uri.fromFile(img);
    }

    public static File getFile(Bitmap bm) {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/com.liyonglin.accounts/img/");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        String random = new Random().nextInt(10000) + "";
        File img = new File(tmpDir.getAbsolutePath(), System.currentTimeMillis() + random + ".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File startImageZoom(Uri uri, Activity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 4);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, AccountAddActivity.CROP_REQUEST_CODE);
        return new File(uri.getPath());
    }

    public static Uri convertUri(Uri uri, Context context) {
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private static File getFileFromUri(Uri uri, Activity activity){
//
//        System.out.println(uri.getPath());
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor actualimagecursor = activity.getContentResolver().query(uri, proj, null, null, null);
//        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        actualimagecursor.moveToFirst();
//        String img_path = actualimagecursor.getString(actual_image_column_index);
//        File file = new File(img_path);
//        return file;
//    }

}
