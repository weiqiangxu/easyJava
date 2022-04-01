package com.example.one.controller;

import com.alibaba.fastjson.JSON;
import com.example.one.po.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// alibaba fast json学习
@RestController
@RequestMapping("/io")
public class TestIO {

    // 对象转为json字符串
    @RequestMapping("/get")
    public String get(){
        return "hello,stream";
    }

    // 很好理解，当我作为java程序，写入的文件对我而言就是输出，将java内存之中的东西输出，所以就是outputStream
    // 对于我要读取的文件，对于java而言就是输入流所以是inputStream
    // 写文件就是 输入流.Read 往 输出流.write

    // FileInputStream(File)
    // 传入文件对象，获取输入流

    // 使用FileStreams复制
    private static void copyFileUsingFileStreams(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            // 定义一个1024字节长度的数组
            // (一个字节8bit最大255数字)
            byte[] buf = new byte[1024];
            int bytesRead;

            // 从输入流中读取一些字节数，并将其存储到缓冲区数组b中
            //读取的第一个字节存储到元素 b[0]中，下一个字节存储到 b[1] 中，
            // 依此类推。读取的字节数最多等于 b 的长度。设 k 为实际读取的字节数;
            // 这些字节将存储在元素 b[0] 到 b[k-1] 中，使元素 b[k] 到 b[b.length-1] 不受影响。
            // -- 实际读取的字节数以整数形式返回, 这句话很重要，也就是说，没有读到的时候就返回0了
            // 这里是1024字节，每次最多读1024字节内容，直到读不到字节了
            while ((bytesRead = input.read(buf)) > 0) {
                // 将 len 字节从指定字节数组开始从偏移量开始写入此输出流。
                // write（b， off， len） 的总约定是数组 b 中的一些字节按顺序写入输出流;
                // 元素 b[off] 是写入的第一个字节，b[off+len-1] 是此操作写入的最后一个字节
                // OutputStream 的 write 方法在要写出的每个字节上调用一个参数的写入方法。
                // 鼓励子类重写此方法并提供更有效的实现。
                output.write(buf, 0, bytesRead);
                // --- 这个是每次从0到读到的最大数量比如读到9个，那么就是0-9字节全部获取
                // 全部写入输出流
            }
        } finally {
            input.close();
            output.close();
        }

    }

    // FileChannel 实现文件拷贝
    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }


    // Apache Commons IO
    private static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
        // FileUtils.copyFile(source, dest);
    }

    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        // Files.copy(source.toPath(), dest.toPath());
    }




}
