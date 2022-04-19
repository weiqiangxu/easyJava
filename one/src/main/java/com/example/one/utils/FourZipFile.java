package com.example.one.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FourZipFile {
    /**
     * 压缩单个文件并加密
     */
    public static String zipFile(String file, String fileOutPath, String passWord, String fileName)
            throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        // 开启加密
        parameters.setSourceExternalStream(true);
        // 文件名称
        parameters.setFileNameInZip(fileName);
        if (!"".equals(passWord)) {
            parameters.setEncryptFiles(true);
            // 加密方式
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            // 设置加密密码
            parameters.setPassword(passWord.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile(fileOutPath + fileName + ".zip");
            zipFile.addStream(fileInputStream, parameters);
            // 加密解压后删除源文件
            deleteFile(file);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return fileOutPath + fileName + ".zip";
    }

    /**
     * 删除文件
     */
    private static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    // 加密文件并压缩
    public static void main(String[] args) throws FileNotFoundException {
        String filePath =  "/Users/xuweiqiang/Desktop/test.txt";
        String fileOutPath = "/Users/xuweiqiang/Desktop/";
        String passWord = "123";
        String fileName = "hello.txt";
        String name = zipFile(filePath,fileOutPath,passWord,fileName);
        System.out.println(name);
    }

}

