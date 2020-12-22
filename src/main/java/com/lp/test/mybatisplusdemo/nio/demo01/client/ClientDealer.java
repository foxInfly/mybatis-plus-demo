package com.lp.test.mybatisplusdemo.nio.demo01.client;

import com.lp.test.mybatisplusdemo.nio.demo01.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class ClientDealer {
    /**
     * 从SocketChannel中读取信息
     * 
     * @param socketChannel socketChannel
     */
    public static void read(SocketChannel socketChannel) throws IOException {

        // 初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024);
        //读到的字节数
        int num = 0;
        StringBuilder content = new StringBuilder();
        //当读完，进入写就绪状态
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
    public static void write(SocketChannel socketChannel,String msg) {

        //初始化缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024);

        // 把消息放到缓冲区中
        buffer.put(msg.getBytes());

        //重置缓冲区指针
        buffer.flip();
        try {
            // 把缓冲区中的数据写到SocketChannel里
            socketChannel.write(buffer);//这里socketChannel进入read就绪事件状态
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}