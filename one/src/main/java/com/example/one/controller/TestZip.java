package com.example.one.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.example.one.po.ZipFileResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/zip")
public class TestZip {

    // 对象转为json字符串
    @RequestMapping("/get")
    public String get(){
        return "hello,zip";
    }

    // 对象转为json字符串
    @RequestMapping("/getFileType")
    public String getFileType(){
        try{
            ZipFileResult z = getFileByteContent("/Users/xuweiqiang/Documents/demo/a.zip");
            return z.getFileType();
        }catch (Exception e){
            return "catch zip exception " + e.getMessage();
        }
    }

    public ZipFileResult getFileByteContent(String fileName) throws Exception {
        ZipFileResult zipFileResult;
        File f = new File(fileName);
        String tmpZip = "/Users/xuweiqiang/Documents/demo/tmp.zip";
        try {
            InputStream inputStream = new FileInputStream(f);
            File file = convertToFile(inputStream, tmpZip);
            // 获取压缩文件的文件类型和解压后的字节流
            zipFileResult = readZipFile(file);
            // 删除临时文件
            FileUtil.del(tmpZip);
        } catch (Exception ipu) {
            throw new Exception("get script bytes error " + ipu.getMessage());
        }
        if (zipFileResult == null) {
            throw new Exception("zip invalid");
        }
        return zipFileResult;
    }

    // inputStream转File对象
    private File convertToFile(InputStream inputStream, String fileName) throws IOException {
        // 创建file对象
        File file = new File(fileName);
        // 创建写入资源handler
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        // 将inputStream写入文件之中
        IoUtil.copy(inputStream, fileOutputStream);
        // 返回file对象
        return file;
    }

    // File对象获取压缩文件
    public ZipFileResult readZipFile(File file) throws IOException {
        boolean validFile = checkZip(file);
        if (!validFile) {
            return null;
        }
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        String fileType = null;
        byte[] contentBytes = new byte[0];
        while ((ze = zin.getNextEntry()) != null) {
            if (!ze.isDirectory()) {
                String fileName = ze.getName();
                String[] fileNameArr = fileName.split("\\.");
                if (fileNameArr.length == 2) {
                    fileType = fileNameArr[1];
                }
                InputStream inputStream = zf.getInputStream(ze);
                contentBytes = inputStream.readAllBytes();
                break;
            }
        }
        in.close();
        zin.close();
        zf.close();
        // lombok的builder创建
        return ZipFileResult.builder().fileType(fileType).contentByte(contentBytes).build();
    }
    private final long FILE_SIZE_LIMIT = 1024*1024*10;

    // checkZip 压缩文件检查压缩文件数量和文件大小
    private boolean checkZip(File file) throws IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
        ZipEntry ze;
        long totalSize = 0;
        long fileCount = 0;
        while ((ze = zin.getNextEntry()) != null) {
            if (!ze.isDirectory()) {
                fileCount++;
                long size = ze.getSize();
                if (size > 0) {
                    totalSize += size;
                }
            }
        }
        zin.close();
        return fileCount == 1 && totalSize < FILE_SIZE_LIMIT;
    }
}
