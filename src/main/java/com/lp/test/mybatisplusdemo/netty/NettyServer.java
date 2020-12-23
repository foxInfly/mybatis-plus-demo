package com.lp.test.mybatisplusdemo.netty;

import com.lp.test.mybatisplusdemo.netty.protocol.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        //两个NioEventLoopGroup对象，可以看作两个线程组。
        //      bossGroup的作用是监听客户端请求。===>serverSocketChannel
        //      workerGroup的作用是处理每条连接的数据读写。===>socketChannel
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //ServerBootstrap是一个引导类，其对象的作用是引导服务器的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(bossGroup, workerGroup)//配置上面两个线程组的角色，也就是谁去监听谁去处理读写。上面只是创建了两个线程组，并没有实际使用。
                .channel(NioServerSocketChannel.class)//.channel是配置服务端的IO模型，上面代码配置的是NIO模型。也可以配置为BIO，如OioServerSocketChannel.class。
                //.childHandler用于配置每条连接的数据读写和业务逻辑等。上面代码用的是匿名内部类，并没有什么内容。
                // 实际使用中为了规范起见，一般会再写一个单独的类也就是初始化器，在里面写上需要的操作。就如Netty实战那篇中的代码一样。
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new FirstServerHandler());
                        ch.pipeline().addLast(new ServerHandler());
                    }
                })
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    protected void initChannel(NioServerSocketChannel ch) {
                        System.out.println("服务端启动中");
                    }
                })//.handler（）方法：上面的cildHandler是处理连接的读写逻辑，这个是用于指定服务端启动中的逻辑;
                //.option（）方法：给服务端的channel设置属性;
                // 下面得代码表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                //开启TCP底层心跳机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //开启Nagle算法，如果要求高实时性，有数据发送时就马上发送，就关闭；如果需要减少发送次数减少网络交互，就开启。
                .childOption(ChannelOption.TCP_NODELAY, true)
                .bind(8000);

    }
}