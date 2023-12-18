package com.wego.framework.util;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static int BUFFER_SIZE = 1024;
    /**
     * 解压zip文件
     * @param zipFilePath
     * @param targetPath
     * @throws ZipException
     */
    public static void unzip(String zipFilePath,String targetPath) throws Exception{
        ZipFile zipFile = new ZipFile(zipFilePath);
        zipFile.extractAll(targetPath);
    }

    /**
     * 解压zip文件（带密码）
     * @param zipFilePath
     * @param targetPath
     * @param password
     * @throws ZipException
     */
    public static void unzip(String zipFilePath,String password,String targetPath) throws Exception{
        ZipFile zipFile = new ZipFile(zipFilePath);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractAll(targetPath);
    }
    public static void toZip(List<File> files, OutputStream outputStream) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zipOutputStream = null;

        try {
            zipOutputStream = new ZipOutputStream(outputStream);
            Iterator var5 = files.iterator();

            while(var5.hasNext()) {
                File file = (File)var5.next();
                byte[] buf = new byte[BUFFER_SIZE];
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream in = new FileInputStream(file);

                int len;
                while((len = in.read(buf)) != -1) {
                    zipOutputStream.write(buf, 0, len);
                }

                zipOutputStream.closeEntry();
                in.close();
            }

            long end = System.currentTimeMillis();
        } catch (Exception var17) {
            throw new RuntimeException("zip error from ZipUtils", var17);
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }

        }
    }
    public static void main(String[] args) throws Exception {
        ZipUtils.unzip("F:\\develop\\upload\\upload.zip","F:\\develop\\upload\\zip\\");
    }
}
