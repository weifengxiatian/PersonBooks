package com.cc.personbooks.utils;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2015-8-24.
 */
public class DownUtils {

    public static File downloadUrl(String str, String path) throws MalformedURLException, FileNotFoundException {

        long beginTimeMillis = System.currentTimeMillis();
        Log.e("downloadUrl", "begin:" + beginTimeMillis);
        URL url = new URL(str);

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        InputStream is = null;
        try {
            URLConnection openConnection = url.openConnection();
            openConnection.getInputStream();
            openConnection.connect();
            is = openConnection.getInputStream();
            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = is.read(buf)) != -1) {
                fileOutputStream.write(buf);
            }
            // fileOutputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        long endTimeMillis = System.currentTimeMillis();
        Log.e("downloadUrl", "during:" + (endTimeMillis - beginTimeMillis));
        return file;
    }

}
