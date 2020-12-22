package com.lp.test.mybatisplusdemo.nio.demo01.util;

import java.nio.charset.Charset;
import java.util.HashSet;

public class Util {

    public static Charset charset = Charset.forName("UTF-8");
    
    // 相当于自定义协议格式，与客户端协商好
    public static String USER_CONTENT_SPILIT = "#@#";
    
    // 用来记录在线人数，以及昵称
    public static HashSet<String> users = new HashSet<>();

    //exception
    public static String USER_EXIST = "system message: user name exist, please change a name";
}