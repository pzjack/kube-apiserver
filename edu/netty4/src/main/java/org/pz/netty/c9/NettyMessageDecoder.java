package org.pz.netty.c9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public class NettyMessageDecoder extends ByteToMessageDecoder {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress sct = (InetSocketAddress)ctx.channel().remoteAddress();
        String ip = sct.getAddress().getHostAddress();
        System.out.println("Decode:" + ip);
        if ("127.0.0.1".equals(ip)) {
//            ctx.close();
//            return;
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() >= Constance.BASE_LENGTH) {
            if (in.readableBytes() > Constance.MAX_LENGTH) {
                in.skipBytes(in.readableBytes());
            }

            // 记录包头开始的index
            int beginReader;
            while (true) {
                // 获取包头开始的index
                beginReader = in.readerIndex();
                // 标记包头开始的index
                in.markReaderIndex();
                // 读到协议的开始标志，结束while循环
                if (in.readInt() == Constance.MAGIC_NUM) {
                    break;
                }

                // 未读到包头，跳过一个字节
                // 每次跳过一个字节后，再去读取包头信息的开始标记
                in.resetReaderIndex();
                in.readByte();
                if (in.readableBytes() < Constance.BASE_LENGTH) {
                    return;
                }
            }

            // 代码到这里，说明已经读到了报文标志
            byte version = in.readByte();
            byte protocol = in.readByte();
            int deviceNo = in.readInt();
            // 消息长度
            int length = in.readInt();
            // 判断请求数据包是否到齐
            if (in.readableBytes() < length) { // 数据不齐，回退读指针
                // 还原读指针
                in.readerIndex(beginReader);
                return;
            }

            // 至此，读到一条完整报文
            byte[] content = new byte[length];
            in.readBytes(content);

            NettyMessage msg = new NettyMessage();
            msg.setVersion(version);
            msg.setProtocol(protocol);
            msg.setLength(length);
            msg.setDeviceNo(deviceNo);
            msg.setContent(content);
            list.add(msg);
        }
    }
}