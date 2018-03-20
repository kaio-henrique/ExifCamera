package br.com.test.kaioh.exifcamera;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

/**
 * Created by
 *  @autor Kaio Henrique on 16/03/2018.
 */

public class LoadPhoto extends AppCompatActivity {

    public static Bitmap loadImage(Context ctx, Uri imageUri, String imagePath) throws IOException {
        Bitmap bitmap = null;
        ExifInterface exif = new ExifInterface(imagePath);
        int orientation;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            orientation = getOrientation(ctx, imageUri);
        } else {
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        }

        if (orientation == ExifInterface.ORIENTATION_NORMAL){
            bitmap = openPhotoRotate(imagePath, 0);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90){
            bitmap = openPhotoRotate(imagePath, 90);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180){
            bitmap = openPhotoRotate(imagePath, 180);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270){
            bitmap = openPhotoRotate(imagePath, 270);
        } else {
            bitmap = openPhotoRotate(imagePath, 0);
        }

        return bitmap;
    }

    public static Bitmap openPhotoRotate(String imagePath, int angle) {
        // Abre o bitmap a partir do caminho da foto
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        // Prepara a operação de rotação com o ângulo escolhido
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        // Cria um novo bitmap a partir do original já com a rotação aplicada
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
    }

    public static int getOrientation(Context ctx, Uri photoUri) {
        Cursor cursor = ctx.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

}
