package com.arch.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wurongqiu on 2018/4/18.
 */

public class HostUtils {

    public static File copy(String asset, Context context) throws IOException {

        InputStream source = context.getAssets().open(new File(asset).getPath());
        File destinationFile = new File(Environment.getExternalStorageDirectory() + "/0", asset);
        destinationFile.getParentFile().mkdirs();
        OutputStream destination = new FileOutputStream(destinationFile);
        byte[] buffer = new byte[1024];
        int nread;

        while ((nread = source.read(buffer)) != -1) {
            if (nread == 0) {
                nread = source.read();
                if (nread < 0)
                    break;
                destination.write(nread);
                continue;
            }
            destination.write(buffer, 0, nread);
        }
        destination.close();

        return destinationFile;
    }
}
