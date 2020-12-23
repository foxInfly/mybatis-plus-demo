package com.lp.test.mybatisplusdemo.netty.protocol;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class Packet {
    //协议版本
    private Byte version = 1;
    //获取数据类型
    public abstract Byte getCommand();
}