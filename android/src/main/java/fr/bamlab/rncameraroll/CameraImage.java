package fr.bamlab.rncameraroll;

import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by florian on 02/12/15.
 */
class CameraImage {
    private String localPath;
    private int width;
    private int height;
    private int orientation;
    private long timestamp;

    CameraImage(String localPath) {
        this.localPath = localPath;

        computeDimensions();
        computeExifProperties();
    }

    private void computeExifProperties() {
        ExifInterface exif;
        try {
            exif = new ExifInterface(localPath);
        } catch(IOException e) {
            return;
        }

        computeOrientation(exif);
        computeTimestamp(exif);
    }

    private void computeDimensions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Avoid decoding the entire file

        BitmapFactory.decodeFile(this.localPath, options);
        width = options.outWidth;
        height = options.outHeight;
    }

    private void computeOrientation(ExifInterface exif) {
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
    }

    private void computeTimestamp(ExifInterface exif) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        String dateString = exif.getAttribute(ExifInterface.TAG_DATETIME);

        try {
            timestamp = fmt.parse(dateString).getTime() / 1000;
        } catch (Exception e) {
            // Can't retrieve the timestamp, let it be 0.
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

    public long getTimestamp() {
        return timestamp;
    }
}
