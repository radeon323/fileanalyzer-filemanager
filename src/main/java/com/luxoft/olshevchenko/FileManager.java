package com.luxoft.olshevchenko;

import java.io.*;
import java.util.Locale;

/**
 * @author Oleksandr Shevchenko
 */
public class FileManager {

    public static int countFiles(String path) {
        int count = 0;
        File[] files = getFiles(path);
        for (File file : files) {
            if (file.isFile()) {
                count++;
            } else {
                count += countFiles(file.getPath());
            }
        }
        return count;
    }

    public static int countDirs(String path) {
        int count = 0;
        for (File file : getFiles(path)) {
            if (file.isDirectory()) {
                count++;
                count += countDirs(file.getPath());
            }
        }
        return count;
    }

    public static void copy(String from, String to) {
        File pathFrom = new File(from);
        File pathTo = new File(to);
        pathValidation(pathFrom);
        if (pathFrom.isFile()) {
            fileCopy(pathFrom, pathTo);
        } else {
            folderCopy(pathFrom, pathTo);
        }
    }

    public static void move(String from, String to) {
        copy(from, to);
        deleteAll(from);
    }


    private static File[] getFiles(String path) {
        File file = new File(path);
        pathValidation(file);
        return file.listFiles();
    }

    protected static void pathValidation(File path) {
        if (!path.exists()) {
            throw new RuntimeException("Path '" + path + "' is invalid.");
        }
    }

    private static void fileCopy(File from, File to) {
        try (FileInputStream fileInputStream = new FileInputStream(from);
             FileOutputStream fileOutputStream = new FileOutputStream(to)) {
            int bufSize;
            byte[] buf = new byte[1024];
            while ((bufSize = fileInputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, bufSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void folderCopy(File from, File to) {
        if (!to.exists()) {
            to.mkdir();
        }
        File[] files = from.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                File fileFrom = new File(from, file.getName());
                File fileTo = new File(to, file.getName());
                copy(String.valueOf(fileFrom), String.valueOf(fileTo));
            }
        }
    }

    public static void deleteAll(String path) {
        for (File file : getFiles(path)) {
            if (file.isDirectory()) {
                deleteAll(file.getPath());
                deleteFileAndMessage(file);
            }
            deleteFileAndMessage(file);
        }
    }

    private static void deleteFileAndMessage(File file) {
        String name = file.getName();
        if (file.isFile() && !file.delete()) {
            System.out.println("Failed to remove file " + name + " .");
        }
        if (file.isDirectory() && !file.delete()) {
            System.out.println("Failed to remove directory [" + name.toUpperCase(Locale.ROOT) + "] .");
        }
    }


}
