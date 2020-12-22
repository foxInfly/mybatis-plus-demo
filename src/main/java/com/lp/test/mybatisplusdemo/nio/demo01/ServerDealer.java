package com.lp.test.mybatisplusdemo.nio.demo01;

import com.lp.test.mybatisplusdemo.nio.demo01.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * @author lp
 * @description Dealer
 * @since 2020/12/22 15:14
 **/
@Slf4j
public class ServerDealer {

    /**
     * accept socket channel
     */
    public static SocketChannel accept(Selector selector, ServerSocketChannel serverSocketChannel) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);//把客户端的socketChannel也注册到服务端的selcter，一起管理

            //write content from server to client
            socketChannel.write(Util.charset.encode("Please input your name:"));//当socketChannel被写入数据后，socketChannel就变成了Read事件状态//标志位
            log.info("04.get the socketChannel from who serverSocketChannel and make socketChannel is ready to read,\n also wite conntent to socketChannel:{}","Please input your name.");

        } catch (Exception e) {
            System.out.println("Error while configure socket channel :" + e);
            log.error("04.get the socketChannel and make socketChannel is ready to read, also wite conntent to socketChannel error");
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return socketChannel;
    }

    /**
     * read
     */
    public static void read(Selector selector, SelectionKey selectionkey, List<SocketChannel> clientChannels) throws IOException {
        SocketChannel socketClientChannel = (SocketChannel) selectionkey.channel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024);
        StringBuilder content = new StringBuilder();
        int num = 0;
        try {
            // 将客户端发上来的消息读到buffer,循环将通道中数据读入buffer
            while (socketClientChannel.read(buffer) > 0) {
                buffer.flip();// 切换成读
                content.append(Util.charset.decode(buffer));
            }

            System.out.println("num:" + num);
            System.out.println("Server is listening from client :"
                    + socketClientChannel.getRemoteAddress() + " data receive is: "
                    + content);
        } catch (IOException e) {
            /* 如果出现异常，则需要关闭连接。故把num设置为-1，用下面的关闭逻辑来关闭channel
             */
            num = -1;
        }

        if (num >= 0) {
            if (content.length() > 0) {
                String[] arrayContent = content.toString().split(Util.USER_CONTENT_SPILIT);
                //注册用户(只有用户名)
                if (arrayContent.length == 1) {
                    String name = arrayContent[0];
                    if (Util.users.contains(name)) {
                        socketClientChannel.write(Util.charset.encode(Util.USER_EXIST));
                    } else {
                        Util.users.add(name);
                        int onlineNum = clientChannels.size();
                        String message = "welcome " + name + " to chat room! Online numbers:" + onlineNum;
                        log.info("04_sys.package message:{}.",message);
                        BroadCast2(clientChannels, null, message);
                    }
                }else if(arrayContent.length > 1) {// 注册完了（用户名+#@#+消息），发送消息
                    String name = arrayContent[0];
                    String message = content.substring(name.length() + Util.USER_CONTENT_SPILIT.length());
                    message = name + " say: " + message;
                    log.info("04_other.package message:{}.",message);
                    if (Util.users.contains(name)) {
                        // 不回发给发送此内容的客户端
                        BroadCast2(clientChannels, socketClientChannel, message);
                    }
                }

                // /**
                // * 把读到的数据原封不动的下发给客户端
                // */
                // int length = clientChannels.size();
                // for (int index = 0; index < length; index++) {
                // // 循环所有的客户端连接，下发数据
                // buffer.flip();
                // try {
                // // 将buffer里的数据再下发给客户端的通道
                // clientChannels.get(index).write(buffer);
                // } catch (IOException e) {
                // e.printStackTrace();
                // }
                // }
            }
        } else {
            /*如果未读到数据，对方关闭了SocketChannel 所以服务器这边也要关闭
             */
            try {
                socketClientChannel.close();
                int length = clientChannels.size();
                for (int index = 0; index < length; index++) {
                    if (clientChannels.get(index).equals(socketClientChannel)) {
                        // 移除当前接受的通道
                        clientChannels.remove(index);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // TODO 要是能检测下线，就不用这么统计了
    public static int OnlineNum(Selector selector) {
        int res = 0;
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();

            if (targetchannel instanceof SocketChannel) {
                res++;
            }
        }
        return res;
    }

    public void BroadCast(Selector selector, SocketChannel except,
                          String content) throws IOException {
        // 广播数据到所有的SocketChannel中
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            // 如果except不为空，不回发给发送此内容的客户端
            if (targetchannel instanceof SocketChannel
                    && targetchannel != except) {
                SocketChannel dest = (SocketChannel) targetchannel;
                dest.write(Util.charset.encode(content));
            }
        }
    }

    public static void BroadCast2(List<SocketChannel> socketChannels, SocketChannel except, String content) throws IOException {
        for (SocketChannel socketChannel : socketChannels) {
            if (!socketChannel.equals(except)) {
                // 除了自己，其它通道都通知
                socketChannel.write(Util.charset.encode(content));
            }
        }
    }

}