package com.lp.test.mybatisplusdemo.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lp
 * @since 2020-12-21 21:23:58
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
//        RandomAccessFile aFile = new RandomAccessFile("G:\\project\\Idea\\demo\\mybatis-plus-demo\\src\\main\\java\\com\\lp\\test\\mybatisplusdemo\\nio\\data\\nio-data.txt", "rw");
        RandomAccessFile aFile = new RandomAccessFile("src/main/resources/nio-data.txt", "rw");

        /*File file = new File("/");
        System.out.println("/ 代表的绝对路径为：" + file.getAbsolutePath());

        File file1 = new File(".");
        System.out.println(". 代表的绝对路径为" + file1.getAbsolutePath());
*/


        FileChannel inChannel = aFile.getChannel();
        int count =0;
        //define the capacity of buffer
        ByteBuffer buf = ByteBuffer.allocate(25);
        int bytesRead = inChannel.read(buf);//read date from channel to buffer
        while (bytesRead != -1) {
            count++;
            System.out.println("第"+count+"次Read: " + bytesRead);
            buf.flip(); //reset position to 0,limit=position;So from write to read
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());// read 1 byte at a time
            }
            System.out.println("\n=============================");
            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        //test write
        buf.put("\nabc".getBytes());
        buf.flip();
        inChannel.write(buf);
        aFile.close();
    }
}
