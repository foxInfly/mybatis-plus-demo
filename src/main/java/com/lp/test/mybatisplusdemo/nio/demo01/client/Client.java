package com.lp.test.mybatisplusdemo.nio.demo01.client;

import com.lp.test.mybatisplusdemo.nio.demo01.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

@Slf4j
public class Client {

    public static void main(String[] args) {
        String name = "";
        ClientDeamon deamon = new ClientDeamon("127.0.0.1", 9999);
        new Thread(deamon).start();

        // 在主线程中 从键盘读取数据输入到服务器端

//        Scanner scan = new Scanner(System.in);
//        while (scan.hasNextLine()) {
//            String line = scan.nextLine();
//            if ("".equals(line)) continue; // 不允许发空消息
//            if ("".equals(name)) {
//                name = line;
//                line = name + Util.USER_CONTENT_SPILIT;
//            } else {
//                line = name + Util.USER_CONTENT_SPILIT + line;
//            }
//            deamon.chancelToWrite(Util.charset.encode(line));// sc既能写也能读，这边是写
//        }
    }



    public static void read(SocketChannel socketChannel) throws IOException {

        // 初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024);
        //读到的字节数
        int num = 0;
        StringBuilder content = new StringBuilder();
        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            content.append(Util.charset.decode(buffer));
        }
        //若系统发送通知名字已经存在，则需要换个昵称
        if(Util.USER_EXIST.equals(content.toString())) log.info("name has exists.");

        System.out.print(content.toString());
    }

    /**
     * 向SocketChannel中写入数据，同事切换成读状态
     *
     * @param socketChannel socketChannel
     */
    public static void write(SocketChannel socketChannel) {

        // 从消息队列中获取要发送的消息
        String msg = "i am client";
//        if (msg == null) {
//            //如果消息队列中没有要发送的消息，则返回。
//            return;
//        }
        //初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024);

        // 把消息放到缓冲区中
        buffer.put(msg.getBytes());

        //重置缓冲区指针
        buffer.flip();
        try {
            // 把缓冲区中的数据写到SocketChannel里
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}