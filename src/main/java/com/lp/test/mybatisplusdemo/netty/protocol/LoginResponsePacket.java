package com.lp.test.mybatisplusdemo.netty.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import static com.lp.test.mybatisplusdemo.netty.protocol.Command.LOGIN_RESPONSE;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LoginResponsePacket extends Packet {
    //是否登录成功
    private boolean success;
    //如果失败，返回的信息
    private String reason;
    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}