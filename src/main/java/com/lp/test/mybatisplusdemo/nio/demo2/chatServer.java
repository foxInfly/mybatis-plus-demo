package com.lp.test.mybatisplusdemo.nio.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class chatServer {
    private int port;
    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);//调整缓冲区大小为1024字节
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private static final String USER_NAME_TAG = "$%%^&*()!@#$^%#@*()*";
    private HashSet<String> users = new HashSet<>();//所有的用户名
    private HashMap<String, String> Users = new HashMap<>();
    private String user_msg;

    public chatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new chatServer(8081).start();
    }

    public void start() {
        ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);  //服务器配置为非阻塞 即异步IO
            serverSocketChannel.socket().bind(new InetSocketAddress(port));   //绑定本地端口
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);   //ssc注册到selector准备连接，关心1：serverSocketChannel的ACCEPT事件就绪状态
            System.out.println("ChatServer started ......,listen port:" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                int events = selector.select();
                if (events > 0) {
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                    while (selectionKeys.hasNext()) {
                        SelectionKey key = selectionKeys.next();
                        selectionKeys.remove();  //移除当前的key
                        if (key.isValid()) {
                            if (key.isAcceptable()) accept(key);

                            if (key.isReadable()) read(key);
                        }
                    }
                }

                if (events < 0 || user_msg == "88888888888") return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);//关心2：clientChannel的READ事件就绪状态
        System.out.println("a new client connected " + clientChannel.getLocalAddress());
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        this.readBuffer.clear();//清除缓冲区，准备接受新数据

        int numRead;
        try {
            numRead = socketChannel.read(this.readBuffer);//总长度
        } catch (IOException e) {    // 客户端断开连接，这里会报错提示远程主机强迫关闭了一个现有的连接。
            offlineUser(key);
            key.cancel();
            socketChannel.close();
            return;
        }
        user_msg = new String(readBuffer.array(), 0, numRead);
        for (String s : users) System.out.println("在线用户: " + s);

        if (user_msg.contains(USER_NAME_TAG)) {   // 用户第一次登陆， 输入登录名
            String user_name = user_msg.replace(USER_NAME_TAG, "");
            user_msg = "欢迎: " + user_name + " 登录聊天室";
            System.out.println(user_msg);
            users.add(user_name);   // 用户名
            brodcast(socketChannel, user_msg);
        } else if (user_msg.equals("1")) {       // 显示在线人数
            user_msg = onlineUser();
            write(socketChannel, user_msg);
        } else {                                // 群聊
            String MspPre = "";
            boolean flag = false;
            String[] s1 = user_msg.split("###");
            for (String s : users) {
                if (s1.length == 2) if(s.equals(s1[0]))flag=true;
            }

            if (flag) {
                MspPre = s1[0] + ": ";
                brodcast(socketChannel, MspPre + s1[1]);
            }else {
                brodcast(socketChannel, MspPre + "不是注册用户");
            }

        }
    }

    private void write(SocketChannel channel, String content) throws IOException, ClosedChannelException {
        sendBuffer.clear();
        sendBuffer.put(content.getBytes());
        sendBuffer.flip();
        channel.write(sendBuffer);
        //注册读操作 下一次进行读
        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 用户下线，同时通知线上用户哪些用户下线了。
     */
    public void offlineUser(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        for (String user : users) {
            String[] s1 = user.split("===");
            if (s1.length == 2) {
                String user_name = s1[1];
                if (user.contains(socketChannel.getRemoteAddress().toString())) {
                    users.remove(user);
                    String message = "用户: " + user_name + " 下线了, 拜拜";
                    brodcast(socketChannel, message);
                }
            }
        }
    }

    /**
     * 在线用户
     */
    private String onlineUser() {
        String online_users = "在线用户:\n";
        String user = "";
        for (String s : users) {
            String[] s1 = s.split("===");
            if (s1.length == 2) {
                user = s1[1];
            } else {
                continue;
            }
            online_users += "\t" + user + "\n";
        }
        System.out.println(" " + online_users);
        return online_users;
    }

    /**
     * 群聊
     */
    public void brodcast(SocketChannel except, String content) throws IOException {
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            if (targetchannel instanceof SocketChannel/* && targetchannel != except*/) {
                SocketChannel channel = (SocketChannel) key.channel();
                write(channel, content);
            }
        }
    }
}