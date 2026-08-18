package com.example.onlinecoachingapp.utils;

import android.content.Context;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.database.Cursor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static File getFile(Context context, Uri uri) {

        try {

            InputStream inputStream =
                    context.getContentResolver().openInputStream(uri);

            String fileName = getFileName(context, uri);

            File tempFile = new File(
                    context.getCacheDir(),
                    fileName);

            FileOutputStream outputStream =
                    new FileOutputStream(tempFile);

            byte[] buffer = new byte[4096];

            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {

                outputStream.write(buffer, 0, bytesRead);

            }

            outputStream.close();
            inputStream.close();

            return tempFile;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }

    }

    private static String getFileName(
            Context context,
            Uri uri) {

        String result = null;

        Cursor cursor = context.getContentResolver()
                .query(uri,
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                int index = cursor.getColumnIndex(
                        OpenableColumns.DISPLAY_NAME);

                if (index >= 0) {

                    result = cursor.getString(index);

                }

            }

            cursor.close();

        }

        if (result == null) {

            result = "assignment.pdf";

        }

        return result;

    }

}