package com.lp.test.mybatisplusdemo.nio.demo2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class chatClient {
    private static final String host = "127.0.0.1";
    private static final int port = 8081;
    private Selector selector;
    private String userName;
    private SocketChannel socketChannel;
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private static final String USER_NAME_TAG = "$%%^&*()!@#$^%#@*()*";

    volatile boolean running = true;

    private static final Logger LOG = LoggerFactory.getLogger(chatClient.class);

    public chatClient() throws IOException {
        connect(host, port);
//         // 读写分离

        Reader reader = new Reader();
        reader.start();

        listen();
    }

    public static void main(String[] args) throws IOException {

        new chatClient();
    }

    public void connect(String host, int port) {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
            this.selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);   //将channel注册到selector中
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int events = selector.select();
                if (events > 0) {
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                    while (selectionKeys.hasNext()) {
                        SelectionKey selectionKey = selectionKeys.next();
                        selectionKeys.remove();

                        //连接事件
                        if (selectionKey.isConnectable()) {
                            socketChannel.finishConnect();
                            // 人员登录
                            login(scanner);
                            //注册写操作
                            socketChannel.register(selector, SelectionKey.OP_WRITE);//关心1：clientChannel的WRITE事件就绪状态
                            break;
                        } else if (selectionKey.isWritable()) {

                            String message = this.userName+"###"+scanner.nextLine();
                            writeBuffer.clear();
                            writeBuffer.put(message.getBytes());
                            //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
                            writeBuffer.flip();
                            socketChannel.write(writeBuffer);

                            socketChannel.register(selector, SelectionKey.OP_WRITE);
                        }
                    }
                }
                if (events < 0 ) return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(Scanner scanner) throws IOException {
        System.out.print("请输入登录名: ");
        this.userName = scanner.nextLine();//这里会阻塞
        writeBuffer.clear();
        writeBuffer.put((USER_NAME_TAG + userName).getBytes());
        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
        writeBuffer.flip();//position=0,limit=postion;
        socketChannel.write(writeBuffer);//将buffer中的数据写入到socketChannel,socketChanne变成read就绪状态
        System.out.println("===================================================================================");
        System.out.println("                                lp聊天室");
        System.out.println("===================================================================================");
    }

    protected class Reader extends Thread {
        private final Selector writeSelector;

        Reader() throws IOException {
            this.setName("Reader");
            this.setDaemon(true);
            writeSelector = Selector.open(); // create a selector
            socketChannel.register(writeSelector, SelectionKey.OP_READ);
        }

        @Override
        public void run() {
            try {
                doRunLoop();
            } finally {
                LOG.info(getName() + ": stopping");
                try {
                    writeSelector.close();
                } catch (IOException ioe) {
                    LOG.error(getName() + ": couldn't close write selector", ioe);
                }
            }
        }

        private void doRunLoop() {
            while (running) {
                try {
                    int keyCt = writeSelector.select();
                    if (keyCt == 0) {
                        continue;
                    }
                    Set<SelectionKey> keys = writeSelector.selectedKeys();
                    Iterator<SelectionKey> iter = keys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        try {
                            if (key.isValid() && key.isReadable()) {
                                Thread.sleep(1);
                                SocketChannel client = (SocketChannel) key.channel();
                                //将缓冲区清空以备下次读取
                                readBuffer.clear();
                                int num = client.read(readBuffer);
                                System.out.println(new String(readBuffer.array(), 0, num));
                                //注册读操作，下一次读
                                socketChannel.register(selector, SelectionKey.OP_READ);
                            }
                        } catch (IOException e) {
                            LOG.debug(getName() + ": Reader", e);
                        }
                    }
                } catch (Exception e) {
                    LOG.warn(getName() + ": exception in Reader " + e);
                }
            }
        }
    }
}
