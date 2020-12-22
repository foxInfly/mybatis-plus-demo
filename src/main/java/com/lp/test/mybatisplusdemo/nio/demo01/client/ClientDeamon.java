package com.lp.test.mybatisplusdemo.nio.demo01.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

@Slf4j
public class ClientDeamon implements Runnable {
    /**
     * 选择器，用于监听注册在上面的SocketChannel的状态
     */
    private Selector selector = null;

    private Scanner scan = new Scanner(System.in);
    /**
     * SocketChannel 用户发送和接受数据的信道
     */
    private SocketChannel socketChannel = null;

    /**
     * 运行标识。在线程里此标识为false的时候会退出线程
     * 该属性在ExitCommandListener里通过调用setFlag方法修改，用于通知线程用户要求退出的程序
     */
    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ClientDeamon(String address, int port) {
        try {
            //一旦连接上，socketChannel进入Connect标志位==连接就绪事件，表示客户与服务器的连接已经建立成功;然后进入写事件状态，随时可以写给server
            socketChannel = SocketChannel.open(new InetSocketAddress(address, port));
            boolean connected = socketChannel.isConnected();
            System.out.println("connected:"+connected);
            socketChannel.configureBlocking(false); // 非阻塞模式
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE); // 客户端直接注册读和写操作
            log.info("00.init nio client(socketChannel,selector) success! connect: {}:{}",address,port);
        } catch (IOException e) {
            log.error("00.init nio client(socketChannel,selector) error! connect: {}:{}",address,port);
            e.printStackTrace();
        }
    }

    public void chancelToWrite(ByteBuffer buffer) {
        try {
            socketChannel.write(buffer);
        } catch (IOException e) {
            log.error("chancelToWrite error!");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        log.info("01.begin start nio clien");
        while (this.flag) {
            //如果可以继续执行，则在循环体内循环执行监听选择操作
            int num;
            try {
                //得到处于可读或者可写状态的SocketChannel对象的个数
                // 客户端的select()并不阻塞线程,是因为客户端一启动就是SelectionKey.OP_WRITE状态
                num = this.selector.select();
                log.info("01.get the number:({}) of channel who is ready to Read or Write.",num);
            } catch (IOException e) {
                //如果出现异常，则此处应该加上日志打印，然后跳出循环,执行循环体下面的释放资源操作
                log.error("get the number:fail of channel who is ready to Read or Write.");
                break;
            }

            if (num > 0) {
                //如果有多个SocketChannel处于可读或者可写状态，则轮询注册在Selector上面的SelectionKey
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    //此步操作用于删除该SelectionKey的被选中状态

                    if (key.isReadable()) {
                        log.info("the channel who is ready to Read");
                        try {
                            ClientDealer.read((SocketChannel) key.channel());
                        } catch (IOException e) {
                            log.error("read data from channel error");
                            e.printStackTrace();
                        }
                    } else if (key.isWritable()) {
                        log.info("the channel who is ready toWrite.");
                        System.out.print("请输入登录名：");
                        String msg = scan.nextLine();
                        ClientDealer.write((SocketChannel) key.channel(),msg);
                    }
                    keys.remove();
                }
            }

            /*取消关注，多用在多线程的时候
             * key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
             *
             * 增加关注
             * key.interestOps(key.interestOps() | SelectionKey.OP_READ);
             * */

//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }

        if (this.socketChannel != null && this.socketChannel.isOpen()) {
            //关闭SocketChannel
            try {
                this.socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.selector != null && this.selector.isOpen()) {
            //关闭Selector选择器
            try {
                this.selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}