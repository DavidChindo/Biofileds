package com.hics.biofields.Library;

import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by david.barrera on 9/14/17.
 */

public class Compress {

    private static final String TAG = Compress.class.getSimpleName();
    private static final int BUFFER = 2048;

    private Context activity;
    private ArrayList<String> files;
    private String zipFile;

    public Compress(Context activity, ArrayList<String> files, String zipFile) {
        this.activity = activity;
        this.files = files;
        this.zipFile = zipFile;
    }

    public void zip() {
        try  {
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[BUFFER];
            for(String file : files){
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            }
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Por el momento no es posible procesar el archivo", Toast.LENGTH_SHORT).show();
        }
    }


    /*
 *
 * Zips a file at a location and places the resulting zip file at the toLocation
 * Example: zipFileAtPath("downloads/myfolder", "downloads/myFolder.zip");
 */

    public boolean zipFileAtPath() {
        final int BUFFER2 = 2048;

        FileOutputStream dest = null;
        try {
            dest = new FileOutputStream(zipFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                dest));

        for (String sourcePath : files) {

            File sourceFile = new File(sourcePath);

            try {
                BufferedInputStream origin = null;

                if (sourceFile.isDirectory()) {
                    zipSubFolder(out, sourceFile, sourceFile.getParent().length());
                } else {
                    byte data[] = new byte[BUFFER2];
                    FileInputStream fi = new FileInputStream(sourcePath);
                    origin = new BufferedInputStream(fi, BUFFER2);
                    ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER2)) != -1) {
                        out.write(data, 0, count);
                    }
                }

            } catch (Exception e) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
                return false;
            }

        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

/*
 *
 * Zips a subfolder
 *
 */
    private void zipSubFolder(ZipOutputStream out, File folder,
                              int basePathLength) throws IOException {

        final int BUFFER2 = 2048;

        File[] fileList = folder.listFiles();
        BufferedInputStream origin = null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                zipSubFolder(out, file, basePathLength);
            } else {
                byte data[] = new byte[BUFFER2];
                String unmodifiedFilePath = file.getPath();
                String relativePath = unmodifiedFilePath
                        .substring(basePathLength);
                FileInputStream fi = new FileInputStream(unmodifiedFilePath);
                origin = new BufferedInputStream(fi, BUFFER2);
                ZipEntry entry = new ZipEntry(relativePath);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER2)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
                Log.d(TAG,"SIZE "+entry.getCompressedSize());
            }
        }
    }

    /*
     * gets the last path component
     * Example: getLastPathComponent("downloads/example/fileToZip");
     * Result: "fileToZip"
     */
    public String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
    }
}


