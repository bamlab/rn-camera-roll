package fr.bamlab.rncameraroll;

import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by florian on 02/12/15.
 */
class CameraImage {
    private String localPath;
    private int width;
    private int height;
    private int orientation;

    CameraImage(String localPath) {
        this.localPath = localPath;

        computeDimensions();
        computeOrientation();
    };

    private void computeDimensions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Avoid decoding the entire file

        BitmapFactory.decodeFile(this.localPath, options);
        width = options.outWidth;
        height = options.outHeight;
    }

    private void computeOrientation() {
        ExifInterface exif;
        orientation = 0;
        try {
            exif = new ExifInterface(localPath);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    orientation = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    orientation = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    orientation = 270;
                    break;
            }
        } catch (IOException e) {
            // Can't retrieve the orientation, let it to 0.
        }
    }

    public String getLocalPath() {
        return localPath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getOrientation() {
        return orientation;
    }
}
