package br.com.test.kaioh.exifcamera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by
 *  @autor Kaio Henrique on 16/03/2018.
 */

public class CarregaFoto {

    public static Bitmap loadImage(String imagePath) throws IOException {
        Bitmap bitmap = null;
        ExifInterface exif = new ExifInterface(imagePath);
        String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int codOrientation = Integer.parseInt(orientation);

        switch (codOrientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                bitmap = openPhotoRotate(imagePath, 0);
            case ExifInterface.ORIENTATION_ROTATE_90:
                bitmap = openPhotoRotate(imagePath, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmap = openPhotoRotate(imagePath, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmap = openPhotoRotate(imagePath, 270);
        }/*

        if (codOrientation == ExifInterface.ORIENTATION_NORMAL){
            bitmap = openPhotoRotate(imagePath, 0);
        } else if (codOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            bitmap = openPhotoRotate(imagePath, 90);
        } else if (codOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            bitmap = openPhotoRotate(imagePath, 180);
        } else if (codOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            bitmap = openPhotoRotate(imagePath, 270);
        }*/

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
}
