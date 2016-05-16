package fr.bamlab.rncameraroll;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almouro on 11/12/15.
 */
class CameraImagesManager {
    public static List<CameraImage> getCameraImages(Context context, int count, String afterCursor) {
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );

        ArrayList<CameraImage> cameraImagePaths = new ArrayList<>();

        if (cursor == null) {
            return cameraImagePaths;
        }

        boolean foundAfter = false;
        if (cursor.moveToLast()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                String localPath = cursor.getString(dataColumn);

                if (!afterCursor.isEmpty() && !foundAfter) {
                    if (afterCursor.equals(localPath)) {
                        foundAfter = true;
                    }
                    continue; // Skip until we get to the first one
                }

                cameraImagePaths.add(new CameraImage(localPath));
            } while (cursor.moveToPrevious() && cameraImagePaths.size() < count);
        }
        cursor.close();

        return cameraImagePaths;
    }
}
