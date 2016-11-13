package com.fisherevans.twc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaUtil {
    public static void addClassLoaderUserPath(String folderPath) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[])field.get(null);
            for (int i = 0; i < paths.length; i++) {
                if (folderPath.equals(paths[i])) {
                    return;
                }
            }
            String[] tmp = new String[paths.length+1];
            System.arraycopy(paths,0,tmp,0,paths.length);
            tmp[paths.length] = folderPath;
            field.set(null,tmp);
            System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + folderPath);
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path");
        }
    }

    public static Path createTempFolderWithJarFiles(String[] jarFiles) {
        try {
            Path tempDir = Files.createTempDirectory("twc_temp");
            tempDir.toFile().deleteOnExit();
            for(String jarFile:jarFiles) {
                String fileName = jarFile.replaceAll(".*/", "");
                Path tempFile = tempDir.resolve(fileName);
                InputStream is = JavaUtil.class.getResourceAsStream(jarFile);
                if(is == null) {
                    throw new RuntimeException("File not found: " + jarFile);
                }
                OutputStream os = new FileOutputStream(tempFile.toFile());
                byte[] buffer = new byte[1024];
                int readBytes;
                try {
                    while ((readBytes = is.read(buffer)) != -1) {
                        os.write(buffer, 0, readBytes);
                    }
                } finally {
                    os.close();
                    is.close();
                }
            }
            return tempDir;
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy files to a temporary folder", e);
        }
    }
}
