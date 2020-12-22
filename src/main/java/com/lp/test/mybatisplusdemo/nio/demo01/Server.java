package com.lp.test.mybatisplusdemo.nio.demo01;

public class Server {

    public static void main(String[] args) {
        ServerDeamon deamon = new ServerDeamon(9999);//监听9999的端口
        new Thread(deamon).start();
    }

}