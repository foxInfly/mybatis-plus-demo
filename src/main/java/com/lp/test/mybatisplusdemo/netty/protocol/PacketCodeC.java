package com.lp.test.mybatisplusdemo.netty.protocol;

import com.lp.test.mybatisplusdemo.netty.protocol.serialize.JSONSerializer;
import com.lp.test.mybatisplusdemo.netty.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.lp.test.mybatisplusdemo.netty.protocol.Command.LOGIN_REQUEST;
import static com.lp.test.mybatisplusdemo.netty.protocol.Command.LOGIN_RESPONSE;

/**
 * @description 编解码的方法
 * @author lp
 * @since 2020/12/23 15:07
 **/
public class PacketCodeC {
    //自定义一个魔数
    private static final int MAGIC_NUMBER = 0x12345678;//可以理解为识别这条二进制数据类型的字段

    //创建一个静态实例供外部调用
    public static final PacketCodeC INSTANCE=new PacketCodeC();

    //创建两个map，一个存储数据类型，如：是登录数据还是普通消息等。第二个是存储序列化类型。
    //这样在解码时就可以把数据转换为对应的类型。如：这个byte数组是LOGIN_REQUEST类型，就把它转换成LoginRequestPacket类型的Java对象
    private  final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private  final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        //初始化map并添加数据类型和序列化类型，如果有其他数据类型，记得在这里添加
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }
    //编码
    public ByteBuf encode(ByteBufAllocator bufAllocator, Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = bufAllocator.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // 3. 实际编码过程，把通信协议几个部分，一一编码
        byteBuf.writeInt(MAGIC_NUMBER);//1.魔数
        byteBuf.writeByte(packet.getVersion());//2.版本号
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());//3.序列化算法
        byteBuf.writeByte(packet.getCommand());//4.指令
        byteBuf.writeInt(bytes.length);//5.数据长度
        byteBuf.writeBytes(bytes);//6.数据
        return byteBuf;
    }
    //解码
    public Packet decode(ByteBuf byteBuf) {

        byteBuf.skipBytes(4); // 跳过魔数

        byteBuf.skipBytes(1);// 跳过版本号

        byte serializeAlgorithm = byteBuf.readByte();// 序列化算法标识
        byte command = byteBuf.readByte(); // 指令

        int length = byteBuf.readInt();// 数据包长度
        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);// 数据包内容

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    //获取序列化类型
    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    //获取数据类型
    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}