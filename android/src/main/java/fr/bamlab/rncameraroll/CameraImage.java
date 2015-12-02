package fr.bamlab.rncameraroll;

import android.graphics.BitmapFactory;

/**
 * Created by florian on 02/12/15.
 */
class CameraImage {
    private String localPath;
    private int width;
    private int height;

    CameraImage(String localPath) {
        this.localPath = localPath;

        computeDimensions();
    };

    private void computeDimensions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Avoid decoding the entire file

        BitmapFactory.decodeFile(this.localPath, options);
        width = options.outWidth;
        height = options.outHeight;
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
}
