package com.lp.test.mybatisplusdemo.nio;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lp
 * @since 2020-12-22 10:33:14
 */
public class SelectorDemo_01 {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();

        RandomAccessFile aFile = new RandomAccessFile("src/main/resources/nio-data.txt", "rw");
        FileChannel iChannel = aFile.getChannel();
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        //与Selector一起使用时，Channel必须处于非阻塞模式下,这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。
        channel.configureBlocking(false);
        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

//        channel.register(selector, SelectionKey.OP_READ);
        channel.register(selector, SelectionKey.OP_WRITE);
        while(true){
            int readyChannels = selector.select();//会阻塞，返回你所感兴趣的事件的已就绪的事件通道数量。阻塞到至少有一个通道在你注册的事件上就绪了。
            System.out.println(" selector.select():"+readyChannels);
//            if (readyChannels == 0) continue;

            Set selectedKeys = selector.selectedKeys();//访问“已选择键集（selected key set）”中的就绪通道
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey key1 = (SelectionKey) keyIterator.next();
                if (key1.isAcceptable()){
                    System.out.println("accept");
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key1.isConnectable()){
                    System.out.println("connect");
                    // a connection was established with a remote server.
                } else if (key1.isReadable()){
                    System.out.println("read");
                    // a channel is ready for reading
                } else if (key1.isWritable()){
                    System.out.println("write");
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }

    }
}
