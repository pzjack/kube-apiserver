package org.pz.netty.c9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getHeader());
        out.writeByte(msg.getVersion());
        out.writeByte(msg.getProtocol());
        out.writeInt(msg.getDeviceNo());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
