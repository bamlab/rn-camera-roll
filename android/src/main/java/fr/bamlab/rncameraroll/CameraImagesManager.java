package fr.bamlab.rncameraroll;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almouro on 11/12/15.
 */
class CameraImagesManager {
    private static final String CAMERA_IMAGE_BUCKET_NAME =
            Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
    private static final String CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    private static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    public static List<String> getCameraImages(Context context, int count, String afterCursor) {
        final String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );

        ArrayList<String> cameraImagePaths = new ArrayList<>();

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

                cameraImagePaths.add(localPath);
            } while (cursor.moveToPrevious() && cameraImagePaths.size() < count);
        }
        cursor.close();

        return cameraImagePaths;
    }
}
