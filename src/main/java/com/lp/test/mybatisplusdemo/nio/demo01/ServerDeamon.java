package com.lp.test.mybatisplusdemo.nio.demo01;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//守护线程
@Slf4j
public class ServerDeamon implements Runnable {

    private boolean flag = true;
    
    //listen the connection from socket channel
    private ServerSocketChannel serverSocketChannel = null;
    private Selector selector = null;
    /**
     * 记录进来的所有的客户端连接(socket channel)
     * */
    private List<SocketChannel> socketChannels = null;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    //初始化.serverSocketChannel,selector,socketChannels
    public ServerDeamon(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));//一旦有连接，成功连接上这个服务后，serverSocketChannel进入Accept标志位（事件状态）
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);//非阻塞模式
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            this.socketChannels = new ArrayList<>();
            log.info("00.init nio server(serverSocketChannel,selector,socketChannels) success! listen port:{}",port);
        } catch (IOException e) {
            log.error("nit nio server fail!");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        log.info("01.begin start serverSocketChannel server.");
        while (this.flag) {
            int num = 0;
            try {
                num = selector.select();//此处select()阻塞了线程,搜索准备好了的通道的个数，
                log.info("02.get the number:{} of channel who is ready.",num);
            } catch (IOException e) {
                log.info("02.get the number:error of channel who is ready to accept.\n{}",e);
            }
            if (num > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();//获取SelectionKey
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    it.remove();
                    if (selectionKey.isAcceptable()) { // 监听到有新的连接socketChannel，则把它注册到selector，事件为读操作
                        log.info("03_1(isAcceptable).get the channel who is ready to accept");
                        this.socketChannels.add(ServerDealer.accept(selector, serverSocketChannel));
                        log.info("05_1.add new socketChannel(clinet connection) to list success!\n new List:{},{}",socketChannels.size(),socketChannels);
                    } else if (selectionKey.isReadable()) {// 监听到读操作
                        log.info("03_2(isReadtable).get the channel who is ready to read");
                        try {
                            ServerDealer.read(selector, selectionKey, socketChannels);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }
        System.out.println("server to close..");
        if (this.serverSocketChannel != null && this.serverSocketChannel.isOpen()) {
            try {
                this.serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.selector != null && this.selector.isOpen()) {
            try {
                this.selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}